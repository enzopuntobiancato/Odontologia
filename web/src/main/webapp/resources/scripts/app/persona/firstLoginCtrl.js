/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 21/12/15
 * Time: 12:33
 * To change this template use File | Settings | File Templates.
 */

var module = angular.module('personaModule');

module.controller('PersonaCtrl_FirstLogin', ['$scope', '$rootScope', '$state', 'authFactory', 'MessageSrv', 'Upload', 'personaResponse', 'tiposDocumentoResponse', 'sexosResponse', 'cargosResponse',
    function ($scope, $rootScope, $state, authFactory, message, Upload, personaResponse, tiposDocumentoResponse, sexosResponse, cargosResponse) {

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

        function save(form) {
            performSubmit(function () {
                Upload.upload({
                    url: 'api/persona/saveUserRelatedData',
                    data: {file: vm.file, usuario: Upload.json(vm.usuario), persona: Upload.json(vm.persona)}
                }).then(function () {
                        vm.usuario.firstLogin = false;
                        authFactory.setAuthData(vm.usuario);
                        authFactory.communicateAuthChanged();
                        $state.go('home');
                    }, function () {
                        message.errorMessage("Error guardando los datos")
                    });
            }, form);
        }

        function isFileSelected() {
            return vm.file &&
                angular.isDefined(vm.file) &&
                vm.file != null
        }


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
