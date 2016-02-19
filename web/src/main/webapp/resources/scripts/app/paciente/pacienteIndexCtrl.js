var module = angular.module('pacienteModule');


module.controller('PacienteCtrl_Index', ['$scope', '$cacheFactory', 'PacienteSrv', '$state', '$stateParams', 'MessageSrv', 'CommonsSrv',  'PaginationService', '$mdDialog','materiasResponse','practicasResponse',
    function ($scope, $cacheFactory, service, $state, $stateParams, message, commons, pagination, $mdDialog,materiasResponse,practicasResponse) {
        $scope.result = [
            {
                id: '1',
                apellido:           'Suárez',
                nombre:             'Luis',
                nroDocumento:       '45267892',
                materia:            'Protodoncia',
                trabajoPractico:    'Trabajo Practico 1',
                img:                'http://laaficion.milenio.com/la_aficion_mundial/Suarez-mordida_de_Suarez-memes_de_Suarez-mordida_a_Chiellini_MILIMA20140624_0389_3.jpg'

            },
            {
                id: '2',
                apellido:           'Zárate',
                nombre:             'Mauro',
                nroDocumento:       '35938590',
                materia:            'Ortodoncia',
                trabajoPractico:    'Trabajo Practico 2',
                img: 'http://diariomovil.com.ar/wp-content/uploads/2014/10/mauro_zarate.jpg'

            },
            {
                id: '3',
                apellido:           'Zanetti',
                nombre:             'Pupi',
                nroDocumento:       '12267892',
                materia:            'Protodoncia',
                trabajoPractico:    'Trabajo Practico 1',
                img:                'http://www.cordobatimes.com/deportes/wp-content/uploads/2013/12/zanetti-mundial.jpg'
            },
            {
                id: '4',
                apellido:           'Crespo',
                nombre:             'Hernán',
                nroDocumento:       '45267892',
                materia:            'Protodoncia',
                trabajoPractico:    'Trabajo Practico 1',
                img:                'http://www.apurogol.net/wp-content/uploads/2008/11/hernan-crespo-real-madrid.jpg'
            },
            {
                id: '5',
                apellido:           'Batistuta',
                nombre:             'Gabriel Omar',
                nroDocumento:       '31267892',
                materia:            'Protodoncia',
                trabajoPractico:    'Obstrucción',
                img:                'http://image.tin247.com/bongdaso/100401192152-674-468.jpg'
            },
            {
                id: '6',
                apellido:           'Alonso',
                nombre:             'Beto',
                nroDocumento:       '32267892',
                materia:            'Cirugía',
                trabajoPractico:    'Extracción',
                img:                'http://www.rivermillonarios.com.ar/media/galeria/132/4/9/2/7/n_river_plate_idolos_millonarios_y_ex_river-1357294.jpg'
            }
        ];
        $scope.filter = {};
        $scope.filterChips = [];
        $scope.aux = {
            showDadosBaja: false,
            mostrarFiltros: true
        };
        $scope.data = {
            materias : materiasResponse.data,
            practicas : practicasResponse.data
        };

        $scope.reload = function(){
            $rootScope.persistedOperation = $scope.data.persistedOperation;
            $state.go($state.current, {}, {reload: true});
        };

        $scope.paginationData = pagination.paginationData;
        pagination.config('api/persona/find');
        //Consulta
        function executeQuery(pageNumber){
            pagination.paginate($scope.filter,pageNumber)
                .then(function(data){
                    $scope.result = data;
                    $scope.aux.showDadosBaja = $scope.filter.dadosBaja;
                    $scope.paginationData = pagination.getPaginationData();
                    updateFilterChips();
                },function(){
                });
        };
        
        $scope.consultar = function () {
            executeQuery();
        }

        $scope.keyboardOk = function (event) {
            if (event.which == 13) {
                executeQuery();
            }
        };
        
        //Chips
        function updateFilterChips() {
            $scope.filterChips = [];
            $scope.filterChips.push(newFilterChip('dadosBaja', 'Dados de baja', $scope.filter.dadosBaja, $scope.filter.dadosBaja ? 'SI' : 'NO'));
            if ($scope.filter.materia) {
                $scope.filterChips.push(newFilterChip('materia', 'Materia', findMateria($scope.filter.materia)));
            }
            if ($scope.filter.nombre) {
                $scope.filterChips.push(newFilterChip('nombre', 'Nombre', $scope.filter.nombre));
            }
            if ($scope.filter.practica) {
                $scope.filterChips.push(newFilterChip('practica', 'Práctica', findPractica($scope.filter.practica)));
            }
        }

        $scope.$watchCollection('filterChips', function(newCol, oldCol) {
            if (newCol.length < oldCol.length) {
                $scope.filter = {};
                angular.forEach(newCol, function(filterChip) {
                    $scope.filter[filterChip.origin] = filterChip.value;
                });
                executeQuery();
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
        
        function findMateria(idMateria){
            var nombre;
            for(var i = 0; i < $scope.data.materias.length; i++){
                if($scope.data.materias[i].id == idMateria){
                    nombre = $scope.data.materias[i].nombre;
                }                
            }
            return nombre;
        }

        function findPractica(idPractica){
            var nombre;
            for(var i = 0; i < $scope.data.practicas.length; i++){
                if($scope.data.practicas[i].id == idPractica){
                    nombre = $scope.data.practicas[i].denominacion;
                }
            }
            return nombre;
        }

        //Caché
        function cacheData() {
            var data = {
                filter: $scope.filter,
                result: $scope.result,
                aux: $scope.aux
            }
            cache.put('data', data);
        };

        function getCachedData() {
            var data = cache.get('data');

            $scope.filter = data.filter;
            $scope.result = data.result;
            $scope.aux = data.aux;
        };

        //Paginación
        $scope.nextPage = function () {
            if ($scope.paginationData.morePages) {
                executeQuery(++$scope.paginationData.pageNumber);
            }
        };
        $scope.previousPage = function () {
            if (!$scope.paginationData.firstPage) {
                executeQuery(--$scope.paginationData.pageNumber);
            }
        };


        //Cambio de estado
        $scope.new = function () {
            $state.go('^.create');
        };

        $scope.edit = function (pacienteId) {
            $state.go('^.edit', {id:pacienteId});

        };

        $scope.viewDetail = function (pacienteId) {
            $state.go('^.view', {id: pacienteId});
        };

        //Diálogos
        //Dar de baja
        $scope.openDeleteDialog = function (ev, pacienteId) {
            $mdDialog.show({
                templateUrl: 'views/paciente/pacienteDelete.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: true,
                locals: {id: pacienteId},
                controller: function DialogController($scope, $mdDialog) {
                    $scope.motivoBaja;
                    $scope.cancelar = function () {
                        $mdDialog.cancel();
                    };
                    $scope.confirmar = function () {
                        $mdDialog.hide($scope.motivoBaja);
                    };
                }
            })
                .then(function (motivoBaja) {
                    //Success
                    service.remove(pacienteId, motivoBaja)
                        .success(function (response) {
                            message.successMessage("Se ha dado de baja el paciente.");
                            executeQuery();
                            console.log(response);
                        })
                        .error(function (error) {
                            message.showMessage("Se ha registrado un error en la transacción.")
                            console.log(error);
                        })
                },
                function () {
                    //Failure
                    $scope.status = 'You cancelled the dialog.';
                    console.log(error);
                });
        };

        //Restaurar
        $scope.openRestoreDialog = function (ev, pacienteId) {
            $mdDialog.show({
                templateUrl: 'views/paciente/pacienteRestore.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: true,
                locals: {id: pacienteId},
                controller: function DialogController($scope, $mdDialog) {
                    $scope.cancelar = function () {
                        $mdDialog.cancel();
                    };
                    $scope.confirmar = function () {
                        $mdDialog.hide();
                    };
                }
            })
                .then(function () {
                    //Success
                    service.restore(pacienteId)
                        .success(function (response) {
                            message.successMessage("Se ha dado de alta.");
                            executeQuery($scope.paginationData.pageNumber);
                            console.log(response);
                        })
                        .error(function (error) {
                            message.showMessage("Se ha registrado un error en la transacción.")
                            executeQuery($scope.paginationData.pageNumber);
                            console.log(error);
                        })
                },
                function () {
                    //Failure
                    $scope.status = 'You cancelled the dialog.';
                    console.log(error);
                });
        };

        //Métodos auxiliares
        $scope.clickIcon = 'expand_more';
        $scope.colorIcon = ['#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF'];

        $scope.cleanFilters = function () {
            $scope.filter = {};
            //executeQuery();
        };

        $scope.mostrarAcciones = function (item) {
            item.showAction = true;
        };

        $scope.ocultarAcciones = function (item) {
            item.showAction = false;
        };

        $scope.colorMouseOver = function (icon) {
            $scope.colorIcon[icon] = '#E91E63';
        };

        $scope.colorMouseLeave = function (icon) {
            $scope.colorIcon[icon] = '#00B0FF';
        };

        $scope.clickIconMorph = function () {
            if ($scope.clickIcon === 'expand_more') {
                $scope.clickIcon = 'expand_less';
            }
            else {
                $scope.clickIcon = 'expand_more';
            }
        };

        $scope.cleanFilters = function () {
            $scope.filter = {};
            executeQuery();
        };



    }]);



