var module = angular.module('trabajoPracticoModule');


module.controller('TrabajoPracticoCtrl_Index', ['$scope', '$cacheFactory', 'TrabajoPracticoSrv', '$state', '$stateParams', 'MessageSrv', 'gruposPracticaResponse', 'practicasResponse', 'PaginationService',
    '$filter', '$mdDialog', 'DeleteRestoreSrv',
    function ($scope, $cacheFactory, service, $state, $stateParams, message, gruposPracticaResponse, practicasResponse, pagination, $filter, $mdDialog, deleteRestoreService) {

        $scope.filter = {};
        $scope.result = [];
        $scope.filterChips = [];

        var handleError = $scope.$parent.handleError;
        $scope.validationErrorFromServer = $scope.$parent.validationErrorFromServer;

        $scope.data = {
            gruposPractica: gruposPracticaResponse.data,
            practicas: practicasResponse.data
        }

        $scope.select = {
            practicas: $scope.data.practicas
        }

        var cache = $cacheFactory.get('trabajoPracticoIndexCache') || $cacheFactory('trabajoPracticoIndexCache');

        $scope.aux = {
            showDadosBaja: false,
            mostrarFiltros: true
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

        function updateFilterChips() {
            $scope.filterChips = [];
            $scope.filterChips.push(newFilterChip('dadosBaja', 'Dados de baja', $scope.filter.dadosBaja, $scope.filter.dadosBaja ? 'SI' : 'NO'));
            if ($scope.filter.practicaId) {
                $scope.filterChips.push(newFilterChip('practicaId', 'Práctica', findPractica($scope.filter.practicaId)));
            }
            if ($scope.filter.nombre) {
                $scope.filterChips.push(newFilterChip('nombre', 'Nombre', $scope.filter.nombre));
            }
        }


        function findPractica(practicaId) {
            var nombre;
            for (var i = 0; i < $scope.data.practicas.length; i++) {
                if ($scope.data.practicas[i].id == practicaId) {
                    nombre = $scope.data.practicas[i].denominacion;
                    break;
                }
            }
            return nombre;
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

        pagination.config('api/trabajoPractico/find');

        $scope.paginationData = pagination.paginationData;
//        executeQuery();
        function executeQuery(pageNumber) {
            $scope.validationErrorFromServer.error = false;
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

        $scope.keyboardOk = function (event) {
            if (event.which == 13) {
                executeQuery();
            }
        }

        $scope.new = function () {
            $state.go('^.create');
        }

        deleteRestoreService.config('api/trabajoPractico/remove', 'api/trabajoPractico/restore', 'Trabajo práctico', executeQuery, handleError);

        $scope.openDeleteDialog = function (event, id, nombre) {
            deleteRestoreService.delete(event, id, nombre, $scope.paginationData.pageNumber, $scope.filter.dadosBaja, $scope.result.length);
        }

        $scope.openRestoreDialog = function (event, id, nombre) {
            deleteRestoreService.restore(event, id, nombre, $scope.paginationData.pageNumber);
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
        }

        $scope.mostrarAcciones = function (item) {
            item.showAction = true;
        }

        $scope.ocultarAcciones = function (item) {
            item.showAction = false;
        }

        $scope.mostrarFiltros = false;

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
                if (toState.name.startsWith('trabajoPractico')) {
                    cacheData();
                } else {
                    cache.destroy();
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
                } else {
                    executeQuery();
                }
            })
    }]);
