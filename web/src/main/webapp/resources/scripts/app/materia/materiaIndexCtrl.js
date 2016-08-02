var module = angular.module('materiaModule');


module.controller('MateriaCtrl_Index', ['$scope', '$cacheFactory', 'MateriaSrv', '$state', '$stateParams', 'MessageSrv', 'CommonsSrv', 'nivelesResponse', 'PaginationService', '$mdDialog', 'DeleteRestoreSrv',
    function ($scope, $cacheFactory, service, $state, $stateParams, message, commons, nivelesResponse, pagination, $mdDialog, deleteRestoreService) {

        $scope.filter = {};
        $scope.result = [];
        $scope.niveles = nivelesResponse.data;
        $scope.filterChips = [];

        var cache = $cacheFactory.get('materiaIndexCache') || $cacheFactory('materiaIndexCache');

        $scope.aux = {
            showDadosBaja: false,
            mostrarFiltros: true
        }

        deleteRestoreService.config('api/materia/remove', 'api/materia/restore', 'Materia', executeQuery);

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

        $scope.openDeleteDialog = function(event, id, nombre) {
            deleteRestoreService.delete(event, id, nombre, $scope.paginationData.pageNumber, $scope.filter.dadosBaja, $scope.result.length);
        }

        $scope.openRestoreDialog = function(event, id, nombre) {
            deleteRestoreService.restore(event, id, nombre, $scope.paginationData.pageNumber);
        }

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
