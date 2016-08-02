var module = angular.module('usuarioModule');

module.controller('UsuarioCtrl_Edit', ['$scope',
    '$rootScope',
    '$state',
    'MessageSrv',
    'Upload',
    '$timeout',
    'usuarioResponse',
    'personaResponse',
    'imageResponse',
    'personaEmumsResponse',
    function ($scope,
              $rootScope,
              $state,
              message,
              Upload,
              $timeout,
              usuarioResponse,
              personaResponse,
              imageResponse,
              personaEmumsResponse) {

        var vm = this;
        vm.persona = personaResponse.data ? personaResponse.data : {};
        vm.usuario = usuarioResponse.data;
        vm.file = imageResponse.data;
        var personaEnums = personaEmumsResponse.data;
        vm.data = {
            persistedOperation: false,
            tiposDocumento: personaEnums.tiposDocumento,
            sexos: personaEnums.sexos,
            cargos: personaEnums.cargos,
            wrongImageFormat: false,
            sendEmail: true
        }

        vm.save = save;
        vm.isFileSelected = isFileSelected;
        var performSubmit = $scope.$parent.performSubmit;
        var handleError = $scope.$parent.handleError;
        vm.validationErrorFromServer = $scope.$parent.validationErrorFromServer;
        vm.fileChanged = fileChanged;
        vm.deleteImage = deleteImage;
        vm.cancel = goIndex;

        function save(form) {
            performSubmit(function () {
                Upload.upload({
                    url: 'api/persona/saveUserRelatedData',
                    data: {file: vm.file, usuario: Upload.json(vm.usuario), persona: Upload.json(vm.persona)}
                }).then(function () {
                        vm.data.persistedOperation = true;
                        message.successMessage('Usuario ' + vm.usuario.nombreUsuario + ' modificado con éxito.');
                        goIndex();
                    }, function (response) {
                        handleError(response.data, response.status);
                    });
            }, form);
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
