var odontologiaApp = angular.module('odontologiaApp', [
//  'ngAnimate',
    'ui.router',
    'oc.lazyLoad',
    'ui.bootstrap',
    'Pagination'
//  'appInterceptorHttp',
//  'moduleHttpService'
]);


odontologiaApp.config(['$urlRouterProvider',
    '$stateProvider',
    '$ocLazyLoadProvider',
//    '$httpProvider',
    function ($urlRouterProvider, $stateProvider, $ocLazyLoadProvider
              //        $httpProvider
        ) {


//para convertir las fechas
//    $httpProvider.defaults.transformResponse.push(function (responseData) {
//        convertDateStringsToDates(responseData);
//        return responseData;
//    });

        function url(url) {
            return 'resources/scripts/app' + url;
        }

//    Date.fromNET = function (sDate) {
//        var oDate = new Date(parseInt(sDate.replace("/Date(", "").replace(")/", ""), 10));
//
//        var curr_date = oDate.getDate();
//        var curr_month = oDate.getMonth() + 1 + "";
//        var curr_year = oDate.getFullYear();
//        var pad = "00";
//        curr_month = pad.substring(0, pad.length - curr_month.length) + curr_month;
//        return curr_date + "." + curr_month + "." + curr_year;
//    };
//
//    function convertDateStringsToDates(input) {
//
//
//        var regexIso8601 = /(\/Date\(\d+\)\/)/;
//
//        // Ignore things that aren't objects.
//        if (typeof input !== "object") return input;
//
//        for (var key in input) {
//            if (!input.hasOwnProperty(key)) continue;
//
//            var value = input[key];
//            var match;
//            // Check for string properties which look like dates.
//            if (typeof value === "string" && (match === value.match(regexIso8601))) {
//
//                var sDate = match[0];
//
//                input[key] = Date.fromNET(sDate);
//
//            } else if (typeof value === "object") {
//                // Recurse into object
//                convertDateStringsToDates(value);
//            }
//        }
//    }
//    
        function module(depency) {
            return {
                loadMyModule: ['$ocLazyLoad', function ($ocLazyLoad) {
                    //lazyload de un modulo
                    return $ocLazyLoad.load(depency);
                } ]
            };
        }

        $urlRouterProvider.otherwise('/');

        $stateProvider
            .state('home', {
                url: '/',
                templateUrl: 'views/home/home.html',
                controller: 'HomeCtrl',
                resolve: module('homeModule')
            })
            .state('materia', {
                url: '/materia',
                template: '<ui-view/>',
                abstract: true,
                resolve: module('materiaModule')
            })
            .state('materia.index', {
                url: '/',
                templateUrl: 'views/materia/query.html',
                controller: 'MateriaCtrl_Index',
                params: {execQuery:false, execQuerySamePage:false},
                resolve: {
                    nivelesResponse: ['CommonsSrv', function (commons) {
                        return commons.getNiveles();
                    }]
                }
            })
            .state('materia.create', {
                url: '/create',
                templateUrl: 'views/materia/create.html',
                controller: 'MateriaCtrl_Create',
                resolve: {
                    nivelesResponse: ['CommonsSrv', function (commons) {
                        return commons.getNiveles();
                    }]
                }
            })
            .state('materia.edit', {
                url: '/edit/:id',
                templateUrl: 'views/materia/edit.html',
                controller: 'MateriaCtrl_Edit',
                resolve: {
                    nivelesResponse: ['CommonsSrv', function (commons) {
                        return commons.getNiveles();
                    }],
                    materiaResponse: ['$stateParams', 'MateriaSrv', function ($stateParams, service) {
                        return service.findById($stateParams.id);
                    }]

                }
            })
            .state('materia.view', {
                url: '/view/:id',
                templateUrl: 'views/materia/view.html',
                resolve: {
                    materiaResponse: ['$stateParams', 'MateriaSrv', function ($stateParams, service) {
                        return service.findById($stateParams.id);
                    }]
                },
                controller: function($scope, $state, materiaResponse) {
                    $scope.materia = materiaResponse.data;

                    $scope.goIndex = function() {
                        $state.go('^.index');
                    }
                }

            })
            .state('practicaOdontologica', {
                url: '/practicaOdontologica',
                template: '<ui-view/>',
                abstract: true,
                resolve: module('practicaOdontologicaModule')
            })
            .state('practicaOdontologica.index', {
                url: '/',
                templateUrl: 'views/practicaOdontologica/query.html',
                params: {execQuery:false, execQuerySamePage:false},
                controller: 'PracticaOdontologicaCtrl_Index',
                resolve: {
                    gruposPracticaResponse: ['CommonsSrv', function(commons){
                        return commons.getGruposPractica();

                    }]
                }
            })
            .state('practicaOdontologica.create', {
                url: '/create',
                templateUrl: 'views/practicaOdontologica/create.html',
                controller: 'PracticaOdontologicaCtrl_Create',
                resolve: {
                    gruposPracticaResponse: ['CommonsSrv', function(commons){
                        return commons.getGruposPractica();
                    }]
                }
            })
            .state('practicaOdontologica.edit', {
                url: '/edit/:id',
                templateUrl: 'views/practicaOdontologica/edit.html',
                controller: 'PracticaOdontologicaCtrl_Edit',
                resolve: {
                    gruposPracticaResponse: ['CommonsSrv', function(commons){
                        return commons.getGruposPractica();
                    }],
                    practicaResponse: ['PracticaOdontologicaSrv','$stateParams', function(service, $stateParams){
                        return service.findById($stateParams.id);
                    }]
                }
            })
            .state('catedra', {
                url: '/catedra',
                template: '<ui-view/>',
                abstract: true,
                resolve: module('catedraModule')
            })
            .state('catedra.create', {
                url: '/create',
                templateUrl: 'views/catedra/create.html',
                controller: 'CatedraCtrl_Create',
                resolve: { materiaResponse: ['CommonsSrv', function (commons) {
                    return commons.getMaterias();
                }],

                    diasResponse: ['CommonsSrv', function (commons) {
                        return commons.getDias();
                    }]}
            })
        ;

        $ocLazyLoadProvider.config({
            debug: true,
            modules: [
                {
                    name: 'homeModule',
                    files: [url('/home/homeCtrl.js')]
                },
                {
                    name: 'materiaModule',
                    files: [url('/materia/materiaCreateCtrl.js'),
                        url('/materia/materiaSrv.js'),
                        url('/materia/materiaIndexCtrl.js'),
                        url('/materia/materiaEditCtrl.js')]
                },
                {
                    name: 'practicaOdontologicaModule',
                    files: [
                        url('/practicaOdontologica/practicaOdontologicaSrv.js'),
                        url('/practicaOdontologica/practicaOdontologicaIndexCtrl.js'),
                        url('/practicaOdontologica/practicaOdontologicaCreateCtrl.js'),
                        url('/practicaOdontologica/practicaOdontologicaEditCtrl.js')
                    ]
                },
                {
                    name: 'catedraModule',
                    files: [url('/catedra/catedraCreateCtrl.js'),
                        url('/catedra/catedraSrv.js')]
                }
            ]
        });

    }]);

