var module = angular.module('historiaClinicaModule');

module.controller('HistoriaClinicaCtrl_Main', ['$scope', '$rootScope', '$state', '$stateParams', 'pacienteResponse', '$cacheFactory', 'DeleteRestoreSrv', '$window', '$location',
    function ($scope, $rootScope, $state, $stateParams, pacienteResponse, $cacheFactory, deleteRestoreService, $window, $location) {
        var vm = this;

        var PREFIX = 'historiaClinica.';
        var DATOS_PERSONALES_TAB = 'datosPersonales';
        var ANTECEDENTES_TAB = 'antecedentes';
        var DIAGNOSTICO_TAB = 'diagnostico';
        var ATENCIONES_TAB = 'atenciones';
        var DOCUMENTACIONES_TAB = 'documentaciones';
        var VIEW_SUFFIX = 'View';
        var EDIT_SUFFIX = 'Edit';

        var cache = $cacheFactory.get('hcCache') || $cacheFactory('hcCache');

        function cacheTab() {
            cache.put('data', vm.data.currentTab);
        }

        function getTab() {
            var data = cache.get('data');
            return data;
        }

        vm.paciente = pacienteResponse.data;
        vm.data = {
            editing: convertToBoolean($stateParams.editing)
        }

        // Switching between main options at the top
        vm.goDatosPersonales = goDatosPersonales;
        vm.goAntecedentes = goAntecedentes;
        vm.goDiagnosticos = goDiagnosticos;
        vm.goAtenciones = goAtenciones;
        vm.goDocumentaciones = goDocumentaciones;
        vm.isDatosPersonalesSelected = isDatosPersonalesSelected;
        vm.isAntecedentesSelected = isAntecedentesSelected;
        vm.isDiagnosticosSelected = isDiagnosticosSelected;
        vm.isAtencionesSelected = isAtencionesSelected;
        vm.isDocumentacionesSelected = isDocumentacionesSelected;

        vm.goEdit = goEdit;
        vm.goToConsultar = goToConsultar;
        vm.cancelEditing = cancelEditing;
        vm.onAtencionesView = onAtencionesView;

        vm.submit = submit;
        vm.openDeleteDialog = openDeleteDialog;
        vm.openRestoreDialog = openRestoreDialog;
        vm.validationErrorFromServer = $scope.$parent.validationErrorFromServer;
        vm.printHC = printHC;

        function submit(form, continueEditing) {
            $scope.$broadcast('validatedForm', {form: form, continueEditing: continueEditing});
        }

        function cancelEditing() {
            var current = vm.data.currentTab;
            $state.go(PREFIX + current + VIEW_SUFFIX, {editing : false});
        }

        function goDatosPersonales() {
            goViewOrEdit(DATOS_PERSONALES_TAB)
        }

        function goAntecedentes() {
            goViewOrEdit(ANTECEDENTES_TAB);
        }

        function goDiagnosticos() {
            goViewOrEdit(DIAGNOSTICO_TAB);
        }

        function goAtenciones() {
            goViewOrEdit(ATENCIONES_TAB)
        }

        function goDocumentaciones() {
            goViewOrEdit(DOCUMENTACIONES_TAB);
        }

        function isDatosPersonalesSelected() {
            return isTabSelected(DATOS_PERSONALES_TAB);
        }

        function isAntecedentesSelected() {
            return isTabSelected(ANTECEDENTES_TAB);
        }

        function isDiagnosticosSelected() {
            return isTabSelected(DIAGNOSTICO_TAB);
        }

        function isAtencionesSelected() {
            return isTabSelected(ATENCIONES_TAB);
        }

        function isDocumentacionesSelected() {
            return isTabSelected(DOCUMENTACIONES_TAB);
        }

        function isTabSelected(tab) {
            return $state.current.name.indexOf(tab) > 1;
        }

        function goViewOrEdit(tab) {
            vm.data.currentTab = tab;
            cacheTab();
            vm.data.editing = convertToBoolean($stateParams.editing);
            var suffix = vm.data.editing ? EDIT_SUFFIX : VIEW_SUFFIX;
            $state.go(PREFIX + tab + suffix, {editing: vm.data.editing});
        }

        function goEdit() {
            var current = vm.data.currentTab || getTab();
            $state.go(PREFIX + current + EDIT_SUFFIX, {editing : true});
        }

        function onAtencionesView() {
            return $state.current.name.indexOf(ATENCIONES_TAB) > 1;
        }

        function goToConsultar() {
            $state.go('paciente.index', {execQuery: $rootScope.created, execQuerySamePage: $rootScope.edited});
        }

        function convertToBoolean(paramValue) {
            return (paramValue == 'true');
        }

        function printHC() {
            var base = $location.protocol() + '://'+ $location.host() +':'+  $location.port() + "/Odontologia-web/api/historiaClinica/printHC";
            $window.open(base + '?idPaciente=' + $stateParams.id);
        }

        deleteRestoreService.config('api/paciente/remove', 'api/paciente/restore', 'Paciente');
        function openDeleteDialog(event, id, nombre) {
            deleteRestoreService.delete(event, id, nombre);
        }
        function openRestoreDialog(event, id, nombre) {
            deleteRestoreService.restore(event, id, nombre);
        }

        $scope.$on('successDeleteRestoreEvent', function(event) {
            event.defaultPrevented = true;
            $rootScope.created = true;
            $state.go($state.current, {editing: convertToBoolean($stateParams.editing)}, {reload: true})
        })


        $scope.$on('$stateChangeSuccess',
            function (event, toState, toParams, fromState, fromParams) {
                if (fromState.name.startsWith('historiaClinica') && toState.name === 'historiaClinica') {
                    goToConsultar();
                }
                if (!fromState.name.startsWith('historiaClinica')) {
                   goViewOrEdit(DATOS_PERSONALES_TAB);
                } else {
                    vm.data.currentTab = getTab() || DATOS_PERSONALES_TAB;
                }
            })
    }]);