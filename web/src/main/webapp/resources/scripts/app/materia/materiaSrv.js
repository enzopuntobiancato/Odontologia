var module = angular.module('materiaModule');


module
    .factory('MateriaSrv', ['$http', function($http) {
        return {
            create: function(materia) {
                return $http({
                    url: 'api/materia/create',
                    method: 'POST',
                    data: angular.toJson(materia)
                })
            },
            find: function(nombre, nivel, dadosBaja) {
                return $http({
                    url: 'api/materia/find',
                    method: 'GET',
                    params: {nombre: nombre, nivel: nivel, dadosBaja: dadosBaja}
                })
            },
            remove: function(materiaId, motivoBaja) {
                var materia = {
                    id: materiaId,
                    motivoBaja: motivoBaja}
                return $http({
                    url: 'api/materia/remove',
                    method: 'POST',
                    data: materia
                })
            }

        }

    }]);
