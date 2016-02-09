var module = angular.module('usuarioModule');


module.controller('UsuarioCtrl_Edit', ['$scope', '$rootScope', 'UsuarioSrv', '$state', 'MessageSrv', 'rolesResponse', 'usuarioResponse', 'tiposDocResponse',
    function ($scope, $rootScope, service, $state, message, rolesResponse, usuarioResponse, tiposDocResponse) {
    $scope.personaDTO = usuarioResponse.data;
    $scope.data = {
        disableFields: false,
        persistedOperation: $rootScope.persistedOperation || false,
        saved: false,
        roles: rolesResponse.data,
        sendEmail: true,
        tiposDoc: tiposDocResponse.data
    };

    $scope.save = function () {
        $scope.personaDTO.nombreRol = $scope.personaDTO.usuario.rol.nombre;
        service.save($scope.personaDTO)
            .success(function (data) {
                $scope.data.persistedOperation = true;
                $scope.data.disableFields = true;
                $scope.data.saved = true;
                message.showMessage("Usuario " + $scope.personaDTO.usuario.nombreUsuario + " modificado con éxito!");
                $scope.goIndex();
            })
            .error(function (data) {
                message.showMessage("Error al modificar " + $scope.personaDTO.usuario.nombreUsuario);
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
