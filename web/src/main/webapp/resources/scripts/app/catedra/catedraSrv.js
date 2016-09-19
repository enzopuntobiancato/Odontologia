var module = angular.module('catedraModule');

module.
    factory('CatedraSrv', ['$http', function ($http) {
        var service = {};

        service.save = function (entity) {
            return $http({
                method: 'POST',
                url: 'api/catedra/save',
                data: angular.toJson(entity)
            })
        }

        service.remove = function (id, motivoBaja) {
            var entity = {
                id: id,
                motivoBaja: motivoBaja}
            return $http({
                url: 'api/catedra/remove',
                method: 'POST',
                data: entity
            })
        }

        /*service.restore = function (id) {
            return $http({
                url: 'api/catedra/restore',
                method: 'PUT',
                params: {id: id}
            })
        }*/

        service.findById = function(id) {
            return $http({
                url: 'api/catedra/findById',
                method: 'GET',
                params: {id: id}
            })
        }

        service.findAllMaterias = function(){
            return $http({
                method:'GET',
                url: 'api/materia/findAll'
            })
        }

        service.getPracticas = function() {
            return $http({
                url: 'api/practicaOdontologica/findAll',
                method: 'GET'
            })
        }
        return service;
    }]);
