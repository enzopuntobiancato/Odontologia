var module = angular.module('permisosModule');


module.controller('PermisosCtrl', ['$scope', 'PermisosSrv', '$filter', '$state', 'MessageSrv', 'funcionalidadesResponse', 'rolesResponse',
    function ($scope, service, $filter, $state, message, funcionalidadesResponse, rolesResponse) {
        var vm = this;
        var performSubmit = $scope.$parent.performSubmit;
        var handleError = $scope.$parent.handleError;
        vm.validationErrorFromServer = $scope.$parent.validationErrorFromServer;

        vm.data = {
            funcionalidades: funcionalidadesResponse.data,
            roles: rolesResponse.data
        }

        vm.selectedIdx = {};
        vm.selectedRol = {};
        vm.clickRol = clickRol;
        vm.clickFuncionalidad = clickFuncionalidad;
        vm.cancelar = cancelar;
        vm.save = save;

        clickRol(vm.data.roles[0], 0);

        function clickRol(rol, idx) {
            vm.selectedIdx = idx;
            vm.selectedRol = rol;
            markFuncionalidades(rol);
        }

        function markFuncionalidades(rol) {
            for (var j = 0; j < vm.data.funcionalidades.length; j++) {
                var permiso = $filter('filter')(rol.privilegios, function(privilegio) {
                    return privilegio.funcionalidad.id == vm.data.funcionalidades[j].id;
                });
                if (permiso.length > 0) {
                    vm.data.funcionalidades[j].selected = true;
                } else {
                    vm.data.funcionalidades[j].selected = false;
                }
            }
        }

        function clickFuncionalidad(funcionalidad) {
            if (funcionalidad.selected) {
                vm.data.roles[vm.selectedIdx].privilegios.push({funcionalidad: funcionalidad});
            } else {
                deletePrivilegio(vm.data.roles[vm.selectedIdx], funcionalidad);
            }
        }

        function deletePrivilegio(rol, funcionalidad) {
            for (var i = 0; i < rol.privilegios.length; i++) {
                if (rol.privilegios[i].funcionalidad.id == funcionalidad.id) {
                    rol.privilegios.splice(i, 1);
                    break;
                }
            }
        }

        function cancelar() {
            $state.go('home');
        }

        function save(form) {
            performSubmit(function() {
                service.save(vm.data.roles)
                    .success(function() {
                        message.successMessage("Los cambios se guardaron correctamente");
                        cancelar();
                    })
                    .error(function(data, status) {
                        handleError(data, status);
                    })
            }, form);
        }

    }]);
