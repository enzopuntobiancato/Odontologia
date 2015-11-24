var module = angular.module('usuarioModule');


module.controller('UsuarioCtrl_Create', ['$scope', '$rootScope', 'UsuarioSrv', '$state', 'MessageSrv', 'rolesResponse', '$mdDialog', 
    function ($scope, $rootScope, service, $state, message, rolesResponse, $mdDialog) {
    $scope.usuario = {};
    $scope.usuario.roles = [];

    $scope.rolesSelected = [];
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
                $mdDialog.hide();
                message.showMessage('Usuario creado');
                $scope.goIndex();
            })
            .error(function (data) {
                $scope.showSimpleToast("No se pudo crear: " + data);
            })
    };

    $scope.goIndex = function ($mdDialog) {
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
