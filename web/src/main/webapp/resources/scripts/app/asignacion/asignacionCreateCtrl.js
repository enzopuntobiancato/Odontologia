    /**
     * Created with IntelliJ IDEA.
     * User: Usuario
     * Date: 4/01/16
     * Time: 19:07
     * To change this template use File | Settings | File Templates.
     */
    var module = angular.module('asignacionModule');

    module.controller('AsignacionCtrl_Create', ['$scope', '$rootScope', 'AsignacionSrv', '$state', 'MessageSrv','$mdDialog',
        function ($scope, $rootScope, service, $state, message, $mdDialog) {
            $scope.filter ={};
            $scope.resultAlumnos = [];
            $scope.resultPacientes = [];
            $scope.data = {
                catedras :[
                    {nombre: 'Prostodoncia A'},{nombre: 'Ortodoncia IIB'},{nombre: 'Cirugia A'},{nombre: 'Endodoncia C'}
                ],
                practicas : [
                    {denominacion: 'TP 1 - Diagnostico'},{denominacion: 'TP 2 - Diagnostico 2'},{denominacion: 'TP 3 - Diagnostico 3'}
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
                ]
            }
            $scope.showSections=[true,false,false];
            //Asignación a crer
            $scope.asignacion = {
            }

            //Consultar
            $scope.consultarAlumno = function(){
               $scope.resultAlumnos =[
                   {
                      apellido: 'Barros',
                      nombre: 'Maximiliano',telefonoFijo: '4672819',
                       celular: '152690514',
                       direccion: 'Río Negro 3126 (Capital Sur)',
                       celular: '152690514',
                      img: 'https://mir-s3-cdn-cf.behance.net/project_modules/disp/a2fa0522433737.5631e82d148c9.png'
                   },
                   {
                       apellido: 'Biancato',
                       nombre: 'Enzo',
                       nroDocumento: '34257187',
                       telefonoFijo: '4672819',
                       celular: '152690514',
                       direccion: 'Río Negro 3126 (Capital Sur)',
                       img: 'http://ih1.redbubble.net/image.15211113.6626/flat,800x800,070,f.jpg'
                   },
                   {
                       apellido: 'López Arzuaga',
                       nombre: 'Ignacio Javier',
                       nroDocumento: '35054738',
                       telefonoFijo: '4672819',
                       celular: '152690514',
                       direccion: 'Río Negro 3126 (Capital Sur)',
                       img: 'https://d13yacurqjgara.cloudfront.net/users/160522/screenshots/1341146/ww3.jpg'
                   },
                   {
                       apellido: 'García',
                       nombre: 'Mauro',
                       nroDocumento: '32960784',
                       telefonoFijo: '4672819',
                       celular: '152690514',
                       direccion: 'Río Negro 3126 (Capital Sur)',
                       img: 'https://mir-s3-cdn-cf.behance.net/project_modules/disp/3c3ad823775971.5632898dbfbfa.png'
                   },
                   {
                       apellido: 'Spessot',
                       nombre: 'Alexis',
                       nroDocumento: '38672901',
                       telefonoFijo: '4672819',
                       celular: '152690514',
                       direccion: 'Río Negro 3126 (Capital Sur)',
                       img: 'https://d13yacurqjgara.cloudfront.net/users/160522/screenshots/1149608/dribbble.jpg'
                   }
               ]
            };
            $scope.consultarPaciente = function(){
                $scope.resultPacientes = [
                    {
                    id: '1',
                    apellido:           'Suárez',
                    nombre:             'Luis',
                    telefonoFijo: '4672819',
                    celular: '152690514',
                    direccion: 'Río Negro 3126 (Capital Sur)',
                    nroDocumento:       '45267892',
                    materia:            'Protodoncia',
                    trabajoPractico:    'Trabajo Practico 1',
                    img:                'http://laaficion.milenio.com/la_aficion_mundial/Suarez-mordida_de_Suarez-memes_de_Suarez-mordida_a_Chiellini_MILIMA20140624_0389_3.jpg'

                },
                {
                    id: '2',
                    apellido:           'Zárate',
                    nombre:             'Mauro',
                    telefonoFijo: '4672819',
                    celular: '152690514',
                    direccion: 'Río Negro 3126 (Capital Sur)',
                    nroDocumento:       '35938590',
                    materia:            'Ortodoncia',
                    trabajoPractico:    'Trabajo Practico 2',
                    img: 'http://diariomovil.com.ar/wp-content/uploads/2014/10/mauro_zarate.jpg'

                },
                {
                    id: '3',
                        apellido:           'Zanetti',
                    nombre:             'Pupi',
                    telefonoFijo: '4672819',
                    celular: '152690514',
                    direccion: 'Río Negro 3126 (Capital Sur)',
                    nroDocumento:       '12267892',
                    materia:            'Protodoncia',
                    trabajoPractico:    'Trabajo Practico 1',
                    img:                'http://www.cordobatimes.com/deportes/wp-content/uploads/2013/12/zanetti-mundial.jpg'
                },
                {
                    id: '4',
                        apellido:           'Crespo',
                    nombre:             'Hernán',
                    telefonoFijo: '4672819',
                    celular: '152690514',
                    direccion: 'Río Negro 3126 (Capital Sur)',
                    nroDocumento:       '45267892',
                    materia:            'Protodoncia',
                    trabajoPractico:    'Trabajo Practico 1',
                    img:                'http://www.apurogol.net/wp-content/uploads/2008/11/hernan-crespo-real-madrid.jpg'
                },
                {
                    id: '5',
                    apellido:           'Batistuta',
                    nombre:             'Gabriel Omar',
                    telefonoFijo: '4672819',
                    celular: '152690514',
                    direccion: 'Río Negro 3126 (Capital Sur)',
                    nroDocumento:       '31267892',
                    materia:            'Protodoncia',
                    trabajoPractico:    'Obstrucción',
                    img:                'http://image.tin247.com/bongdaso/100401192152-674-468.jpg'
                },
                {
                    id: '6',
                    apellido:           'Alonso',
                    nombre:             'Beto',
                    telefonoFijo: '4672819',
                    celular: '152690514',
                    direccion: 'Río Negro 3126 (Capital Sur)',
                    nroDocumento:       '32267892',
                    materia:            'Cirugía',
                    trabajoPractico:    'Extracción',
                    img:                'http://www.rivermillonarios.com.ar/media/galeria/132/4/9/2/7/n_river_plate_idolos_millonarios_y_ex_river-1357294.jpg'
                }
              ];
            };

            $scope.keyboardOkAlumno = function(){
                if (event.which == 13) {
                    $scope.consultarAlumno();//executeQuery();
                }
            }

            $scope.keyboardOkPacientes = function(){
                if (event.which == 13) {
                    $scope.consultarPaciente();//executeQuery();
                }

            }

            $scope.consultarMateria = function(){

            };

            //Seleccionar
            $scope.seleccionarAlumno = function(alumno){
                $scope.asignacion.alumno = alumno;
                $scope.showSections[0] = false;
                $scope.showSections[1] = true;
            }

            $scope.seleccionarPaciente = function(paciente){
                $scope.asignacion.paciente = paciente;
                $scope.showSections[1] = false;
                $scope.showSections[2] = true;
            }

            $scope.editarAlumno = function(){
                $scope.showSections[0] = true;
                $scope.showSections[1] = false;
                $scope.showSections[2] = false;
            }

            $scope.editarPaciente = function(){
                $scope.showSections[0] = false;
                $scope.showSections[1] = true;
                $scope.showSections[2] = false;
            }

            $scope.editarFecha = function(){
                $scope.showSections[0] = false;
                $scope.showSections[1] = false;
                $scope.showSections[2] = true;
            }
            //Guardar
            $scope.save = function(){
            }

            $scope.goIndex = function(){
                $state.go('^.index', {execQuery: $scope.data.persistedOperation});
            }

            $scope.reload = function(){
                $rootScope.persistedOperation = $scope.data.persistedOperation;
                $state.go($state.current, {}, {reload: true});
            }

            //Diálogos

            $scope.openConfirmDialog = function() {
                $mdDialog.show({
                    clickOutsideToClose: true,
                    scope: $scope,        // use parent scope in template
                    preserveScope: true,  // do not forget this if use parent scope
                    templateUrl: 'views/asignacion/asignacionConfirmarCreacion.html',
                    controller: function DialogController($scope, $mdDialog) {
                        $scope.cancelar = function () {
                            $mdDialog.cancel();
                        };
                        $scope.confirmar = function () {
                            $mdDialog.hide();
                        };
                    }
                });
            };

            //Adicionales
            $scope.clickIcon = 'keyboard_arrow_right';
            $scope.colorIcon = ['#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF'];

            $scope.mostrarAcciones = function (item, icon) {
                item.showAction = true;

            }

            $scope.ocultarAcciones = function (item, icon) {
                item.showAction = false;

            }

            $scope.colorMouseOver = function (icon) {
                $scope.colorIcon[icon] = '#E91E63';
            };

            $scope.colorMouseLeave = function (icon) {
                $scope.colorIcon[icon] = '#00B0FF';
            };

            $scope.cleanFiltersAlumno = function(){
              $scope.filterAlumno = {};
              $scope.resultAlumnos = [];
            };

            $scope.cleanFiltersPaciente = function(){
                $scope.filterPaciente = {};
                $scope.resultPacientes = [];
            };

            $scope.$on('$stateChangeStart',
                function (event, toState, toParams, fromState, fromParams) {
                    if (!angular.equals($state.current, toState)) {
                        delete $rootScope.persistedOperation;
                    }
                })

        }]);