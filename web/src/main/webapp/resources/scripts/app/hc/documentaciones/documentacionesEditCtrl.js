var module = angular.module('historiaClinicaModule');

module.controller('DocumentacionesEdit_Ctrl', ['$scope', 'HistoriaClinicaSrv', 'MessageSrv', '$state', '$filter', '$stateParams', '$window', '$mdEditDialog', 'Upload', 'pacienteId', 'docResponse', '$q',
    function ($scope, historiaClinicaSrv, message, $state, $filter, $stateParams, $window, $mdEditDialog, Upload, pacienteId, docResponse, $q) {
        var vm = this;

        var pacienteId = pacienteId;
        vm.documentaciones = docResponse.data || [];
        vm.filesAdded = [];
        vm.newFiles = newFiles;
        vm.editObservaciones = editObservaciones;
        vm.deleteDoc = deleteDoc;
        var performSubmit = $scope.$parent.performSubmit;
        var handleError = $scope.$parent.handleError;
        vm.validationErrorFromServer = $scope.$parent.validationErrorFromServer;

        function editObservaciones(event, doc) {
            event.stopPropagation();

            var promise = $mdEditDialog.large({
                messages: {
                    'md-maxlength': 'Ingresó más caracteres de los permitidos.'
                },
                modelValue: doc.descripcion,
                cancel: 'Cancelar',
                title: 'Descripción: ' + doc.nombre,
                ok: 'Aceptar',
                save: function (input) {
                    doc.descripcion = input.$modelValue;
                },
                targetEvent: event,
                validators: {
                    'md-maxlength': 150
                }
            });
        }

        $scope.$on('validatedForm', function(event, args) {
            event.defaultPrevented = true;
            vm.documentaciones.forEach(function(doc) {
               doc.extension = null;
            });
            performSubmit(function() {
                doSave().then(function (response) {
                        message.successMessage('Documentaciones guardadas con éxito.');
                       goViewOrContinueEditing(args);
                    }, function (response) {
                        handleError(response.data, response.status)
                    });
            }, args.form);
        });

        function doSave() {
            var deferred = $q.defer();
            historiaClinicaSrv.updateCurrentDocsAndSaveTempNewDocs(vm.documentaciones, pacienteId)
                .then(function (response) {
                    if (vm.filesAdded && vm.filesAdded.length) {
                        Upload.upload({
                            url: 'api/historiaClinica/saveNewFiles',
                            data: {files: vm.filesAdded, newFilesIds: Upload.json(response.data)}
                        })
                            .then(function (response) {
                                deferred.resolve(response);
                            }, function (response) {
                                deferred.reject(response);
                            })
                    } else {
                        deferred.resolve(response);
                    }
                }, function (response) {
                    deferred.reject(response);
                })

            return deferred.promise;
        }

        function goViewOrContinueEditing(args) {
            if  (args.continueEditing) {
                $state.go($state.current, {editing: true}, {reload: true})
            } else {
                $state.go('historiaClinica.documentacionesView',  {editing: false});
            }
        }

        function newFiles($files, $file, $newFiles, $duplicateFiles, $invalidFiles, $event) {
            if ($file && $files.length) {
                $files.forEach(function(file) {
                    var duplicate = $filter('filter')(vm.documentaciones, function(currentFile) {
                        return file.name == currentFile.nombre;
                    });
                    if (!duplicate.length) {
                        vm.documentaciones.push(
                            {nombre: file.name,
                            extension: getExtension(file)}
                        );
                        vm.filesAdded.push(file);
                    }
                });
            }
        }

        function getExtension(file) {
            var type = file.type;
            var result = type.substring(type.lastIndexOf('/') + 1, type.length).toUpperCase();
            if (result == 'JPEG') { result = 'JPG';}
            return result;
        }

        function deleteDoc(index) {
            vm.documentaciones.splice(index, 1);
        }

    }]);
