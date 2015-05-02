var module = angular.module('materiaModule');


module.controller('MateriaCtrl_Edit', ['$scope', 'MateriaSrv', '$state', 'NotificationSrv', 'CommonsSrv', 'nivelesResponse', 'materiaResponse', function ($scope, service, $state, notification, commons, nivelesResponse, materiaResponse) {
    $scope.materia = materiaResponse.data;

    $scope.data = {
        disableFields: false,
        niveles: commons.enumToJson(nivelesResponse.data)
    }

    $scope.save = function()
    {
        notification.showWidget();
        service.create($scope.materia)
            .success(function(data) {
                $scope.data.disableFields = true;
                notification.hideWidget();
                notification.good("Cambios guardados con éxito. ", function(){});
            })
            .error(function (data) {
                notification.hideWidget();
                notification.badArray(data, function() {});
            })
    }

    $scope.goIndex = function() {
        $state.go('^.index');
    }

    $scope.reload = function() {
        $state.go($state.current, {}, {reload: true});
    }

}]);
