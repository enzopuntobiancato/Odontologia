var module = angular.module('pacienteModule');

module.controller('PacienteCtrl_EditCreate',
    ['$scope', '$rootScope', '$filter','PacienteSrv', '$state', 'MessageSrv',
        'tiposDocumentoResponse','sexosResponse','provinciaResponse', 'ciudadesResponse','barriosResponse','estadosResponse',
        'trabajosResponse','obrasSocialesResponse','nivelesEstudioResponse','nacionalidadesResponse', 'pacienteResponse',
        'Upload', '$q', 'imagenResponse',
        function ($scope, $rootScope, $filter, service, $state, message,
                  tiposDocumentoResponse,sexosResponse,provinciaResponse,ciudadesResponse,barriosResponse,estadosResponse,
                  trabajosResponse,obrasSocialesResponse,nivelesEstudioResponse,nacionalidadesResponse,pacienteResponse,
                  Upload, $q, imagenResponse
            ) {
            var vm = this;
            vm.paciente= pacienteResponse.data;
            vm.submitted = false;
            vm.file = imagenResponse.data;

            var today = new Date();

            vm.restrictedDate = {
                maxDate: new Date(today.getFullYear() - 3, 0, 1),
                minDate: new Date(1900, 0, 1)
            };

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
                wrongImageFormat: false
            }

            var performSubmit = $scope.$parent.performSubmit;
            var handleError = $scope.$parent.handleError;
            vm.validationErrorFromServer = $scope.$parent.validationErrorFromServer;

            vm.create = create;
            vm.goIndex = goIndex;
            vm.provinciaLugarDeNacimientoChange = provinciaLugarDeNacimientoChange;
            vm.provinciaDomicilioChange = provinciaDomicilioChange;
            vm.ciudadDomicilioChange = ciudadDomicilioChange;
            vm.domicilioRequired = domicilioRequired;
            vm.limpiarCampos = limpiarCampos;
            vm.isFileSelected = isFileSelected;
            vm.fileChanged = fileChanged;
            vm.deleteImage = deleteImage;

            init();

            $scope.$on('validatedForm', function(event, args) {
                event.defaultPrevented = true;
                if (args.form.$valid) {
                    performSubmit(function(){
                        doSave()
                            .then(function(response){
                                var paciente = response.data;
                                message.successMessage(paciente.apellido + ", " + paciente.nombre + " editado con éxito");
                                if  (args.continueEditing) {
                                    $state.go($state.current, {editing: true}, {reload: true})
                                } else {
                                    $state.go($state.current.name.replace('Edit', 'View'),  {editing: false});
                                }
                            }, function(response) {
                                vm.validationErrorFromServer;
                                handleError(response.data,response.status);
                            });
                    },args.form);
                } else {
                    message.errorMessage('Hay errores en los datos cargados. Por favor, revise los mismos.')
                    vm.submitted = true;
                    var mainForm = $scope.hcForm;
                    datosPersonalesForms(mainForm);
//                    setFormErrorsTouched(mainForm.G1);
//                    setFormErrorsTouched(mainForm.G2);
//                    setFormErrorsTouched(mainForm.G3);
//                    setFormErrorsTouched(mainForm.G4);
//                    setFormErrorsTouched(mainForm.G5);
//                    setFormErrorsTouched(mainForm.G6);
//                    setFormErrorsTouched(mainForm.G10);
//                    setFormErrorsTouched(mainForm.G11);
//                    setFormErrorsTouched(mainForm.G12);
//                    setFormErrorsTouched(mainForm.G13);
//                    setFormErrorsTouched(mainForm.G14);
//                    setFormErrorsTouched(mainForm.G15);
//                    setFormErrorsTouched(mainForm.G16);
//                    setFormErrorsTouched(mainForm.G17);
//                    setFormErrorsTouched(mainForm.G18);
//                    setFormErrorsTouched(mainForm.G19);
//                    setFormErrorsTouched(mainForm.G20);
//                    setFormErrorsTouched(mainForm.G21);
//                    setFormErrorsTouched(mainForm.G22);
//                    setFormErrorsTouched(mainForm.G23);
//                    setFormErrorsTouched(mainForm.G27);
//                    setFormErrorsTouched(mainForm.G28);
//                    setFormErrorsTouched(mainForm.G29);
                }
            });

            function doSave() {
                var deferred = $q.defer();
                service.save(vm.paciente)
                    .then(function (response) {
                        Upload.upload({
                            url: 'api/paciente/savePacienteImage/' + response.data.id,
                            data: {file: vm.file}
                        })
                            .then(function (response) {
                                deferred.resolve(response);
                            }, function (response) {
                                deferred.reject(response);
                            })
                    }, function (response) {
                        deferred.reject(response);
                    })

                return deferred.promise;
            }

            function create(form){
                if (form.$valid) {
                    performSubmit(function(){
                        doSave()
                            .then(function(response){
                                message.successMessage(vm.paciente.nombre + " creado con éxito");
                                $rootScope.created = true;
                                $state.go('historiaClinica', {id: response.data.id});
                            }, function(response) {
                                handleError(response.data,response.status);
                            });
                    },form);
                } else {
                    vm.submitted = true;
                    datosPersonalesForms($scope.createPacienteForm);
                }
            }

            function datosPersonalesForms(mainForm) {
                var forms = [];
                if (mainForm) {
                    forms.push(mainForm.datosPersonalesForm);
                    forms.push(mainForm.domicilioContactoForm);
                    forms.push(mainForm.trabajoForm);
                    forms.push(mainForm.datosMedicosForm);
                    forms.push(mainForm.otrosDatosForm);
                    angular.forEach(forms, function(item) {
                        setFormErrorsTouched(item);
                    })
                }
            }

            function setFormErrorsTouched(form) {
                if (form) {
                    angular.forEach(form.$error, function (field) {
                        angular.forEach(field, function (errorField) {
                            errorField.$setTouched();
                        })
                    });
                }
            }

            function init() {
                if (angular.isDefined(vm.paciente.lugarDeNacimiento)) {
                    vm.selectedProvincia = vm.paciente.lugarDeNacimiento.provincia;
                    vm.ciudadesNacSelect = provinciaChange(vm.selectedProvincia);
                }
                if (angular.isDefined(vm.paciente.domicilio)) {
                    vm.provinciaDomicilio = vm.paciente.domicilio.barrio.ciudad.provincia;
                    vm.ciudadesDomicilioSelect = provinciaChange(vm.provinciaDomicilio);
                    vm.ciudadDomicilio = vm.paciente.domicilio.barrio.ciudad;
                    vm.barriosSelect = $filter('filter')(vm.data.barrios, function(barrio) {
                        return barrio.ciudad.id == vm.ciudadDomicilio.id;
                    });
                }
            }

            function isFileSelected() {
                return vm.file &&
                    angular.isDefined(vm.file) &&
                    vm.file != null &&
                    vm.file.size > 0
            }

            function fileChanged($files, $file, $newFiles, $duplicateFiles, $invalidFiles, $event) {
                if ($file) {
                    vm.file = $file;
                }
                if ($invalidFiles.length) {
                    vm.data.wrongImageFormat = true;
                    $timeout(function () {
                        vm.data.wrongImageFormat = false;
                    }, 3000);
                }
            }

            function deleteImage() {
                vm.file = undefined;
            }

            function provinciaLugarDeNacimientoChange() {
                vm.paciente.lugarDeNacimiento = null;
                vm.ciudadesNacSelect = provinciaChange(vm.selectedProvincia);
            }

            function provinciaDomicilioChange() {
                if(vm.paciente.domicilio) {
                    vm.paciente.domicilio.barrio = null;
                }
                vm.ciudadesDomicilioSelect = provinciaChange(vm.provinciaDomicilio);
            }

            function ciudadDomicilioChange() {
                if(vm.paciente.domicilio) {
                    vm.paciente.domicilio.barrio = null;
                }
                vm.barriosSelect = $filter('filter')(vm.data.barrios, function(barrio) {
                    return barrio.ciudad.id == vm.ciudadDomicilio.id;
                });
            }

            function provinciaChange(selectedProvincia) {
                return $filter('filter')(vm.data.ciudades, function(ciudad) {
                    return ciudad.provincia.id == selectedProvincia.id;
                });
            }

            function domicilioRequired() {
                if (vm.paciente.domicilio) {
                    for (var prop in vm.paciente.domicilio) {
                        if (vm.paciente.domicilio.hasOwnProperty(prop)) {
                            return true;
                        }
                    }
                }
                return false;
            }


            function goIndex() {
                $state.go('paciente.index', {execQuery: $rootScope.created});
            }

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
        }]);
