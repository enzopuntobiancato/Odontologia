var loginModule = angular.module('sapo.login');

loginModule.controller('LandingPageCtrl', ['$scope', 'authFactory', '$state', 'MessageSrv', '$mdDialog',
    function ($scope, authFactory, $state, messageSrv, $mdDialog) {
        // InitializationService execution
        var vm = this;

        // Data
        vm.user = {};

        // Methods
        vm.login = login;
        var performSubmit = $scope.$parent.performSubmit;

        function login(form) {
            performSubmit(function () {
                authFactory.login(vm.user)
                    .then(function (response) {
                        if (response.user) {
                            var user = response.user;
                            user.missingRole = false;
                            authFactory.setAuthData(user, response.image);
                            if (response.user.hasMultipleRoles) {
                                user.missingRole = true;
                                authFactory.setAuthData(user, response.image);
                                authFactory.communicateAuthChangedMissingStep();
                                $state.go('selectRol');
                            } else if (response.user.firstLogin) {
                                authFactory.communicateAuthChangedMissingStep();
                                $state.go('persona.firstLogin');
                            } else {
                                authFactory.communicateAuthChanged();
                                $state.go('home');
                            }
                        } else {
                            messageSrv.errorMessage('Usuario y/o contraseña incorrectos');
                        }
                    }, function () {
                        messageSrv.errorMessage('Ocurrió un error. Intente nuevamente');
                    });
            }, form);
        };

        vm.recuperarPassword = recuperarPassword;

        function recuperarPassword(ev) {
            $mdDialog.show({
                controller: createDialogController,
                templateUrl: 'views/home/recuperarPasswordDialog.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: false
            })
                .then(function () {
                    messageSrv.successMessage("Correo electrónico enviado a su cuenta.")
                }, function () {
                    messageSrv.errorMessage()
                });
        };


        function createDialogController($scope, $mdDialog, authFactory, MessageSrv) {

            $scope.email;
            $scope.validationErrorFromServer = {
                error: false,
                data: {}
            }

            $scope.cancel = function () {
                $mdDialog.cancel();
            };
            $scope.recuperar = function (form) {
                if (form.$valid) {
                    authFactory.recuperarPassword($scope.email)
                        .success(function() {
                            $mdDialog.hide();
                        })
                        .error(function(data, status) {
                            if (status === 1000) {
                                // Our error response
                                $scope.validationErrorFromServer.error = true;
                                var message = [];
                                var msgs = Object.keys(data);
                                for (var i = 0; i < msgs.length; i++) {
                                    message.push(data[msgs[i]]);
                                }
                                $scope.validationErrorFromServer.data = message;
                            } else {
                                messageSrv.errorMessage("Ocurrió un error.");
                            }
                        })
                } else {
                    angular.forEach(form.$error, function (field) {
                        angular.forEach(field, function (errorField) {
                            errorField.$setTouched();
                        })
                    });
                }
            };
        }
    }]);



