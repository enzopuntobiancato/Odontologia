var module = angular.module('trabajoPracticoModule');


module.controller('TrabajoPracticoCtrl_Create', ['$scope','$rootScope', 'TrabajoPracticoSrv', '$state', 'NotificationSrv', 'gruposPracticaResponse','practicasResponse', function ($scope,$rootScope, service, $state, notification, gruposPracticaResponse, practicasResponse) {
    $scope.trabajoPractico = {};
    $scope.selectedGrupo = {};

    $scope.data = {
        disableFields: false,
        gruposPractica: gruposPracticaResponse.data,
        practicas: practicasResponse.data,
        persistedOperation: $rootScope.persistedOperation || false,
        saved: false
    };

    $scope.filterPracticas = function(value) {
        delete $scope.trabajoPractico.practicaOdontologica;
       return !$scope.selectedGrupo || !angular.isDefined($scope.selectedGrupo.id) || angular.equals(value.grupo.id, $scope.selectedGrupo.id);
    }

//    $scope.save = function()
//    {
//        notification.showWidget();
//        service.save($scope.materia)
//            .success(function(data) {
//                $scope.data.persistedOperation = true;
//                $scope.data.disableFields = true;
//                $scope.data.saved = true;
//                notification.scrollTo('container');
//                notification.hideWidget();
////                notification.good("Registro realizado con éxito. ", function(){});
//            })
//            .error(function (data) {
//                notification.hideWidget();
//                notification.badArray(data, function() {});
//            })
//    };

    $scope.goIndex = function() {
        $state.go('^.index', {execQuery: $scope.data.persistedOperation});
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
