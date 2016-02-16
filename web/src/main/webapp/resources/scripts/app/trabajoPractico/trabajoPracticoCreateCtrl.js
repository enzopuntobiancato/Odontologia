var module = angular.module('trabajoPracticoModule');


module.controller('TrabajoPracticoCtrl_Create', ['$scope', '$rootScope', 'TrabajoPracticoSrv', '$state', 'MessageSrv', 'gruposPracticaResponse', 'practicasResponse', '$filter',
    function ($scope, $rootScope, service, $state, message, gruposPracticaResponse, practicasResponse, $filter) {
    $scope.trabajoPractico = {};
    $scope.selectedGrupo = {};

    $scope.data = {
        disableFields: false,
        gruposPractica: gruposPracticaResponse.data,
        practicas: practicasResponse.data,
        persistedOperation: $rootScope.persistedOperation || false,
        saved: false
    };
    $scope.practicasSelect = $scope.data.practicas;
        var performSubmit = $scope.$parent.performSubmit;
        var handleError = $scope.$parent.handleError;
        $scope.validationErrorFromServer = $scope.$parent.validationErrorFromServer;


    $scope.$watch('selectedGrupo', function (newValue, oldValue) {
        if (!angular.equals(newValue, oldValue)) {
            delete $scope.trabajoPractico.practicaOdontologica;
            filterPracticas();
        }

        function filterPracticas() {

            if (!$scope.selectedGrupo || !angular.isDefined($scope.selectedGrupo.id)) {
                $scope.practicasSelect = $scope.data.practicas;
            } else {
                $scope.practicasSelect = $filter('filter')($scope.data.practicas, function (value) {
                    return angular.equals(value.grupo.id, $scope.selectedGrupo.id);
                })
            }
        }
    })

    $scope.save = function (form) {
        performSubmit(function() {
            service.save($scope.trabajoPractico)
                .success(function () {
                    $scope.data.persistedOperation = true;
                    $scope.data.disableFields = true;
                    $scope.data.saved = true;
                    message.successMessage($scope.trabajoPractico.nombre + " creado con éxito");
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
