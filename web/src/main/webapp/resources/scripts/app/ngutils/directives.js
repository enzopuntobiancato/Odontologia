var directives = angular.module('sapo.directives', []);

directives.directive('sameAs', function () {
    return {
        require: 'ngModel',
        link: function (scope, elem, attrs, ngModel) {
            ngModel.$parsers.unshift(validate);
            // Force-trigger the parsing pipeline.
            scope.$watch(attrs.sameAs, function () {
                ngModel.$setViewValue(ngModel.$viewValue);
            });

            function validate(value) {
                var isValid = scope.$eval(attrs.sameAs) == value;

                ngModel.$setValidity('sameAs', isValid);

                return isValid ? value : undefined;
            }
        }
    };
});

directives.directive('showFocus', function ($timeout) {
    return function (scope, element, attrs) {
        scope.$watch(attrs.showFocus,
            function (newValue) {
                $timeout(function () {
                    newValue && element.focus() && element.select();
                });
            }, true);
    };
});

directives.directive('pwCheck', [function () {
    return {
        require: 'ngModel',
        link: function (scope, elem, attrs, ctrl) {
            var firstPassword = '#' + attrs.pwCheck;
            elem.add(firstPassword).on('keyup', function () {
                scope.$apply(function () {
                    var v = elem.val() === $(firstPassword).val();
                    ctrl.$setValidity('pwmatch', v);
                });
            });
        }
    }
}]);

directives.directive('validateLength', function () {
    return {
        require: 'ngModel',
        link: function (scope, element, attrs, ngModel) {

            scope.$watch(function () {
                return ngModel.$modelValue && ngModel.$modelValue.length;
            }, function () {
                ngModel.$validate(); // validate again when array changes
            });

            ngModel.$validators.length = function () {
                var arr = ngModel.$modelValue;
                if (!arr) {
                    return false;
                }

                return arr.length > 0;
            };

        }
    };
});

// CUSTOM INPUT DIRECTIVES
var templateInput =
    '<md-input-container flex> ' +
        '<label> {{ ctrl.label }} </label>' +
        '<input name="{{ ctrl.name }}" ng-model="ctrl.model"  ng-required="{{ ctrl.req }}" md-maxlength="{{ ctrl.max }}" ng-pattern="ctrl.pat" minlength="{{ ctrl.min }}" ng-disabled="{{ ctrl.disabled }}" type="{{ ctrl.type }}" aria-label="{{ ctrl.label }}">' +
        '<div ng-messages="ctrl.form.$error" ng-show="ctrl.form.$touched ||  ctrl.submitted  && ctrl.form.$invalid"> ' +
        '<div ng-messages-include="error-messages"></div>' +
        '</div> ' +
        '</md-input-container>';
var inputController = function () {
    var ctrl = this;

    function init() {
        if (ctrl.pattern != 'undefined') {
            ctrl.pat = new RegExp(ctrl.pattern);
        }
    }

    init();
};

var directiveInput = function () {
    return {
        restrict: 'E',
        template: templateInput,
        scope: {
            label: '@',
            name: '@',
            model: '=',
            req: '@',
            max: '@',
            min: '@',
            pattern: '@',
            disabled: '@',
            form: '=',
            submitted: '=',
            type: '@'
        },
        controller: inputController,
        controllerAs: 'ctrl',
        bindToController: true
    };
};

var templateDetalleHC = "<md-list-item class='md-3-line md-long-text' flex-gt-sm>" +
    "<div class='md-list-item-text compact'>" +
    "<div layout='row'> <h4 style='font-weight: bold'> {{ camposino.nombre }}</h4> <h4>{{ camposino.siNo | siNo}} </h4> </div>" +
    "<h4 ng-if='camposino.siNo == true && campodetalle != null'> {{ campodetalle.nombre }} {{ campodetalle.only_detalle }}</h4>" +
    "<p ng-if='camposino.siNo == true && campofecha != null'>{{ campofecha.nombre }} {{ campofecha.fecha |  date:'MM/dd/yyyy' }}</p>" +
    "</div>" +
    "</md-list-item>";
var detalle = function () {
    return {
        restrict: 'E',
//        template: templateDetalleHC,
        templateUrl: 'views/templateDetalleHC.html',
        replace: true,
        scope: {
            camposino: "=",
            campodetalle: "=",
            campofecha: "=",
            exp: "@"
        }
    };
};

