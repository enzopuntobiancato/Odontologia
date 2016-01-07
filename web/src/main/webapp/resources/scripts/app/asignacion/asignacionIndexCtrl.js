var module = angular.module('asignacionModule');


module.controller('AsignacionCtrl_Index', ['$scope', '$cacheFactory', 'AsignacionSrv', '$state', '$stateParams', 'MessageSrv', 'CommonsSrv',  'PaginationService', '$mdDialog',
    function ($scope, $cacheFactory, service, $state, $stateParams, message, commons, pagination, $mdDialog) {
        $scope.result = [
            {
                id: '1',
                apellido:           'Suárez',
                nombre:             'Luis',
                nroDocumento:       '45267892',
                materia:            'Protodoncia',
                trabajoPractico:    'Trabajo Practico 1',
                img:                'http://laaficion.milenio.com/la_aficion_mundial/Suarez-mordida_de_Suarez-memes_de_Suarez-mordida_a_Chiellini_MILIMA20140624_0389_3.jpg'

            },
            {
                id: '2',
                apellido:           'Zárate',
                nombre:             'Mauro',
                nroDocumento:       '35938590',
                materia:            'Ortodoncia',
                trabajoPractico:    'Trabajo Practico 2',
                img: 'http://diariomovil.com.ar/wp-content/uploads/2014/10/mauro_zarate.jpg'

            },
            {
                id: '3',
                apellido:           'Zanetti',
                nombre:             'Pupi',
                nroDocumento:       '12267892',
                materia:            'Protodoncia',
                trabajoPractico:    'Trabajo Practico 1',
                img:                'http://www.cordobatimes.com/deportes/wp-content/uploads/2013/12/zanetti-mundial.jpg'
            },
            {
                id: '4',
                apellido:           'Crespo',
                nombre:             'Hernán',
                nroDocumento:       '45267892',
                materia:            'Protodoncia',
                trabajoPractico:    'Trabajo Practico 1',
                img:                'http://www.apurogol.net/wp-content/uploads/2008/11/hernan-crespo-real-madrid.jpg'
            },
            {
                id: '5',
                apellido:           'Batistuta',
                nombre:             'Gabriel Omar',
                nroDocumento:       '31267892',
                materia:            'Protodoncia',
                trabajoPractico:    'Obstrucción',
                img:                'http://image.tin247.com/bongdaso/100401192152-674-468.jpg'
            },
            {
                id: '6',
                apellido:           'Alonso',
                nombre:             'Beto',
                nroDocumento:       '32267892',
                materia:            'Cirugía',
                trabajoPractico:    'Extracción',
                img:                'http://www.rivermillonarios.com.ar/media/galeria/132/4/9/2/7/n_river_plate_idolos_millonarios_y_ex_river-1357294.jpg'
            }
        ];
        //pacientes
        $scope.filter = {};
        $scope.data = {
        };

        $scope.reload = function(){
            $rootScope.persistedOperation = $scope.data.persistedOperation;
            $state.go($state.current, {}, {reload: true});
        };



        //Consulta

        $scope.consultar = function () {
            // executeQuery();
        }

        $scope.keyboardOk = function (event) {
            if (event.which == 13) {
                //executeQuery();
            }
        };

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

        //Restaurar
        $scope.openRestoreDialog = function (ev, asignacionId) {
            $mdDialog.show({
                templateUrl: 'views/asignacion/asignacionRestore.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: true,
                locals: {id: asignacionId},
                controller: function DialogController($scope, $mdDialog) {
                    $scope.cancelar = function () {
                        $mdDialog.cancel();
                    };
                    $scope.confirmar = function () {
                        $mdDialog.hide();
                    };
                }
            })
                .then(function () {
                    //Success
                    service.restore(asignacionId)
                        .success(function (response) {
                            message.showMessage("Se ha dado de alta.");
                            //executeQuery($scope.paginationData.pageNumber);
                            Console.log(response);
                        })
                        .error(function (error) {
                            message.showMessage("Se ha registrado un error en la transacción.")
                            //executeQuery($scope.paginationData.pageNumber);
                            Console.log(error);
                        })
                },
                function () {
                    //Failure
                    $scope.status = 'You cancelled the dialog.';
                    Console.log(error);
                });
        };

        //Métodos auxiliares
        $scope.mostrarFiltros = false;
        $scope.clickIcon = 'expand_more';
        $scope.colorIcon = ['#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF', '#00B0FF'];

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

        $scope.clickIconMorph = function () {
            if ($scope.clickIcon === 'expand_more') {
                $scope.clickIcon = 'expand_less';
            }
            else {
                $scope.clickIcon = 'expand_more';
            }
        };

        $scope.cleanFilters = function () {
            $scope.filter = {};
            //executeQuery();
        };



    }]);



