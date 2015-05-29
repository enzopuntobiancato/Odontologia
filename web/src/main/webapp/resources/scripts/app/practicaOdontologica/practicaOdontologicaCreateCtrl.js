var module = angular.module('practicaOdontologicaModule');


module.controller('PracticaOdontologicaCtrl_Create', ['$scope', '$rootScope', 'PracticaOdontologicaSrv', '$state', 'NotificationSrv', 'gruposPracticaResponse', '$filter', function ($scope, $rootScope, service, $state, notification, gruposPracticaResponse) {
    $scope.practica = {}

    $scope.data = {
        disableFields: false,
        gruposPractica: gruposPracticaResponse.data,
        persistedOperation: $rootScope.persistedOperation || false,
        saved: false
    }

    $scope.save = function()
    {
//        notification.showWidget();

        service.save($scope.practica)
            .success(function(data) {
                $scope.data.persistedOperation = true;
                $scope.data.disableFields = true;
                $scope.data.saved = true;
                notification.scrollTo('container');
//                notification.hideWidget();
            })
            .error(function (data) {
//                notification.hideWidget();
                notification.badArray(data, function() {});
            })
    }

    $scope.goIndex = function() {
        $state.go('^.index', {execQuery: $scope.data.persistedOperation});
    }

    $scope.reload = function() {
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
