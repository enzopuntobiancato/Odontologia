var module = angular.module('profesorModule');

module.factory('ProfesorSrv', ['$http', function ($http) {
    return {
        findProfesoresByApellido: function (searchText) {
            return $http({
                method: 'GET',
                url: 'api/persona/findProfesoresByApellido',
                params: {searchText: searchText}
            });
        },
        findCatedrasByDenomicacion: function (searchText) {
            return $http({
                method: 'GET',
                url: 'api/catedra/findCatedrasByDenomicacion',
                params: {searchText: searchText}
            });
        },
        addCatedraToProfesor: function (entity) {
            return $http({
                method: 'POST',
                url: 'api/persona/addCatedraToProfesor',
                data: angular.toJson(entity)
            })
        },
        findAllMaterias : function(){
        return $http({
            method:'GET',
            url: 'api/materia/findAll'
        })
    }
    }
}]);