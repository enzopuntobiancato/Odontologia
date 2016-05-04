var module = angular.module('personaModule');

module.factory('PersonaSrv', ['$http', function ($http) {
    return {
        findByUser: function (userName, authToken) {
            return $http({
                method: 'GET',
                url: 'api/persona/findByUser',
                params: {username: userName, authToken: authToken}
            });
        },
        findUserImage: function(imageId) {
            return $http({
                method: 'GET',
                params: {id: imageId},
                url: 'api/file/image',
                headers: {'Content-Type': 'application/json'},
                responseType: 'blob'
            });
        }
    }
}]);