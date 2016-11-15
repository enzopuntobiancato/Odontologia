var module = angular.module('backupModule');


module.controller('BackupCtrl', ['$scope', 'BackupSrv', 'PaginationService', '$filter', '$state', 'MessageSrv', '$window', '$mdDialog', '$location',
    function ($scope, service, pagination, $filter, $state, message, $window, $mdDialog, $location) {
        var vm = this;
        vm.filter = {};
        vm.filterChips = [];
        vm.result = [];
        vm.data = {
            today: new Date()
        }

        vm.cancelar = cancelar;
        vm.consultar = consultar;
        vm.nextPage = nextPage;
        vm.previousPage = previousPage;

        pagination.config('api/backup/find');
        vm.paginationData = pagination.paginationData;

        vm.new = newBackup;

        search();

        function newBackup(form, event) {
            var confirm = $mdDialog.confirm()
                .title('Confirmar generación de backup')
                .textContent('Haga click en "Aceptar" para generar el backup.')
                .targetEvent(event)
                .ok('Aceptar')
                .cancel('Cancelar');
            $mdDialog.show(confirm).then(function() {
                service.generate()
                    .then(function() {
                        message.successMessage('Respaldo de datos generado con éxito')
                        executeQuery(form);
                    }, function() {
                        message.errorMessage('Ocurrió un error al generar el respaldo de datos');
                    })
            }, function() {
            });
        }

        function updateFilterChips() {
            vm.filterChips = [];
            if (vm.filter.fechaGeneracion) {
                vm.filterChips.push(newFilterChip('fechaGeneracion', 'Fecha generación', vm.filter.fechaGeneracion, $filter('date')(vm.filter.fechaGeneracion, 'dd/MM/yyyy')));
            }
        }

        $scope.$watchCollection('vm.filterChips', function(newCol, oldCol) {
            if (newCol.length < oldCol.length) {
                vm.filter = {};
                angular.forEach(newCol, function(filterChip) {
                    vm.filter[filterChip.origin] = filterChip.value;
                });
                search();
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

        function executeQuery(form, pageNumber) {
            if (form.$valid) {
              search(pageNumber);
            }
        }

        function consultar(form) {
            executeQuery(form);
        }

        function nextPage(form) {
            if (vm.paginationData.morePages && form.$valid) {
                executeQuery(form, ++vm.paginationData.pageNumber);
            }
        }

        function previousPage(form) {
            if (!vm.paginationData.firstPage && form.$valid) {
                executeQuery(form, --vm.paginationData.pageNumber);
            }
        }

        vm.openInNewTab = function(backup) {
            var base = $location.protocol() + '://'+ $location.host() +':'+  $location.port() + "/Odontologia-web/api/backup/getBackup";
            $window.open(base + '?idBackup=' + backup.id);
        }

        function search(pageNumber) {
            pagination.paginate(vm.filter, pageNumber).then(function (data) {
                vm.result = data;
                vm.paginationData = pagination.getPaginationData();
                updateFilterChips();
            }, function () {
            });
        }

        function cancelar() {
            $state.go('home');
        }

        vm.colorIcon = ['#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF'];

        vm.cleanFilters = function () {
            vm.filter = {};
            vm.filterChips = [];
        };

        vm.colorMouseOver = function (icon) {
            vm.colorIcon[icon] = '#E91E63';
        };

        vm.colorMouseLeave = function (icon) {
            vm.colorIcon[icon] = '#00B0FF';
        };
    }]);
