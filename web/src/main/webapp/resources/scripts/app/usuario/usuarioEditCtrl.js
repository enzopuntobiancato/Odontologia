var module = angular.module('usuarioModule');


module.controller('UsuarioCtrl_Edit', ['$scope', '$rootScope', 'UsuarioSrv', '$state', 'MessageSrv', 'rolesResponse', 'usuarioResponse', 
    function ($scope, $rootScope, service, $state, message, rolesResponse, usuarioResponse) {
    $scope.usuario = usuarioResponse.data;

    $scope.data = {
        disableFields: false,
        persistedOperation: $rootScope.persistedOperation || false,
        saved: false,
        roles: rolesResponse.data,
        sendEmail: true
    };

    $scope.save = function () {
        service.save($scope.usuario)
            .success(function (data) {
                $scope.data.persistedOperation = true;
                $scope.data.disableFields = true;
                $scope.data.saved = true;
                message.showMessage("Usuario " + $scope.usuario.nombreUsuario + " modificado con éxito!");
                $scope.goIndex();
            })
            .error(function (data) {
                message.showMessage("Error al modificar " + $scope.usuario.nombreUsuario);
            })
    };

    $scope.goIndex = function () {
        $state.go('^.index', {execQuery: $scope.data.persistedOperation});
    };

    $scope.reload = function () {
        $rootScope.persistedOperation = $scope.data.persistedOperation;
        $state.go($state.current, {}, {reload: true});
    };

    $scope.selectedItem = null;
    $scope.searchText = null;
    /**
     * Return the proper object when the append is called.
     */
    function transformChip(chip) {
        // If it is an object, it's already a known chip
        if (angular.isObject(chip)) {
            return chip;
        }
        // Otherwise, create a new one
        return { name: chip, type: 'new' }
    }


    $scope.$on('$stateChangeStart',
        function (event, toState, toParams, fromState, fromParams) {
            if (!angular.equals($state.current, toState)) {
                delete $rootScope.persistedOperation;
            }
        });

}]);
