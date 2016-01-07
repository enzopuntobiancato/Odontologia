/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 28/12/15
 * Time: 16:54
 * To change this template use File | Settings | File Templates.
 */
var module = angular.module('pacienteModule');

module.
    factory('PacienteSrv', ['$http', function ($http) {
        var service = {};

      //  service.save = function (entity) {
      //      return $http({
      //          method: 'POST',
      //          url: 'api/paciente/save',
      //          data: angular.toJson(entity)
      //      })
      //  }
      //
      //  service.remove = function (id, motivoBaja) {
      //      var entity = {
      //          id: id,
      //          motivoBaja: motivoBaja}
      //      return $http({
      //          url: 'api/paciente/remove',
      //          method: 'POST',
      //          data: entity
      //      })
      //  }
      //
      //  service.restore = function (id) {
      //      return $http({
      //          url: 'api/paciente/restore',
      //          method: 'PUT',
      //          params: {id: id}
      //      })
      //  }

        service.findById = function(id) {
            return $http({
                url: 'api/paciente/findById',
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