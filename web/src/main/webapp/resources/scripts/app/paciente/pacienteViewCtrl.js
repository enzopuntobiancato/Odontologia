/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 29/12/15
 * Time: 12:04
 * To change this template use File | Settings | File Templates.
 */
var module = angular.module('pacienteModule');

module.controller('PacienteCtrl_View', ['$scope','$rootScope','PacienteSrv','$state',
    function ($scope, $rootScope, service, $state) {
        $scope.paciente = {
            apellido:           'Zárate',
            nombre:             'Mauro',
            nroDocumento:       '35938590',
            materia:            'Ortodoncia',
            trabajoPractico:    'Trabajo Practico 2',
            img: 'http://diariomovil.com.ar/wp-content/uploads/2014/10/mauro_zarate.jpg',
            nacionalidad: 'argentino',
            nacimiento: 'Córdoba',
            fechaNacimiento: '07/08/1990',
            calle: 'Río Negro',
            numero: '3126',
            piso: '1'      ,
            depto: 'B'      ,
            telefono: '4683729',
            celular: '351152600514',
            trabajo: 'Estudiante',
            obraSocial: 'DASPO',
            nroobraSocial: '13234',
            medicoCabecera: 'Juan Pérez',
            servicio: 'ER emergencias',
            religion: 'Protestante'   ,
            provinciaNacimiento: 'Còrdoba'
        };
        $scope.data = {
            provincias :[
                {nombre: 'Córdoba'},{nombre: 'Buenos Aires'},{nombre: 'Santa Fe'},{nombre: 'San Luis'}
            ],
            ciudades : [
                {nombre: 'Córdoba'},{nombre: 'Carlos Paz'},{nombre: 'Alta Gracia'},{nombre: 'La Falda'}
            ],
            barrios : [
                {nombre: 'Capital Sur'},{nombre: 'Alberdi'},{nombre: 'Centro'},{nombre: 'Barrio Jardìn'}
            ],
            estados : [
                {nombre: 'Soltero'},{nombre: 'Divorciado'},{nombre: 'Casado'},{nombre: 'Viudo'}
            ],
            tiposDocumento : [
                {nombre: 'DNI'},{nombre: 'Pasaporte'},{nombre: 'LC'}
            ],
            nivelesEstudio : [
                {nombre: 'Universitario'},{nombre: 'Terciario'},{nombre: 'Secundario'},{nombre: 'Primario'}
            ],
            disableFields: false,
            persistedOperation: $rootScope.persistedOperation || false,
            saved: false
        }


        $scope.goIndex = function () {
            $state.go('^.index', {execQuery: false, execQuerySamePage: $scope.data.persistedOperation});
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
    }]);