var module = angular.module('permisosModule');


module
    .factory('PermisosSrv', ['$http', function ($http) {
        return {
            save: function (roles) {
                return $http.post('api/permisos/save',
                    roles, roles);
            },
            findAllFuncionalidades: function () {
                return $http({
                    url: 'api/permisos/findAllFuncionalidades',
                    method: 'GET'
                })
            },
            findAllRoles: function () {
                return $http({
                    url: 'api/permisos/findAllRoles',
                    method: 'GET'
                })
            }
        }
    }]);


