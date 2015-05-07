var module = angular.module('materiaModule');


module.controller('MateriaCtrl_Index', ['$scope','$cacheFactory', 'MateriaSrv', '$state', 'NotificationSrv', 'CommonsSrv', 'nivelesResponse', 'PaginationService', function ($scope, $cacheFactory, service, $state, notification, commons, nivelesResponse, pagination) {

    $scope.filter = {};
    $scope.result = [];
    $scope.niveles = commons.enumToJson(nivelesResponse.data);

    var cache = $cacheFactory.get('materiaIndexCache') || $cacheFactory('materiaIndexCache');

    $scope.aux = {
        showDadosBaja: false
    }

    var params = {
        nombre: $scope.filter.nombre,
        nivel: $scope.filter.nivel,
        dadosBaja: $scope.filter.dadosBaja
    }
    pagination.config('api/materia/find', params);

    $scope.paginationData = pagination.paginationData;

    function executeQuery(pageNumber) {
        notification.showWidget();
        pagination.paginate(pageNumber).then(function(data){
            notification.hideWidget();
            $scope.result = data;
            $scope.aux.showDadosBaja = $scope.filter.dadosBaja;
            $scope.paginationData = pagination.getPaginationData();
        }, function(){notification.hideWidget();});
    }

    $scope.consultar = function () {
         executeQuery();
    }

    $scope.nextPage = function(){
       executeQuery(++$scope.paginationData.pageNumber);
    }
    $scope.previousPage = function() {
        executeQuery(--$scope.paginationData.pageNumber);
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

    $scope.viewDetail = function (materiaId) {
        $state.go('^.view', {id: materiaId});

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
