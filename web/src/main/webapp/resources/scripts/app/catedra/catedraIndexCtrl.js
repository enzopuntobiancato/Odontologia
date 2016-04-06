'use strict';
var module = angular.module('catedraModule');

module.controller('CatedraCtrl_Index', ['$scope', '$state', '$cacheFactory', 'MessageSrv', 'CatedraSrv', 'PaginationService', 'materiasResponse', '$mdDialog', '$filter',
    function ($scope, $state, $cacheFactory, message, service, pagination, materiasResponse, $mdDialog, $filter) {

        $scope.filter = {};
        $scope.result = [];
        $scope.filterChips = [];

        var cache = $cacheFactory.get('catedraIndexCache') || $cacheFactory('catedraIndexCache');

        $scope.aux = {
            showDadosBaja: false,
            mostrarFiltros: true
        }

        $scope.data = {
            materias: materiasResponse.data
        }

        pagination.config('api/catedra/find');

        $scope.paginationData = pagination.paginationData;

        function updateFilterChips() {
            $scope.filterChips = [];
            $scope.filterChips.push(newFilterChip('dadosBaja', 'Dados de baja', $scope.filter.dadosBaja, $scope.filter.dadosBaja ? 'SI' : 'NO'));
            if ($scope.filter.materiaId) {
                $scope.filterChips.push(newFilterChip('materiaId', 'Materia', $scope.filter.materiaId,
                    $filter('filter')($scope.data.materias, function (materia) {
                        return $scope.filter.materiaId === materia.id;
                    })[0].nombre
                ));
            }
            if ($scope.filter.denominacion) {
                $scope.filterChips.push(newFilterChip('denominacion', 'Nombre', $scope.filter.denominacion));
            }
        }

        $scope.$watchCollection('filterChips', function (newCol, oldCol) {
            if (newCol.length < oldCol.length) {
                $scope.filter = {};
                angular.forEach(newCol, function (filterChip) {
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

        $scope.openDeleteDialog = function (ev, catedraId, catedraNombre) {
            $mdDialog.show({
                templateUrl: 'views/catedra/catedraDelete.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: true,
                locals: {id: catedraId},
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
                    service.remove(catedraId, motivoBaja)
                        .success(function () {
                            message.showMessage(catedraNombre + " dada de baja.");
                            var execQuerySamePage = $scope.filter.dadosBaja || $scope.result.length > 1;
                            executeQuery(execQuerySamePage ? $scope.paginationData.pageNumber : 0);
                        })
                        .error(function () {
                            message.showMessage("Error dando de baja " + catedraNombre)
                        })
                },
                function () {
                    $scope.status = 'You cancelled the dialog.';
                });
        };


        $scope.openRestoreDialog = function (ev, catedraId, catedraNombre) {
            $mdDialog.show({
                templateUrl: 'views/trabajoPractico/trabajoPracticoRestore.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: true,
                locals: {id: catedraId},
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
                    service.restore(catedraId)
                        .success(function () {
                            message.showMessage(catedraNombre + " dada de alta.");
                            executeQuery($scope.paginationData.pageNumber);
                        })
                        .error(function () {
                            message.showMessage("Error dando de alta " + catedraNombre);
                        })
                },
                function () {
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
                } else {
                    executeQuery();
                }

            })
    }]);

