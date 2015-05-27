var module = angular.module('trabajoPracticoModule');


module
    .factory('TrabajoPracticoSrv', ['$http', 'ABMCFactory', function ($http, ABMCFactory) {
        return {
            abmcFactory: ABMCFactory.config('trabajoPractico'),
            getPracticas: function() {
                return $http({
                    url: 'api/practicaOdontologica/findAll',
                    method: 'GET'
                })
            }
        }

    }]);
