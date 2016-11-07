var module = angular.module('historiaClinicaModule');

module.controller('DiagnosticoCtrl_Edit', ['$scope', 'DiagnosticoSrv', 'MessageSrv', '$mdDialog', '$state', 'pacienteId', 'diagnosticosResponse', 'finalStatesResponse', 'odontogramaResponse', 'hallazgosResponse', 'practicasResponse', '$mdEditDialog', 'Upload',
    function ($scope, service, message, $mdDialog, $state, pacienteId, diagnosticosResponse, finalStatesResponse, odontogramaResponse, hallazgosResponse, practicasResponse, $mdEditDialog, Upload) {
        var vm = this;

        var pacienteId = pacienteId;

        vm.diagnosticos = diagnosticosResponse.data || [];
        vm.diagnosticosToSave = [];

        //DIAGNOSTICOS INDEPENDIENTES
        vm.addNewDiagnostico = addNewDiagnostico;
        vm.editDiagnostico = editDiagnostico;
        vm.cancelEditing = cancelEditing;
        vm.deleteDiagnostico = deleteDiagnostico;
        vm.cancelResolve = cancelResolve;
        vm.acceptResolve = acceptResolve;
        vm.deleteMovimiento = deleteMovimiento;
        var performSubmit = $scope.$parent.performSubmit;
        var handleError = $scope.$parent.handleError;
        vm.validationErrorFromServer = $scope.$parent.validationErrorFromServer;


        //FUNCIONALIDAD ODONTOGRAMA
        vm.selectTratamiento = selectTratamiento;
        vm.addHallazgo = addHallazgo;
        vm.removeHallazgo = removeHallazgo;
        vm.construirDiagnosticos = construirDiagnosticos;
        vm.cambiarEstadoTratamiento = cambiarEstadoTratamiento;
        //DATA ODONTOGRAMA
        vm.tratamientos = hallazgosResponse.data;
        vm.odontograma = odontogramaResponse.data;
        vm.practicas = practicasResponse.data;
        vm.selectedTratamiento = {};
        vm.selectedPiezas = [];
        vm.modifiedPiezas = [];
        vm.diagnosticosOdontograma = [];
        vm.hecho = false;
        vm.estadoSelectedTratamiento = "Pendiente";
        vm.selectedTratamiento = null;

        vm.query = {
            limit: 5,
            page: 1
        };


        vm.aux = {
            editingDiagnostico: undefined,
            wantToResolve: false,
            finalStates: finalStatesResponse.data,
            erroOnEdit: false,
            newMovimiento: {}
        }


        //DIAGNOSTICOS INDEPENDIENTES
        function cancelEditing() {
            vm.aux.editingDiagnostico = undefined;
            vm.aux.wantToResolve = false;
            vm.aux.errorOnEdit = false;
            vm.aux.newMovimiento = {};
        }

        function editDiagnostico(diagnostico, index) {
            vm.aux.newMovimiento = {};
            vm.aux.editingDiagnostico = diagnostico;
            vm.aux.editingDiagnostico.index = index;
        }

        function addAndReverse(newItem) {
            vm.diagnosticos.push(newItem);
            vm.diagnosticosToSave.push(newItem);
        }

        function cancelResolve() {
            vm.aux.wantToResolve = false;
        }

        function acceptResolve(form) {
            if (form.$valid) {
                vm.aux.erroOnEdit = false;
                vm.aux.editingDiagnostico.movimientos.push(vm.aux.newMovimiento);
                vm.aux.editingDiagnostico.editado = true;
                vm.diagnosticos[vm.aux.editingDiagnostico.index] = vm.aux.editingDiagnostico;

                if (vm.aux.editingDiagnostico.piezas != null && vm.aux.editingDiagnostico.piezas > 0) {
                    var cancelado = vm.aux.newMovimiento.estado.key == "CANCELADO" ? true : false;
                    //Limpiar hallazgos.
                    for (var i = 0; i < vm.aux.editingDiagnostico.piezas.length; i++) {
                        var p = vm.aux.editingDiagnostico.piezas[i];
                        if (cancelado) {
                            limpiarHallazgos(p)
                        } else {
                            resolverHallazgos(p, 'REALIZADO');
                        }
                    }
                    $scope.$broadcast('deleted-hallazgo', { piezas: vm.aux.editingDiagnostico.piezas });
                }
            } else {
                vm.aux.errorOnEdit = true;
            }
        }

        function deleteDiagnostico(diagnostico, practica, ev) {
            ev.preventDefault();
            var confirm = $mdDialog.confirm()
                .title('¿Seguro que desea eliminar el diagnóstico recientemente agregado?')
                .textContent('Diagnóstico para práctica odontológica: ' + practica)
                .targetEvent(ev)
                .ok('Aceptar')
                .cancel('Cancelar');
            $mdDialog.show(confirm).then(function () {
                if (diagnostico.piezas != null
                    && diagnostico.piezas.length > 0) {
                    //Borramos los hallazgos de las piezas.
                    var piezasDentales = diagnostico.piezas;
                    for (var i = 0; i < piezasDentales.length; i++) {
                        var p = piezasDentales[i];
                        limpiarHallazgos(p);
                    }
                    $scope.$broadcast('deleted-hallazgo', { piezas: diagnostico.piezas });
                }
                //Quitamos el diagnostico.
                vm.diagnosticos.splice(vm.diagnosticos.indexOf(diagnostico), 1);

                cancelEditing();
            }, function () {
            });
        }

        function deleteMovimiento(index) {
            vm.aux.editingDiagnostico.movimientos.splice(index, 1);
            vm.aux.editingDiagnostico.editado = false;
        }

        $scope.$on('validatedForm', function (event, args) {
            event.defaultPrevented = true;

            var validatePiezas = validarPiezasDentalesPendientes();
            if (!validatePiezas) {
                message.errorMessage("Hay hallazgos pendientes a los que no se les cargó diagnósticos. Revise el odontograma.");
                return;
            }
            performSubmit(function () {
                Upload.upload({
                    url: 'api/diagnostico/save/' + pacienteId,
                    data: {piezas: Upload.json(vm.modifiedPiezas), diagnosticos: Upload.json(vm.diagnosticos)}
                }).then(function (response) {
                        vm.modifiedPiezas = [];
                        vm.diagnosticos = response.data;
                        message.successMessage("Diagnósticos registrados con éxito")
                    }, function (response) {
                        handleError(response.data, response.status)
                    });
            }, args.form);
        });


        function addNewDiagnostico(ev) {
            $mdDialog.show({
                controller: createDialogController,
                templateUrl: 'views/hc/diagnostico/createDialog.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: false
            })
                .then(function (diagnostico) {
                    diagnostico.ultimoMovimiento = {
                        estado: {
                            nombre: 'Aún no guardado'
                        }
                    }
                    addAndReverse(diagnostico);
                }, function () {
                });
        }

        //ODONTOGRAMA
        /**
         * Metodo de callback que se ejecucta desde la directiva diente para agregar un nuevo hallazgo al odontograma
         * y a lista auxiliar de piezas seleccionadas para cargar diagnosticos (ESTADO PENDIENTE)y a la lista de piezas
         * modificadas (incluyendo las piezas que no requieren diagnosticos porque su estado es ESTADO REALIZADO).
         * @param pieza
         * @returns {boolean}
         */
        function addHallazgo(pieza) {
            //Agrego al historial de piezas a la que se les va a cargar el diagnostico.
            var hallazgoClinico = vm.selectedTratamiento;
            if (hallazgoClinico.estado == 'PENDIENTE') {
                var index = null;
                for (var i = 0; i < vm.selectedPiezas.length; i++) {
                    if (vm.selectedPiezas[i] == pieza) {
                        vm.selectedPiezas[i] = pieza;
                        index = i;
                    }
                }
                if (index == null) {
                    vm.selectedPiezas.push(pieza);
                }
            }

            //Reemplazamos la pieza en el odontograma
            if (pieza.numeroSector == 1 || pieza.numeroSector == 2) {
                replacePieza(pieza, vm.odontograma.piezasDentalesSuperiores);
            } else {
                replacePieza(pieza, vm.odontograma.piezasDentalesInferiores);
            }

            //Agregamos al arreglo auxiliar de piezas modificadas que van a ser guardadas despues.
            vm.modifiedPiezas.push(pieza);
            return true;
        }

        function removeHallazgo(pieza, esRealizado) {
            if (!esRealizado) {
                var index = vm.selectedPiezas.indexOf(pieza);
                vm.selectedPiezas.splice(index, 1);
            }

            //Reemplazamos la pieza en el odontograma
            if (pieza.numeroSector == 1 || pieza.numeroSector == 2) {
                replacePieza(pieza, vm.odontograma.piezasDentalesSuperiores);
            } else {
                replacePieza(pieza, vm.odontograma.piezasDentalesInferiores);
            }

            //Agregamos al arreglo auxiliar de piezas modificadas que van a ser guardadas despues.
            vm.modifiedPiezas.push(pieza);
            return true;
        }

        /**
         * Metodo que construye los diagnosticos auxiliares que luego se mostraran el el dialogo. No son diagnosticos
         * propiamente dichos, sino que son DTOS. Se ejecuta al terminar la carga del odontograma.
         * @param ev
         * @returns {boolean}
         */
        function construirDiagnosticos(ev) {
            if (vm.selectedPiezas.length == 0) {
                message.errorMessage("No se han seleccionado dientes para cargar diagnósticos.");
                return false;
            }
            //Recorremos las piezas clickeadas
            for (var i = 0; i < vm.selectedPiezas.length; i++) {
                var pieza = vm.selectedPiezas[i];
                var hallazgoClinico = null;
                //Buscamos el hallazgo clinico tratado.
                if (pieza.hallazgoClinico) {
                    hallazgoClinico = pieza.hallazgoClinico;
                } else {
                    for (var j = 0; j < pieza.carasPiezaDental.length; j++) {
                        var cara = pieza.carasPiezaDental[j];
                        if (cara.hallazgoClinico && cara.hallazgoClinico.estado == 'PENDIENTE') {
                            hallazgoClinico = cara.hallazgoClinico;
                            break;
                        }
                    }
                }
                if (hallazgoClinico == null) {
                    message.errorMessage("No se encontrado ningún hallazgo en la pieza " + pieza.nombrePiezaDental);
                }
                if (hallazgoClinico.aplicaAPiezaGrupal) {
                    //TODO: ANALIZAR DESPUES COMO TRATAR LOS GRUPALES.
                    continue;
                }

                //Buscamos si ya tiene un diagnostico aplicado
                var practica = null;
                var tieneDiagnostico = false;

                for (var j = 0; j < vm.diagnosticos.length; j++) {
                    var d = vm.diagnosticos[j];
                    for (var k = 0; k < d.piezas.length; k++) {
                        var p = d.piezas[k];
                        if (p != pieza.nombrePiezaDental) {
                            continue;
                        }
                        practica = d.practicaOdontologica;
                        tieneDiagnostico = true;
                        break;
                    }
                }
                //Construimos el objeto auxiliar wrapper
                var piezas = [];
                piezas.push(pieza);
                for (var k = 0; k < piezas.length; k++) {
                    piezas[k].hasDiagnostico = tieneDiagnostico;
                }
                var observacion = hallazgoClinico.nombre + " en " + piezas[0].nombrePiezaDental
                var nuevoDiagnostico = { piezas: piezas, hallazgoClinico: hallazgoClinico, observaciones: observacion,
                    practicaOdontologica: practica, tieneDiagnostico: tieneDiagnostico};

                vm.diagnosticosOdontograma.push(nuevoDiagnostico);
            }

            //Desplegamos el dialogo.
            $mdDialog.show({
                controller: createDiagnosticoOdontogramaDialogController,
                templateUrl: 'views/hc/diagnostico/createDiagnosticoOdontogramaDialog.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: false,
                locals: { diagnosticosOdontograma: vm.diagnosticosOdontograma,
                    practicas: vm.practicas}
            })
                .then(function (diagnosticosOdontograma) {
                    //Elige agregar los diagnosticos
                    for (var i = 0; i < diagnosticosOdontograma.length; i++) {
                        //Extraigo cada uno de los diagnosticos auxiliares.
                        var diagnosticoAux = diagnosticosOdontograma[i];

                        if (!diagnosticoAux.tieneDiagnostico) {
                            var nombresPiezas = [];
                            for (var j = 0; j < diagnosticoAux.piezas.length; j++) {
                                var pieza = diagnosticoAux.piezas[j];
                                pieza.hasDiagnostico = true;
                                nombresPiezas.push(pieza.nombrePiezaDental);
                            }
                            //Lo convierto a un diagnostico verdadero.
                            var diagnostico = { piezas: nombresPiezas, observaciones: diagnosticoAux.observaciones,
                                practicaOdontologica: diagnosticoAux.practicaOdontologica, ultimoMovimiento: {estado: {nombre: 'Aún no guardado'}}}
                            //Lo agregamos al arreglo de diganosticos.
                            addAndReverse(diagnostico);
                        }
                    }
                    vm.diagnosticosOdontograma = [];
                    vm.selectedPiezas = [];
                }, function () {
                    //Limpiar los dientes.
                    vm.diagnosticosOdontograma = [];
                });
        }

        function selectTratamiento() {
            if (vm.hecho) {
                vm.selectedTratamiento.estado = 'REALIZADO';
            } else {
                vm.selectedTratamiento.estado = 'PENDIENTE';
            }
        }

        function cambiarEstadoTratamiento() {
            if (vm.hecho) {
                vm.estadoSelectedTratamiento = "Realizado";
                vm.selectedTratamiento.estado = 'REALIZADO';
            } else {
                vm.estadoSelectedTratamiento = "Pendiente";
                vm.selectedTratamiento.estado = 'PENDIENTE';
            }
        }

        //CONTROLLERS DIALOGOS
        /**
         * Controlador dialogo de creacion de diagnosticos independientes.
         * @param $scope
         * @param $mdDialog
         * @param DiagnosticoSrv
         */
        function createDialogController($scope, $mdDialog, DiagnosticoSrv) {
            $scope.diagnostico = {
                movimientos: []
            };
            $scope.aux = {
                practicaSearchText: null,
                practicaNotFound: false
            };

            $scope.searchPractica = function (searchText) {
                return DiagnosticoSrv.searchPracticas(searchText).then(function (response) {
                    return response.data;
                });
            }

            $scope.practicaNoExistente = function () {
                if ($scope.aux.practicaNotFound) {
                    $scope.diagnostico.practicaOdontologica = null;
                    $scope.aux.practicaSearchText = null;
                } else {
                    $scope.diagnostico.practicaNoExistente = null;
                }
            }

            $scope.cancel = function () {
                $mdDialog.cancel();
            };
            $scope.create = function (form) {
                if (form.$valid) {
                    $mdDialog.hide($scope.diagnostico);
                } else {
                    angular.forEach(form.$error, function (field) {
                        angular.forEach(field, function (errorField) {
                            errorField.$setTouched();
                        })
                    });
                }
            };
        }

        /**
         * Controlador de dialogo para la creacion de diagnosticos propios del odontograma.
         * @param $scope
         * @param $mdDialog
         * @param practicas
         * @param diagnosticosOdontograma
         */
        function createDiagnosticoOdontogramaDialogController($scope, $mdDialog, practicas, diagnosticosOdontograma) {

            //Diagnosticos pasados por parametro que deben ser modificados
            $scope.diagnosticosOdontograma = diagnosticosOdontograma;
            //Funcionalidad
            $scope.getNombrePiezasDiagnostico = getNombrePiezasDiagnostico;
            $scope.cancel = cancel;
            $scope.create = create;
            $scope.editComment = editComment;

            //Elementos auxiliares para la tabla
            $scope.practicas = practicas;
            $scope.query = {
                limit: 5,
                page: 1
            };


            function cancel() {
                $mdDialog.cancel();
            }

            function create(form) {
                if (form.$valid) {
                    $mdDialog.hide($scope.diagnosticosOdontograma);
                } else {
                    angular.forEach(form.$error, function (field) {
                        angular.forEach(field, function (errorField) {
                            errorField.$setTouched();
                        })
                    });
                }
            }

            function editComment(event, diagnostico) {
                event.stopPropagation(); // in case autoselect is enabled

                var editDialog = {
                    modelValue: diagnostico.observaciones,
                    placeholder: 'Observaciones',
                    save: function (input) {
                        diagnostico.observaciones = input.$modelValue;
                    },
                    targetEvent: event,
                    title: 'Observaciones',
                    validators: {
                        'md-maxlength': 200
                    }
                };

                var promise;
                promise = $mdEditDialog.large(editDialog);
                promise.then(function (ctrl) {
                    var input = ctrl.getInput();

                    input.$viewChangeListeners.push(function () {
                        input.$setValidity('test', input.$modelValue !== 'test');
                    });
                });
            }

            //METODO AUXILIAR DIALOGO
            function getNombrePiezasDiagnostico(piezas) {
                if (!piezas || piezas.length == 0) {
                    message.errorMessage("No se puede recuperar el nombre de la pieza buscada.");
                    return false;
                }

                var nombre = null;
                if (piezas.length > 1) {
                    for (var i = 0; i < piezas.length; i++) {
                        var numero = piezas[i].numeroSector + "" + piezas[i].numeroPieza;
                        nombre += numero;
                        if (i != (piezas.length - 1)) {
                            nombre += ", ";
                        }
                    }
                    return nombre;
                }
                return piezas[0].numeroSector + "" + piezas[0].numeroPieza;
            }
        }//FIN CONTROLADOR


        //METODOS AUXILIARES
        function replacePieza(newPieza, listaPiezas) {
            for (var i = 0; i < listaPiezas.length; i++) {
                if (newPieza.numeroSector == listaPiezas[i].numeroSector && newPieza.numeroPieza == listaPiezas[i].numeroPieza) {
                    listaPiezas[i] = newPieza;
                    break;
                }
            }
        }

        function limpiarHallazgos(nombrePieza) {
            //busco la pieza dental
            var piezaEncontrada = encontrarPiezaEnOdontograma(nombrePieza);
            //La vacio de hallazgos.
            piezaEncontrada.hallazgoClinico = null;
            piezaEncontrada.hasDiagnostico = false;
            piezaEncontrada.diagnosticoId = null;
            vm.modifiedPiezas.push(piezaEncontrada);
            for (var i = 0; i < piezaEncontrada.carasPiezaDental.length; i++) {
                var cara = piezaEncontrada.carasPiezaDental[i];
                cara.hallazgoClinico = null;
            }

        }

        function resolverHallazgos(nombrePieza, nuevoEstado) {
            if (nombrePieza == null || angular.isUndefined(nombrePieza)) {
                message.errorMessage("No se definio una pieza para limpiar los hallazgos.");
            }
            var piezaEncontrada = encontrarPiezaEnOdontograma(nombrePieza);
            vm.modifiedPiezas.push(piezaEncontrada);
            if (piezaEncontrada.hallazgoClinico) {
                piezaEncontrada.hallazgoClinico.estado = nuevoEstado;
                return;
            }

            for (var i = 0; i < piezaEncontrada.carasPiezaDental.length; i++) {
                var cara = piezaEncontrada.carasPiezaDental[i];
                if (cara.hallazgoClinico) {
                    cara.hallazgoClinico.estado = nuevoEstado;
                }
            }

        }

        function encontrarPiezaEnOdontograma(nombrePieza) {
            if (nombrePieza == null || angular.isUndefined(nombrePieza)) {
                message.errorMessage("No se definio una pieza para limpiar los hallazgos.");
            }

            var arrayPiezas = null;
            if (nombrePieza < 30) {
                arrayPiezas = vm.odontograma.piezasDentalesSuperiores
            } else {
                arrayPiezas = vm.odontograma.piezasDentalesInferiores;
            }

            for (var i = 0; i < arrayPiezas.length; i++) {
                var p = arrayPiezas[i];
                if (p.nombrePiezaDental != nombrePieza) {
                    continue;
                }
                return p;
            }
            return null;
        }

        function validarPiezasDentalesPendientes() {
            for (var i = 0; i < vm.modifiedPiezas.length; i++) {
                var pieza = vm.modifiedPiezas[i];

                if (pieza.hallazgoClinico
                    && pieza.hallazgoClinico.estado == 'PENDIENTE'
                    && pieza.hasDiagnostico != true) {
                    return false;
                }

                for (var j = 0; j < pieza.carasPiezaDental.length; j++) {
                    var cara = pieza.carasPiezaDental[j];
                    if (cara.hallazgoClinico
                        && cara.hallazgoClinico.estado == 'PENDIENTE'
                        && pieza.hasDiagnostico != true) {
                        return false;
                    }
                }
            }
            return true;

        }
    }]);