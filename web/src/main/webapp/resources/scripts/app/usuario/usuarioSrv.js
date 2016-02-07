var module = angular.module('usuarioModule');

module.
    factory('UsuarioSrv', ['$http', function ($http) {
        var service = {};

        service.save = function (dto) {
            console.log("UsuarioSrv save")
            return $http({
                method: 'POST',
                url: 'api/usuario/saveUsuario',
                data: angular.toJson(dto)
            })
        }

        service.remove = function (id, motivoBaja) {
            var entity = {
                id: id,
                motivoBaja: motivoBaja}
            return $http({
                url: 'api/usuario/remove',
                method: 'POST',
                data: entity
            })
        }

        service.restore = function (id) {
            return $http({
                url: 'api/usuario/restore',
                method: 'PUT',
                params: {id: id}
            })
        }

        service.findById = function (id) {
            return $http({
                url: 'api/usuario/findById',
                method: 'GET',
                params: {id: id}
            })
        }

        service.getRoles = function () {
            return $http.get('api/commons/getRoles', {});
        }

        service.find = function (nombre, email, dadosBaja, rol) {
            return $http({
                url: 'api/materia/find',
                method: 'GET',
                params: {nombre: nombre, email: email, dadosBaja: dadosBaja, rolId: rol}
            })
        }


        return service;
    }]);
