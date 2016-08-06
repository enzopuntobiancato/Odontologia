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

        service.save = function (paciente) {
            return $http({
                method: 'POST',
                url: 'api/paciente/save',
                data: paciente
            })
        }
        service.remove = function (pacienteId,motivoBaja) {
            var entity = {
                id: pacienteId,
                motivoBaja: motivoBaja}
            return $http({
                url: 'api/paciente/remove',
                method: 'POST',
                data: entity
            })
        }

        service.find = function(nombre, apellido, nombreUsuario,sexo,tipoDocumento,numeroDocumento){
            return $http({
                method:'GET',
                url:'api/paciente/find',
                params: {nombre:nombre, apellido:apellido,nombreUsuario: nombreUsuario,sexo:sexo
                    ,tipoDocumento: tipoDocumento, numeroDocumento: numeroDocumento}
            })
        }

        service.findById = function(id) {
            return $http({
                url: 'api/paciente/findById',
                method: 'GET',
                params: {id: id}
            })
        }

        service.initPaciente = function() {
            return $http({
                url: 'api/paciente/initPaciente',
                method: 'GET'
            })
        }

        service.restore = function(id){
            return $http({
                method:"PUT",
                url:'api/paciente/restore',
                params:{id:id}
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