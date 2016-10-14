var module = angular.module('asignacionModule');


module.controller('AsignacionCtrl_Index', ['$scope', '$rootScope', '$filter', '$cacheFactory', 'AsignacionSrv', '$state', '$stateParams', 'MessageSrv',
    'CommonsSrv', 'PaginationService', '$mdDialog',
    'practicasResponse', 'tiposDocumentoResponse', 'catedrasResponse', 'estadosAsignacionResponse',
    'trabajosPracticosResponse', 'authFactory',
    function ($scope, $rootScope, $filter, $cacheFactory, service, $state, $stateParams, message, commons, pagination, $mdDialog, practicasResponse, tiposDocumentoResponse, catedrasResponse, estadosAsignacionResponse, trabajosPracticosResponse, authFactory) {
        var vm = this;
        vm.asignacion = {};
        vm.result = [];
        vm.data = {
            practicas: practicasResponse.data,
            tiposDocumentos: tiposDocumentoResponse.data,
            catedras: catedrasResponse.data,
            trabajosPracticos: [],
            estados: estadosAsignacionResponse.data
        };
        vm.aux = {
            showDadosBaja: false,
            isTipoDocumentoSelected: false,
            mostrarFiltros: true
        }
        vm.filter = {};
        vm.filter.dadosBaja = false;
        vm.filterChips = [];
        vm.selectedAsignaciones = [];
//        vm.buscarAsignaciones = buscarAsignaciones;
        //Cambio de estado
        vm.showConfirmarAsignacionDialog = showConfirmarAsignacionDialog;
        vm.cancelar = cancelar;
        vm.view = view;
        vm.edit = edit;
        vm.create = create;
        vm.reload = reload;
        //paginación
        vm.nextPage = nextPage;
        vm.previousPage = previousPage;
        //CONSULTA Y PAGINACION ALUMNO
        vm.alumnos = [];
        vm.filterAlumno = {};
        vm.paginationData = pagination.paginationData;
        vm.busqueda = false;
        vm.onCatedraSelected = onCatedraSelected;
        //Seleccion Alumno
        vm.onAlumnoSelected = onAlumnoSelected;
        vm.deleteSelectedAlumno = deleteSelectedAlumno;
        vm.esAlumno = false;
        vm.user = null;
        vm.showConsultarAlumnosDialog = showConsultarAlumnosDialog;
        vm.consultar = consultar;

        //CACHE
        var cache = $cacheFactory.get('asignacionIndexCache') || $cacheFactory('asignacionIndexCache');

        var handleError = $scope.$parent.handleError;
        $scope.validationErrorFromServer = $scope.$parent.validationErrorFromServer;

        (function init() {
            vm.user = authFactory.getAuthData();
            vm.esAlumno = isAlumno();
            if (vm.esAlumno) {
                findPersona();
            }
        })();

        //USUARIO
        function isAlumno() {
            if (vm.user.alumno) {
                return true;
            }
            return false;
        }

        function findPersona() {
            var alumno = null;
            service.findAlumnoByUser(vm.user.id)
                .success(function (data) {
                    alumno = data;
                    onAlumnoSelected(alumno);
                }).error(function (error) {
                    console.log(error);
                })
        }

        //BUSQUEDA ALUMNO
        function onCatedraSelected() {
            var idCatedra = vm.filter.catedra.id;
            service.getTrabajosPracticosByCatedra(idCatedra)
                .success(function (response) {
                    vm.data.trabajosPracticos = response;
                })
                .error(function (data, status) {
                    handleError(data, status);
                })
        }

        //SELECCION DE ALUMNO
        function onAlumnoSelected(alumno) {
            vm.selectedAlumno = alumno;
            vm.filter.selectedAlumno = alumno;
            vm.filter.alumnoId = alumno.id;
            executeQuery();
//            buscarAsignaciones("consultarAsignacionesForm");
        }

        function deleteSelectedAlumno() {
            vm.filter.selectedAlumno = null;
            vm.asignacion.alumno = {};
/*            vm.alumnos = [];
            vm.selectedAlumnos = [];*/
        }

        function reload() {
            $rootScope.persistedOperation = vm.data.persistedOperation;
            $state.go($state.current, {}, {reload: true});
        }

        vm.paginationData = pagination.paginationData;

        //Consulta

        function executeQuery(pageNumber) {
            pagination.config('api/asignacion/findAsignacionByFilters');
            vm.filter.trabajoPracticoId = angular.isUndefined(vm.filter.trabajoPractico) ? null : vm.filter.trabajoPractico.id;
            vm.filter.catedraId = angular.isUndefined(vm.filter.catedra) ? null : vm.filter.catedra.id;
            pagination.paginate(vm.filter, pageNumber)
                .then(function (data) {
                    vm.result = data;
                    vm.aux.showDadosBaja = vm.filter.dadosBaja;
                    updateFilterChips();
                    vm.paginationData = pagination.getPaginationData();
                }, function () {
                });
        }

        /*function buscarAsignaciones(form) {
            if (!form.$invalid) {
                pagination.config('api/asignacion/findAsignacionByFilters');
                vm.filter.trabajoPracticoId = angular.isUndefined(vm.filter.trabajoPractico) ? null : vm.filter.trabajoPractico.id;
                vm.filter.catedraId = angular.isUndefined(vm.filter.catedra) ? null : vm.filter.catedra.id;
                executeQuery();
            }
        }*/

        function consultar(form){
            if(!form.$invalid){
                executeQuery();
            }
        }

        vm.keyboardOk = function (event) {
            if (event.which == 13) {
                executeQuery();
//                buscarAsignaciones("consultarAsignacionesForm");
            }
        };

        //Chips
        function updateFilterChips() {
            vm.filterChips = [];
//            vm.filterChips.push(newFilterChip('dadosBaja', 'Dados de baja', vm.filter.dadosBaja, vm.filter.dadosBaja ? 'SI' : 'NO'));
            if (vm.filter.selectedAlumno && !vm.esAlumno) {
                vm.filterChips.push(newFilterChip('selectedAlumno', 'Alumno', vm.filter.selectedAlumno, vm.filter.selectedAlumno.apellido));
            }
            if (vm.filter.catedra) {
                vm.filterChips.push(newFilterChip('catedra', 'Cátedra', vm.filter.catedra, (vm.filter.catedra.materia + ' ' + vm.filter.catedra.denominacion)));
            }
            if (vm.filter.trabajoPractico) {
                vm.filterChips.push(newFilterChip('trabajoPractico', 'Trabajo práctico', vm.filter.trabajoPractico, vm.filter.trabajoPractico.nombre));
            }
            if (vm.filter.profesorId) {
                vm.filterChips.push(newFilterChip('profesorId', 'Profesor', vm.filter.profesorId));
            }
            if (vm.filter.fechaAsignacion) {
                vm.filterChips.push(newFilterChip('fechaAsignacion', 'Fecha asignación', vm.filter.fechaAsignacion, $filter('date')(vm.filter.fechaAsignacion, 'dd/MM/yyyy')));
            }
            if (vm.filter.practica) {
                vm.filterChips.push(newFilterChip('practica', 'Práctica', vm.filter.practica));
            }
            if (vm.filter.tipoDocumentoPaciente) {
                vm.filterChips.push(newFilterChip('tipoDocumentoPaciente', 'Tipo doc.', vm.filter.tipoDocumentoPaciente, findInColecction(vm.filter.tipoDocumentoPaciente, vm.data.tiposDocumentos)));
            }
            if (vm.filter.numeroDocumentoPaciente) {
                vm.filterChips.push(newFilterChip('numeroDocumentoPaciente', 'Nro. doc.', vm.filter.numeroDocumentoPaciente));
            }
            if (vm.filter.apellidoPaciente) {
                vm.filterChips.push(newFilterChip('apellidoPaciente', 'Apellido paciente', vm.filter.apellidoPaciente));
            }
            if (vm.filter.nombrePaciente) {
                vm.filterChips.push(newFilterChip('nombrePaciente', 'Nombre paciente', vm.filter.nombrePaciente));
            }
            if (vm.filter.estado) {
                vm.filterChips.push(newFilterChip('estado', 'Estado', vm.filter.estado));
            }
        }

        $scope.$watchCollection('vm.filterChips', function (newCol, oldCol) {
            if (newCol.length < oldCol.length) {
                vm.filter = {};
                if (vm.esAlumno) {
                    vm.filter["selectedAlumno"] = vm.selectedAlumno;
                    vm.filter.alumnoId = vm.selectedAlumno.id;
                }
                angular.forEach(newCol, function (filterChip) {
                    vm.filter[filterChip.origin] = filterChip.value;
                })

                if (vm.filter["selectedAlumno"] != null) {
                    executeQuery();
//                    buscarAsignaciones("consultarAsignacionesForm");
                } else {
                    vm.filter = {};
                    executeQuery();
//                    buscarAsignaciones("consultarAsignacionesForm");
//                    vm.alumnos = [];
                }
            }
        });

        $scope.$watch('vm.filter.tipoDocumento', function () {
            if (vm.filter.tipoDocumento != null && vm.filter.tipoDocumento != 'undefined') {
                vm.aux.isTipoDocumentoSelected = true;
            } else {
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

        function findInColecction(id, collection) {
            for (var i = 0; i < collection.length; i++) {
                if (collection[i].key == id) {
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
                paginationData: vm.paginationData
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
                updateFilterChips();
            } else {
//                buscarAsignaciones("consultarAsignacionesForm")
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

        function edit(asignacionId) {
            console.log("edit:" + asignacionId);
            $state.go('^.edit', {id: asignacionId});
        }

        function view(asignacionId) {
            console.log("view: " + asignacionId);
            $state.go('^.view', {id: asignacionId});
        }

        function showConfirmarAsignacionDialog(event, entity) {
            $mdDialog.show({
                templateUrl: 'views/asignacion/confirmarAsignacionDialog.html',
                parent: angular.element(document.body),
                targetEvent: event,
                clickOutsideToClose: true,
                controller: function DialogController($scope, $mdDialog) {
                    $scope.asignacion = entity;
                    $scope.cancelar = function () {
                        $mdDialog.cancel();
                    };
                    $scope.aceptar = function () {
                        $mdDialog.hide();
                    };
                }
            })
                .then(function () {
                    confirmar(entity);
                },
                function () {
                    // Cancelled dialog. Do nothing
                });
        }

        function confirmar(asignacion) {
            var mensaje = "Asignación para el paciente " + asignacion.apellidoPaciente + ", " + asignacion.nombrePaciente + " confirmada";
            var key = 'CONFIRMADO';
            changeEstado(asignacion, mensaje, key);
        }

        function cancelar(asignacion) {
            var mensaje = "Asignación para el paciente " + asignacion.apellidoPaciente + ", " + asignacion.nombrePaciente + " cancelada";
            var key = 'CANCELADO';
            changeEstado(asignacion, mensaje, key);
        }

        function changeEstado(asignacion, mensaje, estadoKey) {
            var asignaciones = [];
            asignaciones.push(asignacion);
            asignacion.diagnosticoId = asignacion.diagnostico.id;
            service.cambiarEstado(asignacion, estadoKey)
                .then(function () {
                    executeQuery();
//                    buscarAsignaciones('consultarAsignacionesForm');
                    message.successMessage(mensaje, null, 3000);
                }, function (data) {
                    handleError(data, status);
                });
        }

        function showConsultarAlumnosDialog(event){
            $mdDialog.show({
                templateUrl: 'views/asignacion/consultarAlumnoDialog.html',
                parent: angular.element(document.body),
                locals: {
                    tiposDocumentos : vm.data.tiposDocumentos
                },
                targetEvent: event,
                clickOutsideToClose: true,
                controller: function DialogController($scope, $mdDialog, tiposDocumentos) {
                    $scope.alumnos = [];
                    $scope.filterAlumnos = {};
                    $scope.selectedAlumnos = [];
                    $scope.selectedAlumno = {};
                    $scope.paginationData = pagination.paginationData;
                    $scope.colorIcon = ['#00B0FF', '#00B0FF'];
                    $scope.filterChipsAlumnos = [];
                    $scope.tiposDocumentos = tiposDocumentos;

                    $scope.colorMouseOver = function (icon) {
                        $scope.colorIcon[icon] = '#E91E63';
                    };

                    $scope.colorMouseLeave = function (icon) {
                        $scope.colorIcon[icon] = '#00B0FF';
                    };

                    $scope.cancelar = function () {
                        $mdDialog.cancel();
                    };
                    $scope.aceptar = function () {
                        $scope.selectedAlumno = $scope.selectedAlumnos[0];
                        $mdDialog.hide($scope.selectedAlumno);
                    };

                    $scope.buscarAlumnos = function(){
                        pagination.config('api/asignacion/findAlumnoByFilters');
                        executeQueryAlumnos();
                    }

                    $scope.cleanFilters = function () {
                        $scope.filterAlumnos = {};
                        $scope.filterChipsAlumnos = [];
                    };

                    function executeQueryAlumnos(pageNumber) {
                        pagination.paginate($scope.filterAlumnos, pageNumber)
                            .then(function (data) {
                                $scope.alumnos = data;
                                $scope.paginationData = pagination.getPaginationData();
//                                $scope.updateFilterChips();
                            }, function () {
                            });
                    }

                    $scope.nextPageAlumno = function() {
                        if ($scope.paginationData.morePages) {
                            executeQueryAlumnos(++$scope.paginationData.pageNumber);
                        }
                    }

                    $scope.previousPageAlumno = function() {
                        if (!$scope.paginationData.firstPage) {
                            executeQueryAlumnos(--$scope.paginationData.pageNumber);
                        }
                    }

                   /* function updateFilterChips(){
                        if ($scope.filterAlumnos.tipoDocumentoAlumno) {
                            $scope.filterChipsAlumnos.push(newFilterChip('tipoDocumentoPaciente', 'Tipo doc.', $scope.filterChipsAlumnos.tipoDocumentoPaciente, findInColecction(vm.filter.tipoDocumentoPaciente, vm.data.tiposDocumentos)));
                        }
                        if ($scope.filterAlumnos..numeroDocumentoAlumno) {
                            $scope.filterChipsAlumnos.push(newFilterChip('numeroDocumentoPaciente', 'Nro. doc.', $scope.filterChipsAlumnos.numeroDocumentoPaciente));
                        }
                        if ($scope.filterAlumnos.apellidoAlumno) {
                            $scope.filterChipsAlumnos.push(newFilterChip('apellidoAlumno', 'Apellido paciente', $scope.filterChipsAlumnos.apellidoPaciente));
                        }
                        if ($scope.filterAlumnos.nombreAlumno) {
                            $scope.filterChipsAlumnos.push(newFilterChip('nombreAlumno', 'Nombre paciente', $scope.filterChipsAlumnos.nombreAlumno));
                        }
                    }*/
                }
            })
                .then(function(selectedAlumno) {
                    vm.onAlumnoSelected(selectedAlumno);
                },
                function() {
                    // Cancelled dialog. Do nothing
                });
        }

        //Métodos auxiliares
        vm.clickIcon = 'expand_more';
        vm.colorIcon = ['#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF'];

        vm.cleanFilters = function () {
            vm.filter = {};
            vm.filterChips = [];
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
                if (toState.name.startsWith('asignacion')) {
                    cacheData();
                } else {
//                    $rootScope.created = false;
//                    $rootScope.edited = false;
                    cache.destroy();
                }
            });

        $scope.$on('$stateChangeSuccess',
            function (event, toState, toParams, fromState, fromParams) {
                if (fromState.name.startsWith('asignacion')) {
                    if (toParams.execQuery) {
//                        getCachedData();
                        executeQuery();
//                        buscarAsignaciones("consultarAsignacionesForm");
                    } else if (toParams.execQuerySamePage) {
                        getCachedData();
//                        buscarAsignaciones("consultarAlumnosForm");
                        executeQuery($scope.paginationData.pageNumber)
                    } else {
                        getCachedData();
//                        executeQuery();
                    }
                } else {
//                    buscarAsignaciones("consultarAlumnosForm");
                    executeQuery();
                }
            });

    }]);



