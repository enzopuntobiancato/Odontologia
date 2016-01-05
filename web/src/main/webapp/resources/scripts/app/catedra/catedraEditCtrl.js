'use strict';
var module=angular.module('catedraModule');

module.controller('CatedraCtrl_Edit', ['$scope', '$rootScope', '$state', 'CommonsSrv', 'MessageSrv','CatedraSrv', 'materiasResponse', 'diasResponse', 'gruposPracticaResponse', 'practicasResponse',
    '$filter','PaginationService', 'catedraResponse','$mdDialog',
    function($scope, $rootScope, $state, commons, message, service, materiasResponse, diasResponse,
             gruposPracticaResponse, practicasResponse, $filter, pagination, catedraResponse){

        $scope.catedra = catedraResponse.data;

        $scope.data = {
            disableFields: false,
            materias: materiasResponse.data,
            dias: commons.enumToJson(diasResponse.data),
            persistedOperation: $rootScope.persistedOperation || false,
            saved: false
        }

        $scope.tpData = {
            editing: false,
            result: [],
            filter: {},
            gruposPractica: gruposPracticaResponse.data,
            practicas: practicasResponse.data,
            select: {
                practicas: practicasResponse.data
            }
        }

        $scope.addTPs = function(show) {
            $scope.tpData.editing = show;
        }

        $scope.$watch('tpData.filter.grupoPracticaId', function (newValue, oldValue) {
            if (!angular.equals(newValue, oldValue)) {
                delete $scope.tpData.filter.practicaId;
                filterPracticas();
            }

            function filterPracticas() {

                if (!$scope.tpData.filter.grupoPracticaId || !angular.isDefined($scope.tpData.filter.grupoPracticaId)) {
                    $scope.tpData.select.practicas = $scope.tpData.practicas;
                } else {
                    $scope.tpData.select.practicas = $filter('filter')($scope.tpData.practicas, function (value) {
                        return angular.equals(value.grupo.id, $scope.tpData.filter.grupoPracticaId);
                    })
                }
            }
        })

        pagination.config('api/trabajoPractico/find');

        $scope.tpData.paginationData = pagination.paginationData;

        function executeQuery(pageNumber) {
            pagination.paginate($scope.tpData.filter, pageNumber).then(function (data) {
                $scope.tpData.result = data;
                for(var i = 0; i < $scope.tpData.result.length; i++) {
                    var indexInOtherList = findItemIndexInList($scope.tpData.result[i].id, $scope.catedra.trabajosPracticos);
                    if (indexInOtherList > -1 && !$scope.catedra.trabajosPracticos[indexInOtherList].deleted) {
                         $scope.tpData.result[i].added = true;
                    }
                }
                $scope.paginationData = pagination.getPaginationData();
            }, function () {
            });
        }

        $scope.tpData.consultar = function () {
            executeQuery();
        }

        $scope.tpData.cleanFilters = function() {
            $scope.tpData.filter = {};
        }

        $scope.tpData.nextPage = function () {
            if ($scope.paginationData.morePages) {
                executeQuery(++$scope.paginationData.pageNumber);
            }
        }
        $scope.tpData.previousPage = function () {
            if (!$scope.paginationData.firstPage) {
                executeQuery(--$scope.paginationData.pageNumber);
            }
        }

        $scope.isSomethingSelected = function(){
            return $filter('filter')($scope.tpData.result, function(item){
                return item.selected && !item.added;
            }).length;
        }

        $scope.addTrabajo = function(tp){
            if(tp.selected){
                $scope.catedra.trabajosPracticos.push(tp);
            }else{
                $scope.removeTrabajoPractico(tp.id,1);
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

        function findItemById(id, list){
            var tp = {};
            for(var i = 0; i < list.length; i++){
                if(list[i].id === id){
                    tp = list[i];
                    break;
                }
            }
            return tp;
        }

        $scope.removeTrabajoPractico = function(id,band){
            if(band === 1){
                var index = findItemIndexInList(id, $scope.catedra.trabajosPracticos);
                $scope.catedra.trabajosPracticos.splice(index,1);
            }else{
                var tp = findItemById(id,$scope.tpData.result);
                tp.selected = false;
            }

        };


        function newHour(hour,minutes) {
            var date = new Date();
            date.setYear(1970);
            date.setMonth(0);
            date.setDate(1);
            date.setHours(hour);
            date.setMinutes(minutes);
            date.setSeconds(0);
            date.setMilliseconds(0);
            return date;
        }

        $scope.newHorario = {
            horaDesde: newHour(8,0),
            horaHasta: newHour(18,0)
        };

        $scope.addHorario = function() {
            if (!$scope.newHorario.horaDesde || !$scope.newHorario.horaHasta) {
                message.showMessage('Debe seleccionar hora desde y hasta.');
                return;
            }
            var horaDesdeGTHoraHasta = $scope.newHorario.horaDesde >= $scope.newHorario.horaHasta;
            if (horaDesdeGTHoraHasta) {
                message.showMessage('La hora de iniciso debe ser menor a la de finalización.');
                return;
            }
            var isAlreadyAdded = $filter('filter')($scope.catedra.horarios, function(horario){
                return angular.equals(horario, $scope.newHorario);
            }).length;
            if (isAlreadyAdded){
                message.showMessage('Día y horario ya agregado.');
                return;
            }
            var overlappingHours = $filter('filter')($scope.catedra.horarios, function(horario){
                var sameDay = horario.dia === $scope.newHorario.dia;
                var overlappingHours = $scope.newHorario.horaDesde >= horario.horaDesde && $scope.newHorario.horaHasta <= horario.horaHasta;
                overlappingHours |=  $scope.newHorario.horaDesde < horario.horaDesde && $scope.newHorario.horaHasta > horario.horaDesde;
                overlappingHours |=  $scope.newHorario.horaDesde < horario.horaHasta && $scope.newHorario.horaHasta > horario.horaHasta;
                return sameDay && overlappingHours;
            }).length;
            if (overlappingHours) {
                message.showMessage('El horario elegido se encuentra contenido en uno ya agregado.');
                return;
            }
            $scope.catedra.horarios.push($scope.newHorario);
            message.showMessage('Horario agregado');
            $scope.newHorario = {
                horaDesde: newHour(8,0),
                horaHasta: newHour(18,0)
            };
        }

        $scope.deleteHorario = function(index) {
            $scope.catedra.horarios.splice(index, 1);
        }

        function validateRequireList(errors, list, name) {
            if (!list.length) {
                errors.push('Debe ingresar ' + name);
            }
        }

        $scope.save = function() {

            //Quitamos de la lista de trabajos prácticos aquellos que se eliminaron pero que ya estaban persistidos.
            $scope.catedra.trabajosPracticos = $filter('filter')($scope.catedra.trabajosPracticos, function (tp){
                return !tp.deleted;
            })

            $scope.tpData.editing = false;

            service.save($scope.catedra)
                .success(function(data) {
                    $scope.data.persistedOperation = true;
                    $scope.data.disableFields = true;
                    $scope.data.saved = true;
                    message.showMessage("La cátedra fue modificada con éxito");
                    $scope.goIndex();

                })
                .error(function (response) {
                    if (response.status === 1000) {
                    } else {
                        message.showMessage("Hubo un error al modificar la cátedra.")
                    }

                })
        }

        function getTimeFromDate(date) {
            var time = date.getHours() + ':';
            time += date.getMinutes();

            return time;
        }


        $scope.goIndex = function() {
            $state.go('^.index', {execQuery: $scope.data.persistedOperation});
        };

        $scope.reload = function() {
            $rootScope.persistedOperation = $scope.data.persistedOperation;
            $state.go($state.current, {}, {reload: true});
        };

        $scope.$on('$stateChangeStart',
            function (event, toState, toParams, fromState, fromParams) {
                if (!angular.equals($state.current, toState)) {
                    delete $rootScope.persistedOperation;
                }
            });

        $scope.mostrarFiltros = false;

        $scope.clickIcon = 'expand_more';
        $scope.clickIconMorph = function () {
            if ($scope.clickIcon === 'expand_more') {
                $scope.clickIcon = 'expand_less';
            }
            else {
                $scope.clickIcon = 'expand_more';
            }
        };

        $scope.colorIcon = ['#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF'];
        $scope.colorMouseOver = function (icon) {
            $scope.colorIcon[icon] = '#E91E63';
        };

        $scope.colorMouseLeave = function (icon) {
            $scope.colorIcon[icon] = '#00B0FF';
        };
    }]);

