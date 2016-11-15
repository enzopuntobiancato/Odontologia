var module = angular.module('historiaClinicaModule');

module.controller('AtencionCtrl_View', ['$scope', 'MessageSrv', 'AtencionSrv', '$state', 'PaginationService', '$filter', '$stateParams', 'catedrasResponse', '$window', '$location',
    function ($scope, message, service, $state, pagination, $filter, $stateParams, catedrasResponse, $window, $location) {
        var vm = this;
        vm.filter = {};
        vm.result = [];
        vm.filterChips = [];
        vm.aux = {
            viewingAtencion: false,
            practicaSearchText: null,
            mostrarFiltros: true,
            catedras: catedrasResponse.data
        }

        vm.data = {
            catedras: vm.aux.catedras,
            trabajosPracticos: []
        }

        pagination.config('api/atencion/find/' + $stateParams.id);
        vm.paginationData = pagination.paginationData;

        vm.previousPage = previousPage;
        vm.nextPage = nextPage;
        vm.searchPractica = searchPractica;
        vm.consultar = consultar;
        vm.cleanFilters = cleanFilters;
        vm.onCatedraSelect = onCatedraSelect;
        vm.onDiagnosticoSelect = onDiagnosticoSelect;
        vm.viewAtencion = viewAtencion;
        vm.cancelView = cancelView;
        search();

        function cleanFilters() {
            vm.filter = {};
            vm.data.catedras = vm.aux.catedras;
            vm.aux.practicaSearchText = null;
            search();
        }

        function updateFilterChips() {
            vm.filterChips = [];
            if(vm.filter.practica) {
                vm.filterChips.push(newFilterChip('practica', 'Práctica', vm.filter.practica, vm.filter.practica.denominacion));
            }
            if (vm.filter.catedra) {
                vm.filterChips.push(newFilterChip('catedra', 'Catedra', vm.filter.catedra, vm.filter.catedra.materia + ' ' + vm.filter.catedra.denominacion));
            }
            if (vm.filter.trabajoPractico) {
                vm.filterChips.push(newFilterChip('trabajoPractico', 'Trabajo practico', vm.filter.trabajoPractico, vm.filter.trabajoPractico.nombre));
            }
            if (vm.filter.fechaDesde) {
                vm.filterChips.push(newFilterChip('fechaDesde', 'Fecha desde', vm.filter.fechaDesde, $filter('date')(vm.filter.fechaDesde, 'dd/MM/yyyy')));
            }
            if (vm.filter.fechaHasta) {
                vm.filterChips.push(newFilterChip('fechaHasta', 'Fecha hasta', vm.filter.fechaHasta, $filter('date')(vm.filter.fechaHasta, 'dd/MM/yyyy')));
            }
        }

        $scope.$watchCollection('vm.filterChips', function(newCol, oldCol) {
            if (newCol.length < oldCol.length) {
                vm.filter = {};
                angular.forEach(newCol, function(filterChip) {
                    vm.filter[filterChip.origin] = filterChip.value;
                });
                if (!vm.filter.practica) {
                    vm.aux.practicaSearchText = null;
                }
                if (!vm.filter.catedra) {
                    vm.filter.trabajoPractico = null;
                }
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
                catedraId: vm.filter.catedra ? vm.filter.catedra.id : null,
                trabajoPracticoId: vm.filter.trabajoPractico ? vm.filter.trabajoPractico.id : null,
                fechaDesde: vm.filter.fechaDesde,
                fechaHasta: vm.filter.fechaHasta
            }
            pagination.paginate(params, pageNumber).then(function (data) {
                vm.result = data;
                vm.paginationData = pagination.getPaginationData();
                updateFilterChips();
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
            var catedraId = vm.filter.catedra ? vm.filter.catedra.id : null;
            var practicoId = vm.filter.trabajoPractico ? vm.filter.trabajoPractico.id : null;
            return service.searchPracticas(searchText, catedraId, practicoId).then(function(response) {
                return response.data;
            });
        }

        function onCatedraSelect() {
            var idCatedra = vm.filter.catedra.id;
            service.getTrabajosPracticosByCatedra(idCatedra)
                .success(function (response) {
                    vm.data.trabajosPracticos = response;
                })
                .error(function () {
                    message.errorMessage("Error filtrando trabajos prácticos por cátedra");
                })
        }

        function onDiagnosticoSelect() {
            if (vm.filter.practica) {
                service.findCatedrasByPractica(vm.filter.practica.id)
                    .then(function(response) {
                        vm.data.catedras = response.data;
                    })
            } else {
                vm.data.catedras = vm.aux.catedras;
            }
        }

        function viewAtencion(atencion) {
            vm.aux.viewingAtencion = true;
            vm.atencionView = atencion;
            service.findDocumentaciones(atencion.id)
                .then(function(response) {
                    vm.documentaciones = response.data;
                })
        }

        vm.openInNewTab = function(file) {
            var base = $location.protocol() + '://'+ $location.host() +':'+  $location.port() + "/Odontologia-web/api/file/";
            if (file.extension == 'PDF') {
                $window.open(base + 'pdf?id=' + file.id);
            } else {
                $window.open(base + 'image?id=' + file.id);
            }
        }

        function cancelView() {
            vm.aux.viewingAtencion = false;
            vm.atencionView = null;
        }

        vm.clickIcon = 'expand_less';
        vm.colorIcon = ['#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF'];

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
