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
            isAlumno: isAlumno,
            submitted: false,
            missingRole: false
        };

        function getSelectedRoles() {
            return $filter('filter')($scope.data.roles, function (rol) {
                return rol.selected;
            });
        }

        $scope.clickRol = function () {
            if (getSelectedRoles().length == 0) {
                $scope.data.missingRole = true;
            } else {
                $scope.data.missingRole = false;
            }
        }

        init();

        function init() {
            if ($scope.data.isAlumno) {
                for (var i = 0; i < $scope.data.roles.length; i++) {
                    if ($scope.data.roles[i].nombre.key == 'ALUMNO') {
                        $scope.data.roles[i].selected = true;
                        break;
                    }
                }
            }
        }

        var performSubmit = $scope.$parent.performSubmit;
        var handleError = $scope.$parent.handleError;

        $scope.save = save;
        function save(form) {
            $scope.data.submitted = true;
            if (getSelectedRoles().length == 0) {
                $scope.data.missingRole = true;
                return;
            }
            performSubmit(function () {
                $scope.personaDTO.nombreRol = getSelectedRoles()[0].nombre.key;
                $scope.personaDTO.usuario.roles = [];
                var roles = getSelectedRoles();
                for (var i = 0; i < roles.length; i++) {
                    var rolUsuario = {
                        rol: roles[i]
                    }
                    $scope.personaDTO.usuario.roles.push(rolUsuario);
                }
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
