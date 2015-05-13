var services = angular.module('sapo.services', []);

services.
    factory('NotificationSrv', ['$q', '$location','$anchorScroll', function ($q, $location, $anchorScroll) {

        var service = {};

        service.good = function (msg, callback) {
            bootbox.dialog({message: msg,
                title: '<i class="fa fa-check-circle-o fa-lg"></i> Éxito',
                buttons: {ok: {label: "OK", className: "btn-primary", callback: callback } }
            });
        };

        service.goodAndOnEscape = function (msg, callback, onEscape) {
            bootbox.dialog({message: msg,
                title: '<i class="fa fa-check-circle-o fa-lg"></i> Éxito',
                buttons: {ok: {label: "OK", className: "btn-primary", callback: callback } },
                onEscape: onEscape
            });
        };

        service.warning = function (msg, callback) {
            bootbox.dialog({message: msg,
                title: '<i class="fa fa-exclamation-triangle fa-lg"></i></i> Advertencia',
                buttons: {ok: {label: "OK", className: "btn-primary", callback: callback } }
            });
        };

        service.bad = function (msg, callback) {
            bootbox.dialog({message: msg,
                title: '<i class="fa fa-times-circle fa-lg"></i></i></i> Error',
                buttons: {ok: {label: "OK", className: "btn-primary", callback: callback } }
            });
        };

        service.badArray = function (msg, callback) {
            var message = '<ul>';
            var msgs = Object.keys(msg);
            for (var i = 0; i < msgs.length; i++) {
                message += '<li>' + msg[msgs[i]] + '</li>'
            }
            message += '</ul>';
            bootbox.dialog({message: message,
                title: '<i class="fa fa-times-circle fa-lg"></i></i></i> Error',
                buttons: {ok: {label: "OK", className: "btn-primary", callback: callback } }
            });
        };


        service.showWidget = function () {
            angular.element('#widget').show();
        };
        service.hideWidget = function () {
            angular.element('#widget').hide();
        };

        service.scrollTo = function (id) {
            var old = $location.hash();
            $location.hash(id);
            $anchorScroll();
            $location.hash(old);
        };

        service.requestReason = function () {
            var deferred = $q.defer();
            var dialogOptions = {
                title: '<i class="fa fa-eraser fa-lg"></i></i> Ingrese el motivo de baja',
                inputType: 'textarea',
                buttons: {
                    confirm: {
                        label: 'Aceptar'
                    },
                    cancel: {
                        label: 'Cancelar'
                    }
                },
                callback: function (result) {
                    if (result != null) {
                        deferred.resolve(result);
                    } else {
                        deferred.reject();
                    }
                }
            }
            bootbox.prompt(dialogOptions);
            return deferred.promise;
        };

        service.requestConfirmation = function (msg, Okcallback) {
            bootbox.dialog({message: msg,
                title: '<i class="fa fa-question fa-lg"></i></i> Confirmación',
                buttons: {
                    ok: {label: "Aceptar", className: "btn-primary", callback: Okcallback },
                    cancel: {label: "Cancelar", className: "btn-default", callback: function () {
                    }}
                }
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

    service.getMaterias = function () {
        return $http({
            method: 'GET',
            url: 'api/commons/getMaterias',
            cache: true
        })
    };

    service.getGruposPractica = function(){
        return $http({
            url: 'api/commons/getGruposPracticaOdontologica',
            method: 'GET',
            cache: true
        })
    };

    return service;
}]);