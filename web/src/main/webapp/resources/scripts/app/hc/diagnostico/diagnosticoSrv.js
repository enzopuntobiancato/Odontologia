var module = angular.module('historiaClinicaModule');


module
    .factory('DiagnosticoSrv', ['$http', function ($http) {
        return {
            findDiagnosticos: function (pacienteId) {
                return $http({
                    url: 'api/diagnostico/findAllDiagnosticosByPaciente',
                    method: 'GET',
                    params: {pacienteId: pacienteId}
                })
            },
            findOpenDiagnosticos: function (pacienteId) {
                return $http({
                    url: 'api/diagnostico/findOpenDiagnosticosByPaciente',
                    method: 'GET',
                    params: {pacienteId: pacienteId}
                })
            },
            findFinalStates: function() {
                return $http({
                    url: 'api/diagnostico/findFinalStates',
                    method: 'GET'
                })
            },
            searchPracticas: function (searchText) {
                return $http({
                    url: 'api/practicaOdontologica/findByDenominacion',
                    method: 'GET',
                    params: {text: searchText}
                })
            },
            save: function (diagnosticos, pacienteId) {
                return $http({
                    url: 'api/diagnostico/save/' + pacienteId,
                    method: 'POST',
                    data: diagnosticos
                })
            }
        }
    }]);

