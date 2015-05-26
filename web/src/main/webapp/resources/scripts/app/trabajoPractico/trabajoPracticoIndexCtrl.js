/**
 * Created with IntelliJ IDEA.
 * User: enzo
 * Date: 12/05/15
 * Time: 20:49
 */


var module = angular.module('trabajoPracticoModule');


module.controller('TrabajoPracticoCtrl_Index', ['$scope', '$cacheFactory', 'TrabajoPracticoSrv', '$state', '$stateParams', 'NotificationSrv', 'gruposPracticaResponse', 'practicasResponse', 'PaginationService', '$filter',
    function ($scope, $cacheFactory, service, $state, $stateParams, notification, gruposPracticaResponse, practicasResponse, pagination, $filter) {

        $scope.filter = {};
        $scope.result = [];

        $scope.data = {
            gruposPractica: gruposPracticaResponse.data,
            practicas: practicasResponse.data
        }

        $scope.select = {
            practicas: $scope.data.practicas
        }

        var cache = $cacheFactory.get('trabajoPracticoIndexCache') || $cacheFactory('trabajoPracticoIndexCache');

        $scope.aux = {
            showDadosBaja: false
        }

        $scope.$watch('filter.grupoPracticaId', function (newValue, oldValue) {
            if (!angular.equals(newValue, oldValue)) {
                delete $scope.filter.practicaId;
                filterPracticas();
            }

            function filterPracticas() {

                if (!$scope.filter.grupoPracticaId || !angular.isDefined($scope.filter.grupoPracticaId)) {
                    $scope.select.practicas = $scope.data.practicas;
                } else {
                    $scope.select.practicas = $filter('filter')($scope.data.practicas, function (value) {
                        return angular.equals(value.grupo.id, $scope.filter.grupoPracticaId);
                    })
                }
            }
        })

        pagination.config('api/trabajoPractico/find');

        $scope.paginationData = pagination.paginationData;

        function executeQuery(pageNumber) {
//            notification.showWidget();
            pagination.paginate($scope.filter, pageNumber).then(function (data) {
//                notification.hideWidget();
                $scope.result = data;
                $scope.aux.showDadosBaja = $scope.filter.dadosBaja;
                $scope.paginationData = pagination.getPaginationData();
            }, function () {
//                notification.hideWidget();
            });
        }

        $scope.consultar = function () {
            executeQuery();
        }

        $scope.nextPage = function () {
            if ($scope.paginationData.morePages) {
                executeQuery(++$scope.paginationData.pageNumber);
            }
        }
        $scope.previousPage = function () {
            if (!$scope.paginationData.firstPage) {
                executeQuery(--$scope.paginationData.pageNumber);
            }
        }

        $scope.keyboardOk = function (event) {
            if (event.which == 13) {
                executeQuery();
            }
        }

        $scope.new = function () {
            $state.go('^.create');
        }

        $scope.darDeBaja = function (trabajoPracticoId) {
            notification.requestReason().then(function (motivo) {
                if (motivo != null) {
//                    notification.showWidget();
                    service.remove(trabajoPracticoId, motivo).success(function (response) {
//                        notification.hideWidget();
                        notification.goodAndOnEscape("Trabajo práctico dado de baja correctamente.", function () {
                            executeQuery($scope.paginationData.pageNumber);
                        }, function () {
                            executeQuery($scope.paginationData.pageNumber);
                        })
                    })
                        .error(function (response) {
//                            notification.hideWidget();
                            notification.badArray(response, function () {
                            });
                        })
                }
            });
        }

        $scope.darDeAlta = function (id) {
            notification.requestConfirmation("¿Está seguro?", function () {
                altaConfirmada(id)
            });

            function altaConfirmada(id) {
//                notification.showWidget();
                service.restore(id)
                    .success(function () {
//                        notification.hideWidget();
                        notification.goodAndOnEscape("Trabajo práctico dado de alta correctamente.", function () {
                            executeQuery($scope.paginationData.pageNumber);
                        }, function () {
                            executeQuery($scope.paginationData.pageNumber);
                        })
                    })
                    .error(function () {
//                        notification.hideWidget();
                    })
            }
        }

        $scope.edit = function (id) {
            $state.go('^.edit', {id: id});

        }

        $scope.viewDetail = function (id) {
            $state.go('^.view', {id: id});

        }

        $scope.cleanFilters = function () {
            $scope.filter = {};
        }

        function cacheData() {
            var data = {
                filter: $scope.filter,
                result: $scope.result,
                aux: $scope.aux
            }
            cache.put('data', data);
        }

        function getCachedData() {
            var data = cache.get('data');

            $scope.filter = data.filter;
            $scope.result = data.result;
            $scope.aux = data.aux;
        }

        $scope.$on('$stateChangeStart',
            function (event, toState, toParams, fromState, fromParams) {
                if (toState.name.startsWith('trabajoPractico')) {
                    cacheData();
                } else {
                    cache.put('data', null);
                }

            });

        $scope.$on('$stateChangeSuccess',
            function (event, toState, toParams, fromState, fromParams) {
                if (fromState.name.startsWith('trabajoPractico')) {
                    if (toParams.execQuery) {
                        executeQuery();
                    } else if (toParams.execQuerySamePage) {
                        getCachedData();
                        executeQuery($scope.paginationData.pageNumber)
                    } else {
                        getCachedData();
                    }

                }

            })

    }]);
