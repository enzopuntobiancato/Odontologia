/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 29/12/15
 * Time: 12:04
 * To change this template use File | Settings | File Templates.
 */
var module = angular.module('historiaClinicaModule');

module.controller('HistoriaClinicaCtrl_Edit', ['$scope','$rootScope','HistoriaClinicaSrv','$state','MessageSrv','historiaClinicaResponse',
    function ($scope, $rootScope, service, $state, message, historiaClinicaResponse) {
        var vm = this;
        vm.save = save;
        vm.goIndex = goIndex;
        vm.paciente = historiaClinicaResponse.data;
        vm.submitted = false;

        var performSubmit = $scope.$parent.performSubmit;
        var handleError = $scope.$parent.handleError;
        vm.validationErrorFromServer = $scope.$parent.validationErrorFromServer;
        vm.data = {
            disableFields: false,
            persistedOperation: $rootScope.persistedOperation || false,
            saved: false
        };

        function save(form){
            vm.submitted = true;
            performSubmit(function(){
                service.save(vm.paciente.historiaClinicaDTO2)
                    .success(function(){
                        vm.data.persistedOperation = true;
                        vm.data.disableFields = true;
                        vm.data.saved = true;
                        message.successMessage("Historia clínica de " + vm.paciente.apellido +
                            ", " + vm.paciente.nombre + " editada con éxito");
                        vm.goIndex();
                    }).error(function(data,status){
                        handleError(data,status);
                    })
            },form)
        }

        function goIndex(){
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

        //WATCHERS
        $scope.$watch('vm.paciente.historiaClinicaDTO2.g1p1.siNo',function(){
            if(vm.paciente.historiaClinicaDTO2.g1p1.siNo == false){
               vm.paciente.historiaClinicaDTO2.g1p2.only_detalle = null;
               vm.paciente.historiaClinicaDTO2.g1p3.fecha = null;
            }
        });

        $scope.$watch('vm.paciente.historiaClinicaDTO2.g2p1.siNo',function(){
            if(vm.paciente.historiaClinicaDTO2.g2p1.siNo == false){
                vm.paciente.historiaClinicaDTO2.g2p2.only_detalle = null;
            }
        });

        $scope.$watch('vm.paciente.historiaClinicaDTO2.g2p2.siNo',function(){
            if(vm.paciente.historiaClinicaDTO2.g2p2.siNo == false){
                vm.paciente.historiaClinicaDTO2.g2p3.only_detalle = null;
                vm.paciente.historiaClinicaDTO2.g2p4.fecha = null;
            }
        });

        $scope.$watch('vm.paciente.historiaClinicaDTO2.g3p1.siNo',function(){
            if(vm.paciente.historiaClinicaDTO2.g3p1.siNo == false){
                vm.paciente.historiaClinicaDTO2.g3p2.siNo = null;
            }
        });

        $scope.$watch('vm.paciente.historiaClinicaDTO2.g3p2.siNo',function(){
            if(vm.paciente.historiaClinicaDTO2.g3p2.siNo == false || vm.paciente.historiaClinicaDTO2.g3p2.siNo == null ){
                vm.paciente.historiaClinicaDTO2.g3p3.only_detalle = null;
                vm.paciente.historiaClinicaDTO2.g3p4.fecha = null;
                vm.paciente.historiaClinicaDTO2.g3p5.only_detalle = null;
                vm.paciente.historiaClinicaDTO2.g3p6.siNo = null;
                vm.paciente.historiaClinicaDTO2.g3p7.siNo = null;
                vm.paciente.historiaClinicaDTO2.g3p8.only_detalle = null;
                vm.paciente.historiaClinicaDTO2.g3p9.only_detalle = null;
                vm.paciente.historiaClinicaDTO2.g3p10.fecha = null;
                vm.paciente.historiaClinicaDTO2.g3p7.siNo = null;
            }
        });

        $scope.$watch('vm.paciente.historiaClinicaDTO2.g3p6.siNo',function(){
            if(vm.paciente.historiaClinicaDTO2.g3p6.siNo == false){
                vm.paciente.historiaClinicaDTO2.g3p7.siNo = null;
            }
        });

        $scope.$watch('vm.paciente.historiaClinicaDTO2.g3p7.siNo',function(){
            if(vm.paciente.historiaClinicaDTO2.g3p7.siNo == false){
                vm.paciente.historiaClinicaDTO2.g3p8.only_detalle = null;
                vm.paciente.historiaClinicaDTO2.g3p9.only_detalle = null;
                vm.paciente.historiaClinicaDTO2.g3p10.fecha = null;
            }
        });

        $scope.$watch('vm.paciente.historiaClinicaDTO2.g4p1.siNo',function(){
            if(vm.paciente.historiaClinicaDTO2.g4p1.siNo == false){
                vm.paciente.historiaClinicaDTO2.g4p2.siNo = null;
            }
        });

        $scope.$watch('vm.paciente.historiaClinicaDTO2.g4p2.siNo',function(){
            if(vm.paciente.historiaClinicaDTO2.g4p2.siNo == false){
                vm.paciente.historiaClinicaDTO2.g4p3.only_detalle = null;
                vm.paciente.historiaClinicaDTO2.g4p4.fecha = null;
            }
        });

        $scope.$watch('vm.paciente.historiaClinicaDTO2.g5p1.siNo',function(){
            if(vm.paciente.historiaClinicaDTO2.g5p1.siNo == false){
                vm.paciente.historiaClinicaDTO2.g5p2.only_detalle = null;
                vm.paciente.historiaClinicaDTO2.g5p3.fecha = null;
                vm.paciente.historiaClinicaDTO2.g5p8.siNo = null;
            }
        });

        $scope.$watch('vm.paciente.historiaClinicaDTO2.g5p4.siNo',function(){
            if(vm.paciente.historiaClinicaDTO2.g5p4.siNo == false){
                vm.paciente.historiaClinicaDTO2.g5p5.siNo = null;
            }
        });

        $scope.$watch('vm.paciente.historiaClinicaDTO2.g5p5.siNo',function(){
            if(vm.paciente.historiaClinicaDTO2.g5p5.siNo == false){
                vm.paciente.historiaClinicaDTO2.g5p6.only_detalle = null;
                vm.paciente.historiaClinicaDTO2.g5p7.fecha = null;
            }
        });
        $scope.$watch('vm.paciente.historiaClinicaDTO2.g5p8.siNo',function(){
            if(vm.paciente.historiaClinicaDTO2.g5p8.siNo == false){
                vm.paciente.historiaClinicaDTO2.g5p9.fecha = null;
            }
        });

        $scope.$watch('vm.paciente.historiaClinicaDTO2.g6p3.siNo',function(){
            if(vm.paciente.historiaClinicaDTO2.g6p3.siNo == false){
                vm.paciente.historiaClinicaDTO2.g6p4.only_detalle = null;
            }
        });

        $scope.$watch('vm.paciente.historiaClinicaDTO2.g8p1.siNo',function(){
            if(vm.paciente.historiaClinicaDTO2.g8p1.siNo == false){
                vm.paciente.historiaClinicaDTO2.g8p2.checked = null;
                vm.paciente.historiaClinicaDTO2.g8p3.checked = null;
                vm.paciente.historiaClinicaDTO2.g8p4.checked = null;
                vm.paciente.historiaClinicaDTO2.g8p5.checked = null;
                vm.paciente.historiaClinicaDTO2.g8p6.checked = null;
            }
        });

        $scope.$watch('vm.paciente.historiaClinicaDTO2.g9p1.siNo',function(){
            if(vm.paciente.historiaClinicaDTO2.g9p1.siNo == false){
                vm.paciente.historiaClinicaDTO2.g9p2.only_detalle = null;
                vm.paciente.historiaClinicaDTO2.g9p3.only_detalle = null;
                vm.paciente.historiaClinicaDTO2.g9p4.fecha = null;
            }
        });

        $scope.$watch('vm.paciente.historiaClinicaDTO2.g10p1.siNo',function(){
            if(vm.paciente.historiaClinicaDTO2.g10p1.siNo == false){
                vm.paciente.historiaClinicaDTO2.g10p2.only_detalle = null;
                vm.paciente.historiaClinicaDTO2.g10p3.only_detalle = null;
                vm.paciente.historiaClinicaDTO2.g10p3.fecha = null;
            }
        });

        $scope.$watch('vm.paciente.historiaClinicaDTO2.g11p1.siNo',function(){
            if(vm.paciente.historiaClinicaDTO2.g11p1.siNo == false){
                vm.paciente.historiaClinicaDTO2.g11p4.siNo = null;
                vm.paciente.historiaClinicaDTO2.g11p2.checked = null;
                vm.paciente.historiaClinicaDTO2.g11p3.checked = null;
                vm.paciente.historiaClinicaDTO2.g11p5.only_detalle = null;
                vm.paciente.historiaClinicaDTO2.g11p6.fecha = null;
            }
        });

        $scope.$watch('vm.paciente.historiaClinicaDTO2.g11p4.siNo',function(){
            if(vm.paciente.historiaClinicaDTO2.g11p4.siNo == false){
                vm.paciente.historiaClinicaDTO2.g11p5.only_detalle = null;
                vm.paciente.historiaClinicaDTO2.g11p6.fecha = null;
            }
        });

        $scope.$watch('vm.paciente.historiaClinicaDTO2.g12p1.siNo',function(){
            if(vm.paciente.historiaClinicaDTO2.g12p1.siNo == false){
                vm.paciente.historiaClinicaDTO2.g12p2.only_detalle = null;
            }
        });

        $scope.$watch('vm.paciente.historiaClinicaDTO2.g13p1.siNo',function(){
            if(vm.paciente.historiaClinicaDTO2.g13p1.siNo == false){
                vm.paciente.historiaClinicaDTO2.g13p2.fecha = null;
                vm.paciente.historiaClinicaDTO2.g13p3.only_detalle = null;
                vm.paciente.historiaClinicaDTO2.g13p4.only_detalle = null;
            }
        });

        $scope.$watch('vm.paciente.historiaClinicaDTO2.g14p1.siNo',function(){
            if(vm.paciente.historiaClinicaDTO2.g14p1.siNo == false){
                vm.paciente.historiaClinicaDTO2.g14p2.only_detalle = null;
                vm.paciente.historiaClinicaDTO2.g14p3.fecha = null;
                vm.paciente.historiaClinicaDTO2.g14p4.only_detalle = null;
            }
        });

        $scope.$watch('vm.paciente.historiaClinicaDTO2.g15p1.siNo',function(){
                    if(vm.paciente.historiaClinicaDTO2.g15p1.siNo == false){
                       vm.paciente.historiaClinicaDTO2.g15p2.only_detalle = null;
            }
        });

        $scope.$watch('vm.paciente.historiaClinicaDTO2.g15p3.siNo',function(){
            if(vm.paciente.historiaClinicaDTO2.g15p3.siNo == false){
                vm.paciente.historiaClinicaDTO2.g15p4.fecha = null;
            }
        });


        $scope.$watch('vm.paciente.historiaClinicaDTO2.g15p6.siNo',function(){
            if(vm.paciente.historiaClinicaDTO2.g15p6.siNo == false){
                vm.paciente.historiaClinicaDTO2.g15p7.only_detalle = null;
                vm.paciente.historiaClinicaDTO2.g15p8.fecha = null;
                vm.paciente.historiaClinicaDTO2.g15p9.only_detalle = null;
            }
        });


        $scope.$watch('vm.paciente.historiaClinicaDTO2.g15p10.siNo',function(){
            if(vm.paciente.historiaClinicaDTO2.g15p10.siNo == false){
                vm.paciente.historiaClinicaDTO2.g15p12.fecha = null;
                vm.paciente.historiaClinicaDTO2.g15p13.only_detalle = null;
                vm.paciente.historiaClinicaDTO2.g15p11.only_detalle = null;
            }
        });


        $scope.$watch('vm.paciente.historiaClinicaDTO2.g16p1.siNo',function(){
            if(vm.paciente.historiaClinicaDTO2.g16p1.siNo == false){
                vm.paciente.historiaClinicaDTO2.g16p2.only_detalle = null;
                vm.paciente.historiaClinicaDTO2.g16p3.fecha = null;
            }
        });


        $scope.$watch('vm.paciente.historiaClinicaDTO2.g17p1.siNo',function(){
            if(vm.paciente.historiaClinicaDTO2.g17p1.siNo == false){
                vm.paciente.historiaClinicaDTO2.g17p2.only_detalle = null;
                vm.paciente.historiaClinicaDTO2.g17p3.only_detalle = null;
                vm.paciente.historiaClinicaDTO2.g17p4.fecha = null;
                vm.paciente.historiaClinicaDTO2.g17p5.siNo = null;
                vm.paciente.historiaClinicaDTO2.g17p6.fecha = null;
                vm.paciente.historiaClinicaDTO2.g17p7.siNo = null;
            }
        });

        $scope.$watch('vm.paciente.historiaClinicaDTO2.g18p1.siNo',function(){
            if(vm.paciente.historiaClinicaDTO2.g18p1.siNo == false){
                vm.paciente.historiaClinicaDTO2.g18p2.only_detalle = null;
                vm.paciente.historiaClinicaDTO2.g18p3.checked = null;
                vm.paciente.historiaClinicaDTO2.g18p4.checked = null;
                vm.paciente.historiaClinicaDTO2.g18p5.checked = null;
            }
        });

        $scope.$watch('vm.paciente.historiaClinicaDTO2.g19p1.siNo',function(){
            if(vm.paciente.historiaClinicaDTO2.g19p1.siNo == false){
                vm.paciente.historiaClinicaDTO2.g19p2.only_detalle = null;
                vm.paciente.historiaClinicaDTO2.g19p3.fecha = null;
                vm.paciente.historiaClinicaDTO2.g19p4.only_detalle = null;
            }
        });

        $scope.$watch('vm.paciente.historiaClinicaDTO2.g19p6.siNo',function(){
            if(vm.paciente.historiaClinicaDTO2.g19p6.siNo == false){
                vm.paciente.historiaClinicaDTO2.g19p9.fecha = null;
                vm.paciente.historiaClinicaDTO2.g19p7.only_detalle = null;
                vm.paciente.historiaClinicaDTO2.g19p8.only_detalle = null;
            }
        });

        $scope.$watch('vm.paciente.historiaClinicaDTO2.g20p2.siNo',function(){
            if(vm.paciente.historiaClinicaDTO2.g20p2.siNo == false){
                vm.paciente.historiaClinicaDTO2.g20p3.only_detalle = null;
                vm.paciente.historiaClinicaDTO2.g20p4.fecha = null;
            }
        });

        $scope.$watch('vm.paciente.historiaClinicaDTO2.g20p5.siNo',function(){
            if(vm.paciente.historiaClinicaDTO2.g20p5.siNo == false){
                vm.paciente.historiaClinicaDTO2.g20p6.siNo = null;
            }
        });

        $scope.$watch('vm.paciente.historiaClinicaDTO2.g20p6.siNo',function(){
            if(vm.paciente.historiaClinicaDTO2.g20p6.siNo == false){
                vm.paciente.historiaClinicaDTO2.g20p7.only_detalle = null;
                vm.paciente.historiaClinicaDTO2.g20p8.siNo = null;
                vm.paciente.historiaClinicaDTO2.g20p9.siNo = null;
            }
        });

        $scope.$watch('vm.paciente.historiaClinicaDTO2.g20p9.siNo',function(){
            if(vm.paciente.historiaClinicaDTO2.g20p9.siNo == false){
                vm.paciente.historiaClinicaDTO2.g20p10.only_detalle = null;
            }
        });

        $scope.$watch('vm.paciente.historiaClinicaDTO2.g21p1.siNo',function(){
            if(vm.paciente.historiaClinicaDTO2.g21p1.siNo == false){
                vm.paciente.historiaClinicaDTO2.g21p2.fecha = null;
                vm.paciente.historiaClinicaDTO2.g21p3.siNo = null;
                vm.paciente.historiaClinicaDTO2.g21p4.siNo = null;
                vm.paciente.historiaClinicaDTO2.g21p5.only_detalle = null;
                vm.paciente.historiaClinicaDTO2.g21p6.siNo = null;
                vm.paciente.historiaClinicaDTO2.g21p7.only_detalle = null;
                vm.paciente.historiaClinicaDTO2.g21p8.only_detalle = null;
                vm.paciente.historiaClinicaDTO2.g21p9.only_detalle = null;
                vm.paciente.historiaClinicaDTO2.g21p10.fecha = null;
                vm.paciente.historiaClinicaDTO2.g21p11.only_detalle = null;
                vm.paciente.historiaClinicaDTO2.g21p12.only_detalle = null;
                vm.paciente.historiaClinicaDTO2.g21p13.siNo = null;
            }
        });

        $scope.$watch('vm.paciente.historiaClinicaDTO2.g21p4.siNo',function(){
            if(vm.paciente.historiaClinicaDTO2.g21p4siNo == false){
                vm.paciente.historiaClinicaDTO2.g21p5.only_detalle = null;
            }
        });

        $scope.$watch('vm.paciente.historiaClinicaDTO2.g21p6.siNo',function(){
            if(vm.paciente.historiaClinicaDTO2.g21p6.siNo == false){
                vm.paciente.historiaClinicaDTO2.g21p7.only_detalle = null;
                vm.paciente.historiaClinicaDTO2.g21p8.only_detalle = null;
            }
        });

        $scope.$watch('vm.paciente.historiaClinicaDTO2.g22p1.siNo',function(){
            if(vm.paciente.historiaClinicaDTO2.g22p1.siNo == false){
                vm.paciente.historiaClinicaDTO2.g22p2.only_detalle = null;
                vm.paciente.historiaClinicaDTO2.g22p3.siNo = null;
                vm.paciente.historiaClinicaDTO2.g22p4.only_detalle = null;
                vm.paciente.historiaClinicaDTO2.g22p5.only_detalle = null;
            }
        });

        $scope.$watch('vm.paciente.historiaClinicaDTO2.g22p3.siNo',function(){
            if(vm.paciente.historiaClinicaDTO2.g22p3.siNo == false){
                vm.paciente.historiaClinicaDTO2.g22p4.only_detalle = null;
                vm.paciente.historiaClinicaDTO2.g22p5.only_detalle = null;
            }
        });

        $scope.$watch('vm.paciente.historiaClinicaDTO2.g23p1.siNo',function(){
            if(vm.paciente.historiaClinicaDTO2.g23p1.siNo == false){
                vm.paciente.historiaClinicaDTO2.g23p2.only_detalle = null;
            }
        });
        $scope.$watch('vm.paciente.historiaClinicaDTO2.g24p1.siNo',function(){
            if(vm.paciente.historiaClinicaDTO2.g24p1.siNo == false){
                vm.paciente.historiaClinicaDTO2.g24p2.only_detalle = null;
            }
        });

        $scope.$watch('vm.paciente.historiaClinicaDTO2.g25p1.siNo',function(){
            if(vm.paciente.historiaClinicaDTO2.g25p1.siNo == false){
                vm.paciente.historiaClinicaDTO2.g25p2.only_detalle = null;
                vm.paciente.historiaClinicaDTO2.g25p3.checked = null;
                vm.paciente.historiaClinicaDTO2.g25p4.checked = null;
                vm.paciente.historiaClinicaDTO2.g25p5.checked = null;
            }
        });

        $scope.$watch('vm.paciente.historiaClinicaDTO2.g27p2.siNo',function(){
            if(vm.paciente.historiaClinicaDTO2.g27p2.siNo == false){
                vm.paciente.historiaClinicaDTO2.g27p3.only_detalle = null;
                vm.paciente.historiaClinicaDTO2.g27p4.only_detalle = null;
            }
        });

        $scope.$watch('vm.paciente.historiaClinicaDTO2.g28p3.siNo',function(){
            if(vm.paciente.historiaClinicaDTO2.g28p3.siNo == false){
                vm.paciente.historiaClinicaDTO2.g28p4.only_detalle = null;
            }
        });

        $scope.$watch('vm.paciente.historiaClinicaDTO2.g28p5.siNo',function(){
            if(vm.paciente.historiaClinicaDTO2.g28p5.siNo == false){
                vm.paciente.historiaClinicaDTO2.g28p6.only_detalle = null;
            }
        });

        $scope.$watch('vm.paciente.historiaClinicaDTO2.g29p2.siNo',function(){
            if(vm.paciente.historiaClinicaDTO2.g29p2.siNo == false){
                vm.paciente.historiaClinicaDTO2.g29p3.checked = null;
                vm.paciente.historiaClinicaDTO2.g29p4.checked = null;
                vm.paciente.historiaClinicaDTO2.g29p5.checked = null;
            }
        });
    }]);