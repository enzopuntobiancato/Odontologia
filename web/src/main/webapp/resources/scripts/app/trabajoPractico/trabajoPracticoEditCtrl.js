/**
 * Created with IntelliJ IDEA.
 * User: enzo
 * Date: 12/05/15
 * Time: 20:49
 */
var module = angular.module('trabajoPracticoModule');
module.controller('TrabajoPracticoCtrl_Edit', ['$scope', '$rootScope', 'TrabajoPracticoSrv', '$state', 'MessageSrv', 'gruposPracticaResponse', 'practicasResponse', '$filter', 'trabajoPracticoResponse',
    function ($scope, $rootScope, service, $state, message, gruposPracticaResponse, practicasResponse, $filter, trabajoPracticoResponse) {
        var vm = this;
        vm.trabajoPractico = trabajoPracticoResponse.data;
        vm.data = {
            disableFields: false,
            gruposPractica: gruposPracticaResponse.data,
            practicas: practicasResponse.data,
            persistedOperation: $rootScope.persistedOperation || false,
            saved: false
        };
        vm.selectedGrupo = vm.trabajoPractico.practicaOdontologica.grupo;
        vm.practicasSelect = vm.data.practicas;
        var performSubmit = $scope.$parent.performSubmit;
        var handleError = $scope.$parent.handleError;
        vm.validationErrorFromServer = $scope.$parent.validationErrorFromServer;
        vm.save = save;
        vm.reload = reload;
        vm.goIndex = goIndex;

        function save(form) {
            performSubmit(function(){
                service.save(vm.trabajoPractico)
                    .success(function (data) {
                        vm.data.persistedOperation = true;
                        vm.data.disableFields = true;
                        vm.data.saved = true;
                        message.successMessage(vm.trabajoPractico.nombre + " modificado con éxito!");
                        vm.goIndex();
                    })
                    .error(function (data, status) {
                        handleError(data, status);
                    })
            },form);
        }

        function goIndex() {
            $state.go('^.index', {execQuery: false, execQuerySamePage: vm.data.persistedOperation});
        }

        function reload() {
            $rootScope.persistedOperation = vm.data.persistedOperation;
            $state.go($state.current, {}, {reload: true});
        }
//        $scope.aux ={
//            selectedGrupo : $scope.trabajoPractico.practicaOdontologica.grupo
//        };



        /* $scope.$watch('selectedGrupo', function (newValue, oldValue) {
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
         })*/

        $scope.$on('$stateChangeStart',
            function (event, toState, toParams, fromState, fromParams) {
                if (!angular.equals($state.current, toState)) {
                    delete $rootScope.persistedOperation;
                }
            });
    }]);

