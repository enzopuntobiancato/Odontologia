var module = angular.module('materiaModule');


module
    .factory('MateriaSrv', ['$http','ABMCFactory', function ($http, ABMCFactory) {
        return {
            abmcFactory: ABMCFactory.config('materia'),
            find: function (nombre, nivel, dadosBaja) {
                return $http({
                    url: 'api/materia/find',
                    method: 'GET',
                    params: {nombre: nombre, nivel: nivel, dadosBaja: dadosBaja}
                })
            }
        }

    }]);
