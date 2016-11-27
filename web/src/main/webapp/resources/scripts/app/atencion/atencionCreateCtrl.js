var module = angular.module('atencionModule');


module.controller('AtencionCtrl_Create', ['$scope', '$rootScope', 'AtencionSrv', '$state', 'MessageSrv', 'Upload',
    '$filter', 'asignacionResponse',
    function ($scope, $rootScope, service, $state, message, Upload, $filter, asignacionResponse) {
        var vm = this;
        vm.atencion = {
            asignacionPaciente: asignacionResponse.data,
            fechaAtencion: asignacionResponse.data.fechaAsignacion,
            diagnosticoSolucionado: true
        }
        vm.data = {
            today: new Date(),
            files: []
        }
        var performSubmit = $scope.$parent.performSubmit;
        var handleError = $scope.$parent.handleError;
        vm.validationErrorFromServer = $scope.$parent.validationErrorFromServer;
        vm.goIndex = goIndex;
        vm.save = save;
        vm.newFiles = newFiles;
        vm.openSection = openSection;
        vm.sections = [false, false, true];
        vm.odontograma = {};


        (function findOdontograma() {
            var idPaciente = vm.atencion.asignacionPaciente.idPaciente;
            service.findOdontogramaById(idPaciente)
                .then(function (response) {
                    vm.odontograma = response.data;
                }, function (error) {
                    console.log(error);
                });
        })();

        function save(form) {
            performSubmit(function () {
                Upload.upload({
                    url: 'api/atencion/save',
                    data: {files: vm.data.files, atencion: Upload.json(vm.atencion)}
                }).then(function (response) {
                        message.successMessage("Atención registrada con éxito")
                        goIndex();
                    }, function (response) {
                        handleError(response.data, response.status)
                    });
            }, form);
        }

        function goIndex() {
            $state.go('asignacion.index');
        }

        function newFiles($files, $file, $newFiles, $duplicateFiles, $invalidFiles, $event) {
            if ($files.length) {
                $files.forEach(function (file) {
                    var duplicate = $filter('filter')(vm.data.files, function (currentFile) {
                        return file.name == currentFile.name;
                    });
                    if (!duplicate.length) {
                        vm.data.files.push(file);
                    }
                });
            }
        }

        function openSection(idSection) {
            vm.sections[idSection] = !vm.sections[idSection];
        }

    }]);
