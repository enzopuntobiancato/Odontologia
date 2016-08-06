/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 29/12/15
 * Time: 12:04
 * To change this template use File | Settings | File Templates.
 */
var module = angular.module('historiaClinicaModule');

module.controller('HistoriaClinicaCtrl_Edit', ['$scope', '$rootScope', 'HistoriaClinicaSrv', '$state', 'MessageSrv', 'historiaClinicaResponse',
    function ($scope, $rootScope, service, $state, message, historiaClinicaResponse) {
        var vm = this;
        vm.save = save;
        vm.goIndex = goIndex;
        vm.paciente = historiaClinicaResponse.data;
        vm.submitted = false;
        vm.limpiarCampos = limpiarCampos;

        var performSubmit = $scope.$parent.performSubmit;
        var handleError = $scope.$parent.handleError;
        vm.validationErrorFromServer = $scope.$parent.validationErrorFromServer;
        vm.data = {
            disableFields: false,
            persistedOperation: $rootScope.persistedOperation || false,
            saved: false
        };

        function save(form) {
            vm.submitted = true;
            performSubmit(function () {
                service.save(vm.paciente.historiaClinicaDTO)
                    .success(function () {
                        vm.data.persistedOperation = true;
                        vm.data.disableFields = true;
                        vm.data.saved = true;
                        message.successMessage("Historia clínica de " + vm.paciente.apellido +
                            ", " + vm.paciente.nombre + " editada con éxito");
                        vm.goIndex();
                    }).error(function (data, status) {
                        handleError(data, status);
                    })
            }, form)
        }

        function goIndex() {
            $state.go('^.index');
        }

        function getWatchers(root) {
            root = angular.element(root || document.documentElement);
            var watcherCount = 0;

            function getElemWatchers(element) {
                var isolateWatchers = getWatchersFromScope(element.data().$isolateScope);
                var scopeWatchers = getWatchersFromScope(element.data().$scope);
                var watchers = scopeWatchers.concat(isolateWatchers);
                angular.forEach(element.children(), function (childElement) {
                    watchers = watchers.concat(getElemWatchers(angular.element(childElement)));
                });
                return watchers;
            }

            function getWatchersFromScope(scope) {
                if (scope) {
                    return scope.$$watchers || [];
                } else {
                    return [];
                }
            }

            return getElemWatchers(root);
        }

        vm.dial = function(id){
            alert("Alerta: " + id);
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

        //WATCHERS
//        $scope.$watch('vm.paciente.historiaClinicaDTO.g1p1.siNo', function () {
//            if (vm.paciente.historiaClinicaDTO.g1p1.siNo == false) {
//                vm.paciente.historiaClinicaDTO.g1p2.only_detalle = null;
//                vm.paciente.historiaClinicaDTO.g1p3.fecha = null;
//            }
//        });
//
//        $scope.$watch('vm.paciente.historiaClinicaDTO.g2p1.siNo', function () {
//            if (vm.paciente.historiaClinicaDTO.g2p1.siNo == false) {
//                vm.paciente.historiaClinicaDTO.g2p2.only_detalle = null;
//            }
//        });
//
//        $scope.$watch('vm.paciente.historiaClinicaDTO.g2p2.siNo', function () {
//            if (vm.paciente.historiaClinicaDTO.g2p2.siNo == false) {
//                vm.paciente.historiaClinicaDTO.g2p3.only_detalle = null;
//                vm.paciente.historiaClinicaDTO.g2p4.fecha = null;
//            }
//        });
//
//        $scope.$watch('vm.paciente.historiaClinicaDTO.g3p1.siNo', function () {
//            if (vm.paciente.historiaClinicaDTO.g3p1.siNo == false) {
//                vm.paciente.historiaClinicaDTO.g3p2.siNo = false;
//            }
//        });
//
//        $scope.$watch('vm.paciente.historiaClinicaDTO.g3p2.siNo', function () {
//            if (vm.paciente.historiaClinicaDTO.g3p2.siNo == false || vm.paciente.historiaClinicaDTO.g3p2.siNo == null) {
//                vm.paciente.historiaClinicaDTO.g3p3.only_detalle = null;
//                vm.paciente.historiaClinicaDTO.g3p4.fecha = null;
//                vm.paciente.historiaClinicaDTO.g3p5.only_detalle = null;
//                vm.paciente.historiaClinicaDTO.g3p6.siNo = false;
//                vm.paciente.historiaClinicaDTO.g3p7.siNo = false;
//                vm.paciente.historiaClinicaDTO.g3p8.only_detalle = null;
//                vm.paciente.historiaClinicaDTO.g3p9.only_detalle = null;
//                vm.paciente.historiaClinicaDTO.g3p10.fecha = null;
//                vm.paciente.historiaClinicaDTO.g3p7.siNo = false;
//            }
//        });
//
//        $scope.$watch('vm.paciente.historiaClinicaDTO.g3p6.siNo', function () {
//            if (vm.paciente.historiaClinicaDTO.g3p6.siNo == false) {
//                vm.paciente.historiaClinicaDTO.g3p7.siNo = false;
//            }
//        });
//
//        $scope.$watch('vm.paciente.historiaClinicaDTO.g3p7.siNo', function () {
//            if (vm.paciente.historiaClinicaDTO.g3p7.siNo == false) {
//                vm.paciente.historiaClinicaDTO.g3p8.only_detalle = null;
//                vm.paciente.historiaClinicaDTO.g3p9.only_detalle = null;
//                vm.paciente.historiaClinicaDTO.g3p10.fecha = null;
//            }
//        });
//
//        $scope.$watch('vm.paciente.historiaClinicaDTO.g4p1.siNo', function () {
//            if (vm.paciente.historiaClinicaDTO.g4p1.siNo == false) {
//                vm.paciente.historiaClinicaDTO.g4p2.siNo = false;
//            }
//        });
//
//        $scope.$watch('vm.paciente.historiaClinicaDTO.g4p2.siNo', function () {
//            if (vm.paciente.historiaClinicaDTO.g4p2.siNo == false) {
//                vm.paciente.historiaClinicaDTO.g4p3.only_detalle = null;
//                vm.paciente.historiaClinicaDTO.g4p4.fecha = null;
//            }
//        });
//
//        $scope.$watch('vm.paciente.historiaClinicaDTO.g5p1.siNo', function () {
//            if (vm.paciente.historiaClinicaDTO.g5p1.siNo == false) {
//                vm.paciente.historiaClinicaDTO.g5p2.only_detalle = null;
//                vm.paciente.historiaClinicaDTO.g5p3.fecha = null;
//                vm.paciente.historiaClinicaDTO.g5p8.siNo = false;
//            }
//        });
//
//        $scope.$watch('vm.paciente.historiaClinicaDTO.g5p4.siNo', function () {
//            if (vm.paciente.historiaClinicaDTO.g5p4.siNo == false) {
//                vm.paciente.historiaClinicaDTO.g5p5.siNo = false;
//            }
//        });

//        $scope.$watch('vm.paciente.historiaClinicaDTO.g5p5.siNo', function () {
//            if (vm.paciente.historiaClinicaDTO.g5p5.siNo == false) {
//                vm.paciente.historiaClinicaDTO.g5p6.only_detalle = null;
//                vm.paciente.historiaClinicaDTO.g5p7.fecha = null;
//            }
//        });
//        $scope.$watch('vm.paciente.historiaClinicaDTO.g5p8.siNo', function () {
//            if (vm.paciente.historiaClinicaDTO.g5p8.siNo == false) {
//                vm.paciente.historiaClinicaDTO.g5p9.fecha = null;
//            }
//        });
//
//        $scope.$watch('vm.paciente.historiaClinicaDTO.g6p3.siNo', function () {
//            if (vm.paciente.historiaClinicaDTO.g6p3.siNo == false) {
//                vm.paciente.historiaClinicaDTO.g6p4.only_detalle = null;
//            }
//        });
//
//        $scope.$watch('vm.paciente.historiaClinicaDTO.g8p1.siNo', function () {
//            if (vm.paciente.historiaClinicaDTO.g8p1.siNo == false) {
//                vm.paciente.historiaClinicaDTO.g8p2.checked = null;
//                vm.paciente.historiaClinicaDTO.g8p3.checked = null;
//                vm.paciente.historiaClinicaDTO.g8p4.checked = null;
//                vm.paciente.historiaClinicaDTO.g8p5.checked = null;
//                vm.paciente.historiaClinicaDTO.g8p6.checked = null;
//            }
//        });
//
//        $scope.$watch('vm.paciente.historiaClinicaDTO.g9p1.siNo', function () {
//            if (vm.paciente.historiaClinicaDTO.g9p1.siNo == false) {
//                vm.paciente.historiaClinicaDTO.g9p2.only_detalle = null;
//                vm.paciente.historiaClinicaDTO.g9p3.only_detalle = null;
//                vm.paciente.historiaClinicaDTO.g9p4.fecha = null;
//            }
//        });
//
//        $scope.$watch('vm.paciente.historiaClinicaDTO.g10p1.siNo', function () {
//            if (vm.paciente.historiaClinicaDTO.g10p1.siNo == false) {
//                vm.paciente.historiaClinicaDTO.g10p2.only_detalle = null;
//                vm.paciente.historiaClinicaDTO.g10p3.only_detalle = null;
//                vm.paciente.historiaClinicaDTO.g10p3.fecha = null;
//            }
//        });
//
//        $scope.$watch('vm.paciente.historiaClinicaDTO.g11p1.siNo', function () {
//            if (vm.paciente.historiaClinicaDTO.g11p1.siNo == false) {
//                vm.paciente.historiaClinicaDTO.g11p4.siNo = false;
//                vm.paciente.historiaClinicaDTO.g11p2.checked = null;
//                vm.paciente.historiaClinicaDTO.g11p3.checked = null;
//                vm.paciente.historiaClinicaDTO.g11p5.only_detalle = null;
//                vm.paciente.historiaClinicaDTO.g11p6.fecha = null;
//            }
//        });
//
//        $scope.$watch('vm.paciente.historiaClinicaDTO.g11p4.siNo', function () {
//            if (vm.paciente.historiaClinicaDTO.g11p4.siNo == false) {
//                vm.paciente.historiaClinicaDTO.g11p5.only_detalle = null;
//                vm.paciente.historiaClinicaDTO.g11p6.fecha = null;
//            }
//        });
//
//        $scope.$watch('vm.paciente.historiaClinicaDTO.g12p1.siNo', function () {
//            if (vm.paciente.historiaClinicaDTO.g12p1.siNo == false) {
//                vm.paciente.historiaClinicaDTO.g12p2.only_detalle = null;
//            }
//        });
//
//        $scope.$watch('vm.paciente.historiaClinicaDTO.g13p1.siNo', function () {
//            if (vm.paciente.historiaClinicaDTO.g13p1.siNo == false) {
//                vm.paciente.historiaClinicaDTO.g13p2.fecha = null;
//                vm.paciente.historiaClinicaDTO.g13p3.only_detalle = null;
//                vm.paciente.historiaClinicaDTO.g13p4.only_detalle = null;
//            }
//        });
//
//        $scope.$watch('vm.paciente.historiaClinicaDTO.g14p1.siNo', function () {
//            if (vm.paciente.historiaClinicaDTO.g14p1.siNo == false) {
//                vm.paciente.historiaClinicaDTO.g14p2.only_detalle = null;
//                vm.paciente.historiaClinicaDTO.g14p3.fecha = null;
//                vm.paciente.historiaClinicaDTO.g14p4.only_detalle = null;
//            }
//        });
//
//        $scope.$watch('vm.paciente.historiaClinicaDTO.g15p1.siNo', function () {
//            if (vm.paciente.historiaClinicaDTO.g15p1.siNo == false) {
//                vm.paciente.historiaClinicaDTO.g15p2.only_detalle = null;
//            }
//        });
//
//        $scope.$watch('vm.paciente.historiaClinicaDTO.g15p3.siNo', function () {
//            if (vm.paciente.historiaClinicaDTO.g15p3.siNo == false) {
//                vm.paciente.historiaClinicaDTO.g15p4.fecha = null;
//            }
//        });
//
//
//        $scope.$watch('vm.paciente.historiaClinicaDTO.g15p6.siNo', function () {
//            if (vm.paciente.historiaClinicaDTO.g15p6.siNo == false) {
//                vm.paciente.historiaClinicaDTO.g15p7.only_detalle = null;
//                vm.paciente.historiaClinicaDTO.g15p8.fecha = null;
//                vm.paciente.historiaClinicaDTO.g15p9.only_detalle = null;
//            }
//        });
//
//
//        $scope.$watch('vm.paciente.historiaClinicaDTO.g15p10.siNo', function () {
//            if (vm.paciente.historiaClinicaDTO.g15p10.siNo == false) {
//                vm.paciente.historiaClinicaDTO.g15p12.fecha = null;
//                vm.paciente.historiaClinicaDTO.g15p13.only_detalle = null;
//                vm.paciente.historiaClinicaDTO.g15p11.only_detalle = null;
//            }
//        });
//
//
//        $scope.$watch('vm.paciente.historiaClinicaDTO.g16p1.siNo', function () {
//            if (vm.paciente.historiaClinicaDTO.g16p1.siNo == false) {
//                vm.paciente.historiaClinicaDTO.g16p2.only_detalle = null;
//                vm.paciente.historiaClinicaDTO.g16p3.fecha = null;
//            }
//        });
//
//
//        $scope.$watch('vm.paciente.historiaClinicaDTO.g17p1.siNo', function () {
//            if (vm.paciente.historiaClinicaDTO.g17p1.siNo == false) {
//                vm.paciente.historiaClinicaDTO.g17p2.only_detalle = null;
//                vm.paciente.historiaClinicaDTO.g17p3.only_detalle = null;
//                vm.paciente.historiaClinicaDTO.g17p4.fecha = null;
//                vm.paciente.historiaClinicaDTO.g17p5.siNo = false;
//                vm.paciente.historiaClinicaDTO.g17p6.fecha = null;
//                vm.paciente.historiaClinicaDTO.g17p7.siNo = false;
//            }
//        });
//
//        $scope.$watch('vm.paciente.historiaClinicaDTO.g18p1.siNo', function () {
//            if (vm.paciente.historiaClinicaDTO.g18p1.siNo == false) {
//                vm.paciente.historiaClinicaDTO.g18p2.only_detalle = null;
//                vm.paciente.historiaClinicaDTO.g18p3.checked = null;
//                vm.paciente.historiaClinicaDTO.g18p4.checked = null;
//                vm.paciente.historiaClinicaDTO.g18p5.checked = null;
//            }
//        });
//
//        $scope.$watch('vm.paciente.historiaClinicaDTO.g19p1.siNo', function () {
//            if (vm.paciente.historiaClinicaDTO.g19p1.siNo == false) {
//                vm.paciente.historiaClinicaDTO.g19p2.only_detalle = null;
//                vm.paciente.historiaClinicaDTO.g19p3.fecha = null;
//                vm.paciente.historiaClinicaDTO.g19p4.only_detalle = null;
//            }
//        });
//
//        $scope.$watch('vm.paciente.historiaClinicaDTO.g19p6.siNo', function () {
//            if (vm.paciente.historiaClinicaDTO.g19p6.siNo == false) {
//                vm.paciente.historiaClinicaDTO.g19p9.fecha = null;
//                vm.paciente.historiaClinicaDTO.g19p7.only_detalle = null;
//                vm.paciente.historiaClinicaDTO.g19p8.only_detalle = null;
//            }
//        });
//
//        $scope.$watch('vm.paciente.historiaClinicaDTO.g20p2.siNo', function () {
//            if (vm.paciente.historiaClinicaDTO.g20p2.siNo == false) {
//                vm.paciente.historiaClinicaDTO.g20p3.only_detalle = null;
//                vm.paciente.historiaClinicaDTO.g20p4.fecha = null;
//            }
//        });
//
//        $scope.$watch('vm.paciente.historiaClinicaDTO.g20p5.siNo', function () {
//            if (vm.paciente.historiaClinicaDTO.g20p5.siNo == false) {
//                vm.paciente.historiaClinicaDTO.g20p6.siNo = false;
//            }
//        });
//
//        $scope.$watch('vm.paciente.historiaClinicaDTO.g20p6.siNo', function () {
//            if (vm.paciente.historiaClinicaDTO.g20p6.siNo == false) {
//                vm.paciente.historiaClinicaDTO.g20p7.only_detalle = null;
//                vm.paciente.historiaClinicaDTO.g20p8.siNo = false;
//                vm.paciente.historiaClinicaDTO.g20p9.siNo = false;
//            }
//        });
//
//        $scope.$watch('vm.paciente.historiaClinicaDTO.g20p9.siNo', function () {
//            if (vm.paciente.historiaClinicaDTO.g20p9.siNo == false) {
//                vm.paciente.historiaClinicaDTO.g20p10.only_detalle = null;
//            }
//        });
//
//        $scope.$watch('vm.paciente.historiaClinicaDTO.g21p1.siNo', function () {
//            if (vm.paciente.historiaClinicaDTO.g21p1.siNo == false) {
//                vm.paciente.historiaClinicaDTO.g21p2.fecha = null;
//                vm.paciente.historiaClinicaDTO.g21p3.siNo = false;
//                vm.paciente.historiaClinicaDTO.g21p4.siNo = false;
//                vm.paciente.historiaClinicaDTO.g21p5.only_detalle = null;
//                vm.paciente.historiaClinicaDTO.g21p6.siNo = false;
//                vm.paciente.historiaClinicaDTO.g21p7.only_detalle = null;
//                vm.paciente.historiaClinicaDTO.g21p8.only_detalle = null;
//                vm.paciente.historiaClinicaDTO.g21p9.only_detalle = null;
//                vm.paciente.historiaClinicaDTO.g21p10.fecha = null;
//                vm.paciente.historiaClinicaDTO.g21p11.only_detalle = null;
//                vm.paciente.historiaClinicaDTO.g21p12.only_detalle = null;
//                vm.paciente.historiaClinicaDTO.g21p13.siNo = false;
//            }
//        });
//
//        $scope.$watch('vm.paciente.historiaClinicaDTO.g21p4.siNo', function () {
//            if (vm.paciente.historiaClinicaDTO.g21p4siNo == false) {
//                vm.paciente.historiaClinicaDTO.g21p5.only_detalle = null;
//            }
//        });
//
//        $scope.$watch('vm.paciente.historiaClinicaDTO.g21p6.siNo', function () {
//            if (vm.paciente.historiaClinicaDTO.g21p6.siNo == false) {
//                vm.paciente.historiaClinicaDTO.g21p7.only_detalle = null;
//                vm.paciente.historiaClinicaDTO.g21p8.only_detalle = null;
//            }
//        });
//
//        $scope.$watch('vm.paciente.historiaClinicaDTO.g22p1.siNo', function () {
//            if (vm.paciente.historiaClinicaDTO.g22p1.siNo == false) {
//                vm.paciente.historiaClinicaDTO.g22p2.only_detalle = null;
//                vm.paciente.historiaClinicaDTO.g22p3.siNo = false;
//                vm.paciente.historiaClinicaDTO.g22p4.only_detalle = null;
//                vm.paciente.historiaClinicaDTO.g22p5.only_detalle = null;
//            }
//        });
//
//        $scope.$watch('vm.paciente.historiaClinicaDTO.g22p3.siNo', function () {
//            if (vm.paciente.historiaClinicaDTO.g22p3.siNo == false) {
//                vm.paciente.historiaClinicaDTO.g22p4.only_detalle = null;
//                vm.paciente.historiaClinicaDTO.g22p5.only_detalle = null;
//            }
//        });
//
//        $scope.$watch('vm.paciente.historiaClinicaDTO.g23p1.siNo', function () {
//            if (vm.paciente.historiaClinicaDTO.g23p1.siNo == false) {
//                vm.paciente.historiaClinicaDTO.g23p2.only_detalle = null;
//            }
//        });
//        $scope.$watch('vm.paciente.historiaClinicaDTO.g24p1.siNo', function () {
//            if (vm.paciente.historiaClinicaDTO.g24p1.siNo == false) {
//                vm.paciente.historiaClinicaDTO.g24p2.only_detalle = null;
//            }
//        });
//
//        $scope.$watch('vm.paciente.historiaClinicaDTO.g25p1.siNo', function () {
//            if (vm.paciente.historiaClinicaDTO.g25p1.siNo == false) {
//                vm.paciente.historiaClinicaDTO.g25p2.only_detalle = null;
//                vm.paciente.historiaClinicaDTO.g25p3.checked = null;
//                vm.paciente.historiaClinicaDTO.g25p4.checked = null;
//                vm.paciente.historiaClinicaDTO.g25p5.checked = null;
//            }
//        });
//
//        $scope.$watch('vm.paciente.historiaClinicaDTO.g27p2.siNo', function () {
//            if (vm.paciente.historiaClinicaDTO.g27p2.siNo == false) {
//                vm.paciente.historiaClinicaDTO.g27p3.only_detalle = null;
//                vm.paciente.historiaClinicaDTO.g27p4.only_detalle = null;
//            }
//        });
//
//        $scope.$watch('vm.paciente.historiaClinicaDTO.g28p3.siNo', function () {
//            if (vm.paciente.historiaClinicaDTO.g28p3.siNo == false) {
//                vm.paciente.historiaClinicaDTO.g28p4.only_detalle = null;
//            }
//        });
//
//        $scope.$watch('vm.paciente.historiaClinicaDTO.g28p5.siNo', function () {
//            if (vm.paciente.historiaClinicaDTO.g28p5.siNo == false) {
//                vm.paciente.historiaClinicaDTO.g28p6.only_detalle = null;
//            }
//        });
//
//        $scope.$watch('vm.paciente.historiaClinicaDTO.g29p2.siNo', function () {
//            if (vm.paciente.historiaClinicaDTO.g29p2.siNo == false) {
//                vm.paciente.historiaClinicaDTO.g29p3.checked = null;
//                vm.paciente.historiaClinicaDTO.g29p4.checked = null;
//                vm.paciente.historiaClinicaDTO.g29p5.checked = null;
//            }
//        });
    }]);