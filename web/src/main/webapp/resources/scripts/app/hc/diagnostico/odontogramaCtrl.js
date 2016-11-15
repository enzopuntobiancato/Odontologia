var module = angular.module('historiaClinicaModule');

module.controller('OdontogramaCtrl', ['$scope', 'PacienteSrv', 'odontogramaResponse', 'hallazgosResponse', 'MessageSrv', '$mdDialog',
    function ($scope, service, odontogramaResponse, hallazgosResponse, message, $mdDialog) {
        var vm = this;
        vm.tratamientos = hallazgosResponse.data;
        vm.selectedTratamiento = {};
        vm.odontograma = {};
        vm.selectTratamiento = selectTratamiento;
        vm.selectedPiezas = [];
        vm.keyDown = keyDown;
        vm.keyUp = keyUp;
        vm.diagnosticos = [];
        vm.hecho = false;

        function keyDown($event) {
            if ($event.ctrlKey) {
                console.log("Ctrl apretado");
            }
        }

        function keyUp($event) {
            if ($event.ctrlKey) {
                console.log("Ctrl soltado");
            }
        }

        (function init() {
            vm.odontograma = odontogramaResponse.data;
            console.log(vm.tratamientos);
        })();

        $scope.$watch('vm.odontograma', function (newData, oldData) {
            console.log(newData);
        });

        $scope.$on('newHallazgo', function (event, args) {
            event.defaultPrevented = true;
            var pieza = args.newPieza;
            var hallazgoClinico = args.hallazgoClinico;

            if (hallazgoClinico.estado == 1) {
                //esta pendiente
                if (!vm.selectedPiezas.length == 0) {
                    //no es la primera seleccionada
                    if (hallazgoClinico.aplicaACara && !findPieza(pieza)) {
                        //El hallazgo aplica a cara pero se selecciono una pieza distinta a la seleccionada antes.
                        message.errorMessage("Cargue primero el diagnóstico correspondiente a " + vm.selectedPiezas[0]);
                    }
                }
                //Agrego al historial de piezas a la que se les va a cargar el diagnostico.
                vm.selectedPiezas.push(pieza);
            }

            if (pieza.numeroSector == 1 || pieza.numeroSector == 2) {
                replacePieza(pieza, vm.odontograma.piezasDentalesSuperiores);
            } else {
                replacePieza(pieza, vm.odontograma.piezasDentalesInferiores);
            }

        });

        function addAndReverse(newItem) {
            vm.diagnosticos.push(newItem);
        }

        function finalizarCargaHallazgo() {
            if (vm.selectedPiezas.length == 0) {
                message.errorMessage("No se ha marcado ninguna pieza para cargar un diagnóstico.");
            }
            $mdDialog.show({
                controller: createDialogController,
                templateUrl: 'views/hc/diagnostico/createDialog.html',
                parent: angular.element(document.body),
                targetEvent: event,
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
                    $scope.diagnostico.hallazgoClinico = vm.selectedPiezas[0].hallazgoClinico;
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

        function findPieza(pieza) {
            for (var i = 0; i < vm.selectedPiezas.length; i++) {
                if (vm.selectedPiezas[i] == pieza) {
                    return true;
                }
            }
            return false;
        }

        function replacePieza(newPieza, listaPiezas) {
            for (var i = 0; i < listaPiezas.length; i++) {
                if (newPieza.numeroSector == listaPiezas[i].numeroSector && newPieza.numeroPieza == listaPiezas[i].numeroPieza) {
                    listaPiezas[i] = newPieza;
                    break;
                }
            }
        }

        function selectTratamiento(tratamiento) {
            if (!tratamiento) {
                return false;
            }
            if (tratamiento.selected) {
                vm.selectedTratamiento = tratamiento;
                if (vm.hecho) {
                    vm.selectedTratamiento.color = "#3F51B5";
                    vm.selectedTratamiento.estado = 0;
                } else {
                    vm.selectedTratamiento.color = "#F44336";
                    vm.selectedTratamiento.estado = 1;
                }
            } else {
                vm.selectedTratamiento = {};
            }
        }
    }]);