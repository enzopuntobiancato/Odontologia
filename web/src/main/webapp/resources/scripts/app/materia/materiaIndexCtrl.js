var module = angular.module('materiaModule');


module.controller('MateriaCtrl_Index', ['$scope', '$document', '$cacheFactory', 'MateriaSrv', '$state', 'NotificationSrv', 'CommonsSrv', 'nivelesResponse', function ($scope, $document, $cacheFactory, service, $state, notification, commons, nivelesResponse) {

    $scope.filter = {};
    $scope.result = [];
    $scope.niveles = commons.enumToJson(nivelesResponse.data);

    var cache = $cacheFactory.get('materiaIndexCache') || $cacheFactory('materiaIndexCache');

    $scope.aux = {
        showDadosBaja: false
    }

    $scope.consultar = function () {
        notification.showWidget();
        service.find($scope.filter.nombre, $scope.filter.nivel, $scope.filter.dadosBaja).success(function (data) {
            notification.hideWidget();
            $scope.result = data;
            $scope.aux.showDadosBaja = $scope.filter.dadosBaja;
        })
    }

    $scope.new = function () {
        $state.go('^.create');
    }

    $scope.darDeBaja = function (materiaId) {
        notification.requestReason().then(function (motivo) {
            if (motivo != null) {
                notification.showWidget();
                service.remove(materiaId, motivo).success(function (response) {
                    notification.hideWidget();
                    notification.goodAndOnEscape("Materia dada de baja correctamente.", function () {
                        $scope.consultar()
                    }, function () {
                        $scope.consultar()
                    })
                })
                    .error(function (response) {
                        notification.hideWidget();
                        notification.badArray(response, function () {
                        });
                    })
            }
        });
    }

    $scope.darDeAlta = function (materiaId) {
        notification.requestConfirmation("¿Está seguro?", function () {
            altaConfirmada(materiaId)
        });

        function altaConfirmada(materiaId) {
            notification.showWidget();
            service.restore(materiaId)
                .success(function () {
                    notification.hideWidget();
                    notification.goodAndOnEscape("Materia dada de alta correctamente.", function () {
                        $scope.consultar()
                    }, function () {
                        $scope.consultar()
                    })
                })
                .error(function () {
                    notification.hideWidget();
                })
        }
    }

    $scope.edit = function (materiaId) {
        $state.go('^.edit', {id: materiaId});

    }

    $scope.cleanFilters = function () {
        $scope.filter = {};
    }

    function cacheData() {
        var data = {
            filter: $scope.filter,
            result: $scope.result,
            aux: $scope.aux
        }
        cache.put('data', data);
    }

    function getCachedData() {
        var data = cache.get('data');

        $scope.filter = data.filter;
        $scope.result = data.result;
        $scope.aux = data.aux;
    }

    $scope.$on('$stateChangeStart',
        function (event, toState, toParams, fromState, fromParams) {
            if (toState.name.startsWith('materia')) {
                cacheData();
            } else {
                cache.put('data', null);
            }

        });

    $scope.$on('$stateChangeSuccess',
        function (event, toState, toParams, fromState, fromParams) {
            if (fromState.name.startsWith('materia')) {
                getCachedData();
            }

        })

}]);
