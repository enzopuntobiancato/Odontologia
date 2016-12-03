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
            findFinalStates: function () {
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
            findHallazgos: function () {
                return $http({
                    url: 'api/diagnostico/getHallazgos',
                    method: 'GET'
                })
            },
            findOdontogramaById: function (pacienteId) {
                return $http({
                    url: 'api/paciente/findOdontogramaById',
                    method: 'GET',
                    params: {pacienteId: pacienteId}
                })
            },
            findOdontogramaUriById: function (pacienteId) {
                return $http({
                    url: 'api/paciente/findOdontogramaUriById',
                    method: 'GET',
                    params: {pacienteId: pacienteId}
                })
            },
            findPracticas: function(){
                return $http({
                    url: "api/practicaOdontologica/findAll",
                    method: "GET"
                })
            }
        }
    }]);

