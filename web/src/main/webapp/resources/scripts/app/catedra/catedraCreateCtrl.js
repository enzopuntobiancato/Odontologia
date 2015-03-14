var module = angular.module('catedraModule');

module.controller('CatedraCtrl_Create', ['$scope', '$state', 'CommonsSrv', 'NotificationSrv', 'CatedraSrv','materiaResponse', 'diasResponse', function($scope, $state,  commons, notification, service, materiaResponse, diasResponse) {

    $scope.data = {
        disableFields: false,
        materias: materiaResponse.data,
        dias: commons.enumToJson(diasResponse.data)
    }


    $scope.catedra = {};
    $scope.catedra.horarios = [];

    $scope.newHorario = {};

    $scope.addHorario = function() {
        $scope.catedra.horarios.push($scope.newHorario);
        $scope.newHorario = {};
    }

    $scope.save = function() {
        notification.showWidget();
        $scope.catedra.materia = getMateria($scope.catedra.materia);

        for (var i = 0; i < $scope.catedra.horarios.length; i++) {
            $scope.catedra.horarios[i].horaDesde = getTimeFromDate($scope.catedra.horarios[i].horaDesde);
            $scope.catedra.horarios[i].horaHasta = getTimeFromDate($scope.catedra.horarios[i].horaHasta);
        }

        service.create($scope.catedra).success(function(data) {
            notification.good('La operación se realizó con éxito', function() {
                $scope.data.disableFields = true;
                notification.hideWidget();
            })
        })
    }

    function getTimeFromDate(date) {
        var time = date.getHours() + ':';
        time += date.getMinutes();

        return time;
    }


    $scope.reload = function() {
        $state.go($state.current, {}, {reload: true});
    }

    function getMateria(id) {
        var result = {};
        for(var i = 0; i < $scope.data.materias.length; i++) {
            if ($scope.data.materias[i].id == id) {
                result = $scope.data.materias[i];
                break;
            }
        }
        return result;
    }
}])