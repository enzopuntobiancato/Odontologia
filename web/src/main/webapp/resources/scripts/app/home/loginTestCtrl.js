/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 26/09/15
 * Time: 14:11
 * To change this template use File | Settings | File Templates.
 */
var loginModule = angular.module('sapo.login');

loginModule.controller('loginTestCtrl', ['$scope', 'authFactory', '$state',
    function ($scope, authFactory, $state) {
        //if (initializeData) {
        //   // notification.alert(initializeData);
        //}

        $scope.user = {username: 'Nacho'};
        $scope.loginData = {
            userNotFound: false,
            selectRol: false,
            roles: []
        }
        $scope.login = function (user) {
            $scope.loginData.userNotFound = false;
            authFactory.login(user).success(function (data) {
                if (data) {
                    if (!data.roles) {
                        $scope.loginData.userNotFound = false;
                        authFactory.setAuthData(data);
                        authFactory.communicateAuthChanged();
                        if (data.firstLogin) {
                            $state.go('userRelatedData');
                        } else {
                            $state.go('home');
                        }
                    } else {
                        $scope.loginData.selectRol = true;
                        $scope.loginData.roles = data.roles;
                        $scope.user.rol = $scope.loginData.roles[0];
                    }
                } else {
                    $scope.loginData.userNotFound = true;
                }

            }).error(function () {
                    // Error handling
                });
        };

        $scope.selectRol = function (user) {
            authFactory.login(user).success(function (data) {
                if (data) {
                    authFactory.setAuthData(data);
                    authFactory.communicateAuthChanged();
                    if (data.firstLogin) {
                        $state.go('userRelatedData');
                    } else {
                        $state.go('home');
                    }
                }
            })
        }
    }]);



