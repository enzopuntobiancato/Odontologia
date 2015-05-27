var module = angular.module('practicaOdontologicaModule');


module
    .factory('PracticaOdontologicaSrv', ['$http', 'ABMCFactory', function ($http, ABMCFactory) {
        return {
            abmcFactory: ABMCFactory.config('practicaOdontologica'),
            find: function (params) {
                return $http({
                    url: 'api/practicaOdontologica/find',
                    method: 'GET',
                    params: params
                })
            },
            findAll: function() {
                return $http({
                    url: 'api/practicaOdontologica/findAll',
                    method: 'GET'
                })
            }
        }

    }]);
