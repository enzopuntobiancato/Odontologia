/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 29/12/15
 * Time: 12:04
 * To change this template use File | Settings | File Templates.
 */
var module = angular.module('historiaClinicaModule');

module.controller('HistoriaClinicaCtrl_Edit', ['$scope','$rootScope','HistoriaClinicaSrv','$state','MessageSrv',
    function ($scope, $rootScope, service, $state, message) {
        var vm = this;
        vm.save = save;
        vm.goIndex = goIndex;

        function save(form){
            console.log("EDIT");
        }

        function goIndex(){
            $state.go('^.index');
        }
    }]);