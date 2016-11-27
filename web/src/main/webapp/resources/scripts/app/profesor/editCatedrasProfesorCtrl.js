var module = angular.module('profesorModule')

module.controller('CatedrasProfesorCtrl_Edit', ['$scope', '$cacheFactory', 'ProfesorSrv', '$state', '$stateParams', 'MessageSrv', 'CommonsSrv', 'materiasResponse', 'PaginationService',
    function ($scope, $cacheFactory, service, $state, $stateParams, message, CommonsSrv, materiasResponse, pagination) {
        var vm = this;
        //ATRIBUTOS
        vm.profesores = [];
        vm.catedras = [];
        vm.selectedProfesor = null;
        vm.selectedCatedras = [];
        vm.searchTextProfesor = null;
        vm.filter = {
            materiaId: null,
            denominacion: null
        }
        vm.filterChips = [];
        vm.data = {
            materias: materiasResponse.data
        }
        //METODOS
        vm.searchProfesores = searchProfesores;
        vm.searchCatedras = searchCatedras;
        vm.save = save;
        vm.deleteCatedra = deleteCatedra;
        vm.addCatedra = addCatedra;
        vm.onProfesorSelected = onProfesorSelected;
        vm.cleanFilters = cleanFilters;
        //METODOS AUXILIARES
        var performSubmit = $scope.$parent.performSubmit;
        var handleError = $scope.$parent.handleError;
        vm.validationErrorFromServer = $scope.$parent.validationErrorFromServer;
        var cache = $cacheFactory.get('catedrasProfesorEditCache') || $cacheFactory('catedrasProfesorEditCache');
        pagination.config('api/catedra/find');
        vm.paginationData = pagination.paginationData;

        function searchProfesores() {
            for(var i=0; i < vm.catedras.length; i++){
                vm.catedras[i].selected = false;
            }
            return service.findProfesoresByApellido(vm.searchTextProfesor).then(function (response) {
                return response.data;
            });
        }

        function onProfesorSelected() {
            if(vm.selectedProfesor == null){
                return;
            }
            for (var i = 0; i < vm.catedras.length; i++) {
                vm.catedras[i].selected = false;
            }
            for (var i = 0; i < vm.selectedProfesor.catedras.length; i++) {
                var catAux = findItemById(vm.selectedProfesor.catedras[i].id, vm.catedras);
                if (catAux !== null && angular.isDefined(catAux)) {
                    catAux.selected = true;
                }
            }
        }

        function searchCatedras(form) {
            if(!form.$invalid){
                executeQuery();
                for (var i = 0; i < vm.selectedCatedras.length; i++) {
                    var catAux = findItemById(vm.selectedCatedras[i].id, vm.catedras);
                    if (catAux !== null && angular.isDefined(catAux)) {
                        catAux.selected = true;
                    }
                }
            }else{
                angular.forEach(form.$error, function (field) {
                    angular.forEach(field, function (errorField) {
                        console.log(field.name);
                        errorField.$setTouched();
                    })
                });
            }
        }

        function addCatedra(catedra) {
            if (catedra.selected) {
                vm.selectedProfesor.catedras.push(catedra);
            } else {
                vm.deleteCatedra(catedra.id, 1);
            }
        }

        function save(form) {
            performSubmit(function () {
                service.addCatedraToProfesor(vm.selectedProfesor)
                    .success(function () {
                        vm.data.persistedOperation = true;
                        vm.data.disableFields = true;
                        vm.data.saved = true;
                        message.successMessage("El profesor"+ vm.selectedProfesor.apellido + ", "
                            + vm.selectedProfesor.nombre +" has sido modificado correctamente");
                        vm.selectedProfesor = null;
                        vm.selectedCatedras = null;
                        for(var i=0; i < vm.catedras.length; i++){
                            vm.catedras[i].selected = false;
                        }
                    })
                    .error(function (data, status) {
                        handleError(data, status);
                    })
            }, form);
        }

        function deleteCatedra(catedraId, band) {
            if (band == 1) {
                var index = findItemIndexInList(catedraId, vm.selectedProfesor.catedras);
                if (index >= 0) {
                    vm.selectedProfesor.catedras.splice(index, 1);
                }
            } else {
                var cat = findItemById(catedraId, vm.catedras);
                cat.selected = false;
            }
        }

        function findItemIndexInList(id, list) {
            var index = -1;
            for (var i = 0; i < list.length; i++) {
                if (list[i].id === id) {
                    index = i;
                    break;
                }
            }
            return index;
        }

        function findItemById(id, list) {
            var cat = {};
            for (var i = 0; i < list.length; i++) {
                if (list[i].id === id) {
                    cat = list[i];
                    break;
                }
            }
            return cat;
        }

        function cleanFilters(){
            vm.filter = {};
        }

        //AUXILIARES
        function executeQuery(pageNumber) {
            pagination.paginate(vm.filter, pageNumber).then(function (data) {
                vm.catedras = data;
                vm.paginationData = pagination.getPaginationData();
            }, function () {
            });
        }

        function updateFilterChips() {
            vm.filterChips = [];
            if (vm.filter.denominacion) {
                vm.filterChips.push(newFilterChip('denominacion', 'Nombre', vm.filter.denominacion));
            }
        }

        function newFilterChip(origin, name, value, displayValue) {
            var filterChip = {
                origin: origin,
                name: name,
                value: value,
                displayValue: displayValue ? displayValue : value
            }
            return filterChip;
        }

        vm.nextPage = function () {
            if (vm.paginationData.morePages) {
                executeQuery(++vm.paginationData.pageNumber);
            }
        }
        vm.previousPage = function () {
            if (!vm.paginationData.firstPage) {
                executeQuery(--vm.paginationData.pageNumber);
            }
        }

        vm.colorIcon = ['#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF'];
        vm.colorMouseOver = function (icon) {
            vm.colorIcon[icon] = '#E91E63';
        }

        vm.colorMouseLeave = function (icon) {
            vm.colorIcon[icon] = '#00B0FF';
        }

        //CACHE
        function cacheData() {
            var data = {
                filter: vm.filter,
//                result: $scope.result,
//                aux: $scope.aux,
                paginationData: vm.paginationData
            }
            cache.put('data', data);
        }

        function getCachedData() {
            var data = cache.get('data');

            vm.filter = data.filter;
//            $scope.result = data.result;
//            $scope.aux = data.aux;
            vm.paginationData = data.paginationData;
            updateFilterChips();
        }

        $scope.$on('$stateChangeStart',
            function (event, toState, toParams, fromState, fromParams) {
                if (toState.name.startsWith('profesor')) {
                    cacheData();
                } else {
                    cache.destroy();
                }

            });

        $scope.$on('$stateChangeSuccess',
            function (event, toState, toParams, fromState, fromParams) {
                if (fromState.name.startsWith('profesor')) {
                    if (toParams.execQuery) {
                        executeQuery();
                    } else if (toParams.execQuerySamePage) {
                        getCachedData();
                        executeQuery(vm.paginationData.pageNumber)
                    } else {
                        getCachedData();
                    }
                } else {
                    executeQuery();
                }

            })

    }]);