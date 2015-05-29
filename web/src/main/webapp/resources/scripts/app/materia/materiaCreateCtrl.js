var module = angular.module('materiaModule');


module.controller('MateriaCtrl_Create', ['$scope','$rootScope', 'MateriaSrv', '$state', 'NotificationSrv', 'CommonsSrv', 'nivelesResponse', function ($scope,$rootScope, service, $state, notification, commons, nivelesResponse) {
    $scope.materia = {};

    $scope.data = {
        disableFields: false,
        niveles: commons.enumToJson(nivelesResponse.data),
        persistedOperation: $rootScope.persistedOperation || false,
        saved: false
    };

    $scope.save = function()
    {
//            notification.showWidget();
            service.save($scope.materia)
                .success(function(data) {
                $scope.data.persistedOperation = true;
                $scope.data.disableFields = true;
                    $scope.data.saved = true;
                    notification.scrollTo('container');
//                notification.hideWidget();
//                notification.good("Registro realizado con éxito. ", function(){});
            })
                .error(function (data) {
//                    notification.hideWidget();
                    notification.badArray(data, function() {});
                })
    };

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
