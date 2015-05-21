/**
 * Created with IntelliJ IDEA.
 * User: enzo
 * Date: 12/05/15
 * Time: 20:49
 */

var module = angular.module('trabajoPracticoModule');


module.controller('TrabajoPracticoCtrl_Edit', ['$scope','$rootScope', 'TrabajoPracticoSrv', '$state', 'NotificationSrv', 'gruposPracticaResponse','practicasResponse','$filter', 'trabajoPracticoResponse', function ($scope,$rootScope, service, $state, notification, gruposPracticaResponse, practicasResponse, $filter, trabajoPracticoResponse) {
    $scope.trabajoPractico = trabajoPracticoResponse.data;

    $scope.data = {
        disableFields: false,
        gruposPractica: gruposPracticaResponse.data,
        practicas: practicasResponse.data,
        persistedOperation: $rootScope.persistedOperation || false,
        saved: false
    };
    $scope.selectedGrupo = trabajoPracticoResponse.data.practicaOdontologica.grupo;
    $scope.practicasSelect = $scope.data.practicas;


    $scope.$watch('selectedGrupo', function(newValue, oldValue){
        if (!angular.equals(newValue, oldValue)){
            delete $scope.trabajoPractico.practicaOdontologica;
            filterPracticas();
        }

        function filterPracticas() {

            if (!$scope.selectedGrupo || !angular.isDefined($scope.selectedGrupo.id)) {
                $scope.practicasSelect = $scope.data.practicas;
            } else {
                $scope.practicasSelect = $filter('filter')($scope.data.practicas, function(value){
                    return angular.equals(value.grupo.id, $scope.selectedGrupo.id);
                })
            }
        }
    })

    $scope.save = function()
    {
//        notification.showWidget();
        service.save($scope.trabajoPractico)
            .success(function(data) {
                $scope.data.persistedOperation = true;
                $scope.data.disableFields = true;
                $scope.data.saved = true;
                notification.scrollTo('container');
//                notification.hideWidget();
            })
            .error(function (data) {
//                notification.hideWidget();
                notification.badArray(data, function() {});
            })
    };

    $scope.goIndex = function() {
        $state.go('^.index', {execQuery: false, execQuerySamePage: $scope.data.persistedOperation});
    };

    $scope.reload = function() {
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

