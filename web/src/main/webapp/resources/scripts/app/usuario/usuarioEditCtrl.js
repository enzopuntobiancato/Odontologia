var module = angular.module('usuarioModule');

module.controller('UsuarioCtrl_Edit', ['$scope',
    '$rootScope',
    '$state',
    'MessageSrv',
    '$filter',
    'Upload',
    '$timeout',
    'usuarioResponse',
    'imageResponse',
    'rolesResponse',
    'personaEmumsResponse',
    function ($scope,
              $rootScope,
              $state,
              message,
              $filter,
              Upload,
              $timeout,
              usuarioResponse,
              imageResponse,
              rolesResponse,
              personaEmumsResponse) {

        var vm = this;
        vm.usuario = usuarioResponse.data;
        vm.file = imageResponse.data;
        var personaEnums = personaEmumsResponse.data;
        vm.data = {
            roles: rolesResponse.data,
            persistedOperation: false,
            tiposDocumento: personaEnums.tiposDocumento,
            sexos: personaEnums.sexos,
            cargos: personaEnums.cargos,
            wrongImageFormat: false,
            sendEmail: true,
            missingRole: false,
            submitted: false
        }

        vm.save = save;
        vm.isFileSelected = isFileSelected;
        var performSubmit = $scope.$parent.performSubmit;
        var handleError = $scope.$parent.handleError;
        vm.validationErrorFromServer = $scope.$parent.validationErrorFromServer;
        vm.fileChanged = fileChanged;
        vm.deleteImage = deleteImage;
        vm.cancel = goIndex;
        vm.clickRol = clickRol;
        vm.rolSelected = rolSelected;

        init();

        function init() {
            for (var i = 0; i < vm.usuario.roles.length; i++) {
                for (var j = 0; j < vm.data.roles.length; j++) {
                    if (vm.usuario.roles[i].rol.nombre.key == vm.data.roles[j].nombre.key) {
                        vm.data.roles[j].selected = true;
                        break;
                    }
                }
            }
            vm.usuario.autoridadDTO = createObjectIfNotPresent(vm.usuario.autoridadDTO);
            vm.usuario.alumnoDTO = createObjectIfNotPresent(vm.usuario.alumnoDTO);
            vm.usuario.administradorDTO = createObjectIfNotPresent(vm.usuario.administradorDTO);
            vm.usuario.responsableDTO = createObjectIfNotPresent(vm.usuario.responsableDTO);
            vm.usuario.adminAcademicoDTO = createObjectIfNotPresent(vm.usuario.adminAcademicoDTO);
            vm.usuario.profesorDTO = createObjectIfNotPresent(vm.usuario.profesorDTO);
        }

        function createObjectIfNotPresent(object) {
            if (object) {
                return object;
            }
            return {};
        }

        function rolSelected(rol) {
            var selected = false;
            for (var i = 0; i< vm.data.roles.length; i++) {
                if (vm.data.roles[i].selected && vm.data.roles[i].nombre.key == rol) {
                    selected = true;
                    break;
                }
            }
            return selected;
        }

        function associatePersons() {
            vm.usuario.roles = [];
            getSelectedRoles().forEach(function(rol) {
                var persona = getPersonForRol(rol);
                persona.nombreRol = rol.nombre.key;
                vm.usuario.roles.push({
                    rol: rol,
                    persona: persona
                })
            })
        }

        function getPersonForRol(rol) {
            var person;
            switch(rol.nombre.key) {
                case 'ADMINISTRADOR':
                    person = vm.usuario.administradorDTO;
                    break;
                case 'ALUMNO':
                    person = vm.usuario.alumnoDTO;
                    break;
                case 'PROFESOR':
                    person = vm.usuario.profesorDTO;
                    break;
                case 'RESPONSABLE_RECEPCION_PACIENTES':
                    person = vm.usuario.responsableDTO;
                    break;
                case 'ADMINISTRADOR_ACADEMICO':
                    person = vm.usuario.adminAcademicoDTO;
                    break;
                case 'AUTORIDAD':
                    person = vm.usuario.autoridadDTO;
                    break;
                default:
                    person = null;
            }
            return person;
        }

        function save(form) {
            vm.data.submitted = true;
            if (getSelectedRoles().length == 0) {
                vm.data.missingRole = true;
                return;
            }
            associatePersons();
            performSubmit(function () {
                Upload.upload({
                    url: 'api/usuario/update',
                    data: {file: vm.file, usuario: Upload.json(vm.usuario)}
                }).then(function () {
                        vm.data.persistedOperation = true;
                        message.successMessage('Usuario ' + vm.usuario.nombreUsuario + ' modificado con éxito.');
                        goIndex();
                    }, function (response) {
                        handleError(response.data, response.status);
                    });
            }, form);
        }

        function getSelectedRoles() {
            return $filter('filter')(vm.data.roles, function (rol) {
                return rol.selected;
            });
        }

        function clickRol() {
            if (getSelectedRoles().length == 0) {
                vm.data.missingRole = true;
            } else {
                vm.data.missingRole = false;
            }
        }

        function isFileSelected() {
            return vm.file &&
                angular.isDefined(vm.file) &&
                vm.file != null &&
                vm.file.size > 0
        }

        function fileChanged($files, $file, $newFiles, $duplicateFiles, $invalidFiles, $event) {
            if ($file) {
                vm.file = $file;
            }
            if ($invalidFiles.length) {
                vm.data.wrongImageFormat = true;
                $timeout(function () {
                    vm.data.wrongImageFormat = false;
                }, 3000);
            }
        }

        function deleteImage() {
            vm.file = undefined;
        }

        vm.colorIcon = ['#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF'];
        $scope.colorMouseOver = function (icon) {
            vm.colorIcon[icon] = '#E91E63';
        };

        $scope.colorMouseLeave = function (icon) {
            vm.colorIcon[icon] = '#00B0FF';
        };

        function goIndex() {
            $state.go('^.index', {execQuery: vm.data.persistedOperation});
        };

        $scope.$on('$stateChangeStart',
            function (event, toState, toParams, fromState, fromParams) {
                if (!angular.equals($state.current, toState)) {
                    delete $rootScope.persistedOperation;
                }
            });
    }]);