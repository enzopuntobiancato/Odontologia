var module = angular.module('historiaClinicaModule');


module.controller('HistoriaClinicaCtrl_Index', ['$scope', '$cacheFactory', 'HistoriaClinicaSrv', '$state', '$stateParams',
    'MessageSrv', 'CommonsSrv',  'PaginationService', '$mdDialog','sexosResponse','tiposDocumentoResponse',
    function ($scope, $cacheFactory, service, $state, $stateParams, message, commons, pagination, $mdDialog,sexosResponse,tiposDocumentoResponse) {
        var vm = this;
        vm.new = create;
        vm.edit = edit;
        vm.view = view;
        vm.consultar = consultar;
        vm.result = [];
        vm.filter =[];
        vm.filterChips = [];
        vm.data = {
            sexos : sexosResponse.data,
            tiposDocumentos : tiposDocumentoResponse.data
        };

        vm.aux ={
            showDadosBaja: false,
            isTipoDocumentoSelected: false,
            mostrarFiltros: true
        }
        vm.paginationData = pagination.paginationData;
        pagination.config('api/historiaClinica/findPacientes');
        //Consulta
        function executeQuery(pageNumber){
            pagination.paginate(vm.filter,pageNumber)
                .then(function(data){
                    vm.result = data;
                    vm.aux.showDadosBaja = vm.filter.dadosBaja;
                    vm.paginationData = pagination.getPaginationData();
                    updateFilterChips();
                },function(){
                });
        }

        function consultar(form){
            if(!form.$invalid){
                executeQuery();
            }
        }

        //Paginación
        vm.nextPage = function () {
            if (vm.paginationData.morePages) {
                executeQuery(++vm.paginationData.pageNumber);
            }
        };
        vm.previousPage = function () {
            if (!vm.paginationData.firstPage) {
                executeQuery(--vm.paginationData.pageNumber);
            }
        };

        //Chips
        function updateFilterChips() {
            vm.filterChips = [];
            vm.filterChips.push(newFilterChip('dadosBaja', 'Dados de baja', vm.filter.dadosBaja, vm.filter.dadosBaja ?
                'SI' : 'NO'));
            if (vm.filter.apellido) {
                vm.filterChips.push(newFilterChip('apellido', 'Apellido', vm.filter.apellido));
            }
            if (vm.filter.nombre) {
                vm.filterChips.push(newFilterChip('nombre', 'Nombre', vm.filter.nombre));
            }
            if (vm.filter.nombreUsuario) {
                vm.filterChips.push(newFilterChip('nombreUsuario', 'Usuario', vm.filter.nombreUsuario));
            }
            if (vm.filter.numeroDocumento) {
                vm.filterChips.push(newFilterChip('numeroDocumento', 'Nro. doc.', vm.filter.numeroDocumento));
            }
            if (vm.filter.tipoDocumento) {
                vm.filterChips.push(newFilterChip('tipoDocumento', 'Tipo doc.', findInColecction(vm.filter.tipoDocumento,
                    vm.data.tiposDocumentos) ));
            }
            if (vm.filter.sexo) {
                vm.filterChips.push(newFilterChip('sexo', 'Sexo', findInColecction(vm.filter.sexo, vm.data.sexos)));
            }
        }

        $scope.$watchCollection('vm.filterChips', function(newCol, oldCol) {
            if (newCol.length < oldCol.length) {
                vm.filter = {};
                angular.forEach(newCol, function(filterChip) {
                    vm.filter[filterChip.origin] = filterChip.value;
                });
                executeQuery();
            }
        });

        $scope.$watch('vm.filter.tipoDocumento',function(){
            if(vm.filter.tipoDocumento != null && vm.filter.tipoDocumento != 'undefined'){
                vm.aux.isTipoDocumentoSelected = true;
            }else{
                vm.aux.isTipoDocumentoSelected = false;
            }
        });

        function newFilterChip(origin, name, value, displayValue) {
            var filterChip = {
                origin: origin,
                name: name,
                value: value,
                displayValue: displayValue ? displayValue : value
            }
            return filterChip;
        }

        function findInColecction(id,collection){
            for(var i = 0; i < collection.length;i++){
                if(collection[i].key == id){
                    return collection[i].nombre;
                }
            }
            return "Sin definir";
        }


        //Cambio de estado
        function create() {
            $state.go('^.create');
        }

        function edit(historiaClinicaId) {
            $state.go('^.edit', {id:historiaClinicaId});

        }

        function view(historiaClinicaId) {
            $state.go('^.view', {id: historiaClinicaId});
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

        function cleanFilters(form) {
            vm.filter = {};
            vm.filterChips = {};
            vm.consultar(form);
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

        //Caché
        function cacheData() {
            var data = {
                filter: vm.filter,
                result: vm.result,
                aux: vm.aux
            }
            cache.put('data', data);
        };

        function getCachedData() {
            var data = cache.get('data');

            vm.filter = data.filter;
            vm.result = data.result;
            vm.aux = data.aux;
        };

    }]);



