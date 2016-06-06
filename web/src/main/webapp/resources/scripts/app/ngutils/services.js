var services = angular.module('sapo.services', []);

services
    .controller('ToastCtrl', function ($scope, $mdToast) {
    $scope.closeToast = function () {
        $mdToast.hide();
    };
});

services
    .factory('MessageSrv', ['$mdToast', function ($mdToast) {
    var service = {};

    service.successMessage = function (msg, position, hideDelay) {
        $mdToast.show({
            controller: 'ToastCtrl',
            template: '<md-toast>\
                <span flex>' + msg + '</span>\
                <md-button class="md-highlight" ng-click="closeToast()">\
                Cerrar</md-button>\
                </md-toast>',
            hideDelay: hideDelay ? hideDelay : 3000,
            position: position ? position : 'top right'
        });
    };

    service.errorMessage = function (msg, position, hideDelay) {
        $mdToast.show({
            controller: 'ToastCtrl',
            template: '<md-toast>\
                <span flex>' + msg + '</span>\
                <md-button class="md-highlight md-warn" ng-click="closeToast()">\
                Cerrar</md-button>\
                </md-toast>',
            hideDelay: hideDelay ? hideDelay : 3000,
            position: position ? position : 'top right'
        });
    }
    service.showMessage = function (msg, position, hideDelay) {
        $mdToast.show({
            controller: 'ToastCtrl',
            template: '<md-toast>\
                <span flex>' + msg + '</span>\
                <md-button ng-click="closeToast()">\
                Cerrar</md-button>\
                </md-toast>',
            hideDelay: hideDelay ? hideDelay : 3000,
            position: position ? position : 'top right'
        });
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

        service.getTiposDocumento = function () {
            return $http({
                url: 'api/commons/getTiposDocumento',
                method: 'GET',
                cache: true
            })
        };

        service.savePerson = function (persona) {
            return $http({
                url: 'api/persona/save',
                method: 'POST',
                data: persona
            })
        };

        service.initializeData = function () {
            return $http({
                url: 'api/commons/initializeData',
                method: 'POST'
            })
        };

        service.getSexos = function () {
            return $http.get('api/commons/getSexos', {});
        };

        service.getCargos = function () {
            return $http.get('api/commons/getCargos', {});
        };

        service.getProvincias = function(){
            return $http.get('api/location/getProvincias',{});
        };

       service.getCiudades = function(){
            return $http.get('api/location/getCiudades',{});
        };

        service.getBarrios = function(){
            return $http.get('api/location/getBarrios',{});
        };

        service.getEstadosCivil = function(){
            return $http.get('api/commons/getEstadosCivil',{});
        };

        service.getTrabajos = function(){
            return $http.get('api/commons/getTrabajos',{});
        };

        service.getObrasSociales = function(){
            return $http.get('api/commons/getObrasSociales',{});
        };

        service.getNivelesEstudio = function(){
            return $http.get('api/commons/getNivelesEstudio',{});
        };

        service.getNacionalidaes = function(){
            return $http.get('api/commons/getNacionalidades',{});
        };

        service.getPersonaEnums = function() {
            return $http.get('api/commons/getPersonaEnums', {});
        }

        return service;
    }]);

services
    .factory('LocationSrv', ['$http', function ($http) {
        var service = {};
        service.getProvincias = function(){
            $http.get('api/location/getProvincias',{});
        }

       /* service.enumToJson = function (data) {
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

        service.getTiposDocumento = function () {
            return $http({
                url: 'api/commons/getTiposDocumento',
                method: 'GET',
                cache: true
            })
        };

        service.savePerson = function (persona) {
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

        service.getSexos = function () {
            return $http.get('api/commons/getSexos', {});
        }

        service.getCargos = function () {
            return $http.get('api/commons/getCargos', {});
        }

        service.getProvincias = function(){
            return $http.get('api/location/getProvincias',{})
        }

        *//* service.getCiudades = function(){
         return $http.get('api/commons/getCiudades',{})
         }

         service.getBarrios = function(){
         return $http.get('api/commons/getBarrios',{})
         }*//*

        service.getEstadoCivil = function(){
            return $http.get('api/commons/getEstadosCivil',{})
        }*/

        return service;
    }]);

