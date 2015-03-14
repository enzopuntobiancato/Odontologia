var module = angular.module('catedraModule');

module.
    factory('CatedraSrv', ['$http', function ($http) {
        var service = {};

        service.create = function (catedra) {
            return $http({
                method: 'POST',
                url: 'api/catedra/create',
                data: angular.toJson(catedra)
            })
        }


        return service;
    }])