var errorMessages = function () {
    return{
        restrict: 'AE',
        template: '<div class="validation-messages" ng-messages="form.$error" ng-if="form.$touched ||  submitted  && form.$invalid" multiple> ' +
            '<div ng-messages-include="error-messages"></div>' +
            '</div> ',
        scope: {
            form: '=',
            submitted: '='
        }
    }
};

var controllerEditHC = function ($scope) {
    var vm = this;
    vm.today = new Date();
    function limpiarCampos(id) {
        $scope.limpiarCampos(id);
    }

    vm.limpiarCampos = limpiarCampos;
};

var templateEditHC =
    '<div layout-gt-sm="row" layout-margin flex-gt-sm>' +
        '<md-switch ng-model="vm.camposino.siNo" aria-label="vm.camposino.nombre" ng-true-value="true" ng-false-value="false" class="md-primary" ng-change="vm.change({id : vm.camposino.id})"> {{vm.camposino.nombre }}: {{vm.camposino.siNo | siNo}} </md-switch>' +
        '</div>' +
        '<div layout-gt-sm="row" layout-margin flex-gt-sm>' +
        '<md-input-container flex-gt-sm ng-if="vm.campodetalle && vm.camposino.siNo == true" ng-form="formDetalle">' +
        '<label> {{ vm.campodetalle.nombre }} </label>' +
        '<input name="detalle" ng-model="vm.campodetalle.only_detalle" md-maxlength="75" ng-required="vm.camposino.siNo == true" aria-label="{{vm.campodetalle.nombre }}">' +
        '<error-messages form="formDetalle.detalle" submitted="vm.submitted"></error-messages>' +
        '</md-input-container>' +
        '<md-input-container flex  ng-if="vm.campofecha && vm.camposino.siNo == true" ng-form="formDetalle" layout-margin>' +
        '<label>{{ vm.campofecha.nombre }}</label>' +
        '<md-datepicker name="fecha" ng-model="vm.campofecha.fecha" ng-required="vm.camposino.siNo == true"></md-datepicker>' +
        '<error-messages form="formDetalle.fecha" submitted="vm.submitted"></error-messages>' +
        '</md-input-container>' +
        '<div ng-transclude layout-gt-sm="row" layout-margin flex-gt-sm></div> ' +
        '</div>';

var editHC = function () {
    return {
        restrict: 'E',
        scope: {
            camposino: '=',
            campodetalle: '=',
            campofecha: '=',
            form: '=',
            submitted: '=',
            change: '&'
        },
//        template: templateEditHC,
        templateUrl: 'views/templateEditHC.html',
        controller: controllerEditHC,
        controllerAs: 'vm',
        bindToController: true,
        transclude: true
    }
};

var toolbar = '<md-toolbar class="md-accent">' +
    '<div class="md-toolbar-tools">' +
    '<img ng-src="resources/img/image_user.jpg" class="md-avatar-login " hide-xs ng-click="ctrl.showCard($event, ctrl.paciente)"/>' +
    '<h2>' +
    '<span>{{ ctrl.paciente.apellido }}</span> ' +
    '</h2>' +
    '<span flex></span>' +
    '<md-button class="md-icon-button" aria-label="Editar paciente" ng-click="ctrl.edit(ctrl.paciente.id)">' +
    '<ng-md-icon icon="edit" size="24" style="fill: white">' +
    '<md-tooltip> Editar paciente </md-tooltip>' +
    '</ng-md-icon>' +
    '</md-button>' +
    '</div>' +
    '</md-toolbar>'
var pacienteToolbar = function () {
    return {
        restrict: 'E',
        template: toolbar,
        scope: {
            paciente: '='
        },
        controller: pacienteToolbarCtrl,
        controllerAs: 'ctrl',
        bindToController: true,
        replace: true
    }
}

