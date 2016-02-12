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
            save: function (trabajoPracticoDTO) {
                return $http({
                    url: 'api/trabajoPractico/save',
                    method: 'POST',
                    data: trabajoPracticoDTO
                })
            },
            remove: function (id, motivoBaja) {
                var entity = {
                    id: id,
                    motivoBaja: motivoBaja}
                return $http({
                    url: 'api/trabajoPractico/remove',
                    method: 'POST',
                    data: entity
                })
            },
            restore: function (id) {
                return $http({
                    url: 'api/trabajoPractico/restore',
                    method: 'PUT',
                    params: {id: id}
                })
            },
            findById: function(id) {
                return $http({
                    url: 'api/trabajoPractico/findById',
                    method: 'GET',
                    params: {id: id}
                })
            }

        }

    }]);
