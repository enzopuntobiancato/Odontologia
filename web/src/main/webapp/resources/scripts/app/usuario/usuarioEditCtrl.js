var module = angular.module('usuarioModule');


module.controller('UsuarioCtrl_Edit', ['$scope','$rootScope', 'UsuarioSrv', '$state', 'NotificationSrv', 'rolesResponse', 'usuarioResponse', function ($scope,$rootScope, service, $state, notification, rolesResponse, usuarioResponse) {
    $scope.usuario = usuarioResponse.data;

    $scope.data = {
        disableFields: false,
        persistedOperation: $rootScope.persistedOperation || false,
        saved: false,
        roles: rolesResponse.data,
        sendEmail: true
    };

    $scope.save = function()
    {
        service.save($scope.usuario)
            .success(function(data) {
                $scope.data.persistedOperation = true;
                $scope.data.disableFields = true;
                $scope.data.saved = true;
                notification.scrollTo('container');
            })
            .error(function (data) {
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
