var module = angular.module('materiaModule');


module.controller('MateriaCtrl_Edit', ['$scope', '$rootScope', 'MateriaSrv', '$state', 'MessageSrv', 'CommonsSrv', 'nivelesResponse', 'materiaResponse',
    function ($scope, $rootScope, service, $state, message, commons, nivelesResponse, materiaResponse, $mdToast) {
    $scope.materia = materiaResponse.data;

    $scope.data = {
        disableFields: false,
        niveles: commons.enumToJson(nivelesResponse.data),
        persistedOperation: false,
        saved: false
    }

    $scope.save = function () {
        service.save($scope.materia)
            .success(function (data) {
                $scope.data.persistedOperation = true;
                $scope.data.disableFields = true;
                $scope.data.saved = true;
                message.showMessage("Materia " + $scope.materia.nombre + " modificada con éxito!");
                $scope.goIndex();
            })
            .error(function (data) {
                showToast("Error al modificar " + $scope.materia.nombre);
            })
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
