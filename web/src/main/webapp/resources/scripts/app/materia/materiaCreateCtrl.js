var module = angular.module('materiaModule');


module.controller('MateriaCtrl_Create', ['$scope', 'MateriaSrv', '$state', 'NotificationSrv', 'CommonsSrv', 'nivelesResponse', function ($scope, service, $state, notification, commons, nivelesResponse) {
    $scope.materia = {}

    $scope.data = {
        disableFields: false,
        niveles: commons.enumToJson(nivelesResponse.data),
        persistedOperation: false
    }

    $scope.save = function()
    {
            notification.showWidget();
            service.save($scope.materia)
                .success(function(data) {
                $scope.data.persistedOperation = true;
                $scope.data.disableFields = true;
                notification.hideWidget();
                notification.good("Registro realizado con éxito. ", function(){});
            })
                .error(function (data) {
                    notification.hideWidget();
                    notification.badArray(data, function() {});
                })
    }

    $scope.goIndex = function() {
         $state.go('^.index', {execQuery: $scope.data.persistedOperation});
    }

    $scope.reload = function() {
        $state.go($state.current, {}, {reload: true});
    }

}]);