angular.module('homeModule', []);
angular.module('materiaModule', []);
angular.module('practicaOdontologicaModule', []);
angular.module('catedraModule', []);


odontologiaApp.controller('AppController', function ($scope, $rootScope, $document) {

//    $rootScope.$on('$stateChangeSuccess',
//        function(event, toState, toParams, fromState, fromParams){
//                $.material.checkbox();
//        })

});

odontologiaApp
    .controller('datepickerCtrl', function ($scope) {

        $scope.clear = function () {
            $scope.dt = null;
        };

        // Disable weekend selection
        $scope.disabled = function (date, mode) {
            return ( mode === 'day' && ( date.getDay() === 0 || date.getDay() === 6 ) );
        };

        $scope.toggleMin = function () {
            $scope.minDate = $scope.minDate ? null : new Date();
        };
        $scope.toggleMin();

        $scope.open = function ($event) {
            $event.preventDefault();
            $event.stopPropagation();

            $scope.opened = true;
        };

        $scope.dateOptions = {
            formatYear: 'yyyy',
            startingDay: 1
        };

        $scope.formats = ['dd/MM/yyyy', 'dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
        $scope.format = $scope.formats[0];
    })

odontologiaApp
    .directive('datePicker', function () {
        return {
            templateUrl: 'views/commons/datepicker.html'
        }
    })

odontologiaApp.
    factory('NotificationSrv', ['$q', '$location','$anchorScroll', function ($q, $location, $anchorScroll) {

        var service = {};

        service.good = function (msg, callback) {
            bootbox.dialog({message: msg,
                title: '<i class="fa fa-check-circle-o fa-lg"></i> Éxito',
                buttons: {ok: {label: "OK", className: "btn-primary", callback: callback } }
            });
        }

        service.goodAndOnEscape = function (msg, callback, onEscape) {
            bootbox.dialog({message: msg,
                title: '<i class="fa fa-check-circle-o fa-lg"></i> Éxito',
                buttons: {ok: {label: "OK", className: "btn-primary", callback: callback } },
                onEscape: onEscape
            });
        }

        service.warning = function (msg, callback) {
            bootbox.dialog({message: msg,
                title: '<i class="fa fa-exclamation-triangle fa-lg"></i></i> Advertencia',
                buttons: {ok: {label: "OK", className: "btn-primary", callback: callback } }
            });
        }

        service.bad = function (msg, callback) {
            bootbox.dialog({message: msg,
                title: '<i class="fa fa-times-circle fa-lg"></i></i></i> Error',
                buttons: {ok: {label: "OK", className: "btn-primary", callback: callback } }
            });
        }

        service.badArray = function (msg, callback) {
            var message = '<ul>';
            var msgs = Object.keys(msg);
            for (var i = 0; i < msgs.length; i++) {
                message += '<li>' + msg[msgs[i]] + '</li>'
            }
            message += '</ul>';
            bootbox.dialog({message: message,
                title: '<i class="fa fa-times-circle fa-lg"></i></i></i> Error',
                buttons: {ok: {label: "OK", className: "btn-primary", callback: callback } }
            });
        }


        service.showWidget = function () {
            angular.element('#widget').show();
        };
        service.hideWidget = function () {
            angular.element('#widget').hide();
        };

        service.scrollTo = function (id) {
            var old = $location.hash();
            $location.hash(id);
            $anchorScroll();
            $location.hash(old);
        }

        service.requestReason = function () {
            var deferred = $q.defer();
            var dialogOptions = {
                title: '<i class="fa fa-eraser fa-lg"></i></i> Ingrese el motivo de baja',
                inputType: 'textarea',
                buttons: {
                    confirm: {
                        label: 'Aceptar'
                    },
                    cancel: {
                        label: 'Cancelar'
                    }
                },
                callback: function (result) {
                    if (result != null) {
                        deferred.resolve(result);
                    } else {
                        deferred.reject();
                    }
                }
            }
            bootbox.prompt(dialogOptions);
            return deferred.promise;
        }

        service.requestConfirmation = function (msg, Okcallback) {
            bootbox.dialog({message: msg,
                title: '<i class="fa fa-question fa-lg"></i></i> Confirmación',
                buttons: {
                    ok: {label: "Aceptar", className: "btn-primary", callback: Okcallback },
                    cancel: {label: "Cancelar", className: "btn-default", callback: function () {
                    }}
                }
            });
        }

        return service;

    }]);

odontologiaApp.factory('CommonsSrv', ['$http', function ($http) {
    var service = {};

    service.enumToJson = function (data) {
        var result = [];

        for (var i = 0; i < data.length; i++) {
            var json = {};
            json.id = i;
            json.nombre = data[i];
            result.push(json);
        }
        return result;
    }

    service.getNiveles = function () {
        return $http({
            method: 'GET',
            url: 'api/commons/getNiveles',
            cache: true
        })
    }

    service.getDias = function () {
        return $http({
            method: 'GET',
            url: 'api/commons/getDias',
            cache: true
        })
    }

    service.getMaterias = function () {
        return $http({
            method: 'GET',
            url: 'api/commons/getMaterias',
            cache: true
        })
    }

    service.getGruposPractica = function(){
        return $http({
            url: 'api/commons/getGruposPracticaOdontologica',
            method: 'GET',
            cache: true
        })
    }

    return service;
}])

var submitValidate = ['$parse', '$location', '$anchorScroll', function ($parse, $location, $anchorScroll) {
    return {
        restrict: 'A',
        require: 'form',
        link: function (scope, formElement, attributes, formController) {

            var fn = $parse(attributes.submitValidate);
            formElement.bind('submit', function (event) {

                if (formController.$invalid) {
                    var invalidElements = formElement[0].querySelectorAll('.ng-invalid');
                    for (var i = 0; i < invalidElements.length; i++) {
                        var element = angular.element(invalidElements[i]);
                        element.blur();
                    }

                    scope.$apply(function () {
                        scope.showSummaryError = true;
                    })
                    var old = $location.hash();
                    $location.hash('container');
                    $anchorScroll();
                    $location.hash(old);
                    return false;
                }

                scope.$apply(function () {
                    scope.showSummaryError = false;
                    fn(scope, {$event: event});
                });
            });
        }

    };
}]

odontologiaApp
    .directive('submitValidate', submitValidate);

odontologiaApp.directive('summaryError', function () {
    return {
        restrict: 'E',
        scope: {
            var: '='
        },
        template: ' <div ng-if="var"><div class="alert alert-danger" role="alert"><i class="fa fa-exclamation-circle"></i><strong> ¡Datos erróneos!</strong> Verifique el formulario y complete los datos correctamente.</div></div>',
        controller: function ($scope) {
        }
    }
})

odontologiaApp.directive('showErrors', function () {
    return {
        restrict: 'A',
        require: '^form',
        link: function (scope, el, attrs, formCtrl) {
            // find the text box element, which has the 'name' attribute
            var inputEl = el[0].querySelector("[name]");
            // convert the native text box element to an angular element
            var inputNgEl = angular.element(inputEl);
            // get the name on the text box so we know the property to check
            // on the form controller
            var inputName = inputNgEl.attr('name');
            var errors = formCtrl[inputName].$error;
            // only apply the has-error class after the user leaves the text box
            inputNgEl.bind('blur', function () {
                el.toggleClass('has-error', formCtrl[inputName].$invalid);
                var msgSpan = angular.element(el[0].querySelector("[for]"));
                var aux = '';
                if (formCtrl[inputName].$invalid) {
                    var errorTypes = Object.keys(errors);
                    for (var i = 0; i < errorTypes.length; i++) {
                        aux += getErrorMessageByType(errorTypes[i]);
                    }
                }
                msgSpan.text(aux);
            });

        }
    }
});

function addErrorToView(el, formCtrl, errors, inputName) {
    el.toggleClass('has-error', formCtrl[inputName].$invalid);
    var msgSpan = angular.element(el[0].querySelector("[for]"));
    var aux = '';
    if (formCtrl[inputName].$invalid) {
        var errorTypes = Object.keys(errors);
        for (var i = 0; i < errorTypes.length; i++) {
            aux += getErrorMessageByType(errorTypes[i]);
        }
    }
    msgSpan.text(aux);
}

function getErrorMessageByType(type) {
    var msg;
    switch (type) {
        case 'required':
            msg = 'Campo obligatorio';
            break;
        case 'email':
            msg = 'E-mail no válido';
            break;
        default:
            msg = 'Error';
            break;

    }
    return msg;
}