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

        service.save = function (entity) {
            return $http({
                method: 'POST',
                url: 'api/asignacion/save',
                data: angular.toJson(entity)
            })
        }

        service.cambiarEstado = function (dto, estadoKey) {
            return $http({
                url: 'api/asignacion/cambiarEstadoAsignacion',
                method: 'POST',
                data: dto,
                params: {estadoKey: estadoKey}
            })
        }

        service.autorizar = function(asignaciones){
            return $http({
                url: 'api/asignacion/autorizarAsignaciones',
                method: 'POST',
                data : asignaciones
            })
        }

        service.remove = function (id, motivoBaja) {
            var entity = {
                id: id,
                motivoBaja: motivoBaja}
            return $http({
                url: 'api/asignacion/remove',
                method: 'POST',
                data: entity
            })
        }

        service.restore = function (id) {
            return $http({
                url: 'api/asignacion/restore',
                method: 'PUT',
                params: {id: id}
            })
        }

        service.findById = function(id) {
            return $http({
                url: 'api/asignacion/findById',
                method: 'GET',
                params: {id: id}
            })
        }

        service.findAlumnoByUser = function (id) {
            return $http({
                url: 'api/asignacion/findAlumnoByUser',
                method: 'GET',
                params: {id: id}
            })
        }

        service.findProfesorByUser = function (id) {
            return $http({
                url: 'api/asignacion/findProfesorByUser',
                method: 'GET',
                params: {id: id}
            })
        }

        service.find = function(nombreAlumno, apellidoAlumno,  tipoDocumentoAlumno,numeroDocumentoAlumno,
                                nombrePaciente, apellidoPaciente, dadosBaja, tipoDocumentoPaciente, numeroDocumentoPaciente,
                                catedra, trabajoPractico, estado, fechaAsignacion) {
            return $http({
                url: 'api/asignacion/find',
                method: 'GET',
                params: {alumnoId: alumnoId, nombrePaciente: nombrePaciente, apellidoPaciente: apellidoPaciente,
                    tipoDocumentoPaciente: tipoDocumentoPaciente, numeroDocumentoPaciente: numeroDocumentoPaciente,
                    profesorId: profesorId, catedraId: catedraId, trabajoPracticoId: trabajoPracticoId, estado: estado,
                    fechaCreacion: fechaCreacion, fechaAsignacion: fechaAsignacion,dadosBaja: dadosBaja}
            })
        }

        service.findDiagnosticosByFilters = function(nombre, apellido, tipoDocumento, numeroDocumento, catedra, trabajoPractico){
            return $http({
                url:'api/asignacion/findDiagnosticosByFilters',
                method: 'GET',
                params: {nombre: nombre, apellido: apellido, tipoDocumento : tipoDocumento, numeroDocumento : numeroDocumento,
                    catedra : catedra, trabajoPractico:trabajoPractico}
            })
        }

        service.getCatedras = function(){
            return $http({
                method:'GET',
                url: 'api/asignacion/getCatedras'
            })
        }

        service.findCatedrasByProfesor = function(profesorId){
            return $http({
                method:'GET',
                url: 'api/asignacion/findCatedrasByProfesor',
                params: {
                    profesorId : profesorId
                }
            })
        }

        service.getTrabajosPracticosByCatedra = function(idCatedra){
            return $http({
                method:'GET',
                url:'api/asignacion/getTrabajosPracticosByCatedra',
                params:{idCatedra : idCatedra}
            })
        }

        service.estaAutorizado = function(alumno, trabajoPractico){
            return $http({
                method: 'GET',
                url: 'api/asignacion/estaAutorizado',
                params:{
                    trabajoPractico : trabajoPractico,
                    alumno : alumno
                }
            })
        }

        service.getTrabajosPracticos = function(){
            return $http({
                method:'GET',
                url: 'api/asignacion/getTrabajosPracticos'
            })
        }

        service.getPracticas = function() {
            return $http({
                url: 'api/practicaOdontologica/findAll',
                method: 'GET'
            })
        }

        service.getEstadosAsignaciones = function() {
            return $http({
                url: 'api/asignacion/getEstadosAsignaciones',
                method: 'GET'
            })
        }

        return service;
    }]);