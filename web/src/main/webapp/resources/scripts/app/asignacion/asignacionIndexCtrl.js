var module = angular.module('asignacionModule');


module.controller('AsignacionCtrl_Index', ['$scope', '$cacheFactory', 'AsignacionSrv', '$state', '$stateParams', 'MessageSrv', 'CommonsSrv',  'PaginationService', '$mdDialog',
    function ($scope, $cacheFactory, service, $state, $stateParams, message, commons, pagination, $mdDialog) {
        $scope.filterAlumno ={};
        $scope.filterAsignaciones ={};
        $scope.showSections = [true, false];
        $scope.resultAlumnos = [];
        $scope.resultAsignaciones = [];
        $scope.data = {
            materias :[
                {nombre: 'Prostodoncia'},{nombre: 'Ortodoncia'},{nombre: 'Cirugia'},{nombre: 'Endodoncia'}
            ],
            practicas : [
                {denominacion: 'TP 1 - Diagnostico'},{denominacion: 'TP 2 - Diagnostico 2'},{denominacion: 'TP 3 - Diagnostico 3'}
            ],
            estados:["Pendiente", "Reservado", "Solucionado", "Cancelado", "Solucionado en CE"]
        }

        $scope.reload = function(){
            $rootScope.persistedOperation = $scope.data.persistedOperation;
            $state.go($state.current, {}, {reload: true});
        };

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
        $scope.consultarAsignaciones = function(){
            $scope.resultAsignaciones = [
                {
                    id: '1',
                    paciente :{
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
                    materia:{
                        nombre: "Prostodoncia"
                    },
                    trabajoPractico:{
                        nombre: 'Obturación con amalgama'
                    },
                    fechaCreacion: '04/10/2015'
                },
                {
                    id: '2',
                    paciente :{
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
                    materia:{
                        nombre: "Prostodoncia"
                    },
                    trabajoPractico:{
                        nombre: 'Obturación con amalgama'
                    },
                    fechaCreacion: '04/10/2015'
                },
                {
                    id: '3',
                    paciente :{
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
                    materia:{
                        nombre: "Prostodoncia"
                    },
                    trabajoPractico:{
                        nombre: 'Obturación con amalgama'
                    },
                    fechaCreacion: '04/10/2015'

                },
                {
                    id: '4',
                    paciente :{
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
                    materia:{
                        nombre: "Prostodoncia"
                    },
                    trabajoPractico:{
                        nombre: 'Obturación con amalgama'
                    },
                    fechaCreacion: '04/10/2015'
                },
                {
                    id: '5',
                    paciente :{
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
                    materia:{
                        nombre: "Prostodoncia"
                    },
                    trabajoPractico:{
                        nombre: 'Obturación con amalgama'
                    },
                    fechaCreacion: '04/10/2015'
                },
                {
                    id: '6',
                    paciente :{
                        apellido:           'Alonso',
                        nombre:             'Beto',
                        telefonoFijo: '4672819',
                        celular: '152690514',
                        direccion: 'Río Negro 3126 (Capital Sur)',
                        nroDocumento:       '32267892',
                        materia:            'Cirugía',
                        trabajoPractico:    'Extracción',
                        img:                'http://www.rivermillonarios.com.ar/media/galeria/132/4/9/2/7/n_river_plate_idolos_millonarios_y_ex_river-1357294.jpg'
                    },
                    materia:{
                        nombre: "Prostodoncia"
                    },
                    trabajoPractico:{
                        nombre: 'Obturación con amalgama'
                    },
                    fechaCreacion: '04/10/2015'
                }
            ];
        };

        $scope.keyboardOkAlumno = function(){
            if (event.which == 13) {
                $scope.consultarAlumno();//executeQuery();
            }
        }

        $scope.keyboardOkAsignaciones = function(){
            if (event.which == 13) {
                $scope.consultarAsignaciones();//executeQuery();
            }

        }

        //Caché
        function cacheData() {
            var data = {
                filter: $scope.filter,
                result: $scope.result,
                aux: $scope.aux
            }
            cache.put('data', data);
        };

        function getCachedData() {
            var data = cache.get('data');

            $scope.filter = data.filter;
            $scope.result = data.result;
            $scope.aux = data.aux;
        };

        //Paginación
        $scope.nextPage = function () {
            if ($scope.paginationData.morePages) {
                //executeQuery(++$scope.paginationData.pageNumber);
            }
        };
        $scope.previousPage = function () {
            if (!$scope.paginationData.firstPage) {
                //executeQuery(--$scope.paginationData.pageNumber);
            }
        };


        //Cambio de estado
        $scope.new = function () {
            $state.go('^.create');
        };

        $scope.edit = function (asignacionId) {
            $state.go('^.edit', {id:asignacionId});

        };

        $scope.viewDetail = function (asignacionId) {
            $state.go('^.view', {id: asignacionId});
        };

        //Diálogos
        //Dar de baja
        $scope.openDeleteDialog = function (ev, asignacionId) {
            $mdDialog.show({
                templateUrl: 'views/asignacion/asignacionDelete.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: true,
                locals: {id: asignacionId},
                controller: function DialogController($scope, $mdDialog) {
                    $scope.motivoBaja;
                    $scope.cancelar = function () {
                        $mdDialog.cancel();
                    };
                    $scope.confirmar = function () {
                        $mdDialog.hide($scope.motivoBaja);
                    };
                }
            })
                .then(function (motivoBaja) {
                    //Success
                    service.remove(asignacionId, motivoBaja)
                        .success(function (response) {
                            message.showMessage("Se ha dado de baja.");
                            //executeQuery();
                            Console.log(response);
                        })
                        .error(function (error) {
                            message.showMessage("Se ha registrado un error en la transacción.")
                            Console.log(error);
                        })
                },
                function () {
                    //Failure
                    $scope.status = 'You cancelled the dialog.';
                    Console.log(error);
                });
        };

        //Seleccionar
        $scope.seleccionarAlumno = function(){
            $scope.showSections[0] = false;
            $scope.showSections[1] = true;
        }

        //Métodos auxiliares
        $scope.mostrarFiltros = false;
        $scope.clickIcon = 'expand_more';
        $scope.colorIcon = ['#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF','#00B0FF'];

        $scope.cleanFilters = function () {
            $scope.filter = {};
            //executeQuery();
        };

        $scope.mostrarAcciones = function (item) {
            item.showAction = true;
        };

        $scope.ocultarAcciones = function (item) {
            item.showAction = false;
        };

        $scope.colorMouseOver = function (icon) {
            $scope.colorIcon[icon] = '#E91E63';
        };

        $scope.colorMouseLeave = function (icon) {
            $scope.colorIcon[icon] = '#00B0FF';
        };

        $scope.cleanFiltersAlumno = function () {
            $scope.filterAlumno = {};
            //executeQuery();
        };

        $scope.cleanFiltersAsignaciones = function () {
            $scope.filterAsignaciones = {};
            //executeQuery();
        };



    }]);



