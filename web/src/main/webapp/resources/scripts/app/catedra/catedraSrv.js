var module = angular.module('catedraModule');

module.
    factory('CatedraSrv', ['$http', 'ABMCFactory', function ($http, ABMCFactory) {
        var service = {
            abmcFactory: ABMCFactory.config('catedra')
        };

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
    }])