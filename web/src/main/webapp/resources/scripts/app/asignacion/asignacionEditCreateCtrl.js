/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 22/12/15
 * Time: 13:53
 * To change this template use File | Settings | File Templates.
 */
var module = angular.module('asignacionModule');

module.controller('AsignacionCtrl_EditCreate',
    ['$scope', '$rootScope', '$filter', 'AsignacionSrv', '$state', 'MessageSrv', 'PaginationService',
        'tiposDocumentoResponse', 'sexosResponse','catedrasResponse', 'trabajosPracticosResponse',
        function ($scope, $rootScope, $filter, service, $state, message, pagination, tiposDocumentoResponse,
                   sexosResponse, catedrasResponse,  trabajosPracticosResponse
                 ) {
            var vm = this;
            //
            vm.asignacion = {};
            //Data auxiliar.
            vm.data = {
                sexos : sexosResponse.data,
                tiposDocumentos : tiposDocumentoResponse.data,
                catedras: catedrasResponse.data,
                trabajosPracticos: trabajosPracticosResponse.data
            }
            //Resultados a mostrar
            vm.pacientes = [];
            vm.alumnos = [];
            vm.selectedPacientes = [];
            //CONSULTA Y PAGINACION PACIENTE
            vm.buscarPacientes = buscarPaciente;
            vm.nextPagePaciente = nextPageAlumno;
            vm.previousPagePaciente = previousPageAlumno;
            vm.filterPaciente = {};
            vm.isBusquedaPaciente = true;
            vm.filteredTrabajosPracticos = [];
            //CONSULTA Y PAGINACION ALUMNO
            vm.buscarAlumnos = buscarAlumnos;
            vm.limpiarCampos = limpiarCampos;
            vm.nextPageAlumno = nextPageAlumno;
            vm.previousPageAlumno = previousPageAlumno;
            vm.filterAlumno = {};
            vm.paginationData = pagination.paginationData;

            vm.busqueda = false;
            //Seleccion Alumno
            vm.onAlumnoSelected = onAlumnoSelected;
            vm.deleteSelectedAlumno = deleteSelectedAlumno;
            vm.isAlumnoSelected = false;
            vm.selectedAlumno = {};
            vm.selectedAlumnos = [];
            //Seleccion paciente
            vm.onPacienteSelected = onPacienteSelected;
            vm.deleteSelectedPaciente = deleteSelectedPaciente;
            vm.isPacienteSelected = false;
            vm.selectedPaciente = {};
            vm.selectedPacientes = [];
            //GUARDAR
            vm.save = save;
            //Navegacion
            vm.goIndex = goIndex;
            vm.reload = reload;
            vm.submitted = false;
            var performSubmit = $scope.$parent.performSubmit;
            var handleError = $scope.$parent.handleError;
            vm.validationErrorFromServer = $scope.$parent.validationErrorFromServer;

            //CONSULTA  Y PAGINACION
            function executeQuery(pageNumber) {
                pagination.paginate(vm.filterAlumno, pageNumber).then(function (data) {
                    if(vm.isBusquedaPaciente){
                        vm.pacientes = data;
                    }else{
                        vm.alumnos = data;
                    }
                    vm.paginationData = pagination.getPaginationData();
                }, function () {
                });
            }

            //BUSQUEDA PACIENTE
            function buscarPaciente(){
                pagination.config('api/asignacion/findPacientes');
                vm.isBusquedaPaciente = true;
                executeQuery(0);
            }

            function nextPagePaciente() {
                if (vm.paginationData.morePages) {
                    executeQuery(++vm.paginationData.pageNumber);
                }
            }

            function previousPagePaciente() {
                if (!vm.paginationData.firstPage) {
                    executeQuery(--vm.paginationData.pageNumber);
                }
            }

            //BUSQUEDA ALUMNO
            function buscarAlumnos(){
                pagination.config('api/asignacion/findAlumnos');
                vm.isBusquedaPaciente = false;
                executeQuery();
            }

            function nextPageAlumno() {
                if (vm.paginationData.morePages) {
                    executeQuery(++vm.paginationData.pageNumber);
                }
            }

            function previousPageAlumno() {
                if (!vm.paginationData.firstPage) {
                    executeQuery(--vm.paginationData.pageNumber);
                }
            }

            function limpiarCampos(){

            }

            //SELECCION DE ALUMNO
            function onAlumnoSelected(alumno){
                vm.selectedAlumno = alumno;
                vm.isAlumnoSelected = true;
            }

            function deleteSelectedAlumno(){
                vm.isAlumnoSelected = false;
                vm.selectedAlumno = {};
                vm.selectedAlumnos = [];
            }
            //SELECCION DE PACIENTE
            function onPacienteSelected(paciente){
                vm.isPacienteSelected = true;
                vm.selectedPaciente= paciente;
            }

            function deleteSelectedPaciente(){
                vm.isPacienteSelected = false;
                vm.selectedPaciente= {};
                vm.selectedPacientes = [];
            }

            //GUARDAR
            function save(form) {
                vm.submitted = true;
                performSubmit(function () {
                    service.save(vm.paciente)
                        .success(function () {
                            vm.data.persistedOperation = true;
                            vm.data.disableFields = true;
                            vm.data.saved = true;
//                            message.successMessage(vm.paciente.nombre + " creado con éxito");
                            vm.goIndex();
                        }).error(function (data, status) {
                            vm.paciente.documento = "";
                            handleError(data, status);
                        })
                }, form)
            }

            //WATCHER
//            $scope.$watch(
//                'vm.filterPaciente.materia',
//                function(newValue, oldValue){
////                    delete vm.paciente.ciudadNacimiento;
//                    filterTrabajosPracticos();
//                    function filterTrabajosPracticos(){
//                        if(!vm.filterPaciente.materia || !angular.isDefined(vm.filterPaciente.materia.id)){
//                            vm.filteredTrabajosPracticos = vm.data.trabajosPracticos;
//                        }else{
//                            vm.filteredTrabajosPracticos = $filter('filter')(vm.data.trabajosPracticos, function(value){
//                                return angular.equals(value.provincia.id,vm.paciente.provinciaNacimiento.id);
//                            })
//                        }
//                    }
//                })

            //NAVEGACION
            function goIndex() {
                $state.go('^.index', {execQuery: vm.data.persistedOperation});
            }

            function reload() {
                $rootScope.persistedOperation = vm.data.persistedOperation;
                $state.go($state.current, {}, {reload: true});
            }

            $scope.$on('$stateChangeStart',
                function (event, toState, toParams, fromState, fromParams) {
                    if (!angular.equals($state.current, toState)) {
                        delete $rootScope.persistedOperation;
                    }
                })
        }]);
