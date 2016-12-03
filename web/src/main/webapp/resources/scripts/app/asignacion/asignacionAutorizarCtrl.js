var module = angular.module('asignacionModule');


module.controller('AsignacionCtrl_Autorizar', ['$scope', '$cacheFactory', 'AsignacionSrv', '$state', '$stateParams', 'MessageSrv',
    'CommonsSrv', 'PaginationService', '$mdDialog','practicasResponse','tiposDocumentoResponse',
    'estadosAsignacionResponse', 'catedrasResponse','$filter', '$window', '$location',
    function ($scope, $cacheFactory, service, $state, $stateParams, message, commons, pagination, $mdDialog,
              practicasResponse, tiposDocumentoResponse,estadosAsignacionResponse,catedrasResponse, $filter, $window, $location) {
        var vm = this;
        vm.result = [];
        vm.data = {
            practicas: practicasResponse.data,
            tiposDocumentos: tiposDocumentoResponse.data,
            estados: estadosAsignacionResponse.data,
            catedras: catedrasResponse.data,
            trabajosPracticos: []
        };
        vm.aux = {
            mostrarFiltros: true
        }

        vm.filter = { fechaAsignacion: new Date()};
        vm.restrictedDate = {
            maxDate: new Date()
        }
        vm.filterChips = [];
        vm.selectedAsignaciones = [];
        vm.buscarAsignaciones = buscarAsignaciones;
        //Cambio de estado
        vm.autorizar = autorizar;
        //Diálogos
        vm.filterAlumno = {};
        vm.paginationData = pagination.paginationData;
        vm.busqueda = false;
        vm.onCatedraSelected = onCatedraSelected;
        vm.goHome = goHome;
        vm.printResults = printResults;

        function goHome() {
            $state.go('home');
        }

        var handleError = $scope.$parent.handleError;
        $scope.validationErrorFromServer = $scope.$parent.validationErrorFromServer;


        //BUSQUEDA ALUMNO
        function onCatedraSelected() {
            var idCatedra = vm.filter.catedra.id;
            service.getTrabajosPracticosByCatedra(idCatedra)
                .success(function (response) {
                    vm.data.trabajosPracticos = response;
                })
                .error(function (data, status) {
                    handleError(data, status);
                })
        }

        vm.paginationData = pagination.paginationData;
        pagination.config('api/asignacion/findAsignacionesConfirmadas');

        //Consulta
        function executeQuery(pageNumber) {
            pagination.paginate(vm.filter, pageNumber)
                .then(function (data) {
                        vm.result = data;
                        updateFilterChips();
                    vm.paginationData = pagination.getPaginationData();
                }, function () {
                });
        }

        function buscarAsignaciones(form) {
            if (!form.$invalid) {
                vm.filter.trabajoPracticoId = angular.isUndefined(vm.filter.trabajoPractico) ? null : vm.filter.trabajoPractico.id;
                vm.filter.catedraId = angular.isUndefined(vm.filter.catedra) ? null : vm.filter.catedra.id;
                executeQuery();
            }else {
                angular.forEach(form.$error, function (field) {
                    angular.forEach(field, function (errorField) {
                        console.log(field.name);
                        errorField.$setTouched();
                    })
                });
            }
        }

        //Chips
        function updateFilterChips() {
            vm.filterChips = [];
            if (vm.filter.fechaAsignacion) {
                vm.filterChips.push(newFilterChip('fechaAsignacion', 'Fecha asignación', vm.filter.fechaAsignacion, $filter('date')(vm.filter.fechaAsignacion, 'dd/MM/yyyy')));
            }
            if (vm.filter.catedra) {
                vm.filterChips.push(newFilterChip('catedra', 'Cátedra', vm.filter.catedra, (vm.filter.catedra.materia + ' ' +  vm.filter.catedra.denominacion)));
            }
            if (vm.filter.trabajoPractico) {
                vm.filterChips.push(newFilterChip('trabajoPractico', 'Trabajo práctico', vm.filter.trabajoPractico, vm.filter.trabajoPractico.nombre ));
            }
            if (vm.filter.apellidoAlumno) {
                vm.filterChips.push(newFilterChip('apellidoAlumno', 'Apellido alumno', vm.filter.apellidoAlumno));
            }
            if (vm.filter.nombreAlumno) {
                vm.filterChips.push(newFilterChip('nombreAlumno', 'Nombre alumno', vm.filter.nombreAlumno));
            }

            if (vm.filter.tipoDocumentoAlumno) {
                vm.filterChips.push(newFilterChip('tipoDocumentoAlumno', 'Tipo doc. alumno', vm.filter.tipoDocumentoAlumno, vm.filter.tipoDocumentoAlumno.nombre));
            }

            if (vm.filter.numeroDocumentoAlumno) {
                vm.filterChips.push(newFilterChip('numeroDocumentoAlumno', 'Núm. doc. alumno', vm.filter.numeroDocumentoAlumno));
            }

        }

        $scope.$watchCollection('vm.filterChips', function (newCol, oldCol) {
            if (newCol.length < oldCol.length) {
                vm.filter = {};
                angular.forEach(newCol, function (filterChip) {
                    vm.filter[filterChip.origin] = filterChip.value;
                });
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

        //Cambio de estado
        function autorizar(){
            var today = new Date();
            if(vm.filter.fechaAsignacion > today){
                message.errorMessage("Solo pueden autorizarse las asignaciones a partir del día " +  $filter('date')(vm.filter.fechaAsignacion, 'dd/MM/yyyy') +".");
                return;
            }
            service.autorizar(vm.selectedAsignaciones)
                .then(function(){
                    vm.selectedAsignaciones = [];
                    buscarAsignaciones("autorizarAsignacionesForm");
                    message.successMessage("Asignaciones autorizadas");
                }, function(data){
                    handleError(data, status);
                });

        }

        function printResults(asignaciones) {
            var ids = [];
            asignaciones.forEach(function(asignacion) {
               ids.push(asignacion.id);
            });
            var base = $location.protocol() + '://' + $location.host() + ':' + $location.port() + "/Odontologia-web/api/asignacion/printPorAutorizar";
            $window.open(base + '?ids=' + ids);
        }

        //Métodos auxiliares
        vm.clickIcon = 'expand_less';
        vm.colorIcon = ['#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF'];

        vm.cleanFilters = function () {
            vm.filter = {};
            vm.filterChips = [];
        };

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