function pacienteToolbarCtrl($mdDialog) {
    var ctrl = this;

    ctrl.showCard = showCard;
    ctrl.edit = edit;

    function edit(pacienteId) {
        $state.go('paciente.edit', {id: pacienteId});
    }

    function showCard(event, paciente) {
        $mdDialog.show({
                templateUrl: 'views/historiaClinica/historiaClinicaCard.html',
                parent: angular.element(document.body),
                locals: {
                    pacienteData: paciente},
                targetEvent: event,
                clickOutsideToClose: true,
                controller: function DialogController($scope, $mdDialog, $state, pacienteData) {
                    $scope.paciente = pacienteData;
                    $scope.goHistoriaClinica = goHistoriaClinica;
                    $scope.goDatosPersonales = goDatosPersonales;
                    $scope.close = close;

                    function close() {
                        $mdDialog.hide();
                    }

                    function goDatosPersonales(pacienteId) {
                        $state.go('paciente.view', {id: pacienteId});
                        close();
                    }

                    function goHistoriaClinica(pacienteId) {
                        $state.go('historiaClinica.view', {id: pacienteId});
                        close();
                    }
                }
            }
        );
    }
}

directives.directive('validatedInput', directiveInput);
directives.directive('errorMessages', errorMessages);
directives.directive('detalle', detalle);
directives.directive('itemHistoria', editHC);
directives.directive('pacienteToolbar', pacienteToolbar);

