var module = angular.module('materiaModule');


module.controller('MateriaCtrl_Index', ['$scope', '$cacheFactory', 'MateriaSrv', '$state', '$stateParams', 'MessageSrv', 'CommonsSrv', 'nivelesResponse', 'PaginationService', 'materiasResponse', '$mdDialog',
    function ($scope, $cacheFactory, service, $state, $stateParams, message, commons, nivelesResponse, pagination, materiasResponse, $mdDialog) {

    $scope.filter = {};
    $scope.result = [];
    $scope.niveles = commons.enumToJson(nivelesResponse.data);
    $scope.materiasTodas = commons.enumToJson(materiasResponse.data);
    $scope.nivel;

    var cache = $cacheFactory.get('materiaIndexCache') || $cacheFactory('materiaIndexCache');

    $scope.aux = {
        showDadosBaja: false
    }

    pagination.config('api/materia/find');

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



    $scope.openRestoreDialog = function (ev, materiaId) {
        $mdDialog.show({
            templateUrl: 'views/materia/materiaRestore.html',
            parent: angular.element(document.body),
            targetEvent: ev,
            clickOutsideToClose: true,
            locals: {id: materiaId},
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
                service.restore(materiaId)
                    .success(function (response) {
                        message.showMessage("Se ha dado de alta.");
                        executeQuery($scope.paginationData.pageNumber);
                        Console.log(response);
                    })
                    .error(function (error) {
                        message.showMessage("Se ha registrado un error al dar de alta la materia.")
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

    $scope.openDeleteDialog = function (ev, materiaId) {
        $mdDialog.show({
            templateUrl: 'views/materia/materiaDelete.html',
            parent: angular.element(document.body),
            targetEvent: ev,
            clickOutsideToClose: true,
            locals: {id: materiaId},
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
                service.remove(materiaId, motivoBaja)
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


    $scope.colorIcon = ['#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF'];
    $scope.colorMouseOver = function (icon) {
        $scope.colorIcon[icon] = '#E91E63';
    };

    $scope.colorMouseLeave = function (icon) {
        $scope.colorIcon[icon] = '#00B0FF';
    };

    $scope.edit = function (materiaId) {
        $state.go('^.edit', {id: materiaId});

    }

    $scope.viewDetail = function (materiaId) {
        $state.go('^.view', {id: materiaId});

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

    $scope.$on('$stateChangeStart',
        function (event, toState, toParams, fromState, fromParams) {
            if (toState.name.startsWith('materia')) {
                cacheData();
            } else {
                cache.put('data', null);
            }

        });

    $scope.$on('$stateChangeSuccess',
        function (event, toState, toParams, fromState, fromParams) {
            if (fromState.name.startsWith('materia')) {
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
