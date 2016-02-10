var module = angular.module('materiaModule');


module.controller('MateriaCtrl_Index', ['$scope', '$cacheFactory', 'MateriaSrv', '$state', '$stateParams', 'MessageSrv', 'CommonsSrv', 'nivelesResponse', 'PaginationService', '$mdDialog',
    function ($scope, $cacheFactory, service, $state, $stateParams, message, commons, nivelesResponse, pagination, $mdDialog) {

        $scope.filter = {};
        $scope.result = [];
        $scope.niveles = nivelesResponse.data;
        $scope.filterChips = [];

        var cache = $cacheFactory.get('materiaIndexCache') || $cacheFactory('materiaIndexCache');

        $scope.aux = {
            showDadosBaja: false,
            mostrarFiltros: true
        }

        pagination.config('api/materia/find');

        $scope.paginationData = pagination.paginationData;

        function updateFilterChips() {
            $scope.filterChips = [];
            $scope.filterChips.push(newFilterChip('dadosBaja', 'Dados de baja', $scope.filter.dadosBaja, $scope.filter.dadosBaja ? 'SI' : 'NO'));
            if ($scope.filter.nivel) {
                $scope.filterChips.push(newFilterChip('nivel', 'Nivel', $scope.filter.nivel));
            }
            if ($scope.filter.nombre) {
                $scope.filterChips.push(newFilterChip('nombre', 'Nombre', $scope.filter.nombre));
            }
        }

        $scope.$watchCollection('filterChips', function(newCol, oldCol) {
            if (newCol.length < oldCol.length) {
                $scope.filter = {};
                angular.forEach(newCol, function(filterChip) {
                    $scope.filter[filterChip.origin] = filterChip.value;
                });
                executeQuery();
            }
        });

        function newFilterChip(origin, name, value, displayValue) {
            var filterChip = {
                origin: origin,
                name: name,
                value: value,
                displayValue: displayValue ? displayValue : value
            }
            return filterChip;
        }

        function executeQuery(pageNumber) {
            pagination.paginate($scope.filter, pageNumber).then(function (data) {
                $scope.result = data;
                $scope.aux.showDadosBaja = $scope.filter.dadosBaja;
                $scope.paginationData = pagination.getPaginationData();
                updateFilterChips();
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

        $scope.new = function () {
            $state.go('^.create');
        }

        $scope.openRestoreDialog = function (ev, materiaId, materiaNombre) {
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
                            message.showMessage(materiaNombre + ' dada de alta.');
                            executeQuery($scope.paginationData.pageNumber);
                        })
                        .error(function (error) {
                            message.showMessage('Error dando de alta ' + materiaNombre);
                            executeQuery($scope.paginationData.pageNumber);
                        })
                },
                function () {
                    //Failure
                    $scope.status = 'You cancelled the dialog.';
                });
        };

        $scope.openDeleteDialog = function (ev, materiaId, nombreMateria) {
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
                    $scope.confirmar = function (form) {
                        if (form.$valid) {
                            $mdDialog.hide($scope.motivoBaja);
                        }
                    };
                }
            })
                .then(function (motivoBaja) {
                    //Success
                    service.remove(materiaId, motivoBaja)
                        .success(function (response) {
                            message.showMessage(nombreMateria + ' dada de baja.');
                            executeQuery();
                        })
                        .error(function (error) {
                            message.showMessage('Error dando de baja ' + nombreMateria);
                        })
                },
                function () {
                    //Failure
                    $scope.status = 'You cancelled the dialog.';
                });
        };

        $scope.mostrarAcciones = function (item) {
            item.showAction = true;
        }

        $scope.ocultarAcciones = function (item) {
            item.showAction = false;
        }

        $scope.clickIcon = 'expand_less';
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

        $scope.cleanFilters = function (filterToClean) {
            $scope.filter = {};
            executeQuery();
        }

        function cacheData() {
            var data = {
                filter: $scope.filter,
                result: $scope.result,
                aux: $scope.aux,
                paginationData: $scope.paginationData
            }
            cache.put('data', data);
        }

        function getCachedData() {
            var data = cache.get('data');

            $scope.filter = data.filter;
            $scope.result = data.result;
            $scope.aux = data.aux;
            $scope.paginationData = data.paginationData;
            updateFilterChips();
        }

        $scope.$on('$stateChangeStart',
            function (event, toState, toParams, fromState, fromParams) {
                if (toState.name.startsWith('materia')) {
                    cacheData();
                } else {
                    cache.destroy();
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
                } else {
                    executeQuery();
                }
            })

    }]);
