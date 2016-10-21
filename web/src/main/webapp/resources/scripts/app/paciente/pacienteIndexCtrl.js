var module = angular.module('pacienteModule');


module.controller('PacienteCtrl_Index', ['$scope', '$rootScope', '$cacheFactory', 'PacienteSrv', '$state', '$stateParams', 'MessageSrv',
    'CommonsSrv',  'PaginationService', '$mdDialog','sexosResponse','tiposDocumentoResponse',
    function ($scope, $rootScope, $cacheFactory, service, $state, $stateParams, message, commons, pagination, $mdDialog,
              sexosResponse,tiposDocumentoResponse) {
        var vm = this;
        vm.result = [];
        vm.data = {
            sexos : sexosResponse.data,
            tiposDocumentos : tiposDocumentoResponse.data
        };
        vm.aux ={
            showDadosBaja: false,
            isTipoDocumentoSelected: false,
            mostrarFiltros: true
        }
        vm.filter = {};
        vm.filterChips = [];
        vm.consultar = consultar;
        //Cambio de estado
        vm.view = view;
        vm.create = create;

        //paginación
        vm.nextPage = nextPage;
        vm.previousPage = previousPage;

        vm.paginationData = pagination.paginationData;
        pagination.config('api/paciente/find');

        var cache = $cacheFactory.get('pacienteIndexCache') || $cacheFactory('pacienteIndexCache');

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

        vm.keyboardOk = function (event) {
            if (event.which == 13) {
                executeQuery();
            }
        };

        //Chips
        function updateFilterChips() {
            vm.filterChips = [];
            vm.filterChips.push(newFilterChip('dadosBaja', 'Dados de baja', vm.filter.dadosBaja, vm.filter.dadosBaja ? 'SI' : 'NO'));
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
                vm.filterChips.push(newFilterChip('tipoDocumento', 'Tipo doc.', findInColecction(vm.filter.tipoDocumento, vm.data.tiposDocumentos) ));
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

        //Caché
        function cacheData() {
            var data = {
                filter: vm.filter,
                result: vm.result,
                aux: vm.aux,
                paginationData: vm.paginationData,

            }
            cache.put('data', data);
        };

        function getCachedData() {
            var data = cache.get('data');

            if (data) {
                vm.filter = data.filter;
                vm.result = data.result;
                vm.aux = data.aux;
                vm.paginationData = data.paginationData;
            } else {
                executeQuery();
            }
        };

        //Paginación
        function nextPage() {
            if (vm.paginationData.morePages) {
                executeQuery(++vm.paginationData.pageNumber);
            }
        }

        function previousPage() {
            if (!vm.paginationData.firstPage) {
                executeQuery(--vm.paginationData.pageNumber);
            }
        }

        //Cambio de estado
        function create() {
            console.log("create");
            $state.go('^.create');
        }

        function view(pacienteId){
           $state.go('historiaClinica', {id: pacienteId});
        };

        //Métodos auxiliares
        vm.clickIcon = 'expand_less';
        vm.colorIcon = ['#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF'];

        vm.cleanFilters = function () {
            vm.filter = {};
            vm.filterChips = {};
        };

        vm.mostrarAcciones = function (item) {
            item.showAction = true;
        };

        vm.ocultarAcciones = function (item) {
            item.showAction = false;
        };

        vm.colorMouseOver = function (icon) {
            vm.colorIcon[icon] = '#E91E63';
        };

        vm.colorMouseLeave = function (icon) {
            vm.colorIcon[icon] = '#00B0FF';
        };

        vm.clickIconMorph = function () {
            if (vm.clickIcon === 'expand_more') {
                vm.clickIcon = 'expand_less';
            }
            else {
                vm.clickIcon = 'expand_more';
            }
        };

        $scope.$on('$stateChangeStart',
            function (event, toState) {
                if (toState.name.startsWith('paciente') || toState.name.startsWith('historiaClinica')) {
                    cacheData();
                } else {
                    $rootScope.created = false;
                    $rootScope.edited = false;
                    cache.destroy();
                }
            });

        $scope.$on('$stateChangeSuccess',
            function (event, toState, toParams, fromState, fromParams) {
                if (fromState.name.startsWith('paciente') || fromState.name.startsWith('historiaClinica')) {
                    if (toParams.execQuery) {
//                        acava el fil
                        executeQuery();
                    } else if (toParams.execQuerySamePage) {
                        getCachedData();
                        executeQuery($scope.paginationData.pageNumber)
                    } else {
                        //mortal
                        getCachedData();
                        executeQuery()
                    }
                } else {
                    executeQuery();
                }
            });
    }]);



