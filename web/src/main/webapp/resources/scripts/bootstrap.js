


var odontologiaApp = angular.module('odontologiaApp', [
//  'ngAnimate',
    'ui.router',
    'oc.lazyLoad',
    'ui.date',
    'ui.bootstrap'
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
                templateUrl: 'views/materia/index.html',
                controller: 'MateriaCtrl_Index',
                resolve: {
                    nivelesResponse : ['CommonsSrv', function(commons) {
                        return commons.getNiveles();
                    }]
                }
            })
            .state('materia.create', {
                url: '/create',
                templateUrl: 'views/materia/create.html',
                controller: 'MateriaCtrl_Create',
                resolve: {
                    nivelesResponse : ['CommonsSrv', function(commons) {
                        return commons.getNiveles();
                    }]
                }
            })
            .state('catedra', {
                url: '/catedra',
                template:'<ui-view/>',
                abstract: true,
                resolve: module('catedraModule')
            })
            .state('catedra.create', {
                url: '/create',
                templateUrl: 'views/catedra/create.html',
                controller: 'CatedraCtrl_Create',
                resolve: { materiaResponse: ['CommonsSrv', function(commons) {
                    return commons.getMaterias();
                }],

                    diasResponse: ['CommonsSrv', function(commons) {
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
                        url('/materia/materiaIndexCtrl.js')]
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
angular.module('catedraModule', []);


odontologiaApp.controller('AppController', function ($scope) {

});


odontologiaApp
    .controller('datepickerCtrl', function($scope) {

        $scope.clear = function () {
            $scope.dt = null;
        };

        // Disable weekend selection
        $scope.disabled = function(date, mode) {
            return ( mode === 'day' && ( date.getDay() === 0 || date.getDay() === 6 ) );
        };

        $scope.toggleMin = function() {
            $scope.minDate = $scope.minDate ? null : new Date();
        };
        $scope.toggleMin();

        $scope.open = function($event) {
            $event.preventDefault();
            $event.stopPropagation();

            $scope.opened = true;
        };

        $scope.dateOptions = {
            formatYear: 'yyyy',
            startingDay: 1
        };

        $scope.formats = ['dd/MM/yyyy','dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
        $scope.format = $scope.formats[0];
    })

odontologiaApp
    .directive('datePicker', function() {
        return {
            templateUrl: 'views/commons/datepicker.html'
        }
    })

odontologiaApp
    .directive('timePicker', function() {
        return {
            restrict: 'E',
            templateUrl: 'views/commons/timepicker.html',
            require: 'ngModel',
            scope: {ngModel: '='},
            link: function(scope, element, attrs, ngModelCtrl) {
                  var model = scope.ngModel;
                var hola = attrs.ngModel;
            }

        }
    })

odontologiaApp
    .controller('timepickerCtrl', function($scope){
        $scope.hstep = 1;
        $scope.mstep = 15;

        $scope.time = defaultTime();

        function defaultTime() {
            var time = new Date();
            time.setHours(8);
            time.setMinutes(0);
            time.setMilliseconds(0);

            return time;
        }

    })



odontologiaApp.
    factory('NotificationSrv', [function () {

        var service = {};

        service.good = function (msg, callback) {
            bootbox.dialog({message: msg,
                title: '<i class="fa fa-check-circle-o fa-lg"></i> Éxito',
                buttons: {ok: {label: "OK", className: "btn-primary", callback: callback() } }
            });
        }

        service.warning = function (msg, callback) {
            bootbox.dialog({message: msg,
                title: '<i class="fa fa-exclamation-triangle fa-lg"></i></i> Advertencia',
                buttons: {ok: {label: "OK", className: "btn-primary", callback: callback() } }
            });
        }

        service.bad = function (msg, callback) {
            bootbox.dialog({message: msg,
                title: '<i class="fa fa-times-circle fa-lg"></i></i></i> Error',
                buttons: {ok: {label: "OK", className: "btn-primary", callback: callback() } }
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
                buttons: {ok: {label: "OK", className: "btn-primary", callback: callback() } }
            });
        }


        service.showWidget = function () {
            angular.element('#widget').show();
        };
        service.hideWidget = function () {
            angular.element('#widget').hide();
        };

        return service;

    }]);

odontologiaApp.factory('CommonsSrv', ['$http', function($http) {
    var service = {};

    service.enumToJson = function(data) {
        var result = [];

        for (var i = 0; i < data.length; i++)
        {
            var json = {};
            json.id = i;
            json.nombre = data[i];
            result.push(json);
        }
        return result;
    }

    service.getNiveles = function() {
        return $http({
            method: 'GET',
            url: 'api/commons/getNiveles',
            cache: true
        })
    }

    service.getDias = function() {
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

    return service;
}])

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