var module = angular.module('practicaOdontologicaModule');


module
    .factory('PracticaOdontologicaSrv', ['$http', function ($http) {
        return {
            save: function (practicaOdontologica) {
                return $http({
                    url: 'api/practicaOdontologica/save',
                    method: 'POST',
                    data: angular.toJson(practicaOdontologica)
                })
            },
            find: function (params) {
                return $http({
                    url: 'api/practicaOdontologica/find',
                    method: 'GET',
                    params: params
                })
            },
            remove: function (practicaId, motivoBaja) {
                var practica = {
                    id: practicaId,
                    motivoBaja: motivoBaja}
                return $http({
                    url: 'api/practicaOdontologica/remove',
                    method: 'POST',
                    data: practica
                })
            },
            restore: function (practicaId) {
                return $http({
                    url: 'api/practicaOdontologica/restore',
                    method: 'PUT',
                    params: {id: practicaId}
                })
            },
            findById: function(id) {
                return $http({
                    url: 'api/practicaOdontologica/findById',
                    method: 'GET',
                    params: {id: id}
                })
            }

        }

    }]);
