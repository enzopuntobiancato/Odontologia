var module = angular.module('historiaClinicaModule');

module.controller('DiagnosticoCtrl_View', ['$scope', 'DiagnosticoSrv', 'MessageSrv', '$state', 'PaginationService',
    '$filter', 'estadosDiagnosticoResponse', '$stateParams', 'odontogramaResponse',
    function ($scope, service, message, $state, pagination, $filter, estadosDiagnosticoResponse, $stateParams, odontogramaResponse) {
        var vm = this;
        vm.filter = {};
        vm.result = [];
        vm.filterChips = [];
        vm.aux = {
            practicaSearchText: null,
            estados: estadosDiagnosticoResponse.data,
            odontograma: odontogramaResponse.data,
            errorFecha: false,
            mostrarFiltros: false,
            viewingDiagnostico: undefined
        }
        pagination.config('api/diagnostico/find/' + $stateParams.id);
        vm.paginationData = pagination.paginationData;

        vm.searchPractica = searchPractica;
        vm.consultar = consultar;
        vm.nextPage = nextPage;
        vm.previousPage = previousPage;
        vm.viewDiagnostico = viewDiagnostico;
        vm.cancelView = cancelView;
        vm.cleanFilters = cleanFilters;
        vm.query = {
            limit: 5,
            page: 1
        };
        vm.dientesSvg = [];
        search();

        function cleanFilters() {
            vm.filter = {};
            vm.aux.practicaSearchText = null;
            search();
        }

        function viewDiagnostico(diagnostico) {
            vm.aux.viewingDiagnostico = diagnostico;
        }

        function cancelView() {
            vm.aux.viewingDiagnostico = undefined;
        }

        function updateFilterChips() {
            vm.filterChips = [];
            if (vm.filter.practica) {
                vm.filterChips.push(newFilterChip('practica', 'Práctica', vm.filter.practica, vm.filter.practica.denominacion));
            }
            if (vm.filter.estado) {
                vm.filterChips.push(newFilterChip('estado', 'Estado', vm.filter.estado));
            }
            if (vm.filter.fechaDesde) {
                vm.filterChips.push(newFilterChip('fechaDesde', 'Fecha desde', vm.filter.fechaDesde, $filter('date')(vm.filter.fechaDesde, 'dd/MM/yyyy')));
            }
            if (vm.filter.fechaHasta) {
                vm.filterChips.push(newFilterChip('fechaHasta', 'Fecha hasta', vm.filter.fechaHasta, $filter('date')(vm.filter.fechaHasta, 'dd/MM/yyyy')));
            }
        }

        $scope.$watchCollection('vm.filterChips', function (newCol, oldCol) {
            if (newCol.length < oldCol.length) {
                vm.filter = {};
                angular.forEach(newCol, function (filterChip) {
                    vm.filter[filterChip.origin] = filterChip.value;
                });
                search();
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

        function search(pageNumber) {
            var params = {
                practicaId: vm.filter.practica ? vm.filter.practica.id : null,
                estado: vm.filter.estado,
                fechaDesde: vm.filter.fechaDesde,
                fechaHasta: vm.filter.fechaHasta
            }
            pagination.paginate(params, pageNumber).then(function (data) {
                vm.result = data;
                vm.paginationData = pagination.getPaginationData();
                updateFilterChips();
                if (vm.aux.mostrarFiltros) {
                    vm.aux.mostrarFiltros = false;
                    vm.clickIconMorph();
                }
            }, function () {
            });
        }

        function executeQuery(form, pageNumber) {
            if (form.$valid) {
                search(pageNumber);
            }
        }

        function consultar(form) {
            executeQuery(form);
        }

        function nextPage(form) {
            if (vm.paginationData.morePages) {
                executeQuery(form, ++vm.paginationData.pageNumber);
            }
        }

        function previousPage(form) {
            if (!vm.paginationData.firstPage) {
                executeQuery(form, --vm.paginationData.pageNumber);
            }
        }

        function searchPractica(searchText) {
            return service.searchPracticas(searchText).then(function (response) {
                return response.data;
            });
        }

        vm.clickIcon = 'expand_more';
        vm.colorIcon = ['#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF'];

        vm.mostrarAcciones = function (item) {
            item.showAction = true;
        };

        vm.ocultarAcciones = function (item) {
            item.showAction = false;
        };

        vm.colorMouseOver = function (icon) {
            vm.colorIcon[icon] = '#E91E63';
        };

        vm.colorMouseLeave = function (icon) {
            vm.colorIcon[icon] = '#00B0FF';
        };

        vm.clickIconMorph = function () {
            if (vm.clickIcon === 'expand_more') {
                vm.clickIcon = 'expand_less';
            }
            else {
                vm.clickIcon = 'expand_more';
            }
        };
    }]);