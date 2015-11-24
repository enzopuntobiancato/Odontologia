var module = angular.module('materiaModule');


module.controller('MateriaCtrl_Create', ['$scope', '$rootScope', 'MateriaSrv', '$state', 'MessageSrv', 'CommonsSrv', 'nivelesResponse',
    function ($scope, $rootScope, service, $state, message, commons, nivelesResponse) {
    $scope.materia = {};

    $scope.data = {
        disableFields: false,
        niveles: commons.enumToJson(nivelesResponse.data),
        persistedOperation: $rootScope.persistedOperation || false,
        saved: false
    };

    $scope.save = function () {
        service.save($scope.materia)
            .success(function (data) {
                $scope.data.persistedOperation = true;
                $scope.data.disableFields = true;
                $scope.data.saved = true;
                message.showMessage("Materia creada con éxito");
                $scope.goIndex();
            })
            .error(function (data) {
                showToast("Ha habido un error al crear la materia.");
                Console.log(data);
                $scope.goIndex();
            })
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
