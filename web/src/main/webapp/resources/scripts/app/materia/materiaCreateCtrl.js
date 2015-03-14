var module = angular.module('materiaModule');


module.controller('MateriaCtrl_Create', ['$scope', 'MateriaSrv', '$state', 'NotificationSrv', 'CommonsSrv', 'nivelesResponse', function ($scope, service, $state, notification, commons, nivelesResponse) {
    $scope.materia = {}

    $scope.data = {
        disableFields: false,
        niveles: commons.enumToJson(nivelesResponse.data)
    }

//    init();
//
//    function init() {
//        commonsSrv.getNiveles().success(function(data) {
//            $scope.data.niveles = commonsSrv.enumToJson(data);
//        })
//    }

    $scope.save = function()
    {
//        if ($scope.createMateriaForm.$valid)
//        {
            notification.showWidget();
            service.create($scope.materia)
                .success(function(data) {
                $scope.data.disableFields = true;
                notification.hideWidget();
                notification.good("Registro realizado con éxito. ", function(){});
            })
                .error(function (data) {
                    notification.hideWidget();
                    notification.badArray(data, function() {});
                })
//        } else
//        {
//            $scope.createMateriaForm.$setDirty();
//        }

    }

    $scope.goIndex = function() {
         $state.go('^.index');
    }

    $scope.reload = function() {
        $state.go($state.current, {}, {reload: true});
    }

}]);
