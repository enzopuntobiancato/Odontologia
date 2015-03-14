var module = angular.module('materiaModule');


module.controller('MateriaCtrl_Index', ['$scope', 'MateriaSrv', '$state', 'NotificationSrv', 'CommonsSrv', 'nivelesResponse', function ($scope, service, $state, notification, commons, nivelesResponse) {

    $scope.filter = {};
    $scope.result = [];
    $scope.niveles = commons.enumToJson(nivelesResponse.data);

   /* init();

    function init() {
        commonsSrv.getNiveles().success(function(data) {
            $scope.niveles = commonsSrv.enumToJson(data);
        })
    }*/

    $scope.consultar = function() {
        notification.showWidget();
        service.find($scope.filter.nombre, $scope.filter.nivel).success(function(data) {
            notification.hideWidget();
             $scope.result = data;
        })
    }

    $scope.new = function()
    {
        $state.go('^.create');
    }

}]);
