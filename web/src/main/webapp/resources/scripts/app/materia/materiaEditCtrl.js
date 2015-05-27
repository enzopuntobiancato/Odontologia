var module = angular.module('materiaModule');


module.controller('MateriaCtrl_Edit', ['$scope', '$rootScope', 'MateriaSrv', '$state', 'NotificationSrv', 'CommonsSrv', 'nivelesResponse', 'materiaResponse', function ($scope,$rootScope, service, $state, notification, commons, nivelesResponse, materiaResponse) {
    $scope.materia = materiaResponse.data;

    $scope.data = {
        disableFields: false,
        niveles: commons.enumToJson(nivelesResponse.data),
        persistedOperation: false,
        saved: false
    }

    $scope.save = function()
    {
//        notification.showWidget();
        service.abmcFactory.save($scope.materia)
            .success(function(data) {
                $scope.data.persistedOperation = true;
                $scope.data.disableFields = true;
                $scope.data.saved = true;
//                notification.hideWidget();
//                notification.good("Cambios guardados con éxito. ", function(){});
            })
            .error(function (data) {
//                notification.hideWidget();
                notification.badArray(data, function() {});
            })
    }

    $scope.goIndex = function() {
        $state.go('^.index', {execQuery: false, execQuerySamePage: $scope.data.persistedOperation});
    }

    $scope.reload = function() {
        $state.go($state.current, {}, {reload: true});
    }

    $scope.$on('$stateChangeStart',
        function (event, toState, toParams, fromState, fromParams) {
            if (!angular.equals($state.current, toState)) {
                delete $rootScope.persistedOperation;
            }
        });

}]);
