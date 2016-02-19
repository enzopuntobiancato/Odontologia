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

        var performSubmit = $scope.$parent.performSubmit;
        var handleError = $scope.$parent.handleError;
        $scope.validationErrorFromServer = $scope.$parent.validationErrorFromServer;

        $scope.save = function (form) {
            performSubmit(function () {
                service.save($scope.practica)
                    .success(function () {
                        $scope.data.persistedOperation = true;
                        $scope.data.disableFields = true;
                        $scope.data.saved = true;
                        message.showMessage($scope.practica.denominacion + " modificada con éxito.");
                        $scope.goIndex();
                    })
                    .error(function (data, status) {
                        handleError(data, status);
                    })
            }, form);
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
