var module = angular.module('practicaOdontologicaModule');


module.controller('PracticaOdontologicaCtrl_Create', ['$scope', '$rootScope', 'PracticaOdontologicaSrv', '$state', 'MessageSrv', 'gruposPracticaResponse', '$filter',
    function ($scope, $rootScope, service, $state, message, gruposPracticaResponse) {
        $scope.practica = {};

        $scope.data = {
            disableFields: false,
            gruposPractica: gruposPracticaResponse.data,
            persistedOperation: $rootScope.persistedOperation || false,
            saved: false
        };

        $scope.save = function () {
            service.save($scope.practica)
                .success(function (data) {
                    $scope.data.persistedOperation = true;
                    $scope.data.disableFields = true;
                    $scope.data.saved = true;
                    message.showMessage("Práctica creada con éxito");
                    $scope.goIndex();
                })
                .error(function (data) {
                    message.showMessage("Error al crear la práctica");
                })
        };

        $scope.goIndex = function () {
            $state.go('^.index', {execQuery: false, execQuerySamePage: $scope.data.persistedOperation});
        };

        $scope.reload = function () {
            $rootScope.persistedOperation = $scope.data.persistedOperation;
            $state.go($state.current, {}, {reload: true});
        };

        $scope.$on('$stateChangeStart',
            function (event, toState, toParams, fromState, fromParams) {
                if (!angular.equals($state.current, toState)) {
                    delete $rootScope.persistedOperation;
                }
            });
    }]);
