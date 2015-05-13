var module = angular.module('trabajoPracticoModule');


module
    .factory('TrabajoPracticoSrv', ['$http', function ($http) {
        return {
            getPracticas: function() {
                return $http({
                    url: 'api/practicaOdontologica/findAll',
                    method: 'GET'
                })
            },
            save: function (trabajoPractico) {
                return $http({
                    url: 'api/trabajoPractico/save',
                    method: 'POST',
                    data: angular.toJson(trabajoPractico)
                })
            }
//            remove: function (materiaId, motivoBaja) {
//                var materia = {
//                    id: materiaId,
//                    motivoBaja: motivoBaja}
//                return $http({
//                    url: 'api/materia/remove',
//                    method: 'POST',
//                    data: materia
//                })
//            },
//            restore: function (materiaId) {
//                return $http({
//                    url: 'api/materia/restore',
//                    method: 'PUT',
//                    params: {id: materiaId}
//                })
//            },
//            findById: function(id) {
//                return $http({
//                    url: 'api/materia/findById',
//                    method: 'GET',
//                    params: {id: id}
//                })
//            }

        }

    }]);
