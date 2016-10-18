var module = angular.module('usuarioModule');


module.controller('UsuarioCtrl_Create', ['$scope', '$rootScope', 'UsuarioSrv', '$state', 'MessageSrv', 'rolesResponse', 'tiposDocResponse', 'sexosResponse', 'isAlumno', '$filter',
    function ($scope, $rootScope, service, $state, message, rolesResponse, tiposDocResponse, sexosResponse, isAlumno, $filter) {

        $scope.personaDTO = {
            usuario: {}
        };
        $scope.data = {
            disableFields: false,
            persistedOperation: $rootScope.persistedOperation || false,
            saved: false,
            roles: rolesResponse.data,
            tiposDoc: tiposDocResponse.data,
            sexos: sexosResponse.data,
            sendEmail: true,
            isAlumno: isAlumno
        };
        init();

        function init() {
            if ($scope.data.isAlumno) {
                $scope.personaDTO.usuario.rol = $filter('filter')($scope.data.roles, function (rol) {
                    return rol.nombre.key == 'ALUMNO';
                })[0];
            }
        }

        var performSubmit = $scope.$parent.performSubmit;
        var handleError = $scope.$parent.handleError;

        $scope.save = save;
        function save(form) {
            performSubmit(function () {
                $scope.personaDTO.nombreRol = $scope.personaDTO.usuario.rol.nombre.key;
                service.save($scope.personaDTO)
                    .success(function (data) {
                        $scope.data.persistedOperation = true;
                        $scope.data.disableFields = true;
                        $scope.data.saved = true;
                        message.successMessage('Usuario para ' + $scope.personaDTO.apellido + ', ' + $scope.personaDTO.nombre + ' creado.');
                        $scope.goIndex();
                    })
                    .error(function (data, status) {
                        console.log(status);
                        console.log(data);
                        handleError(data, status);
                    })

            }, form);
        };


        $scope.goIndex = function () {
            if ($scope.data.isAlumno) {
                $state.go('home');
            } else {
                $state.go('^.index', {execQuery: $scope.data.persistedOperation});
            }
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
