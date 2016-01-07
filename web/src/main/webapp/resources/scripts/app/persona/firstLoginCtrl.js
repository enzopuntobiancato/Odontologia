/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 21/12/15
 * Time: 12:33
 * To change this template use File | Settings | File Templates.
 */

var module = angular.module('personaModule');

module.controller('PersonaCtrl_FirstLogin', ['$scope', '$rootScope','$state','MessageSrv','$mdDialog',
function($scope,$rootScope,$state,message,$mdDialog){

    $scope.persona = {};
    $scope.data ={
        provincias :[
            {nombre: 'Córdoba'},{nombre: 'Buenos Aires'},{nombre: 'Santa Fe'},{nombre: 'San Luis'}
        ],
        ciudades : [
            {nombre: 'Córdoba'},{nombre: 'Carlos Paz'},{nombre: 'Alta Gracia'},{nombre: 'La Falda'}
        ],
        barrios : [
            {nombre: 'Capital Sur'},{nombre: 'Alberdi'},{nombre: 'Centro'},{nombre: 'Barrio Jardìn'}
        ]
    }

    $scope.save = function(){

    }

    $scope.goIndex = function(){
        $state.go('landingPage', {execQuery: $scope.data.persistedOperation});
    }

    $scope.reload = function(){
        $rootScope.persistedOperation = $scope.data.persistedOperation;
        $state.go($state.current, {}, {reload: true});
    }

    $scope.$on('$stateChangeStart',
        function (event, toState, toParams, fromState, fromParams) {
            if (!angular.equals($state.current, toState)) {
                delete $rootScope.persistedOperation;
            }
        })
}]);
