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
        };

        service.findPacienteLightById = function(id) {
            return $http({
                url: 'api/paciente/findPacienteLightById',
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

        service.initOdontograma = function() {
            return $http({
                url: 'api/paciente/initOdontograma',
                method: 'GET'
            })
        };

        service.findHallazgos = function() {
            return $http({
                url: 'api/diagnostico/getHallazgos',
                method: 'GET'
            })
        };

        service.findPacienteImage = function(imageId) {
            return $http({
                method: 'GET',
                params: {id: imageId},
                url: 'api/file/image',
                headers: {'Content-Type': 'application/json'},
                responseType: 'blob'
            });
        }
        return service;
    }]);