'use strict';

var auth = angular.module('auth.services', []);

auth.factory('authFactory', ['$rootScope', '$http', function($rootScope, $http){
    var authFactory = {
        authData: undefined
    };

    authFactory.login = function(user) {
        return $http({
            url: 'api/auth/login',
            method: 'POST',
            data: angular.toJson(user)

        });
    };

    authFactory.setAuthData = function (authData) {
        this.authData = {
            authId: authData.authId,
            authToken: authData.authToken,
            authPermission: authData.authPermission
        };
        $rootScope.$broadcast('authChanged');
    };

    authFactory.getAuthData = function () {
        return this.authData;
    };

    authFactory.isAuthenticated = function () {
        return !angular.isUndefined(this.getAuthData());
    };
    return authFactory;
}]);

 auth.factory('authHttpRequestInterceptor', ['$rootScope', '$injector',
     function($rootScope, $injector){
         var authHttpRequestInterceptor = {
             request: function ($request) {
                 var authFactory = $injector.get('authFactory');
                 if (authFactory.isAuthenticated()) {
                     $request.headers['auth-id'] = authFactory.getAuthData().authId;
                     $request.headers['auth-token'] = authFactory.getAuthData().authToken;
                 }
                 return $request;
             }
     };
         return authHttpRequestInterceptor;
 }]);