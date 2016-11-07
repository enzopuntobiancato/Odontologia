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
           /* save: function (diagnosticos, piezas, pacienteId) {
                var formData = new FormData();

                formData.append("pacienteId", pacienteId);
                formData.append('diagnosticos', diagnosticos);
                formData.append('piezas', piezas);

                return $http({
                    url: 'api/diagnostico/save/' + pacienteId,
                    method: 'POST',
                    data: formData,
                    //assign content-type as undefined, the browser
                    //will assign the correct boundary for us
                    headers: { 'Content-Type': undefined},
                    //prevents serializing payload.  don't do it.
                    transformRequest: angular.identity
                })
            },*/
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
            findPracticas: function(){
                return $http({
                    url: "api/practicaOdontologica/findAll",
                    method: "GET"
                })
            }
        }
    }]);

