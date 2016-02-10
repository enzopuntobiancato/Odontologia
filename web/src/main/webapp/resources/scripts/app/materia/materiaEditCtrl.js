var module = angular.module('materiaModule');


module.controller('MateriaCtrl_Edit', ['$scope', '$rootScope', 'MateriaSrv', '$state', 'MessageSrv', 'CommonsSrv', 'nivelesResponse', 'materiaResponse',
    function ($scope, $rootScope, service, $state, message, commons, nivelesResponse, materiaResponse) {
        $scope.materia = materiaResponse.data;

        $scope.data = {
            disableFields: false,
            niveles: nivelesResponse.data,
            persistedOperation: false,
            saved: false
        }

        var performSubmit = $scope.$parent.performSubmit;

        $scope.save = function (form) {
            performSubmit(function() {
                service.save($scope.materia)
                    .success(function () {
                        $scope.data.persistedOperation = true;
                        $scope.data.disableFields = true;
                        $scope.data.saved = true;
                        message.successMessage($scope.materia.nombre + " modificada con éxito.");
                        $scope.goIndex();
                    })
                    .error(function () {
                        message.errorMessage("Error al modificar " + $scope.materia.nombre);
                    })
            }, form);

        }

        $scope.goIndex = function () {
            $state.go('^.index', {execQuery: false, execQuerySamePage: $scope.data.persistedOperation});
        }

        $scope.reload = function () {
            $state.go($state.current, {}, {reload: true});
        }


        $scope.$on('$stateChangeStart',
            function (event, toState, toParams, fromState, fromParams) {
                if (!angular.equals($state.current, toState)) {
                    delete $rootScope.persistedOperation;
                }
            });

    }]);
