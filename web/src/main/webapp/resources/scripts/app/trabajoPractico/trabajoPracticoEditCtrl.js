/**
 * Created with IntelliJ IDEA.
 * User: enzo
 * Date: 12/05/15
 * Time: 20:49
 */

var module = angular.module('trabajoPracticoModule');


module.controller('TrabajoPracticoCtrl_Edit', ['$scope', '$rootScope', 'TrabajoPracticoSrv', '$state', 'MessageSrv', 'gruposPracticaResponse', 'practicasResponse', '$filter', 'trabajoPracticoResponse',
    function ($scope, $rootScope, service, $state, message, gruposPracticaResponse, practicasResponse, $filter, trabajoPracticoResponse) {
    $scope.trabajoPractico = trabajoPracticoResponse.data;
    var performSubmit = $scope.$parent.performSubmit;
    var handleError = $scope.$parent.handleError;
    $scope.validationErrorFromServer = $scope.$parent.validationErrorFromServer;

    $scope.data = {
        disableFields: false,
        gruposPractica: gruposPracticaResponse.data,
        practicas: practicasResponse.data,
        persistedOperation: $rootScope.persistedOperation || false,
        saved: false
    };

        $scope.aux ={
            selectedGrupo : $scope.trabajoPractico.practicaOdontologica.grupo
        }
//    $scope.selectedGrupo = $scope.trabajoPractico.practicaOdontologica.grupo;
    $scope.practicasSelect = $scope.data.practicas;


   /* $scope.$watch('selectedGrupo', function (newValue, oldValue) {
        if (!angular.equals(newValue, oldValue)) {
            delete $scope.trabajoPractico.practicaOdontologica;
            filterPracticas();
        }

        function filterPracticas() {

            if (!$scope.selectedGrupo || !angular.isDefined($scope.selectedGrupo.id)) {
                $scope.practicasSelect = $scope.data.practicas;
            } else {
                $scope.practicasSelect = $filter('filter')($scope.data.practicas, function (value) {
                    return angular.equals(value.grupo.id, $scope.selectedGrupo.id);
                })
            }
        }
    })*/

    $scope.save = function (form) {
        performSubmit(function(){
            service.save($scope.trabajoPractico)
                .success(function (data) {
                    $scope.data.persistedOperation = true;
                    $scope.data.disableFields = true;
                    $scope.data.saved = true;
                    message.successMessage($scope.trabajoPractico.nombre + " modificado con éxito!");
                    $scope.goIndex();
                })
                .error(function (data, status) {
                    handleError(data, status);
                })
        },form);
    };

    $scope.goIndex = function () {
        $state.go('^.index', {execQuery: false, execQuerySamePage: $scope.data.persistedOperation});
    };

    $scope.reload = function () {
        $rootScope.persistedOperation = $scope.data.persistedOperation;
        $state.go($state.current, {}, {reload: true});
    };

    $scope.$on('$stateChangeStart',
        function (event, toState, toParams, fromState, fromParams) {
            if (!angular.equals($state.current, toState)) {
                delete $rootScope.persistedOperation;
            }
        });
}]);

