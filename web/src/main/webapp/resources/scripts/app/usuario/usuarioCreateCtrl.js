var module = angular.module('usuarioModule');


module.controller('UsuarioCtrl_Create', ['$scope', '$rootScope', 'UsuarioSrv', '$state', 'MessageSrv', 'rolesResponse', 'tiposDocResponse', '$mdDialog',
    function ($scope, $rootScope, service, $state, message, rolesResponse, tiposDocResponse) {
    $scope.usuario = {nombreUsuario : "Juan"};

    $scope.data = {
        disableFields: false,
        persistedOperation: $rootScope.persistedOperation || false,
        saved: false,
        roles: rolesResponse.data,
        tiposDoc: tiposDocResponse.data,
        sendEmail: true
    };

    $scope.save = function () {
        service.save($scope.usuario)
            .success(function (data) {
                $scope.data.persistedOperation = true;
                $scope.data.disableFields = true;
                $scope.data.saved = true;
                message.showMessage('Usuario ' + $scope.usuario.nombreUsuario +' creado');
                $scope.goIndex();
            })
            .error(function (data, status) {
                console.log(status);
                console.log(data);
                message.showMessage('No se pudo crear el usuario ' + $scope.usuario.nombreUsuario);
            })
    };

    $scope.goIndex = function () {
        $state.go('^.index', {execQuery: $scope.data.persistedOperation});
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
