var module = angular.module('personaModule');

module.factory('PersonaSrv', ['$http', function ($http) {
    return {
        findByUser: function (userName, authToken, rol) {
            return $http({
                method: 'GET',
                url: 'api/persona/findByUser',
                params: {username: userName, authToken: authToken, rol: rol}
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