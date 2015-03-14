var module = angular.module('materiaModule');


module
    .factory('MateriaSrv', ['$http', function($http) {
        return {
            create: function(materia) {
                return $http({
                    url: '/api/materia/create',
                    method: 'POST',
                    data: angular.toJson(materia)
                })
            },
            find: function(nombre, nivel) {
                return $http({
                    url: '/api/materia/find',
                    method: 'GET',
                    params: {nombre: nombre, nivel: nivel}
                })
            }

        }

    }]);
