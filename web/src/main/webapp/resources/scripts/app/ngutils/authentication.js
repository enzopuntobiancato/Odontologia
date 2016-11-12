'use strict';

var auth = angular.module('auth.services', ['ngCookies']);

auth.factory('authFactory', ['$rootScope', '$http', '$cookies', '$q', function ($rootScope, $http, $cookies, $q) {

    var USER_COOKIE = 'USER';
    var MENU_COOKIE = 'MENU';

    var authFactory = {
        session: {
            user: undefined,
            image: undefined
        }
    };

    authFactory.login = function (user) {
        var deferred = $q.defer();
        $http({
            url: 'api/auth/login',
            method: 'POST',
            data: user
        }).then(function (response) {
                var authData = response.data;
                if (authData.imagenId) {
                  lookForImage(authData.imagenId)
                      .then(function(response) {
                          deferred.resolve({user: authData, image: response.data});
                    });
                } else {
                    deferred.resolve({user: authData});
                }
            });
        return deferred.promise;
    };

    authFactory.selectRol = function(rol) {
        return $http({
            url: 'api/auth/selectRol',
            method: 'POST',
            data: rol
        })
    };

    authFactory.fetchUser = function() {
        var current = this.session.user;

    }

    function lookForImage(imageId) {
        return $http({
            method: 'GET',
            params: {id: imageId},
            url: 'api/file/image',
            headers: {'Content-Type': 'application/json'},
            responseType: 'blob'
        });
    }

    authFactory.lookForImage = function(imageId) {
        return lookForImage(imageId);
    }

    function getExpiresDate() {
        var now = new Date();
        now.setMinutes(now.getMinutes() + 15);
        return now;
    }

    authFactory.setAuthData = function (user, image) {
        this.session.user = user;
        this.session.image = image ? image : this.session.image;
        $cookies.remove(USER_COOKIE);
        $cookies.putObject(USER_COOKIE, this.session.user, {expires: getExpiresDate()});
    };

    authFactory.communicateAuthChanged = function () {
        $rootScope.$broadcast('authChanged');
    }

    authFactory.communicateAuthChangedMissingStep = function() {
        $rootScope.$broadcast('authChangedMissingStep');
    }

    authFactory.getAuthData = function () {
        if (this.session.user) {
           return this.session.user;
        } else {
            this.session.user = $cookies.getObject(USER_COOKIE);
        }
        return this.session.user;
    };

    authFactory.setImage = function(image) {
        this.session.image = image;
    }

    authFactory.getImage = function() {
        if (this.session.image) {
            return this.session.image;
        }
        return undefined;
    }

    authFactory.getSession = function() {
        var deferred = $q.defer();
        this.session.user = this.getAuthData();
        if (this.session.user && !this.session.image && this.session.user.imagenId) {
           this.lookForImage().then(function(response) {
               deferred.resolve(this.loadImage(response.data));
           });
        } else {
            deferred.resolve(this.session);
        }
        return deferred.promise;
    }

    authFactory.isAuthenticated = function () {
        var user = this.getAuthData();
        return user && !angular.isUndefined(user) && user != null;
    };

    authFactory.updateExpiresTime = function () {
        this.session.user = this.getAuthData();
        $cookies.putObject(USER_COOKIE, this.session.user, {expires: getExpiresDate()});
    };

    authFactory.logout = function () {
        $cookies.remove(USER_COOKIE);
        return this.session = {};
    };

    authFactory.getPermisos = function(rolKey) {
        return $http({
            url: 'api/usuario/findPermisosRol',
            method: 'GET',
            params: {rol: rolKey}
        })
    };

    authFactory.recuperarPassword = function(email) {
        return $http({
            url: 'api/usuario/recuperarPassword',
            method: 'PUT',
            params: {email: email}
        })
    }

    return authFactory;
}]);

auth.factory('authHttpRequestInterceptor', ['$rootScope', '$injector',
    function ($rootScope, $injector) {
        var authHttpRequestInterceptor = {
            request: function ($request) {
                var authFactory = $injector.get('authFactory');
                if (authFactory.isAuthenticated()) {
                    authFactory.updateExpiresTime();
                    $request.headers['auth-id'] = authFactory.getAuthData().nombreUsuario;
                    $request.headers['auth-token'] = authFactory.getAuthData().authToken;
                }
                return $request;
            }
        };
        return authHttpRequestInterceptor;
    }]);