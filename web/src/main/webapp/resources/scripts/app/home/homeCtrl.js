var homeModule = angular.module('homeModule');


homeModule.controller('HomeCtrl', [ '$scope','$http', function ($scope, $http) {
    $scope.userName="enzo.biancato";
}]);
