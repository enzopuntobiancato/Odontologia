/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 22/12/15
 * Time: 13:53
 * To change this template use File | Settings | File Templates.
 */
var module = angular.module('pacienteModule');

module.controller('PacienteCtrl_Create',
    ['$scope', '$rootScope', '$filter','PacienteSrv', '$state', 'MessageSrv',
        'tiposDocumentoResponse','sexosResponse','provinciaResponse', 'ciudadesResponse','barriosResponse','estadosResponse',
        'trabajosResponse','obrasSocialesResponse','nivelesEstudioResponse','nacionalidadesResponse',
    function ($scope, $rootScope, $filter, service, $state, message,
              tiposDocumentoResponse,sexosResponse,provinciaResponse,ciudadesResponse,barriosResponse,estadosResponse,
              trabajosResponse,obrasSocialesResponse,nivelesEstudioResponse,nacionalidadesResponse
        ) {
        var vm = this;
        vm.paciente={};
        vm.data = {
            ciudades : ciudadesResponse.data,
            barrios : barriosResponse.data,
            estados : estadosResponse.data,
            provincias : provinciaResponse.data,
            tiposDocumento : tiposDocumentoResponse.data,
            sexos: sexosResponse.data,
            trabajos: trabajosResponse.data,
            obrasSociales: obrasSocialesResponse.data,
            nivelesEstudio: nivelesEstudioResponse.data,
            nacionalidades: nacionalidadesResponse.data
        }
        vm.selectedCiudad ={};
        var performSubmit = $scope.$parent.performSubmit;
        var handleError = $scope.$parent.handleError;
        //Guardar
        vm.save = save;
        vm.goIndex = goIndex;
        vm.reload =reload;
        //TrabajoSearch
//        vm.trabajosSearch = queryTrabajosSearch();

        function goIndex(){
            $state.go('^.index',{execQuery: $scope.data.persistedOperation});
        }

        function reload(){
            $rootScope.persistedOperation = $scope.data.persistedOperation;
            $state.go($state.current, {}, {reload: true});
        }
        function save(form){
            vm.submitted = true;
            if(!form.$invalid){
                performSubmit(function(){
                    service.save(vm.paciente)
                        .then(function successCallback(data){
                            vm.data.persistedOperation = true;
                            vm.data.disableFields;
                            vm.data.saved=true;
                            message.successMessage("Paciente " +vm.paciente.apellido + ', ' + vm.paciente.nombre + ' creado con éxito');
                            vm.goIndex();
                        }
                        ,function errorCallback(data, status){
                            handleError(data,status)
                        })
                },form);
            }
        }
        $scope.$watch(
            'vm.paciente.provinciaNacimiento',
            function(newValue, oldValue){
                delete vm.paciente.ciudadNacimiento;
                filterCiudades();
            function filterCiudades(){
               if(!vm.paciente.provinciaNacimiento || !angular.isDefined(vm.paciente.provinciaNacimiento.id)){
                   vm.ciudadesNacSelect = vm.data.ciudades;
               }else{
                   vm.ciudadesNacSelect = $filter('filter')(vm.data.ciudades, function(value){
                       return angular.equals(value.provincia.id,vm.paciente.provinciaNacimiento.id);
                   })
               }
            }
        })

        $scope.$watch(
            'vm.selectedCiudad',
            function(newValue, oldValue){
                if(vm.paciente.domicilio){
                    delete vm.paciente.domicilio.barrio;
                }
                filterBarrios();
                function filterBarrios(){
                    if(!vm.selectedCiudad || !angular.isDefined(vm.selectedCiudad.id)){
                        vm.barriosSelect = vm.data.barrios;
                    }else{
                        vm.barriosSelect = $filter('filter')(vm.data.barrios, function(value){
                            return angular.equals(value.ciudad.id,vm.selectedCiudad.id);
                        })
                    }
                }
            })

//        function queryTrabajosSearch(query){
//            return query ?
//                vm.data.trabajos.filter(crearFilterTrabajo(query))
//                : vm.data.trabajos;
//        }
//
//        function crearFilterTrabajo(query){
//           var primeraLetra = angular.lowercase(query);
//            return function filterTrabajos(trabajo){
//                return (trabajo.nombre.indexOf(primeraLetra)===0);
//            };
//        }

        function goIndex(){
            $state.go('^.index');
        }
        //Adicionales
        vm.secciones = [true,false,false,false,false];
        vm.clickIcon = 'keyboard_arrow_right';
        vm.colorIcon = ['#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF'];

        vm.mostrarSeccion = function(idSeccion, titulo){
           for(var ind=0; ind < 5; ind++){
               if(ind == idSeccion){
                   vm.secciones[ind] = true;// true para la sección activa
               }else{
                   vm.secciones[ind] = false;
               }
           }
        };

        vm.colorMouseOver = function (icon) {
            vm.colorIcon[icon] = '#E91E63';
        };

        vm.colorMouseLeave = function (icon) {
            vm.colorIcon[icon] = '#00B0FF';
        };

        vm.clickIconMorph = function () {
            if (vm.clickIcon === 'keyboard_arrow_right') {
                vm.clickIcon = 'expand_less';
            }
            else {
                vm.clickIcon = 'keyboard_arrow_right';
            }
        };

        $scope.$on('$stateChangeStart',
            function (event, toState, toParams, fromState, fromParams) {
                if (!angular.equals($state.current, toState)) {
                    delete $rootScope.persistedOperation;
                }
            })
    }]);
