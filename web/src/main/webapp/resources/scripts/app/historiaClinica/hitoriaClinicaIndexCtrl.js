var module = angular.module('historiaClinicaModule');


module.controller('HistoriaClinicaCtrl_Index', ['$scope', '$cacheFactory', 'HistoriaClinicaSrv', '$state', '$stateParams',
    'MessageSrv', 'CommonsSrv',  'PaginationService', '$mdDialog',
    function ($scope, $cacheFactory, service, $state, $stateParams, message, commons, pagination, $mdDialog) {
        var vm = this;
        vm.new = create;
        vm.edit = edit;
        vm.view = view;
        vm.consultar = consultar;
        vm.result = [];
        vm.filter =[];
        vm.data = [];

        function consultar(){
            console.log("Consultar");
            vm.result = [{
                apellido: 'Messi',
                nombre:'Lionel',
                nroDocumento: '36781910',
                fecha: '01/02/1990'
            }];
        }

        function executeQuery(){

        }

        //Cambio de estado
        function create() {
            $state.go('^.create');
        }

        function edit(pacienteId) {
            $state.go('^.edit', {id:pacienteId});

        }

        function view(pacienteId) {
            $state.go('^.view', {id: pacienteId});
        }

        //Métodos auxiliares
        vm.mostrarFiltros = false;
        vm.clickIcon = 'expand_more';
        vm.colorIcon = ['#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF'];
        vm.cleanFilters = cleanFilters;
        vm.mostrarAcciones = mostrarAcciones;
        vm.ocultarAcciones = ocultarAcciones;
        vm.colorMouseOver = colorMouseOver;
        vm.colorMouseLeave = colorMouseLeave;
        vm.clickIconMorph = clickIconMorph;

        function cleanFilters() {
            vm.filter = {};
            vm.consultar;
        }

        function  mostrarAcciones (item) {
            item.showAction = true;
        }

         function ocultarAcciones(item) {
            item.showAction = false;
        }

         function colorMouseOver(icon) {
            vm.colorIcon[icon] = '#E91E63';
        }

        function colorMouseLeave(icon) {
            vm.colorIcon[icon] = '#00B0FF';
        }

         function clickIconMorph() {
            if (vm.clickIcon === 'expand_more') {
                vm.clickIcon = 'expand_less';
            }
            else {
                vm.clickIcon = 'expand_more';
            }
        }

    }]);



