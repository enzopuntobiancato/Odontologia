var module = angular.module('personaModule');

module.controller('PersonaCtrl_FirstLogin', ['$scope', 'PersonaSrv', '$rootScope', '$state', 'authFactory', 'MessageSrv', 'Upload', 'personaResponse', 'tiposDocumentoResponse', 'sexosResponse', 'cargosResponse', '$timeout', '$q',
    function ($scope, personaSrv, $rootScope, $state, authFactory, message, Upload, personaResponse, tiposDocumentoResponse, sexosResponse, cargosResponse, $timeout, $q) {

        var vm = this;
        var today = new Date();

        vm.restrictedDate = {
            maxDate: new Date(today.getFullYear() - 15, 0, 1),
            minDate: new Date(1900, 0, 1)
        };

        vm.persona = personaResponse.data ? personaResponse.data : {};
        vm.usuario = authFactory.getAuthData();
        vm.data = {
            tiposDocumento: tiposDocumentoResponse.data,
            sexos: sexosResponse.data,
            cargos: cargosResponse.data
        }

        vm.save = save;
        vm.isFileSelected = isFileSelected;
        var performSubmit = $scope.$parent.performSubmit;
        var handleError = $scope.$parent.handleError;
        vm.validationErrorFromServer = $scope.$parent.validationErrorFromServer;
        vm.fileChanged = fileChanged;
        vm.deleteImage = deleteImage;

        function save(form) {
            vm.persona.usuario.changePassword = true;
            vm.persona.usuario.fromFirstLogin = true;
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
                .then(function(response) {
                    if (isFileSelected()) {
                        Upload.upload({
                            url: 'api/persona/saveUserImage/' + vm.usuario.id,
                            params: {rol: vm.usuario.rol.key},
                            data: {file: vm.file}
                        })
                            .then(function(response) {
                                deferred.resolve(response);
                            }, function(response) {
                                deferred.reject(response);
                            })
                    } else {
                        deferred.resolve(response);
                    }
                }, function(response) {
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

//        $scope.goIndex = function () {
//            $state.go('landingPage', {execQuery: $scope.data.persistedOperation});
//        }
//
//        $scope.reload = function () {
//            $rootScope.persistedOperation = $scope.data.persistedOperation;
//            $state.go($state.current, {}, {reload: true});
//        }

//        $scope.$on('$stateChangeStart',
//            function (event, toState, toParams, fromState, fromParams) {
//                if (!angular.equals($state.current, toState)) {
//                    delete $rootScope.persistedOperation;
//                }
//            })

//        // Changing actual state
//        $scope.$on('$stateChangeStart',
//            function (event, toState) {
//                var user = authFactory.getAuthData();
//                if (user) {
//                    if (toState.name != $state.$current.name && user.firstLogin) {
//                        event.preventDefault();
//                        $scope.user = authFactory.logout();
//                        $state.go('home');
//                    }
//                }
//            });
    }]);
