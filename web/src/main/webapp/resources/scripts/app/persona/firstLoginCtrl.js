var module = angular.module('personaModule');

module.controller('PersonaCtrl_FirstLogin', ['$scope', '$rootScope', '$state', 'authFactory', 'MessageSrv', 'Upload', 'personaResponse', 'tiposDocumentoResponse', 'sexosResponse', 'cargosResponse', '$timeout',
    function ($scope, $rootScope, $state, authFactory, message, Upload, personaResponse, tiposDocumentoResponse, sexosResponse, cargosResponse, $timeout) {

        var vm = this;
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
                Upload.upload({
                    url: 'api/persona/saveUserRelatedData',
                    data: {file: vm.file, usuario: Upload.json(vm.usuario), persona: Upload.json(vm.persona)}
                }).then(function (response) {
                        authFactory.setAuthData(response.data, vm.file);
                        authFactory.communicateAuthChanged();
                        $state.go('home');
                    }, function (response) {
                        handleError(response.data, response.status)
                    });
            }, form);
        }

        function isFileSelected() {
            return vm.file &&
                angular.isDefined(vm.file) &&
                vm.file != null
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
