/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 22/12/15
 * Time: 13:53
 * To change this template use File | Settings | File Templates.
 */
var module = angular.module('pacienteModule');

module.controller('PacienteCtrl_Create', ['$scope', '$rootScope', 'PacienteSrv', '$state', 'MessageSrv',
    function ($scope, $rootScope, service, $state, message) {
//        ,provinciasResponse,ciudadesResponse,barriosResponse,estadoCivilResponse,tiposDocumentoResponse,nivelesEstudioResponse
//        ,'provinciasResponse','ciudadesResponse','barriosResponse','estadoCivilResponse','tiposDocumentoResponse','nivelesEstudioResponse'
        $scope.paciente={};
        $scope.data = {
            provincias : [{id:"1", nombre:"Córdoba"},{id:"2", nombre:"Santa Fe"},{id:"3", nombre:"Buenos Aires"}],
            ciudades : [{id:"1", nombre:"Córdoba"},
                {id:"2", nombre:"Santa Fe"},
                {id:"3", nombre:"Capital Federal"}],
            barrios : [{id:"1", nombre:"Capital Sur"},
                {id:"2", nombre:"Barrio Jardìn"},
                {id:"3", nombre:"Alberdi"}],
            estados : [{id:"1", nombre:"Solter"},
                {id:"2", nombre:"Casado"},
                {id:"3", nombre:"Viudo"}],
            tiposDocumento : [{id:"1", nombre:"Solter"},
                {id:"2", nombre:"Casado"},
                {id:"3", nombre:"Viudo"}],
            nivelesEstudio : [{id:"1", nombre:"Solter"},
                {id:"2", nombre:"Casado"},
                {id:"3", nombre:"Viudo"}]
        }
        var performSubmit = $scope.$parent.performSubmit;
        var handleError = $scope.$parent.handleError;

        //Guardar
        $scope.save = function(form){
            performSubmit(function(){
                service.save(paciente)
                    .success(function(data){
                        $scope.data.persistedOperation = true;
                        $scope.data.disableFields;
                        $scope.data.saved = true;
                        message.successMessage("Paciente " +$scope.paciente.apellido + ', ' + $scope.paciente.nombre + ' creado con éxito');
                        $scope.goIndex();
                    })
                    .error(function(data, status){
                        handleError(data, status);
                    })
            },form);
        }


        $scope.goIndex = function(){
            $state.go('^.index', {execQuery: $scope.data.persistedOperation});
        }

        $scope.reload = function(){
            $rootScope.persistedOperation = $scope.data.persistedOperation;
            $state.go($state.current, {}, {reload: true});
        }

        //Adicionales
        $scope.secciones = [true,false,false,false,false];
        $scope.tituloSeccion = 'Datos personales';
        $scope.clickIcon = 'keyboard_arrow_right';
        $scope.colorIcon = ['#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF'];

        $scope.mostrarSeccion = function(idSeccion, titulo){
           for(var ind=0; ind < 5; ind++){
               if(ind == idSeccion){
                   $scope.secciones[ind] = true;// true para la sección activa
               }else{
                   $scope.secciones[ind] = false;
               }
           }
            $scope.tituloSeccion = titulo;
        };

        $scope.colorMouseOver = function (icon) {
            $scope.colorIcon[icon] = '#E91E63';
        };

        $scope.colorMouseLeave = function (icon) {
            $scope.colorIcon[icon] = '#00B0FF';
        };

        $scope.clickIconMorph = function () {
            if ($scope.clickIcon === 'keyboard_arrow_right') {
                $scope.clickIcon = 'expand_less';
            }
            else {
                $scope.clickIcon = 'keyboard_arrow_right';
            }
        };

        $scope.$on('$stateChangeStart',
            function (event, toState, toParams, fromState, fromParams) {
                if (!angular.equals($state.current, toState)) {
                    delete $rootScope.persistedOperation;
                }
            })

    }]);
