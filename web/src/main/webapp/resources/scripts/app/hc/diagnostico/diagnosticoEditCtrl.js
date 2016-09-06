var module = angular.module('historiaClinicaModule');

module.controller('DiagnosticoCtrl_Edit', ['$scope', 'DiagnosticoSrv', 'MessageSrv', '$mdDialog', '$state', 'pacienteId', 'diagnosticosResponse', 'finalStatesResponse',
    function ($scope, service, message, $mdDialog, $state, pacienteId, diagnosticosResponse, finalStatesResponse) {
        var vm = this;

        var pacienteId = pacienteId;

        vm.diagnosticos =  diagnosticosResponse.data || [];

        vm.addNewDiagnostico = addNewDiagnostico;
        vm.editDiagnostico = editDiagnostico;
        vm.cancelEditing = cancelEditing;
        vm.deleteDiagnostico = deleteDiagnostico;
        vm.cancelResolve = cancelResolve;
        vm.acceptResolve = acceptResolve;
        var performSubmit = $scope.$parent.performSubmit;
        var handleError = $scope.$parent.handleError;
        vm.validationErrorFromServer = $scope.$parent.validationErrorFromServer;
        vm.deleteMovimiento = deleteMovimiento;

        vm.aux = {
            editingDiagnostico: undefined,
            wantToResolve: false,
            finalStates: finalStatesResponse.data,
            erroOnEdit: false,
            newMovimiento: {}
        }

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
        }

        function cancelResolve() {
            vm.aux.wantToResolve = false;
        }

        function acceptResolve(form) {
            if(form.$valid) {
                vm.aux.erroOnEdit = false;
                vm.aux.editingDiagnostico.movimientos.push(vm.aux.newMovimiento);
                vm.aux.editingDiagnostico.editado = true;
                vm.diagnosticos[vm.aux.editingDiagnostico.index] = vm.aux.editingDiagnostico;
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
            $mdDialog.show(confirm).then(function() {
                vm.diagnosticos.splice(vm.diagnosticos.indexOf(diagnostico), 1);
                cancelEditing();
            }, function() {});
        }

        function deleteMovimiento(index) {
            vm.aux.editingDiagnostico.movimientos.splice(index, 1);
            vm.aux.editingDiagnostico.editado = false;
        }

        $scope.$on('validatedForm', function(event, args) {
            event.defaultPrevented = true;
            performSubmit(function() {
                service.save(vm.diagnosticos, pacienteId)
                    .success(function() {
                        message.successMessage('Diagnósticos guardados con éxito.');
                        if  (args.continueEditing) {
                            $state.go($state.current, {editing: true}, {reload: true})
                        } else {
                            $state.go('historiaClinica.diagnosticoView',  {editing: false});
                        }
                    })
                    .error(function(data, status) {
                        handleError(data, status);
                    })
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
                }, function () {});
        };

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

            $scope.practicaNoExistente = function() {
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
    }]);