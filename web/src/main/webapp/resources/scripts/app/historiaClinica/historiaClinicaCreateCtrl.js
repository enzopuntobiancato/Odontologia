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

        function save(form){
            console.log("SAVE");
        }

        function goIndex(){
            $state.go('^.index');
        }


    }]);
