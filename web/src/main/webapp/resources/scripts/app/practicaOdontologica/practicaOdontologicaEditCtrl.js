var module = angular.module('practicaOdontologicaModule');


module.controller('PracticaOdontologicaCtrl_Edit', ['$scope', '$rootScope', 'PracticaOdontologicaSrv', '$state', 'MessageSrv', 'gruposPracticaResponse', 'practicaResponse',
    function ($scope, $rootScope, service, $state, message, gruposPracticaResponse, practicaResponse) {
        $scope.practica = practicaResponse.data;

        $scope.data = {
            disableFields: false,
            gruposPractica: gruposPracticaResponse.data,
            persistedOperation: $rootScope.persistedOperation || false,
            saved: false
        }

        $scope.save = function () {
            service.save($scope.practica)
                .success(function (data) {
                    $scope.data.persistedOperation = true;
                    $scope.data.disableFields = true;
                    $scope.data.saved = true;
                    message.showMessage("Práctica odontológica " + $scope.practica.denominacion + " modificada  con éxito!");
                    $scope.goIndex();
                })
                .error(function (data) {
                    message.showMessage("Error al modificar " + $scope.practica.denominacion);
                })
        }

        $scope.goIndex = function () {
            $state.go('^.index', {execQuery: false, execQuerySamePage: $scope.data.persistedOperation});
        }

        $scope.reload = function () {
            $rootScope.persistedOperation = $scope.data.persistedOperation;
            $state.go($state.current, {}, {reload: true});
        }

        $scope.$on('$stateChangeStart',
            function (event, toState, toParams, fromState, fromParams) {
                if (!angular.equals($state.current, toState)) {
                    delete $rootScope.persistedOperation;
                }
            });
    }]);
