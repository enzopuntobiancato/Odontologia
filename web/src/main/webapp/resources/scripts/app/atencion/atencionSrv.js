var module = angular.module('atencionModule');

module
    .factory('AtencionSrv', ['$http', function ($http) {
        return {
            findAsignacion: function (asignacionId) {
                return $http({
                    url: 'api/asignacion/findById',
                    method: 'GET',
                    params: {id: asignacionId}
                });
            },
            searchPracticas: function (searchText, catedraId, trabajoPracticoId) {
                return $http({
                    url: 'api/atencion/findPracticas',
                    method: 'GET',
                    params: {text: searchText, catedraId: catedraId, practicoId: trabajoPracticoId}
                })
            },
            getCatedras: function () {
                return $http({
                    method: 'GET',
                    url: 'api/asignacion/getCatedras'
                })
            },
            getTrabajosPracticos: function () {
                return $http({
                    method: 'GET',
                    url: 'api/asignacion/getTrabajosPracticos'
                })
            },
            getTrabajosPracticosByCatedra: function (idCatedra) {
                return $http({
                    method: 'GET',
                    url: 'api/asignacion/getTrabajosPracticosByCatedra',
                    params: {idCatedra: idCatedra}
                })
            },
            findCatedrasByPractica: function (practicaId) {
                return $http({
                    method: 'GET',
                    url: 'api/atencion/findCatedrasByPractica',
                    params: {practicaId: practicaId}
                })
            },
            findDocumentaciones: function (atencionId) {
                return $http({
                    method: 'GET',
                    url: 'api/atencion/findDocumentaciones',
                    params: {atencionId: atencionId}
                })
            },
            findOdontogramaById: function (pacienteId) {
                return $http({
                    url: 'api/paciente/findOdontogramaById',
                    method: 'GET',
                    params: {pacienteId: pacienteId}
                })
            },
            saveAtencion: function(atencion) {
                return $http({
                    url: 'api/atencion/save',
                    method: 'POST',
                    data: atencion
                })
            }
        }
    }]);

