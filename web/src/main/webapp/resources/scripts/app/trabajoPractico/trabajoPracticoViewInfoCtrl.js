var module = angular.module('trabajoPracticoModule');


module.controller('TrabajoPracticoCtrl_ViewInfo', ['$scope', 'TrabajoPracticoSrv','$state', 'catedrasResponse',
    function ($scope, service, $state, catedrasResponse) {
        var vm = this;

        vm.catedras = catedrasResponse.data;

        vm.selectedCatedra = {};
        vm.clickCatedra = clickCatedra;
        vm.cancelar = cancelar;
        vm.resultTPs = [];
        vm.nextPage = nextPage;
        vm.previousPage = previousPage;
        vm.paginationData = {
            pageNumber: 0,
            pageSize: 2
        };

        vm.selected = null,
            vm.previous = null;
        vm.selectedIndex = 2;
        $scope.$watch('selectedIndex', function(current, old){
            vm.previous = vm.selected;
            vm.selected = vm.resultTPs[current];
            if ( old + 1 && (old != current)) $log.debug('Goodbye ' + previous.title + '!');
            if ( current + 1 )                $log.debug('Hello ' + selected.title + '!');
        });

        clickCatedra(vm.catedras[0]);

        function clickCatedra(rol) {
            vm.selectedCatedra = rol;
            if (!vm.selectedCatedra.trabajosPracticos) {
                vm.selectedCatedra.trabajosPracticos = [];
            }
            getPage();
        }

        function getPage() {
            vm.resultTPs = vm.selectedCatedra.trabajosPracticos;
        }

        function nextPage() {
            getPage(++vm.paginationData.pageNumber);
        }

        function previousPage() {
            getPage(--vm.paginationData.pageNumber);
        }

        function cancelar() {
            $state.go('home');
        }
    }]);
