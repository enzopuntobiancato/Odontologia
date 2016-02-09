var module = angular.module('usuarioModule');


module.controller('UsuarioCtrl_Create', ['$scope', '$rootScope', 'UsuarioSrv', '$state', 'MessageSrv', 'rolesResponse', 'tiposDocResponse','sexosResponse', '$mdDialog',
    function ($scope, $rootScope, service, $state, message, rolesResponse, tiposDocResponse,sexosResponse) {

    $scope.personaDTO ={};
    var performSubmit = $scope.$parent.performSubmit;
    $scope.data = {
        disableFields: false,
        persistedOperation: $rootScope.persistedOperation || false,
        saved: false,
        roles: rolesResponse.data,
        tiposDoc: tiposDocResponse.data,
        sexos: sexosResponse.data,
        sendEmail: true
    };


        $scope.save = save;
        function save(form) {
            performSubmit(function () {
                $scope.personaDTO.nombreRol = $scope.personaDTO.usuario.rol.nombre;
                service.save($scope.personaDTO)
                    .success(function (data) {
                        $scope.data.persistedOperation = true;
                        $scope.data.disableFields = true;
                        $scope.data.saved = true;
                        message.showMessage('Usuario'+ $scope.personaDTO.apellido +', '+ $scope.personaDTO.nombre +' creado');
                        $scope.goIndex();
                    })
                    .error(function (data, status) {
                        console.log(status);
                        console.log(data);
                        message.showMessage('No se pudo crear el usuario');
                    })

            }, form);
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
