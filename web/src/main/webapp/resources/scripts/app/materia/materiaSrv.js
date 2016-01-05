var module = angular.module('materiaModule');


module
    .factory('MateriaSrv', ['$http', function ($http) {
        return {
            save: function (materia) {
                return $http({
                    url: 'api/materia/save',
                    method: 'POST',
                    data: angular.toJson(materia)
                })
            },
            find: function (nombre, nivel, dadosBaja) {
                return $http({
                    url: 'api/materia/find',
                    method: 'GET',
                    params: {nombre: nombre, nivel: nivel, dadosBaja: dadosBaja}
                })
            },
            remove: function (materiaId, motivoBaja) {
                var materia = {
                    id: materiaId,
                    motivoBaja: motivoBaja}
                return $http({
                    url: 'api/materia/remove',
                    method: 'POST',
                    data: materia
                })
            },
            restore: function (materiaId) {
                return $http({
                    url: 'api/materia/restore',
                    method: 'PUT',
                    params: {id: materiaId}
                })
            },
            findById: function (id) {
                return $http({
                    url: 'api/materia/findById',
                    method: 'GET',
                    params: {id: id}
                })
            },

            findAll: function () {
                return $http({
                    url: 'api/materia/findAll',
                    method: 'GET'
                })
            }

        }

    }]);

