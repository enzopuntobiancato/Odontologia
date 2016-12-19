var homeModule = angular.module('homeModule');


homeModule.controller('HomeCtrl', [ '$scope', function ($scope) {
    $scope.carouselData = [
        {src: 'resources/img/fotos_home/1.jpeg'},
        {src: 'resources/img/fotos_home/2.jpeg'},
        {src: 'resources/img/fotos_home/3.jpeg'},
        {src: 'resources/img/fotos_home/6.jpg'},
        {src: 'resources/img/fotos_home/7.jpg'}
    ];
}]);