/* DIRECTIVES ODONTOGRAMA */
directives.directive('diente', ['MessageSrv', function (message) {
    return {
        restrict: 'A',
        scope: {
            model: '=',
            selectedTratamiento: '=',
            addHallazgo: '&',
            removeHallazgo: '&'
        },
        templateUrl: 'resources/img/diente_3 - copia.svg',
        link: function (scope, element, attrs) {
            (function init() {
                pintarDiente(scope.model);
            })();
            scope.dienteClick = dienteClick;
            scope.removeArreglo = removeArreglo;
            scope.model = scope.model;
            scope.agregar = true;

            //FUNCIONALIDAD
            function dienteClick(idCara) {
                //VERIFICACIONES
                if (angular.isUndefined(scope.selectedTratamiento) || scope.selectedTratamiento == null) {
                    message.errorMessage("Debe elegir un tratamiento.");
                    return false;
                }
                if (idCara == 6 && scope.selectedTratamiento.aplicaACara) {
                    message.errorMessage("No se puede aplicar el tratamiento " + scope.selectedTratamiento.nombre + " al diente completo, " +
                        "pues solo se puede aplicar a una cara.");
                    return false;
                }
                if (idCara !== 6 && scope.selectedTratamiento.aplicaAPieza) {
                    message.errorMessage("No se puede aplicar el tratamiento " + scope.selectedTratamiento.nombre + " a la cara, " +
                        "pues solo se puede aplicar al diente completo.");
                    return false;
                }
                if (idCara != 5 && scope.selectedTratamiento.aplicaACaraCentral) {
                    message.errorMessage("El tratamiento " + scope.selectedTratamiento.nombre + " solo puede aplicarse a la cara central.");
                    return false;
                }

                //Valor booleano TRVE si aplica a cara, FALSE si aplica a la pieza dental entera
                var aplicaACara = scope.selectedTratamiento.aplicaACara;
                if (aplicaACara) {
                    //Revisamos si el diente ya tiene un hallazgo.
                    if (scope.model.hallazgoClinico) {
                        message.errorMessage("El diente seleccionado tiene un tratamiento. Elimínelo antes de agregar uno nuevo.");
                        return false;
                    }
                    //Revisamos si la cara seleccionada ya tiene un hallazgo.
                    var index = findCaraByPosicion(scope.model.carasPiezaDental, idCara);
                    if (index == null) {
                        alert("La cara buscada no existe en el diente");
                        return false;
                    }
                    var cara = scope.model.carasPiezaDental[index];
                    if (cara.hallazgoClinico) {
                        message.errorMessage("La cara seleccionada ya posee un hallazgo. Elimínelo antes de agregar uno nuevo");
                        return false;
                    }
                } else {
                    //Aplica al diente entero. No puede haber otro arreglo sobre el diente o sobre las caras.
                    if (scope.model.hallazgoClinico) {
                        message.errorMessage("El diente ya posee un hallazgo. Elimínelo antes de agregar uno nuevo.");
                        return false;
                    }
                    //Revisamos que no haya algun arreglo en alguna de las caras.
                    for (var i = 0; i < scope.model.carasPiezaDental.length; i++) {
                        if (scope.model.carasPiezaDental[i].hallazgoClinico) {
                            message.errorMessage("El diente ya posee un hallazgo en la cara " + scope.model.carasPiezaDental[i].nombre
                                + ". Elimínelo antes de agregar uno nuevo.");
                            return false;
                        }
                    }
                }

                //CON LA DATA LIMPIA, PASAMOS A AGREGAR EL TRATAMIENTO AL DIENTE O CARA CORRESPONDIENTE
                var index = null;
                var arreglo = angular.copy(scope.selectedTratamiento);
                if (aplicaACara) {
                    index = findCaraByPosicion(scope.model.carasPiezaDental, idCara);
                    if (index == null) {
                        alert("La cara buscada no existe en el diente");
                        return false;
                    }
                    scope.model.carasPiezaDental[index].hallazgoClinico = arreglo;
                } else {
                    index = 6;
                    scope.model.hallazgoClinico = arreglo;
                }
                //Enviamos el evento al controlador del odontograma para que lo agregue a la HC.
                scope.addHallazgo()(scope.model);
                //Pintamos el diente.
                pintarDiente(scope.model);
                message.successMessage("Se ha agregado " + scope.selectedTratamiento.nombre + " al diente "
                    + scope.model.numeroSector + scope.model.numeroPieza);

            }

            function removeArreglo(idCara) {
                if (scope.model.hasDiagnostico || scope.model.diagnosticoId != null) {
                    message.errorMessage("No se puede eliminar el arreglo del diente  " + scope.model.nombrePiezaDental +
                        " pues tiene un diagnóstico asociado. Elimine el diagnóstico primero.");
                    return false;
                }
                if (!idCara) {
                    alert("Id de cara vacío.")
                    return false;
                }
                var esRealizado;
                //Si es el ID del diente completo, se borran todos los tratamientos.
                if (idCara == 6) {
                    esRealizado = scope.model.hallazgoClinico.estado == "REALIZADO" ? true : false;
                    if (!scope.model.hallazgoClinico) {
                        console.log("El diente no tiene arreglos registrados.");
                        return false;
                    }
                    scope.model.hallazgoClinico = null;

                    for (var i = 0; i < scope.model.carasPiezaDental.length; i++) {
                        scope.model.carasPiezaDental[i].hallazgoClinico = null;
                        console.log("Tratamiento eliminado de la cara " + scope.model.carasPiezaDental[i].nombre
                            + " diente " + scope.model.numeroSector + scope.model.numeroPieza);
                    }
                    //Enviamos el evento al controlador del odontograma para que lo agregue a la HC.
                    scope.removeHallazgo()(scope.model, esRealizado);
//                    scope.removeHallazgo()(scope.model, esRealizado);
                    message.successMessage("Tratamiento eliminado del diente " + scope.model.numeroSector + scope.model.numeroPieza);
                    //Pintamos el diente.
                    pintarDiente(scope.model);
                    return true;
                }

                //Si se hace click sobre una cara, buscamos la cara.
                var indexCara = findCaraByPosicion(scope.model.carasPiezaDental, idCara);
                if (!scope.model.carasPiezaDental[indexCara].hallazgoClinico) {
                    message.errorMessage("La cara " + idCara + " no tiene arreglos.");
                    return false;
                }
                esRealizado = scope.model.carasPiezaDental[indexCara].hallazgoClinico.estado == "REALIZADO" ? true : false;
                //Quitamos el tratamiento
                scope.model.carasPiezaDental[indexCara].hallazgoClinico = null;
                //Enviamos el evento al controlador del odontograma para que lo agregue a la HC.
//                scope.$emit('newHallazgo', {newPieza: scope.model});
                scope.removeHallazgo()(scope.model, esRealizado);
                if (scope.agregar) {
                    message.successMessage("Tratamiento eliminado de la cara " + scope.model.carasPiezaDental[indexCara].nombre
                        + " diente " + scope.model.numeroSector + scope.model.numeroPieza);
                    //Pintamos el diente.
                    pintarDiente(scope.model);
                }
            }

            function pintarDiente(diente) {
                if (!diente) {
                    return false;
                }

                resetNodos(element);
                var nodoPiezaCompleta = element[0].children[0].children[0]; //Pieza completa
                var nodoDiente = element[0].children[0].children[0].children[0];
                var nodosCaras = element[0].children[0].children[0].children[0].children; //Caras

                //Existe un hallazgo en el diente entero por lo que los demas posibles hallazgos no importan
                if (diente.hallazgoClinico) {
                    var nodoEncontrado = null;
                    if (diente.hallazgoClinico.markID == "tratamientoConducto"
                        || diente.hallazgoClinico.markID == "puente") {
                        var sup = diente.numeroSector < 3 ? "Superior" : "Inferior";
                        var nombre = diente.hallazgoClinico.markID + sup;
                        nodoEncontrado = findHallazgoEnNodoGrupo(nodoPiezaCompleta, nombre);
                    } else {
                        nodoEncontrado = findHallazgoEnNodoGrupo(nodoDiente, diente.hallazgoClinico.markID);
                    }
                    if (!nodoEncontrado) {
                        //No se encontro --> error!
                        console.log("No se encoinntro el nodo SVG para el diente " + diente);
                        return false;
                    }
                    var color = null;
                    if (diente.hallazgoClinico.estado == 'PENDIENTE') {
                        color = "#3F51B5";
                    } else {
                        color = "#F44336";
                    }
                    //Pintamos el nodo encontrado.
                    nodoEncontrado.attr('stroke', color);
                    nodoEncontrado.attr('opacity', '1');
                    if (diente.hallazgoClinico.markID != "corona") {
                        nodoEncontrado.attr('fill', color);
                    }
                    return true;
                }
                //Buscarmos cada cara del diente.
                var caras = diente.carasPiezaDental;
                for (var i = 0; i < caras.length; i++) {
                    var cara = caras[i];
                    if (!cara.hallazgoClinico) {
                        continue;
                    }
                    //Para esa cara busco el nodo SVG correspondiente
                    var nodoCara = null;
                    for (var j = 0; j < nodosCaras.length; j++) {
                        var n = nodosCaras[j];
                        if (n.id == cara.posicion) {
                            nodoCara = n;
                            break;
                        }
                    }

                    if (!nodoCara) {
                        console.log("Error! No se encontró la cara en el diente " + cara);
                        return false;
                    }
                    var nodoEncontrado = findHallazgoEnNodoGrupo(nodoCara, cara.hallazgoClinico.markID);
                    if (!nodoEncontrado) {
                        //No se encontro --> error!
                        console.log("No se encontró la marca correspondiente al tratamiento " + cara.hallazgoClinico);
                        return false;
                    }

                    var color = null;
                    if (cara.hallazgoClinico.estado == 'PENDIENTE') {
                        color = "#3F51B5";
                    } else {
                        color = "#F44336";
                    }
                    //Pintamos el nodo encontrado.
                    nodoEncontrado.attr('opacity', '1');
                    nodoEncontrado.attr('fill', color);
                }
            }

            /**
             * Metodo que busca entre los hijos de un grupo el nodo del mismo que se corresponde al hallazgo de la cara
             * pasado por parametro.
             * @param elementoPadre Nodo SVG
             * @param hallazgoPorBuscar HallazgoClinico de la cara
             * @returns nodoEncontrado o null si no lo encuentra.
             */
            function findHallazgoEnNodoGrupo(elementoPadre, hallazgoPorBuscar) {
                //Recuperamos los hijos, es decir todos los nodos interiores donde vamos a buscar la marca del tratamiento.
                var nodosHijos = elementoPadre.children;
                var nodoEncontrado = null;
                //Iteramos y buscamos en los nodos hijos la marca.
                for (var i = 0; i < nodosHijos.length; i++) {
                    //Busco en el grupo, el nodo que simboliza la marca.
                    if (nodosHijos[i].id == hallazgoPorBuscar) {
                        //Encuentro el nodo y salgo
                        nodoEncontrado = angular.element(nodosHijos[i]);
                        break;
                    }
                }
                return nodoEncontrado;
            }

            /**
             * Metodo que buscar en un array de caras la cara correspondiente a la posicion pasada por parametro.
             * @param arrayCaras
             * @param posicion
             * @returns {*}
             */
            function findCaraByPosicion(arrayCaras, posicion) {
                for (var index = 0; index < arrayCaras.length; index++) {
                    if (arrayCaras[index].posicion == posicion) {
                        return index;
                    }
                }
            }

            /**
             * Metodo que limpia todas las marcas del diente.
             * @param element
             */
            function resetNodos(element) {
                var nodos = element[0].querySelectorAll('.marca');
                angular.forEach(nodos, function (path, key) {
                    var nodoElement = angular.element(path);
                    nodoElement.attr("opacity", 0);
                })
            }

            scope.$watch('model', function (newData, oldData) {
                pintarDiente(scope.model);
            });

            scope.$on('deleted-hallazgo', function (event, args) {
                event.defaultPrevented = true;
                var piezas = args.piezas;
                for (var i = 0; i < piezas.length; i++) {
                    if (piezas[i] != scope.model.nombrePiezaDental) {
                        continue;
                    }
                    pintarDiente(scope.model);
                    break;
                }
            });
        }
    }
}]);

