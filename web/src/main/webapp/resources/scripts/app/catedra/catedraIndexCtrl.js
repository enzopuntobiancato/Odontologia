'use strict';
var module = angular.module('catedraModule');

module.controller('CatedraCtrl_Index', ['$scope', '$state', '$cacheFactory', 'NotificationSrv', 'CatedraSrv', 'PaginationService',
    function ($scope, $state, $cacheFactory, notification, service, pagination) {

        $scope.filter = {};
        $scope.result = [];

        var cache = $cacheFactory.get('catedraIndexCache') || $cacheFactory('catedraIndexCache');

        $scope.aux = {
            showDadosBaja: false
        }

        pagination.config('api/catedra/find');

        $scope.paginationData = pagination.paginationData;

        function executeQuery(pageNumber) {
            pagination.paginate($scope.filter, pageNumber).then(function (data) {
                $scope.result = data;
                $scope.aux.showDadosBaja = $scope.filter.dadosBaja;
                $scope.paginationData = pagination.getPaginationData();
            }, function () {
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

        $scope.darDeBaja = function (id) {
            notification.requestReason().then(function (motivo) {
                if (motivo != null) {
                    service.remove(id, motivo).success(function (response) {
                        notification.goodAndOnEscape("Cátedra dada de baja correctamente.", function () {
                            executeQuery($scope.paginationData.pageNumber);
                        }, function () {
                            executeQuery($scope.paginationData.pageNumber);
                        })
                    })
                        .error(function (response) {
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
                service.restore(id)
                    .success(function () {
                        notification.goodAndOnEscape("Cátedra dada de alta correctamente.", function () {
                            executeQuery($scope.paginationData.pageNumber);
                        }, function () {
                            executeQuery($scope.paginationData.pageNumber);
                        })
                    })
                    .error(function () {
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
                if (toState.name.startsWith('catedra')) {
                    cacheData();
                } else {
                    cache.put('data', null);
                }

            });

        $scope.$on('$stateChangeSuccess',
            function (event, toState, toParams, fromState, fromParams) {
                if (fromState.name.startsWith('catedra')) {
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

