var module = angular.module('personaModule');

module.controller('PersonaCtrl_DatosUsuario', ['$scope', '$rootScope', '$state', 'authFactory', 'MessageSrv', 'Upload', 'personaResponse', 'tiposDocumentoResponse', 'sexosResponse', 'cargosResponse', 'imageResponse', '$timeout',
    function ($scope, $rootScope, $state, authFactory, message, Upload, personaResponse, tiposDocumentoResponse, sexosResponse, cargosResponse, imageResponse, $timeout) {

        var vm = this;
        vm.persona = personaResponse.data ? personaResponse.data : {};
        vm.usuario = authFactory.getAuthData();
        vm.file = imageResponse.data;
        vm.data = {
            tiposDocumento: tiposDocumentoResponse.data,
            sexos: sexosResponse.data,
            cargos: cargosResponse.data,
            showChangePassword: false,
            wrongImageFormat: false
        }

        vm.save = save;
        vm.isFileSelected = isFileSelected;
        var performSubmit = $scope.$parent.performSubmit;
        var handleError = $scope.$parent.handleError;
        vm.validationErrorFromServer = $scope.$parent.validationErrorFromServer;
        vm.fileChanged = fileChanged;
        vm.deleteImage = deleteImage;
        vm.cancel = cancel;

        function save(form) {
            performSubmit(function () {
                Upload.upload({
                    url: 'api/persona/saveUserRelatedData',
                    data: {file: vm.file, usuario: Upload.json(vm.usuario), persona: Upload.json(vm.persona)}
                }).then(function (response) {
                        authFactory.setAuthData(response.data, vm.file);
                        authFactory.communicateAuthChanged();
                        $state.go('home');
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

        function cancel() {
            $state.go('home');
        }
    }]);
