var loginModule = angular.module('sapo.login');

loginModule.controller('LandingPageCtrl', ['$scope', 'authFactory', '$state', 'MessageSrv', 'initializeData',
    function ($scope, authFactory, $state, messageSrv, initializeData) {
        // InitializationService execution
        if (initializeData) {
            messageSrv.showMessage(initializeData);
        }

        var vm = this;

        // Data
        vm.user = {};

        // Methods
        vm.login = login;
        var performSubmit = $scope.$parent.performSubmit;

        function login(form) {
            performSubmit(function () {
                authFactory.login(vm.user)
                    .success(function (data) {
                        if (data) {
                            authFactory.setAuthData(data);
                            if (data.firstLogin) {
                                $state.go('persona.firstLogin');
                            } else {
                                authFactory.communicateAuthChanged();
                                $state.go('home');
                            }
                        } else {
                            messageSrv.errorMessage('Usuario y/o contraseña incorrectos');
                        }
                    })
                    .error(function () {
                        messageSrv.errorMessage('Ocurrió un error. Intente nuevamente');
                    });
            }, form);
        };
    }]);



