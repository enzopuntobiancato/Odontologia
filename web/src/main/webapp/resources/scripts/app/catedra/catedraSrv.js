var module = angular.module('catedraModule');

module.
    factory('CatedraSrv', ['$http', function ($http) {
        var service = {};

        service.save = function (catedra) {
            return $http({
                method: 'POST',
                url: 'api/catedra/save',
                data: angular.toJson(catedra)
            })
        }

        service.findAllMaterias = function(){
            return $http({
                method:'GET',
                url: 'api/materia/findAll'
            })
        }
        return service;
    }])