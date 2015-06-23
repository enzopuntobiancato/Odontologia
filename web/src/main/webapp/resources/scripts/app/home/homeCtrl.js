var homeModule = angular.module('homeModule');


homeModule.controller('HomeCtrl', [ '$scope', 'initializeData', 'NotificationSrv', function ($scope, initializeData, notification) {
    if (initializeData) {
        notification.alert(initializeData);
    }

}]);
