var module = angular.module('personaModule');

module.controller('PersonaCtrl_DatosUsuario', ['$scope', 'PersonaSrv', '$rootScope', '$state', 'authFactory', 'MessageSrv', 'Upload', 'personaResponse', 'tiposDocumentoResponse', 'sexosResponse', 'cargosResponse', 'imageResponse', '$timeout', '$q',
    function ($scope, personaSrv, $rootScope, $state, authFactory, message, Upload, personaResponse, tiposDocumentoResponse, sexosResponse, cargosResponse, imageResponse, $timeout, $q) {

        var vm = this;
        var today = new Date();

        vm.restrictedDate = {
            maxDate: new Date(today.getFullYear() - 15, 0, 1),
            minDate: new Date(1900, 0, 1)
        };

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
                doSave().then(function (response) {
                    authFactory.setAuthData(response.data, vm.file);
                    authFactory.communicateAuthChanged();
                    $state.go('home');
                }, function (response) {
                    handleError(response.data, response.status);
                });
            }, form);
        }

        function doSave() {
            var deferred = $q.defer();
            vm.persona.nombreRol = vm.usuario.rol.key;
            personaSrv.saveUserRelatedData(vm.persona, vm.usuario.rol.key)
                .then(function (response) {
                    Upload.upload({
                        url: 'api/persona/saveUserImage/' + vm.usuario.id,
                        params: {rol: vm.usuario.rol.key},
                        data: {file: vm.file}
                    })
                        .then(function (response) {
                            deferred.resolve(response);
                        }, function (response) {
                            deferred.reject(response);
                        })
                }, function (response) {
                    deferred.reject(response);
                })

            return deferred.promise;
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
