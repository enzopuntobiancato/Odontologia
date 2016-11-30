var module = angular.module('historiaClinicaModule');

module
    .factory('HistoriaClinicaSrv', ['$http', function ($http) {
        return {
            save: function (historiaClinica) {
                return $http.post('api/historiaClinica/save',
                    historiaClinica, {

                    });
            },
            findPacientes : function(nombre, apellido, nombreUsuario,sexo,tipoDocumento,numeroDocumento){
                return $http({
                    method:'GET',
                    url:'api/historiaClinica/findPacientes',
                    params: {nombre:nombre, apellido:apellido,nombreUsuario: nombreUsuario,sexo:sexo
                        ,tipoDocumento: tipoDocumento, numeroDocumento: numeroDocumento}
                })
            },
            find: function (numero, fechaApertura, dadosBaja) {
                return $http({
                    url: 'api/historiaClinica/find',
                    method: 'GET',
                    params: {numero: numero, fechaApertura: fechaApertura, dadosBaja: dadosBaja}
                })
            },
            remove: function (historiaClinicaId, motivoBaja) {
                var historiaClinica = {
                    id: historiaClinicaId,
                    motivoBaja: motivoBaja}
                return $http({
                    url: 'api/historiaClinica/remove',
                    method: 'POST',
                    data: historiaClinica
                })
            },
            restore: function (historiaClinicaId) {
                return $http({
                    url: 'api/historiaClinica/restore',
                    method: 'PUT',
                    params: {id: historiaClinicaId}
                })
            },
            findById: function (pacienteId) {
                return $http({
                    url: 'api/historiaClinica/findById',
                    method: 'GET',
                    params: {id: pacienteId}
                })
            },
            findAll: function () {
                return $http({
                    url: 'api/historiaClinica/findAll',
                    method: 'GET'
                })
            },
            findPacienteLightById: function (pacienteId) {
                return $http({
                    url: 'api/paciente/findPacienteLightById',
                    method: 'GET',
                    params: {id: pacienteId}
                })
            },
            findPacienteById: function(pacienteId) {
                return $http({
                    url: 'api/paciente/findById',
                    method: 'GET',
                    params: {id: pacienteId}
                })
            },
            findDocumentacionesByPaciente: function(pacienteId) {
                return $http({
                    url: 'api/historiaClinica/findDocumentaciones',
                    method: 'GET',
                    params: {pacienteId: pacienteId}
                })
            },
            updateCurrentDocsAndSaveTempNewDocs: function(documentaciones, idPaciente) {
                return $http({
                    url: 'api/historiaClinica/saveDocumentaciones/' + idPaciente,
                    method: 'POST',
                    data: documentaciones
                })
            }
        }

    }]);