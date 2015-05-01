var module = angular.module('materiaModule');


module.controller('MateriaCtrl_Index', ['$scope', '$document', 'MateriaSrv', '$state', 'NotificationSrv', 'CommonsSrv', 'nivelesResponse', function ($scope, $document, service, $state, notification, commons, nivelesResponse) {

    $scope.filter = {};
    $scope.result = [];
    $scope.niveles = commons.enumToJson(nivelesResponse.data);

    $scope.aux = {
        showDadosBaja: false
    }

    $scope.consultar = function () {
        notification.showWidget();
        service.find($scope.filter.nombre, $scope.filter.nivel, $scope.filter.dadosBaja).success(function (data) {
            notification.hideWidget();
            $scope.result = data;
            $scope.aux.showDadosBaja = $scope.filter.dadosBaja;
        })
    }

    $scope.new = function () {
        $state.go('^.create');
    }

    $scope.darDeBaja = function (materiaId) {
        notification.requestReason().then(function (motivo) {
            if (motivo != null) {
                notification.showWidget();
                service.remove(materiaId, motivo).success(function (response) {
                    notification.hideWidget();
                    notification.goodAndOnEscape("Materia dada de baja correctamente.", function(){$scope.consultar()}, function(){$scope.consultar()})
                })
                    .error(function (response) {
                        notification.hideWidget();
                    })
            }
        });

        $scope.darDeAlta = function (materiaId) {
             notification.requestConfirmation("¿Está seguro?", function(){altaConfirmada(materiaId)});
        }

        function altaConfirmada(materiaId) {
            notification.showWidget();
            service.restore(materiaId)
                .success(function () {
                    notification.hideWidget();
                    notification.goodAndOnEscape("Materia dada de alta correctamente.", function (){$scope.consultar()}, function(){$scope.consultar()})
                })
                .error(function () {
                    notification.hideWidget();
                })
        }

//
//        bootbox.dialog({
//            message: '<textarea class="form-control" rows="3" name="motivoBaja"></textarea>' + $scope.motivoBaja,
//            title: 'Ingrese el motivo de baja',
//            buttons: {
//                aceptar: {
//                    label: "Aceptar",
//                    className: "btn-primary",
//                    callback: function() {
//                        notification.showWidget();
//                        $document.find
//                        service.remove(materiaId, $scope.motivoBaja).success(function(response){
//                            notification.hideWidget();
//                            notification.goodAndOnEscape("Materia dada de baja correctamente.", $scope.consultar(), $scope.consultar())
//                        })
//                            .error(function(response){
//                                notification.hideWidget();
//                            })
//                    }
//                },
//                cancelar: {
//                    label: "Cancelar",
//                    className: "btn-default",
//                    callback: function() {
//                    }
//                }
//            }
//        });
    }

    $scope.cleanFilters = function () {
        $scope.filter = {};
    }

}]);