directives.directive('ngRightClick', function ($parse) {
    return function (scope, element, attrs) {
        var fn = $parse(attrs.ngRightClick);
        element.bind('contextmenu', function (event) {
            scope.$apply(function () {
                event.preventDefault();
                fn(scope, {$event: event});
            });
        });
    };
});

directives.directive('dienteConsulta', ['MessageSrv', function (message) {
    return {
        restrict: 'A',
        scope: {
            model: '=',
            selectedTratamiento: '='
        },
        templateUrl: 'resources/img/diente-consulta.svg',
        link: function (scope, element, attrs) {
            (function init() {
                pintarDiente(scope.model);
            })();
            scope.model = scope.model;

            //FUNCIONALIDAD
            function pintarDiente(diente) {
                if (!diente) {
                    return false;
                }

                resetNodos(element);
                var nodoPiezaCompleta = element[0].children[0].children[0]; //Pieza completa
                var nodoDiente = element[0].children[0].children[0].children[0];
                var nodosCaras = element[0].children[0].children[0].children[0].children; //Caras

                //Existe un hallazgo en el diente entero por lo que los demas posibles hallazgos no importan
                if (diente.hallazgoClinico) {
                    var nodoEncontrado = null;
                    if (diente.hallazgoClinico.markID == "tratamientoConducto"
                        || diente.hallazgoClinico.markID == "puente") {
                        var sup = diente.numeroSector < 3 ? "Superior" : "Inferior";
                        var nombre = diente.hallazgoClinico.markID + sup;
                        nodoEncontrado = findHallazgoEnNodoGrupo(nodoPiezaCompleta, nombre);
                    } else {
                        nodoEncontrado = findHallazgoEnNodoGrupo(nodoDiente, diente.hallazgoClinico.markID);
                    }
                    if (!nodoEncontrado) {
                        //No se encontro --> error!
                        console.log("No se encoinntro el nodo SVG para el diente " + diente);
                        return false;
                    }
                    var color = null;
                    if (diente.hallazgoClinico.estado == 'PENDIENTE') {
                        color = "#3F51B5";
                    } else {
                        color = "#F44336";
                    }
                    //Pintamos el nodo encontrado.
                    nodoEncontrado.attr('stroke', color);
                    nodoEncontrado.attr('opacity', '1');
                    if (diente.hallazgoClinico.markID != "corona") {
                        nodoEncontrado.attr('fill', color);
                    }
                    return true;
                }
                //Buscarmos cada cara del diente.
                var caras = diente.carasPiezaDental;
                for (var i = 0; i < caras.length; i++) {
                    var cara = caras[i];
                    if (!cara.hallazgoClinico) {
                        continue;
                    }
                    //Para esa cara busco el nodo SVG correspondiente
                    var nodoCara = null;
                    for (var j = 0; j < nodosCaras.length; j++) {
                        var n = nodosCaras[j];
                        if (n.id == cara.posicion) {
                            nodoCara = n;
                            break;
                        }
                    }

                    if (!nodoCara) {
                        console.log("Error! No se encontró la cara en el diente " + cara);
                        return false;
                    }
                    var nodoEncontrado = findHallazgoEnNodoGrupo(nodoCara, cara.hallazgoClinico.markID);
                    if (!nodoEncontrado) {
                        //No se encontro --> error!
                        console.log("No se encontró la marca correspondiente al tratamiento " + cara.hallazgoClinico);
                        return false;
                    }

                    var color = null;
                    if (cara.hallazgoClinico.estado == 'PENDIENTE') {
                        color = "#3F51B5";
                    } else {
                        color = "#F44336";
                    }
                    //Pintamos el nodo encontrado.
                    nodoEncontrado.attr('opacity', '1');
                    nodoEncontrado.attr('fill', color);
                }
            }

            /**
             * Metodo que busca entre los hijos de un grupo el nodo del mismo que se corresponde al hallazgo de la cara
             * pasado por parametro.
             * @param elementoPadre Nodo SVG
             * @param hallazgoPorBuscar HallazgoClinico de la cara
             * @returns nodoEncontrado o null si no lo encuentra.
             */
            function findHallazgoEnNodoGrupo(elementoPadre, hallazgoPorBuscar) {
                //Recuperamos los hijos, es decir todos los nodos interiores donde vamos a buscar la marca del tratamiento.
                var nodosHijos = elementoPadre.children;
                var nodoEncontrado = null;
                //Iteramos y buscamos en los nodos hijos la marca.
                for (var i = 0; i < nodosHijos.length; i++) {
                    //Busco en el grupo, el nodo que simboliza la marca.
                    if (nodosHijos[i].id == hallazgoPorBuscar) {
                        //Encuentro el nodo y salgo
                        nodoEncontrado = angular.element(nodosHijos[i]);
                        break;
                    }
                }
                return nodoEncontrado;
            }

            /**
             * Metodo que limpia todas las marcas del diente.
             * @param element
             */
            function resetNodos(element) {
                var nodos = element[0].querySelectorAll('.marca');
                angular.forEach(nodos, function (path, key) {
                    var nodoElement = angular.element(path);
                    nodoElement.attr("opacity", 0);
                })
            }
        }
    }
}]);

