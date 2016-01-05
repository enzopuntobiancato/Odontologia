'use strict';
var module = angular.module('catedraModule');

module.controller('CatedraCtrl_Index', ['$scope', '$state', '$cacheFactory', 'MessageSrv', 'CatedraSrv', 'PaginationService',
    'materiasResponse','$mdDialog',
    function ($scope, $state, $cacheFactory, message, service, pagination,materiasResponse,$mdDialog) {

        $scope.filter = {};
        $scope.result = [];

        var cache = $cacheFactory.get('catedraIndexCache') || $cacheFactory('catedraIndexCache');

        $scope.aux = {
            showDadosBaja: false
        }

        pagination.config('api/catedra/find');

        executeQuery();

        $scope.paginationData = pagination.paginationData;

        function executeQuery(pageNumber) {
            pagination.paginate($scope.filter, pageNumber).then(function (data) {
                $scope.result = data;
                $scope.aux.showDadosBaja = $scope.filter.dadosBaja;
                $scope.paginationData = pagination.getPaginationData();
            }, function () {
            });
        }

        $scope.data= {
            materias: materiasResponse.data
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
                        // Easily hides most recent dialog shown...
                        // no specific instance reference is needed.
                        $mdDialog.cancel();
                    };
                    $scope.confirmar = function () {
                        // Easily hides most recent dialog shown...
                        // no specific instance reference is needed.
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

