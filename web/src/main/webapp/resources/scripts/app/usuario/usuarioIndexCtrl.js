var module = angular.module('materiaModule');


module.controller('UsuarioCtrl_Index', ['$scope','$cacheFactory', 'UsuarioSrv', '$state', '$stateParams', 'NotificationSrv', 'PaginationService', 'rolesResponse', function ($scope, $cacheFactory, service, $state, $stateParams, notification, pagination, rolesResponse) {

    $scope.filter = {};
    $scope.result = [];

    $scope.roles = rolesResponse.data;

    var cache = $cacheFactory.get('usuarioIndexCache') || $cacheFactory('usuarioIndexCache');

    $scope.aux = {
        showDadosBaja: false
    }

    pagination.config('api/usuario/find');

    $scope.paginationData = pagination.paginationData;

    function executeQuery(pageNumber) {
        pagination.paginate($scope.filter, pageNumber).then(function(data){
            $scope.result = data;
            $scope.aux.showDadosBaja = $scope.filter.dadosBaja;
            $scope.paginationData = pagination.getPaginationData();
        }, function(){
        });
    }

    $scope.consultar = function () {
        executeQuery();
    }

    $scope.nextPage = function () {
        if ($scope.paginationData.morePages) {
            executeQuery(++$scope.paginationData.pageNumber);
        }
    }


    $scope.previousPage = function () {
        if (!$scope.paginationData.firstPage) {
            executeQuery(--$scope.paginationData.pageNumber);
        }
    }

    $scope.keyboardOk = function (event) {
        if (event.which == 13) {
            executeQuery();
        }
    }

    $scope.new = function () {
        $state.go('^.create');
    }

    $scope.darDeBaja = function (id) {
        notification.requestReason().then(function (motivo) {
            if (motivo != null) {
//                notification.showWidget();
                service.remove(id, motivo).success(function (response) {
//                    notification.hideWidget();
                    notification.goodAndOnEscape("Usuario dado de baja correctamente.", function () {
                        executeQuery($scope.paginationData.pageNumber);
                    }, function () {
                        executeQuery($scope.paginationData.pageNumber);
                    })
                })
                    .error(function (response) {
//                        notification.hideWidget();
                        notification.badArray(response, function () {
                        });
                    })
            }
        });
    }

    $scope.darDeAlta = function (id) {
        notification.requestConfirmation("¿Está seguro?", function () {
            altaConfirmada(id)
        });

        function altaConfirmada(id) {
//            notification.showWidget();
            service.restore(id)
                .success(function () {
//                    notification.hideWidget();
                    notification.goodAndOnEscape("Usuario dado de alta correctamente.", function () {
                        executeQuery($scope.paginationData.pageNumber);
                    }, function () {
                        executeQuery($scope.paginationData.pageNumber);
                    })
                })
                .error(function () {
//                    notification.hideWidget();
                })
        }
    }

    $scope.edit = function (id) {
        $state.go('^.edit', {id: id});

    }

    $scope.viewDetail = function (id) {
        $state.go('^.view', {id: id});

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
            if (toState.name.startsWith('usuario')) {
                cacheData();
            } else {
                cache.put('data', null);
            }

        });

    $scope.$on('$stateChangeSuccess',
        function (event, toState, toParams, fromState, fromParams) {
            if (fromState.name.startsWith('usuario')) {
                if (toParams.execQuery) {
                    executeQuery();
                } else if (toParams.execQuerySamePage){
                    getCachedData();
                    executeQuery($scope.paginationData.pageNumber)
                } else {
                    getCachedData();
                }

            }

        })

}]);