//FILTERS
directives.filter('cut', function () {
    return function (value, wordwise, max) {
        if (!value) return '';

        max = parseInt(max, 10);
        if (!max) return value;
        if (value.length <= max) return value;

        value = value.substr(0, max);
        if (wordwise) {
            var lastspace = value.lastIndexOf(' ');
            if (lastspace != -1) {
                //Also remove . and , so its gives a cleaner result.
                if (value.charAt(lastspace - 1) == '.' || value.charAt(lastspace - 1) == ',') {
                    lastspace = lastspace - 1;
                }
                value = value.substr(0, lastspace);
            }
        }

        return value + '…';
    };
});

directives.filter('nombrarArchivo', function () {
    return function (nombrePaquete, tipoABM) {
        var nombreArchivo = nombrePaquete.replace(/[\s]/g, '');
        nombreArchivo = nombreArchivo.toLowerCase();

        var tittles = "ãàáäâèéëêìíïîòóöôùúüûç";
        var original ="aaaaaeeeeiiiioooouuuuc";

        for (var i = 0; i < tittles.length; i++) {
            nombreArchivo = nombreArchivo.replace(tittles.charAt(i), original.charAt(i)).toLowerCase();
        };

        switch (tipoABM){
            case 0:
                nombreArchivo = nombreArchivo.concat("Registrar");
                break;
            case 1:
                nombreArchivo = nombreArchivo.concat("Baja");
                break;
            case 2:
                nombreArchivo = nombreArchivo.concat("Consulta");
                break;
            default :
                nombreArchivo;
        }
        return nombreArchivo;
    };
});

//DIRECTIVES AUXILIARES
directives.filter('noDefinido', function () {
    return function (input) {
        if (angular.isUndefined(input) || input == null) {
            return 'No definido';
        }
        return input;
    }
})

directives.filter('siNo', function () {
    return function (input) {
        if (angular.isDefined(input) && input == true) {
            return "Sí";
        } else {
            return "No";
        }
    }
});

directives.directive('filterChips', function () {
    return {
        restrict: 'E',
        template: '<div class="query-chips" layout-gt-sm="row" ng-if="data.length > 0" layout-margin> ' +
            '<md-chips ng-model="data" readonly="readonly">' +
            '<md-chip-template>' +
            '<em>{{$chip.name}}</em> ' +
            '<b>{{$chip.displayValue}}</b>' +
            '</md-chip-template>' +
            '</md-chips></div>',
        scope: {
            data: '=',
            readonly: '='
        },
        controller: function ($scope) {
        }
    }
});

directives.filter('truncate', function () {
    return function (text, length, end) {
        if (isNaN(length))
            length = 10;

        if (end === undefined)
            end = "...";

        if (text.length <= length || text.length - end.length <= length) {
            return text;
        } else {
            return String(text).substring(0, length - end.length) + end;
        }

    };
});




