/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 22/12/15
 * Time: 13:53
 * To change this template use File | Settings | File Templates.
 */
var module = angular.module('pacienteModule');

module.controller('PacienteCtrl_EditCreate',
    ['$scope', '$rootScope', '$filter','PacienteSrv', '$state', 'MessageSrv',
        'tiposDocumentoResponse','sexosResponse','provinciaResponse', 'ciudadesResponse','barriosResponse','estadosResponse',
        'trabajosResponse','obrasSocialesResponse','nivelesEstudioResponse','nacionalidadesResponse', 'pacienteResponse',
        'DeleteRestoreSrv',
        function ($scope, $rootScope, $filter, service, $state, message,
                  tiposDocumentoResponse,sexosResponse,provinciaResponse,ciudadesResponse,barriosResponse,estadosResponse,
                  trabajosResponse,obrasSocialesResponse,nivelesEstudioResponse,nacionalidadesResponse,pacienteResponse,
                  deleteRestoreService
            ) {
            var vm = this;
            vm.paciente= pacienteResponse.data;
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
                nacionalidades: nacionalidadesResponse.data,
                persistedOperation: false,
                saved: false
            }

            //Navegacion
            vm.save = save;
            vm.goIndex = goIndex;
            vm.reload =reload;
            vm.mostrar = mostrar;
            vm.limpiarCampos = limpiarCampos;
            vm.updating = $state.current.data.updating;
            vm.tabs = [true, false, false, false];
            vm.tabsTitulos = ["Datos personales", "Antecedentes", "Diagnósticos", "Atenciones"];
            vm.titulo = vm.updating ? "Editar " +  vm.paciente.apellido : "Registrar nuevo paciente";
            vm.tabTitulo = vm.tabsTitulos[0];

            var performSubmit = $scope.$parent.performSubmit;
            var handleError = $scope.$parent.handleError;
            vm.validationErrorFromServer = $scope.$parent.validationErrorFromServer;

            function mostrar(tabId){
                for(var i = 0; i < vm.tabs.length; i++){
                    if(i == tabId){
                        vm.tabs[i] = true;
                        vm.tabTitulo = vm.tabsTitulos[i];
                    }else{
                        vm.tabs[i] = false;
                    }
                }
            }

            function goIndex(){
                console.log("index");
                $state.go('^.index',{execQuery: vm.data.persistedOperation});
            }

            function reload(){
                $rootScope.persistedOperation = vm.data.persistedOperation;
                $state.go($state.current, {}, {reload: true});
            }

            function save(form){
                vm.submitted = true;
                performSubmit(function(){
                    service.save(vm.paciente)
                        .success(function(){
                            vm.data.persistedOperation = true;
                            vm.data.disableFields = true;
                            vm.data.saved = true;
                            message.successMessage(vm.paciente.nombre + " creado con éxito");
                            vm.goIndex();
                        }).error(function(data,status){
                            vm.paciente.documento = "";
                            handleError(data,status);
                        })
                },form)
            }

            //BAJA Y RESTAURAR
            vm.openDeleteDialog = function(event) {
                var nombre = vm.paciente.apellido + ", " + vm.paciente.nombre;
                deleteRestoreService.delete(event, vm.paciente.id, nombre, null,null,null);
            }

            vm.openRestoreDialog = function(event) {
                var nombre = vm.paciente.apellido + ", " + vm.paciente.nombre;
                deleteRestoreService.restore(event, vm.paciente.id, nombre, null);
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

            function limpiarCampos(idCampo) {
                switch (idCampo) {
                    case vm.paciente.historiaClinicaDTO.g1p1.id:
                        if (vm.paciente.historiaClinicaDTO.g1p1.siNo == false) {
                            vm.paciente.historiaClinicaDTO.g1p2.only_detalle = null;
                            vm.paciente.historiaClinicaDTO.g1p3.fecha = null;
                        }
                        break;
                    case vm.paciente.historiaClinicaDTO.g2p1.id:
                        if(vm.paciente.historiaClinicaDTO.g2p1.siNo == false){
                            vm.paciente.historiaClinicaDTO.g2p2.siNo = false;
                            vm.paciente.historiaClinicaDTO.g2p3.only_detalle = null;
                            vm.paciente.historiaClinicaDTO.g2p4.fecha = null;
                        }
                    case vm.paciente.historiaClinicaDTO.g2p2.id:
                        if(vm.paciente.historiaClinicaDTO.g2p2.siNo == false){
                            vm.paciente.historiaClinicaDTO.g2p3.only_detalle = null;
                            vm.paciente.historiaClinicaDTO.g2p4.fecha = null;
                        }
                        break;
                    case vm.paciente.historiaClinicaDTO.g3p1.id:
                        if(vm.paciente.historiaClinicaDTO.g3p1.siNo == false){
                            vm.paciente.historiaClinicaDTO.g3p2.siNo = false;
                            vm.paciente.historiaClinicaDTO.g3p3.only_detalle = null;
                            vm.paciente.historiaClinicaDTO.g3p4.fecha = null;
                            vm.paciente.historiaClinicaDTO.g3p5.only_detalle = null;
                            vm.paciente.historiaClinicaDTO.g3p6.siNo = false;
                            vm.paciente.historiaClinicaDTO.g3p7.siNo = false;
                            vm.paciente.historiaClinicaDTO.g3p8.only_detalle = null;
                            vm.paciente.historiaClinicaDTO.g3p9.only_detalle = null;
                            vm.paciente.historiaClinicaDTO.g3p10.fecha = null;
                            vm.paciente.historiaClinicaDTO.g3p7.siNo = false;
                        }
                    case vm.paciente.historiaClinicaDTO.g3p2.id:
                        if(vm.paciente.historiaClinicaDTO.g3p2.siNo == false){
                            vm.paciente.historiaClinicaDTO.g3p3.only_detalle = null;
                            vm.paciente.historiaClinicaDTO.g3p4.fecha = null;
                            vm.paciente.historiaClinicaDTO.g3p5.only_detalle = null;
                            vm.paciente.historiaClinicaDTO.g3p6.siNo = false;
                            vm.paciente.historiaClinicaDTO.g3p7.siNo = false;
                            vm.paciente.historiaClinicaDTO.g3p8.only_detalle = null;
                            vm.paciente.historiaClinicaDTO.g3p9.only_detalle = null;
                            vm.paciente.historiaClinicaDTO.g3p10.fecha = null;
                        }
                    case vm.paciente.historiaClinicaDTO.g3p6.id:
                        if(vm.paciente.historiaClinicaDTO.g3p6.siNo == false){
                            vm.paciente.historiaClinicaDTO.g3p7.siNo = false;
                            vm.paciente.historiaClinicaDTO.g3p8.only_detalle = null;
                            vm.paciente.historiaClinicaDTO.g3p9.only_detalle = null;
                            vm.paciente.historiaClinicaDTO.g3p10.fecha = null;
                        }
                    case vm.paciente.historiaClinicaDTO.g3p7.id:
                        if(vm.paciente.historiaClinicaDTO.g3p7.siNo == false){
                            vm.paciente.historiaClinicaDTO.g3p8.only_detalle = null;
                            vm.paciente.historiaClinicaDTO.g3p9.only_detalle = null;
                            vm.paciente.historiaClinicaDTO.g3p10.fecha = null;
                        }
                        break;
                    case vm.paciente.historiaClinicaDTO.g4p1.id:
                        if(vm.paciente.historiaClinicaDTO.g4p1.siNo == false){
                            vm.paciente.historiaClinicaDTO.g4p2.siNo = false;
                        }
                    case vm.paciente.historiaClinicaDTO.g4p2.id:
                        if(vm.paciente.historiaClinicaDTO.g4p2.siNo == false){
                            vm.paciente.historiaClinicaDTO.g4p3.only_detalle = null;
                            vm.paciente.historiaClinicaDTO.g4p4.fecha = null;
                        }
                        break;
                    case vm.paciente.historiaClinicaDTO.g5p1.id:
                        if(vm.paciente.historiaClinicaDTO.g5p1.siNo == false){
                            vm.paciente.historiaClinicaDTO.g5p2.only_detalle = null;
                            vm.paciente.historiaClinicaDTO.g5p3.fecha = null;
                            vm.paciente.historiaClinicaDTO.g5p8.siNo = false;
                            vm.paciente.historiaClinicaDTO.g5p9.fecha = null;
                        }
                        break;
                    case vm.paciente.historiaClinicaDTO.g5p4.id:
                        if(vm.paciente.historiaClinicaDTO.g5p4.siNo == false){
                            vm.paciente.historiaClinicaDTO.g5p5.siNo = false;
                        }
                    case vm.paciente.historiaClinicaDTO.g5p5.id:
                        if(vm.paciente.historiaClinicaDTO.g5p5.siNo == false){
                            vm.paciente.historiaClinicaDTO.g5p6.only_detalle = null;
                            vm.paciente.historiaClinicaDTO.g5p7.fecha = null;
                        }
                        break;
                    case vm.paciente.historiaClinicaDTO.g5p8.id:
                        if(vm.paciente.historiaClinicaDTO.g5p8.siNo == false){
                            vm.paciente.historiaClinicaDTO.g5p9.fecha = null;
                        }
                        break;
                    case vm.paciente.historiaClinicaDTO.g6p3.id:
                        if(vm.paciente.historiaClinicaDTO.g6p3.siNo == false){
                            vm.paciente.historiaClinicaDTO.g6p4.only_detalle = null;
                        }
                        break;
                    case vm.paciente.historiaClinicaDTO.g8p1.id:
                        if(vm.paciente.historiaClinicaDTO.g8p1.siNo == false){
                            vm.paciente.historiaClinicaDTO.g8p2.checked = null;
                            vm.paciente.historiaClinicaDTO.g8p3.checked = null;
                            vm.paciente.historiaClinicaDTO.g8p4.checked = null;
                            vm.paciente.historiaClinicaDTO.g8p5.checked = null;
                            vm.paciente.historiaClinicaDTO.g8p6.checked = null;
                        }
                        break;

                    case vm.paciente.historiaClinicaDTO.g9p1.id:
                        if(vm.paciente.historiaClinicaDTO.g8p1.siNo == false){
                            vm.paciente.historiaClinicaDTO.g8p2.checked = null;
                            vm.paciente.historiaClinicaDTO.g8p3.checked = null;
                            vm.paciente.historiaClinicaDTO.g8p4.checked = null;
                            vm.paciente.historiaClinicaDTO.g8p5.checked = null;
                            vm.paciente.historiaClinicaDTO.g8p6.checked = null;
                        }
                        break;
                    case vm.paciente.historiaClinicaDTO.g10p1.id:
                        if(vm.paciente.historiaClinicaDTO.g10p1.siNo == false){
                            vm.paciente.historiaClinicaDTO.g10p2.only_detalle = null;
                            vm.paciente.historiaClinicaDTO.g10p3.only_detalle = null;
                            vm.paciente.historiaClinicaDTO.g10p3.fecha = null;
                        }
                        break;
                    case vm.paciente.historiaClinicaDTO.g10p6.id:
                        if(vm.paciente.historiaClinicaDTO.g10p6.siNo == false){
                            vm.paciente.historiaClinicaDTO.g10p7.only_detalle = null;
                        }
                        break;
                    case vm.paciente.historiaClinicaDTO.g11p1.id:
                        if(vm.paciente.historiaClinicaDTO.g11p1.siNo == false){
                            vm.paciente.historiaClinicaDTO.g11p4.siNo = false;
                            vm.paciente.historiaClinicaDTO.g11p2.checked = null;
                            vm.paciente.historiaClinicaDTO.g11p3.checked = null;
                            vm.paciente.historiaClinicaDTO.g11p4.checked = null;
                            vm.paciente.historiaClinicaDTO.g11p5.only_detalle = null;
                            vm.paciente.historiaClinicaDTO.g11p6.fecha = null;
                        }
                    case vm.paciente.historiaClinicaDTO.g11p4.id:
                        if(vm.paciente.historiaClinicaDTO.g11p4.siNo == false){
                            vm.paciente.historiaClinicaDTO.g11p5.only_detalle = null;
                            vm.paciente.historiaClinicaDTO.g11p6.fecha = null;
                        }
                        break;
                    case vm.paciente.historiaClinicaDTO.g12p1.id:
                        if(vm.paciente.historiaClinicaDTO.g12p1.siNo == false){
                            vm.paciente.historiaClinicaDTO.g12p2.only_detalle = null;
                        }
                        break;
                    case vm.paciente.historiaClinicaDTO.g13p1.id:
                        if(vm.paciente.historiaClinicaDTO.g13p1.siNo == false){
                            vm.paciente.historiaClinicaDTO.g13p2.fecha = null;
                            vm.paciente.historiaClinicaDTO.g13p3.only_detalle = null;
                            vm.paciente.historiaClinicaDTO.g13p4.only_detalle = null;
                        }
                        break;
                    case vm.paciente.historiaClinicaDTO.g14p1.id:
                        if(vm.paciente.historiaClinicaDTO.g14p1.siNo == false){
                            vm.paciente.historiaClinicaDTO.g14p2.only_detalle = null;
                            vm.paciente.historiaClinicaDTO.g14p3.fecha = null;
                            vm.paciente.historiaClinicaDTO.g14p4.only_detalle = null;
                        }
                        break;
                    case vm.paciente.historiaClinicaDTO.g15p1.id:
                        if(vm.paciente.historiaClinicaDTO.g15p1.siNo == false){
                            vm.paciente.historiaClinicaDTO.g15p2.only_detalle = null;
                        }
                        break;
                    case vm.paciente.historiaClinicaDTO.g15p3.id:
                        if(vm.paciente.historiaClinicaDTO.g15p3.siNo == false){
                            vm.paciente.historiaClinicaDTO.g15p4.only_detalle = null;
                        }
                        break;
                    case vm.paciente.historiaClinicaDTO.g15p6.id:
                        if(vm.paciente.historiaClinicaDTO.g15p6.siNo == false){
                            vm.paciente.historiaClinicaDTO.g15p7.only_detalle = null;
                            vm.paciente.historiaClinicaDTO.g15p8.fecha = null;
                            vm.paciente.historiaClinicaDTO.g15p9.only_detalle = null;
                        }
                        break;
                    case vm.paciente.historiaClinicaDTO.g15p10.id:
                        if(vm.paciente.historiaClinicaDTO.g15p10.siNo == false){
                            vm.paciente.historiaClinicaDTO.g15p12.fecha = null;
                            vm.paciente.historiaClinicaDTO.g15p13.only_detalle = null;
                            vm.paciente.historiaClinicaDTO.g15p11.only_detalle = null;
                        }
                        break;
                    case vm.paciente.historiaClinicaDTO.g16p1.id:
                        if(vm.paciente.historiaClinicaDTO.g16p1.siNo == false){
                            vm.paciente.historiaClinicaDTO.g16p2.only_detalle = null;
                            vm.paciente.historiaClinicaDTO.g16p3.fecha = null;
                        }
                        break;
                    case vm.paciente.historiaClinicaDTO.g17p1.id:
                        if(vm.paciente.historiaClinicaDTO.g17p1.siNo == false){
                            vm.paciente.historiaClinicaDTO.g17p2.only_detalle = null;
                            vm.paciente.historiaClinicaDTO.g17p3.only_detalle = null;
                            vm.paciente.historiaClinicaDTO.g17p4.only_detalle = null;
                            vm.paciente.historiaClinicaDTO.g17p5.siNo = false;
                            vm.paciente.historiaClinicaDTO.g17p6.fecha = null;
                            vm.paciente.historiaClinicaDTO.g17p7.siNo = false;
                        }
                        break;
                    case vm.paciente.historiaClinicaDTO.g17p5.id:
                        if(vm.paciente.historiaClinicaDTO.g17p5.siNo == false){
                            vm.paciente.historiaClinicaDTO.g17p6.fecha = null;
                            vm.paciente.historiaClinicaDTO.g17p7.only_detalle = false;
                        }
                        break;
                    case vm.paciente.historiaClinicaDTO.g18p1.id:
                        if (vm.paciente.historiaClinicaDTO.g18p1.siNo == false) {
                            vm.paciente.historiaClinicaDTO.g18p2.only_detalle = null;
                            vm.paciente.historiaClinicaDTO.g18p3.checked = null;
                            vm.paciente.historiaClinicaDTO.g18p4.checked = null;
                            vm.paciente.historiaClinicaDTO.g18p5.checked = null;
                        }
                        break;
                    case vm.paciente.historiaClinicaDTO.g19p1.id:
                        if(vm.paciente.historiaClinicaDTO.g19p1.siNo == false){
                            vm.paciente.historiaClinicaDTO.g19p2.only_detalle = null;
                            vm.paciente.historiaClinicaDTO.g19p3.fecha = null;
                            vm.paciente.historiaClinicaDTO.g19p4.only_detalle = null;
                        }
                        break;
                    case vm.paciente.historiaClinicaDTO.g19p6.id:
                        if(vm.paciente.historiaClinicaDTO.g19p6.siNo == false){
                            vm.paciente.historiaClinicaDTO.g19p9.only_detalle = null;
                            vm.paciente.historiaClinicaDTO.g19p7.only_detalle = null;
                            vm.paciente.historiaClinicaDTO.g19p8.only_detalle = null;
                        }
                        break;
                    case vm.paciente.historiaClinicaDTO.g20p2.id:
                        if(vm.paciente.historiaClinicaDTO.g20p2.siNo == false){
                            vm.paciente.historiaClinicaDTO.g20p3.only_detalle = null;
                            vm.paciente.historiaClinicaDTO.g20p4.fecha = null;
                        }
                    case vm.paciente.historiaClinicaDTO.g20p5.id:
                        if(vm.paciente.historiaClinicaDTO.g20p5.siNo == false){
                            vm.paciente.historiaClinicaDTO.g20p6.siNo = false;
                        }
                    case vm.paciente.historiaClinicaDTO.g20p6.id:
                        if(vm.paciente.historiaClinicaDTO.g20p6.siNo == false){
                            vm.paciente.historiaClinicaDTO.g20p7.only_detalle = null;
                            vm.paciente.historiaClinicaDTO.g20p8.siNo = false;
                            vm.paciente.historiaClinicaDTO.g20p9.siNo = false;
                        }
                    case vm.paciente.historiaClinicaDTO.g20p9.id:
                        if(vm.paciente.historiaClinicaDTO.g20p9.siNo == false){
                            vm.paciente.historiaClinicaDTO.g20p10.only_detalle = null;
                            vm.paciente.historiaClinicaDTO.g20p11.only_detalle = null;
                        }
                        break;
                    case vm.paciente.historiaClinicaDTO.g20p13.id:
                        if(vm.paciente.historiaClinicaDTO.g20p13.siNo == false){
                            vm.paciente.historiaClinicaDTO.g20p14.only_detalle = null;
                        }
                        break;
                    case vm.paciente.historiaClinicaDTO.g21p1.id:
                        if(vm.paciente.historiaClinicaDTO.g21p1.siNo == false){
                            vm.paciente.historiaClinicaDTO.g21p2.fecha = null;
                            vm.paciente.historiaClinicaDTO.g21p3.siNo = false;
                            vm.paciente.historiaClinicaDTO.g21p4.siNo = false;
                            vm.paciente.historiaClinicaDTO.g21p5.only_detalle = null;
                            vm.paciente.historiaClinicaDTO.g21p6.siNo = false;
                            vm.paciente.historiaClinicaDTO.g21p7.only_detalle = null;
                            vm.paciente.historiaClinicaDTO.g21p8.only_detalle = null;
                            vm.paciente.historiaClinicaDTO.g21p9.only_detalle = null;
                            vm.paciente.historiaClinicaDTO.g21p10.fecha = null;
                            vm.paciente.historiaClinicaDTO.g21p11.only_detalle = null;
                            vm.paciente.historiaClinicaDTO.g21p12.only_detalle = null;
                            vm.paciente.historiaClinicaDTO.g21p13.siNo = false;
                        }
                    case vm.paciente.historiaClinicaDTO.g21p4.id:
                        if(vm.paciente.historiaClinicaDTO.g21p4.siNo == false){
                            vm.paciente.historiaClinicaDTO.g21p5.only_detalle = null;
                        }
                    case vm.paciente.historiaClinicaDTO.g21p6.id:
                        if(vm.paciente.historiaClinicaDTO.g21p6.siNo == false){
                            vm.paciente.historiaClinicaDTO.g21p7.only_detalle = null;
                            vm.paciente.historiaClinicaDTO.g21p8.only_detalle = null;
                        }
                        break;
                    case vm.paciente.historiaClinicaDTO.g22p1.id:
                        if(vm.paciente.historiaClinicaDTO.g22p1.siNo == false){
                            vm.paciente.historiaClinicaDTO.g22p2.only_detalle = null;
                            vm.paciente.historiaClinicaDTO.g22p3.siNo = false;
                            vm.paciente.historiaClinicaDTO.g22p4.only_detalle = null;
                            vm.paciente.historiaClinicaDTO.g22p5.only_detalle = null;
                        }
                    case vm.paciente.historiaClinicaDTO.g22p3.id:
                        if(vm.paciente.historiaClinicaDTO.g22p3.siNo == false){
                            vm.paciente.historiaClinicaDTO.g22p4.only_detalle = null;
                            vm.paciente.historiaClinicaDTO.g22p5.only_detalle = null;
                        }
                        break;
                    case vm.paciente.historiaClinicaDTO.g23p1.id:
                        if(vm.paciente.historiaClinicaDTO.g23p1.siNo == false){
                            vm.paciente.historiaClinicaDTO.g23p2.only_detalle = null;
                        }
                        break;
                    case vm.paciente.historiaClinicaDTO.g24p1.id:
                        if(vm.paciente.historiaClinicaDTO.g24p1.siNo == false){
                            vm.paciente.historiaClinicaDTO.g24p2.only_detalle = null;
                        }
                        break;
                    case vm.paciente.historiaClinicaDTO.g25p1.id:
                        if(vm.paciente.historiaClinicaDTO.g25p1.siNo == false){
                            vm.paciente.historiaClinicaDTO.g25p2.only_detalle = null;
                            vm.paciente.historiaClinicaDTO.g25p3.checked = null;
                            vm.paciente.historiaClinicaDTO.g25p4.checked = null;
                            vm.paciente.historiaClinicaDTO.g25p5.checked = null;
                        }
                        break;
                    case vm.paciente.historiaClinicaDTO.g27p2.id:
                        if(vm.paciente.historiaClinicaDTO.g27p2.siNo == false){
                            vm.paciente.historiaClinicaDTO.g27p3.only_detalle = null;
                            vm.paciente.historiaClinicaDTO.g27p4.only_detalle = null;
                        }
                        break;
                    case vm.paciente.historiaClinicaDTO.g28p3.id:
                        if(vm.paciente.historiaClinicaDTO.g28p3.siNo == false){
                            vm.paciente.historiaClinicaDTO.g28p4.only_detalle = null;
                        }
                        break;
                    case vm.paciente.historiaClinicaDTO.g28p5.id:
                        if(vm.paciente.historiaClinicaDTO.g28p5.siNo == false){
                            vm.paciente.historiaClinicaDTO.g28p6.only_detalle = null;
                        }
                        break;
                    case vm.paciente.historiaClinicaDTO.g29p2.id:
                        if(vm.paciente.historiaClinicaDTO.g29p2.siNo == false){
                            vm.paciente.historiaClinicaDTO.g29p3.checked = null;
                            vm.paciente.historiaClinicaDTO.g29p4.checked = null;
                            vm.paciente.historiaClinicaDTO.g29p5.checked = null;
                        }
                        break;
                }
                console.log("item: " +  idCampo);
            }
            //Adicionales
//            $scope.$on('$viewContentLoaded', function(){
//                alert("Content loaded! puto el que lee");
//                            //Here your view content is fully loaded !! })
//            });
            $scope.$on('$stateChangeStart',
                function (event, toState, toParams, fromState, fromParams) {
                    if (!angular.equals($state.current, toState)) {
                        delete $rootScope.persistedOperation;
                    }
                })
        }]);
