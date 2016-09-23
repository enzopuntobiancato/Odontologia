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

        service.getProvincias = function () {
            return $http.get('api/location/getProvincias', {});
        };

        service.getCiudades = function () {
            return $http.get('api/location/getCiudades', {});
        };

        service.getBarrios = function () {
            return $http.get('api/location/getBarrios', {});
        };

        service.getEstadosCivil = function () {
            return $http.get('api/commons/getEstadosCivil', {});
        };

        service.getTrabajos = function () {
            return $http.get('api/commons/getTrabajos', {});
        };

        service.getObrasSociales = function () {
            return $http.get('api/commons/getObrasSociales', {});
        };

        service.getNivelesEstudio = function () {
            return $http.get('api/commons/getNivelesEstudio', {});
        };

        service.getNacionalidaes = function () {
            return $http.get('api/commons/getNacionalidades', {});
        };

        service.getPersonaEnums = function () {
            return $http.get('api/commons/getPersonaEnums', {});
        };

        service.getEstadosDiagnostico = function() {
            return $http.get('api/commons/getEstadosDiagnostico', {});
        }

        return service;
    }]);

services
    .factory('DeleteRestoreSrv', ['$http', '$mdDialog', 'MessageSrv', function ($http, $mdDialog, messages) {
        var service = {};

        service.data = {};

        service.config = function (deleteUrl, restoreUrl, entityName, queryFunction) {
            service.data.deleteUrl = deleteUrl;
            service.data.restoreUrl = restoreUrl;
            service.data.entityName = entityName;
            service.data.queryFunction = queryFunction;
        };

        service.delete = function (event, entityId, liveEntityName, currentPage, dadosBaja, resultSize) {
            $mdDialog.show({
                templateUrl: 'views/deleteEntity.html',
                parent: angular.element(document.body),
                targetEvent: event,
                clickOutsideToClose: true,
                controller: function DialogController($scope, $mdDialog) {
                    $scope.liveEntityName = liveEntityName;
                    $scope.entityName = service.data.entityName;
                    $scope.motivoBaja;
                    $scope.cancelar = function () {
                        $mdDialog.cancel();
                    };
                    $scope.confirmar = function (form) {
                        if (form.$valid) {
                            $mdDialog.hide($scope.motivoBaja);
                        }
                    };
                }
            })
                .then(function (motivoBaja) {
                    //Success
                    $http({
                        url: service.data.deleteUrl,
                        method: 'POST',
                        data: {id: entityId, motivoBaja: motivoBaja}

                    }).then(function () {
                            messages.successMessage('Baja exitosa: ' + liveEntityName);
                            if(dadosBaja != null && resultSize != null && currentPage != null){
                                var execQuerySamePage = dadosBaja || resultSize > 1;
                                service.data.queryFunction(execQuerySamePage ? currentPage : 0);
                            }
                        }, function () {
                            messages.errorMessage('Error en baja: ' + liveEntityName);
                        })
                },
                function () {
                    //Cancelled dialog - Do nothing
                });
        };

        service.restore = function (event, entityId, liveEntityName, currentPage) {
            $mdDialog.show({
                templateUrl: 'views/restoreEntity.html',
                parent: angular.element(document.body),
                targetEvent: event,
                clickOutsideToClose: true,
                controller: function DialogController($scope, $mdDialog) {
                    $scope.liveEntityName = liveEntityName;
                    $scope.entityName = service.data.entityName;
                    $scope.cancelar = function () {
                        $mdDialog.cancel();
                    };
                    $scope.confirmar = function () {
                        $mdDialog.hide();
                    };
                }
            })
                .then(function () {
                    $http({
                        url: service.data.restoreUrl,
                        method: 'PUT',
                        params: {id: entityId}
                    }).then(function () {
                            messages.successMessage('Alta exitosa: ' + liveEntityName);
                            if(currentPage != null){
                                service.data.queryFunction(currentPage);
                            }
                        }, function () {
                            messages.errorMessage('Error en alta: ' + liveEntityName);
                        })
                },
                function () {
                    // Cancelled dialog. Do nothing
                });
        }

        return service;
    }]);

services
    .factory('ConfirmCreateEntitySrv', ['$http', '$mdDialog', 'MessageSrv', function ($http, $mdDialog, messages) {
        var service = {};

        service.data = {};

        service.config = function (createUrl,entityName, goIndexFunction) {
            service.data.createUrl = createUrl;
            service.data.entityName = entityName;
            service.data.goIndexFunction = goIndexFunction;
        };

        service.confirmCreate = function (event, entity, liveEntityName, isUpdate) {
            $mdDialog.show({
                templateUrl: 'views/confirmCreateEntity.html',
                parent: angular.element(document.body),
                targetEvent: event,
                clickOutsideToClose: true,
                controller: function DialogController($scope, $mdDialog) {
                    $scope.isUpdate = isUpdate;
                    $scope.liveEntityName = liveEntityName;
                    $scope.entity = entity;
                    $scope.entityName = service.data.entityName;
                    $scope.titulo = isUpdate ? 'Edición de ' : 'Creación de ';
                    $scope.contenido = isUpdate ? '¿Está seguro que desea proseguir con la edición?'
                        : ' ¿Está seguro que desea proseguir con la creación?';

                    $scope.cancelar = function () {
                        $mdDialog.cancel();
                    };
                    $scope.confirmar = function () {
                        $mdDialog.hide();
                    };
                }
            })
                .then(function () {
                     var successMsg = isUpdate ? "Edición exitosa: " : "Alta exitosa: ";
                    var failedMsg = isUpdate ? "Error en edición: " : "Error en creación: ";
                    $http({
                        url: service.data.createUrl,
                        method: 'POST',
                        data: entity
                    }).then(function () {
                            messages.successMessage(successMsg + liveEntityName);
                            service.data.goIndexFunction();
                        }, function () {
                            messages.errorMessage(failedMsg + liveEntityName);
                        })
                },
                function () {
                    // Cancelled dialog. Do nothing
                });
        }

        return service;
    }]);

services
    .factory('LocationSrv', ['$http', function ($http) {
        var service = {};
        service.getProvincias = function () {
            $http.get('api/location/getProvincias', {});
        }
        return service;
    }]);

