/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 28/12/15
 * Time: 16:54
 * To change this template use File | Settings | File Templates.
 */
var module = angular.module('asignacionModule');

module.
    factory('AsignacionSrv', ['$http', function ($http) {
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

        service.findAllCatedras = function(){
            return $http({
                method:'GET',
                url: 'api/asignacion/findAllCatedras'
            })
        }

        service.findAllTrabajosPracticos = function(){
            return $http({
                method:'GET',
                url: 'api/asignacion/findAllTrabajosPracticos'
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