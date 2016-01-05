/**
 * Created with IntelliJ IDEA.
 * User: enzo
 * Date: 12/05/15
 * Time: 20:49
 */


var module = angular.module('trabajoPracticoModule');


module.controller('TrabajoPracticoCtrl_Index', ['$scope', '$cacheFactory', 'TrabajoPracticoSrv', '$state', '$stateParams', 'MessageSrv', 'gruposPracticaResponse', 'practicasResponse', 'PaginationService',
    '$filter', '$mdDialog',
    function ($scope, $cacheFactory, service, $state, $stateParams, message, gruposPracticaResponse, practicasResponse, pagination, $filter, $mdDialog) {

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
        executeQuery();
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


        $scope.openDeleteDialog = function (ev, trabajoPracticoId) {
            $mdDialog.show({
                templateUrl: 'views/trabajoPractico/trabajoPracticoDelete.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: true,
                locals: {id: trabajoPracticoId},
                controller: function DialogController($scope, $mdDialog) {
                    $scope.motivoBaja;
                    $scope.cancelar = function () {
                        $mdDialog.cancel();
                    };
                    $scope.confirmar = function () {
                        $mdDialog.hide($scope.motivoBaja);
                    };
                }
            })
                .then(function (motivoBaja) {
                    //Success
                    service.remove(trabajoPracticoId, motivoBaja)
                        .success(function (response) {
                            message.showMessage("Se ha dado de baja.");
                            executeQuery();
                            Console.log(response);
                        })
                        .error(function (error) {
                            message.showMessage("Se ha registrado un error en la transacción.")
                            Console.log(error);
                        })
                },
                function () {
                    //Failure
                    $scope.status = 'You cancelled the dialog.';
                    Console.log(error);
                });
        };

        $scope.openRestoreDialog = function (ev, trabajoPracticoId) {
            $mdDialog.show({
                templateUrl: 'views/trabajoPractico/trabajoPracticoRestore.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: true,
                locals: {id: trabajoPracticoId},
                controller: function DialogController($scope, $mdDialog) {
                    $scope.cancelar = function () {
                        $mdDialog.cancel();
                    };
                    $scope.confirmar = function () {
                        $mdDialog.hide();
                    };
                }
            })
                .then(function () {
                    //Success
                    service.restore(trabajoPracticoId)
                        .success(function (response) {
                            message.showMessage("Se ha dado de alta.");
                            executeQuery($scope.paginationData.pageNumber);
                            Console.log(response);
                        })
                        .error(function (error) {
                            message.showMessage("Se ha registrado un error en la transacción.")
                            executeQuery($scope.paginationData.pageNumber);
                            Console.log(error);
                        })
                },
                function () {
                    //Failure
                    $scope.status = 'You cancelled the dialog.';
                    Console.log(error);
                });
        };


        $scope.edit = function (id) {
            $state.go('^.edit', {id: id});

        }

        $scope.viewDetail = function (id) {
            $state.go('^.view', {id: id});

        }

        $scope.cleanFilters = function () {
            $scope.filter = {};
            executeQuery();
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

        $scope.mostrarAcciones = function (item) {
            item.showAction = true;
        }

        $scope.ocultarAcciones = function (item) {
            item.showAction = false;
        }

        $scope.mostrarFiltros = false;

        $scope.clickIcon = 'expand_more';
        $scope.clickIconMorph = function () {
            if ($scope.clickIcon === 'expand_more') {
                $scope.clickIcon = 'expand_less';
            }
            else {
                $scope.clickIcon = 'expand_more';
            }
        };

        $scope.colorIcon = ['#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF'];
        $scope.colorMouseOver = function (icon) {
            $scope.colorIcon[icon] = '#E91E63';
        };

        $scope.colorMouseLeave = function (icon) {
            $scope.colorIcon[icon] = '#00B0FF';
        };


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
