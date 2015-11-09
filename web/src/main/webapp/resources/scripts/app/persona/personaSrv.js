var module = angular.module('personaModule');

module.factory('PersonaSrv', ['$http', function ($http) {
    return {
        findByUser: function (userName, authToken) {
            return $http.get('api/persona/findByUser', {username: userName, authToken: authToken})
        }
    }
}]);