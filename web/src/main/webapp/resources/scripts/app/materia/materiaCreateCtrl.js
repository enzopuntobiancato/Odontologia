var module = angular.module('materiaModule');


module.controller('MateriaCtrl_Create', ['$scope', '$rootScope', 'MateriaSrv', '$state', 'MessageSrv', 'nivelesResponse',
    function ($scope, $rootScope, service, $state, message, nivelesResponse) {
        $scope.materia = {};

        $scope.data = {
            disableFields: false,
            niveles: nivelesResponse.data,
            persistedOperation: $rootScope.persistedOperation || false,
            saved: false
        };

        var performSubmit = $scope.$parent.performSubmit;
        var handleError = $scope.$parent.handleError;
        $scope.validationErrorFromServer = $scope.$parent.validationErrorFromServer;

        $scope.save = function (form) {
            performSubmit(function() {
                service.save($scope.materia)
                    .success(function () {
                        $scope.data.persistedOperation = true;
                        $scope.data.disableFields = true;
                        $scope.data.saved = true;
                        message.successMessage($scope.materia.nombre + " creada con éxito");
                        $scope.goIndex();
                    })
                    .error(function (data, status) {
                        handleError(data, status);
                    })
            }, form);
        };

        $scope.goIndex = function () {
            $state.go('^.index', {execQuery: $scope.data.persistedOperation});
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
