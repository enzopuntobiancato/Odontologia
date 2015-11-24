var services = angular.module('sapo.services', []);


services.factory('MessageSrv', ['$mdToast', function($mdToast){
   var service = {};
   service.showMessage = function(msg){
       $mdToast.show(
           $mdToast.simple()
               .content(msg)
               .position('top right')
               .hideDelay(3000)
       );
   };

    return service;
}]);

services
    .factory('CommonsSrv', ['$http', function ($http) {
        var service = {};

        service.enumToJson = function (data) {
            var result = [];

            for (var i = 0; i < data.length; i++) {
                var json = {};
                json.id = i;
                json.nombre = data[i];
                result.push(json);
            }
            return result;
        };

        service.getNiveles = function () {
            return $http({
                method: 'GET',
                url: 'api/commons/getNiveles',
                cache: true
            })
        };

        service.getDias = function () {
            return $http({
                method: 'GET',
                url: 'api/commons/getDias',
                cache: true
            })
        };

        service.getGruposPractica = function () {
            return $http({
                url: 'api/commons/getGruposPracticaOdontologica',
                method: 'GET',
                cache: true
            })
        };

        service.getTiposDocumento = function() {
            return $http({
                url: 'api/commons/getTiposDocumento',
                method: 'GET',
                cache: true
            })
        };

        service.savePerson = function(persona) {
            return $http({
                url: 'api/persona/save',
                method: 'POST',
                data: persona
            })
        }

        service.initializeData = function () {
            return $http({
                url: 'api/commons/initializeData',
                method: 'POST'
            })
        }

        return service;
    }]);
