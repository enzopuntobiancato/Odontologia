/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 22/12/15
 * Time: 13:53
 * To change this template use File | Settings | File Templates.
 */
var module = angular.module('historiaClinicaModule');

module.controller('HistoriaClinicaCtrl_Create', ['$scope', '$rootScope', 'HistoriaClinicaSrv', '$state', 'MessageSrv',
    function ($scope, $rootScope, service, $state, message) {
        var vm = this;
        vm.save = save;
        vm.goIndex = goIndex;
        vm.hc = {};

        var performSubmit = $scope.$parent.performSubmit;
        var handleError = $scope.$parent.handleError;
        vm.validationErrorFromServer = $scope.$parent.validationErrorFromServer;

        vm.data = {
            disableFields: false,
            persistedOperation: $rootScope.persistedOperation || false,
            saved: false
        };


        function save(form){
            console.log("SAVE");
            vm.submitted = true;
            performSubmit(function(){
                service.save(vm.hc)
                    .success(function(){
                        vm.data.persistedOperation = true;
                        vm.data.disableFields = true;
                        vm.data.saved = true;
                        message.successMessage("HC creado con éxito");
                        vm.goIndex();
                    }).error(function(data,status){
                        vm.hc = "";
                        handleError(data,status);
                    })
            },form)
        }

        function goIndex(){
            $state.go('^.index');
        }


    }]);
