'use strict';

var odontologiaApp = angular.module('odontologiaApp', [
    'ui.router',
    'oc.lazyLoad',
    'Pagination',
    'sapo.directives',
    'sapo.services',
    'angular-loading-bar',
    'auth.services',
    'ngMaterial',
    'ngAnimate',
    'ngMessages',
    'ngMdIcons',
    'ngFileUpload'
]);


odontologiaApp.config(['$urlRouterProvider',
    '$stateProvider',
    '$ocLazyLoadProvider', 'cfpLoadingBarProvider', '$httpProvider', '$mdThemingProvider', '$mdIconProvider',
    function ($urlRouterProvider, $stateProvider, $ocLazyLoadProvider, cfpLoadingBarProvider, $httpProvider, $mdThemingProvider, $mdIconProvider) {

        var customBlueMap = $mdThemingProvider.extendPalette('light-blue', {
            'contrastDefaultColor': 'light',
            'contrastDarkColors': ['50'],
            '50': 'ffffff'
        });
        $mdThemingProvider.definePalette('customBlue', customBlueMap);
        $mdThemingProvider.theme('default')
            .primaryPalette('customBlue', {
                'default': '500',
                'hue-1': '50'
            })
            .accentPalette('pink');

        $mdIconProvider
            .iconSet('social', 'img/icons/sets/social-icons.svg', 24)
            .iconSet('device', 'img/icons/sets/device-icons.svg', 24)
            .iconSet('communication', 'img/icons/sets/communication-icons.svg', 24)
            .defaultIconSet('img/icons/sets/core-icons.svg', 24);

        $httpProvider.interceptors.push('authHttpRequestInterceptor');

        cfpLoadingBarProvider.loadingBarTemplate = '<div id="bar-container"></div><div id="loading-bar"><div class="bar"><div class="peg"></div></div></div></div>';

        $httpProvider.defaults.transformResponse.push(function (responseData) {
            convertDateStringsToDates(responseData);
            return responseData;
        });

        function convertDateStringsToDates(input) {
            var dateRX = /^\d{4}-\d{1,2}-\d{1,2}T\d{2}:\d{2}:\d{2}.\d{3}Z$/;
            // Ignore things that aren't objects.
            if (typeof input !== "object") return input;
            for (var key in input) {
                if (!input.hasOwnProperty(key)) continue;
                var value = input[key];
                // Check for string properties which look like dates.
                if (typeof value === "string" && (value.match(dateRX) != null)) {
                    var sDate = value.match(dateRX)[0];
                    input[key] = new Date(sDate);
                } else if (typeof value === "object") {
                    // Recurse into object
                    convertDateStringsToDates(value);
                }
            }
        }

        function url(url) {
            return 'resources/scripts/app' + url;
        }

        function module(depency) {
            return {
                loadMyModule: ['$ocLazyLoad', function ($ocLazyLoad) {
                    //lazyload de un modulo
                    return $ocLazyLoad.load(depency);
                }]
            };
        }

        $urlRouterProvider.otherwise('/');

        $stateProvider
            .state('landingPage', {
                url: '/',
                templateUrl: 'views/home/landingPage.html',
                controller: 'LandingPageCtrl',
                controllerAs: 'vm',
                resolve: {
                    loadMyModule: ['$ocLazyLoad', function ($ocLazyLoad) {
                        //lazyload de un modulo
                        return $ocLazyLoad.load('sapo.login');
                    }],
                    initializeData: ['loadMyModule', '$http', '$q', function (loadMyModule, $http, $q) {
                        var deferred = $q.defer();
                        $http({
                            url: 'api/commons/initializeData',
                            method: 'POST'
                        })
                            .success(function (response) {
                                if (response) {
                                    deferred.resolve('Inicialización de datos ejecutada con éxito.');
                                } else {
                                    deferred.resolve(undefined);
                                }

                            })
                            .error(function () {
                                deferred.resolve('Error durante la ejecución de la inicialización de datos');
                            });
                        return deferred.promise;
                    }]
                }
            })
            .state('home', {
                url: '/home',
                templateUrl: 'views/home/home.html',
                controller: 'HomeCtrl',
                data: {
                    isFree: true
                },
                resolve: {
                    loadMyModule: ['$ocLazyLoad', function ($ocLazyLoad) {
                        //lazyload de un modulo
                        return $ocLazyLoad.load('homeModule');
                    }]}

            })
            .state('persona', {
                url: '/persona',
                template: '<ui-view/>',
                abstract: true,
                resolve: module('personaModule')
            })
            .state('persona.firstLogin', {
                url: '/firstLogin',
                templateUrl: 'views/persona/firstLogin.html',
                controller: 'PersonaCtrl_FirstLogin',
                controllerAs: 'vm',
                resolve: {
                    personaResponse: ['loadMyModule', 'authFactory', 'PersonaSrv', function (loadMyModule, authFactory, personaSrv) {
                        var user = authFactory.getAuthData();
                        return personaSrv.findByUser(user.nombreUsuario, user.authToken);
                    }],
                    tiposDocumentoResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, commons) {
                        return commons.getTiposDocumento();
                    }],
                    sexosResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, commons) {
                        return commons.getSexos();
                    }],
                    cargosResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, commons) {
                        return commons.getCargos();
                    }]
                }
            })
            .state('materia', {
                url: '/materia',
                template: '<ui-view/>',
                abstract: true,
                resolve: module('materiaModule')
            })

            .state('materia.index', {
                url: '/',
                templateUrl: 'views/materia/materiaQuery.html',
                controller: 'MateriaCtrl_Index',
                params: {execQuery: false, execQuerySamePage: false},
                resolve: {
                    nivelesResponse: ['CommonsSrv', function (commons) {
                        return commons.getNiveles();
                    }],
                    materiasResponse: ['loadMyModule', 'MateriaSrv', function (loadMyModule, service) {
                        return service.findAll();
                    }]
                }
            })
            .state('materia.create', {
                url: '/create',
                templateUrl: 'views/materia/materiaCreate.html',
                controller: 'MateriaCtrl_Create',
                resolve: {
                    nivelesResponse: ['CommonsSrv', function (commons) {
                        return commons.getNiveles();
                    }]
                }
            })
            .state('materia.edit', {
                url: '/edit/:id',
                templateUrl: 'views/materia/materiaEdit.html',
                controller: 'MateriaCtrl_Edit',
                resolve: {
                    nivelesResponse: ['CommonsSrv', function (commons) {
                        return commons.getNiveles();
                    }],
                    materiaResponse: ['loadMyModule', '$stateParams', 'MateriaSrv', function (loadMyModule, $stateParams, service) {
                        return service.findById($stateParams.id);
                    }]

                }
            })
            .state('materia.view', {
                url: '/view/:id',
                templateUrl: 'views/materia/materiaView.html',
                resolve: {
                    materiaResponse: ['loadMyModule', '$stateParams', 'MateriaSrv', function (loadMyModule, $stateParams, service) {
                        return service.findById($stateParams.id);
                    }]
                },
                controller: function ($scope, $state, materiaResponse) {
                    $scope.materia = materiaResponse.data;

                    $scope.goIndex = function () {
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
                templateUrl: 'views/practicaOdontologica/practicaOdontologicaQuery.html',
                params: {execQuery: false, execQuerySamePage: false},
                controller: 'PracticaOdontologicaCtrl_Index',
                resolve: {
                    gruposPracticaResponse: ['CommonsSrv', function (commons) {
                        return commons.getGruposPractica();

                    }]
                }
            })
            .state('practicaOdontologica.create', {
                url: '/create',
                templateUrl: 'views/practicaOdontologica/practicaOdontologicaCreate.html',
                controller: 'PracticaOdontologicaCtrl_Create',
                resolve: {
                    gruposPracticaResponse: ['CommonsSrv', function (commons) {
                        return commons.getGruposPractica();
                    }]
                }
            })
            .state('practicaOdontologica.edit', {
                url: '/edit/:id',
                templateUrl: 'views/practicaOdontologica/practicaOdontologicaEdit.html',
                controller: 'PracticaOdontologicaCtrl_Edit',
                resolve: {
                    gruposPracticaResponse: ['CommonsSrv', function (commons) {
                        return commons.getGruposPractica();
                    }],
                    practicaResponse: ['loadMyModule', 'PracticaOdontologicaSrv', '$stateParams', function (loadMyModule, service, $stateParams) {
                        return service.findById($stateParams.id);
                    }]
                }
            })
            .state('practicaOdontologica.view', {
                url: '/view/:id',
                templateUrl: 'views/practicaOdontologica/practicaOdontologicaView.html',
                resolve: {
                    practicaResponse: ['loadMyModule', '$stateParams', 'PracticaOdontologicaSrv', function (loadMyModule, $stateParams, service) {
                        return service.findById($stateParams.id);
                    }]
                },
                controller: function ($scope, $state, practicaResponse) {
                    $scope.practica = practicaResponse.data;

                    $scope.goIndex = function () {
                        $state.go('^.index');
                    }
                }

            })
            .state('trabajoPractico', {
                url: '/trabajoPractico',
                template: '<ui-view/>',
                abstract: true,
                resolve: module('trabajoPracticoModule')
            })
            .state('trabajoPractico.index', {
                url: '/',
                templateUrl: 'views/trabajoPractico/trabajoPracticoQuery.html',
                params: {execQuery: false, execQuerySamePage: false},
                controller: 'TrabajoPracticoCtrl_Index',
                resolve: {
                    gruposPracticaResponse: ['CommonsSrv', function (commons) {
                        return commons.getGruposPractica();
                    }],
                    practicasResponse: ['loadMyModule', 'TrabajoPracticoSrv', function (module, service) {
                        return service.getPracticas();
                    }]
                }
            })
            .state('trabajoPractico.create', {
                url: '/create',
                templateUrl: 'views/trabajoPractico/trabajoPracticoCreate.html',
                controller: 'TrabajoPracticoCtrl_Create',
                resolve: {
                    gruposPracticaResponse: ['CommonsSrv', function (commons) {
                        return commons.getGruposPractica();
                    }],
                    practicasResponse: ['loadMyModule', 'TrabajoPracticoSrv', function (loadMyModule, service) {
                        return service.getPracticas();
                    }]
                }
            })
            .state('trabajoPractico.edit', {
                url: '/edit/:id',
                templateUrl: 'views/trabajoPractico/trabajoPracticoEdit.html',
                controller: 'TrabajoPracticoCtrl_Edit',
                resolve: {
                    gruposPracticaResponse: ['CommonsSrv', function (commons) {
                        return commons.getGruposPractica();
                    }],
                    practicasResponse: ['loadMyModule', 'TrabajoPracticoSrv', function (loadMyModule, service) {
                        return service.getPracticas();
                    }],
                    trabajoPracticoResponse: ['loadMyModule', '$stateParams', 'TrabajoPracticoSrv', function (loadMyModule, $stateParams, service) {
                        return service.findById($stateParams.id)
                    }]
                }
            })
            .state('trabajoPractico.view', {
                url: '/view/:id',
                templateUrl: 'views/trabajoPractico/trabajoPracticoView.html',
                resolve: {
                    trabajoPracticoResponse: ['loadMyModule', '$stateParams', 'TrabajoPracticoSrv', function (loadMyModule, $stateParams, service) {
                        return service.findById($stateParams.id);
                    }]
                },
                controller: function ($scope, $state, trabajoPracticoResponse) {
                    $scope.trabajoPractico = trabajoPracticoResponse.data;

                    $scope.goIndex = function () {
                        $state.go('^.index');
                    }
                }

            })
            .state('catedra', {
                url: '/catedra',
                template: '<ui-view/>',
                abstract: true,
                resolve: module('catedraModule')
            })
            .state('catedra.index', {
                url: '/',
                templateUrl: 'views/catedra/catedraQuery.html',
                params: {execQuery: false, execQuerySamePage: false},
                controller: 'CatedraCtrl_Index',
                resolve: {
                    materiasResponse: ['loadMyModule', 'CatedraSrv', function (loadMyModule, service) {
                        return service.findAllMaterias();
                    }]
                }
            })
            .state('catedra.create', {
                url: '/create',
                templateUrl: 'views/catedra/catedraCreate.html',
                controller: 'CatedraCtrl_Create',
                resolve: {
                    materiasResponse: ['loadMyModule', 'CatedraSrv', function (loadMyModule, service) {
                        return service.findAllMaterias();
                    }],
                    diasResponse: ['CommonsSrv', function (commons) {
                        return commons.getDias();
                    }],
                    gruposPracticaResponse: ['CommonsSrv', function (commons) {
                        return commons.getGruposPractica();
                    }],
                    practicasResponse: ['loadMyModule', 'CatedraSrv', function (loadMyModule, service) {
                        return service.getPracticas();
                    }]
                }
            })
            .state('catedra.edit', {
                url: '/edit/:id',
                templateUrl: 'views/catedra/catedraEdit.html',
                controller: 'CatedraCtrl_Edit',
                resolve: {
                    materiasResponse: ['loadMyModule', 'CatedraSrv', function (loadMyModule, service) {
                        return service.findAllMaterias();
                    }],
                    diasResponse: ['CommonsSrv', function (commons) {
                        return commons.getDias();
                    }],
                    gruposPracticaResponse: ['CommonsSrv', function (commons) {
                        return commons.getGruposPractica();
                    }],
                    practicasResponse: ['loadMyModule', 'CatedraSrv', function (loadMyModule, service) {
                        return service.getPracticas();
                    }],
                    catedraResponse: ['loadMyModule', '$stateParams', 'CatedraSrv', function (loadMyModule, $stateParams, service) {
                        return service.findById($stateParams.id);
                    }]
                }
            })
            .state('catedra.view', {
                url: '/view/:id',
                templateUrl: 'views/catedra/catedraView.html',
                resolve: {
                    catedraResponse: ['loadMyModule', '$stateParams', 'CatedraSrv', function (loadMyModule, $stateParams, service) {
                        return service.findById($stateParams.id);
                    }]
                },
                controller: function ($scope, $state, catedraResponse) {
                    $scope.catedra = catedraResponse.data;

                    $scope.goIndex = function () {
                        $state.go('^.index');
                    }
                }
            })


            .state('usuario', {
                url: '/usuario',
                template: '<ui-view/>',
                abstract: true,
                resolve: module('usuarioModule')
            })
            .state('usuario.index', {
                url: '/',
                templateUrl: 'views/usuario/usuarioQuery.html',
                params: {execQuery: false, execQuerySamePage: false},
                controller: 'UsuarioCtrl_Index',
                resolve: {
                    rolesResponse: ['loadMyModule', 'UsuarioSrv', function (loadMyModule, service) {
                        return service.getRoles();
                    }]
                }
            })
            .state('usuario.create', {
                url: '/create',
                templateUrl: 'views/usuario/usuarioCreate.html',
                controller: 'UsuarioCtrl_Create',
                resolve: {
                    rolesResponse: ['loadMyModule', 'UsuarioSrv', function (loadMyModule, service) {
                        return service.getRoles();
                    }],
                    tiposDocResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, service) {
                        return service.getTiposDocumento();
                    }],
                    sexosResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, commons) {
                        return commons.getSexos();
                    }]
                }
            })
            .state('usuario.edit', {
                url: '/edit/:id',
                templateUrl: 'views/usuario/usuarioEdit.html',
                controller: 'UsuarioCtrl_Edit',
                resolve: {
                    rolesResponse: ['loadMyModule', 'UsuarioSrv', function (loadMyModule, service) {
                        return service.getRoles();
                    }],
                    usuarioResponse: ['loadMyModule', '$stateParams', 'UsuarioSrv', function (loadMyModule, $stateParams, service) {
                        return service.findPersona($stateParams.id);
                    }],
                    tiposDocResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, service) {
                        return service.getTiposDocumento();
                    }]
                }
            })
            .state('usuario.view', {
                url: '/view/:id',
                templateUrl: 'views/usuario/usuarioView.html',
                resolve: {
                    usuarioResponse: ['loadMyModule', '$stateParams', 'UsuarioSrv', function (loadMyModule, $stateParams, service) {
                        return service.findById($stateParams.id);
                    }]
                },
                controller: function ($scope, $state, usuarioResponse) {
                    $scope.usuario = usuarioResponse.data;
                    $scope.goIndex = function () {
                        $state.go('^.index');
                    }
                }
            })
            .state('paciente', {
                url: '/paciente',
                template: '<ui-view/>',
                abstract: true,
                resolve: module('pacienteModule')
            })
            .state('paciente.index', {
                url: '/',
                templateUrl: 'views/paciente/pacienteQuery.html',
                params: {execQuery: false, execQuerySamePage: false},
                controller: 'PacienteCtrl_Index',
                resolve: {
                    materiasResponse: ['loadMyModule', 'PacienteSrv', function (loadMyModule, service) {
                        return service.findAllMaterias();
                    }],
                    practicasResponse: ['loadMyModule', 'PacienteSrv', function (loadMyModule, service) {
                        return service.getPracticas();
                    }]
                }
            })
            .state('paciente.create', {
                url: '/create',
                templateUrl: 'views/paciente/pacienteCreate.html',
                controller: 'PacienteCtrl_Create'
            })
            .state('paciente.edit', {
                url: '/edit/:id',
                templateUrl: 'views/paciente/pacienteEdit.html',
                controller: 'PacienteCtrl_Edit'
            })
            .state('paciente.view', {
                url: '/view/:id',
                templateUrl: 'views/paciente/pacienteView.html',
                controller: 'PacienteCtrl_View'
            })
            .state('asignacion', {
                url: '/asignacion',
                template: '<ui-view/>',
                abstract: true,
                resolve: module('asignacionModule')
            })
            .state('asignacion.index', {
                url: '/',
                templateUrl: 'views/asignacion/asignacionQuery.html',
                controller: 'AsignacionCtrl_Index'
            })
            .state('asignacion.create', {
                url: '/create',
                templateUrl: 'views/asignacion/asignacionCreate.html',
                controller: 'AsignacionCtrl_Create'
            })
        ;

        $ocLazyLoadProvider.config({
            debug: true,
            modules: [
                {
                    name: 'sapo.login',
                    files: [url('/home/landingPageCtrl.js')]
                },
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
                    name: 'trabajoPracticoModule',
                    files: [
                        url('/trabajoPractico/trabajoPracticoSrv.js'),
                        url('/trabajoPractico/trabajoPracticoIndexCtrl.js'),
                        url('/trabajoPractico/trabajoPracticoCreateCtrl.js'),
                        url('/trabajoPractico/trabajoPracticoEditCtrl.js')
                    ]
                },
                {
                    name: 'catedraModule',
                    files: [
                        url('/catedra/catedraSrv.js'),
                        url('/catedra/catedraIndexCtrl.js'),
                        url('/catedra/catedraCreateCtrl.js'),
                        url('/catedra/catedraEditCtrl.js')
                    ]
                },
                {
                    name: 'usuarioModule',
                    files: [
                        url('/usuario/usuarioSrv.js'),
                        url('/usuario/usuarioIndexCtrl.js'),
                        url('/usuario/usuarioCreateCtrl.js'),
                        url('/usuario/usuarioEditCtrl.js')
                    ]
                },
                {
                    name: 'personaModule',
                    files: [
                        url('/persona/firstLoginCtrl.js'),
                        url('/persona/personaSrv.js')
                    ]
                },
                {
                    name: 'pacienteModule',
                    files: [
                        url('/paciente/pacienteSrv.js'),
                        url('/paciente/pacienteIndexCtrl.js'),
                        url('/paciente/pacienteCreateCtrl.js'),
                        url('/paciente/pacienteEditCtrl.js'),
                        url('/paciente/pacienteViewCtrl.js')
                    ]
                },
                {
                    name: 'asignacionModule',
                    files: [
                        url('/asignacion/asignacionSrv.js'),
                        url('/asignacion/asignacionIndexCtrl.js'),
                        url('/asignacion/asignacionCreateCtrl.js')
                    ]
                }
            ]
        });

    }]);

angular.module('homeModule', []);
angular.module('materiaModule', []);
angular.module('practicaOdontologicaModule', []);
angular.module('catedraModule', []);
angular.module('trabajoPracticoModule', []);
angular.module('usuarioModule', []);
angular.module('sapo.login', []);
angular.module('personaModule', []);
angular.module('pacienteModule', []);
angular.module('asignacionModule', []);


odontologiaApp.controller('AppController', ['$scope', '$state', '$timeout', 'authFactory', '$filter', '$mdSidenav', function ($scope, $state, $timeout, authFactory, $filter, $mdSidenav) {

    $scope.performSubmit = performSubmit;

    $scope.toggleSidenav = function (menuId) {
        $mdSidenav(menuId).toggle();
    };

    $scope.show = "false";
    $scope.showFilters = function () {
        $scope.show = true;
    };

    $scope.user = authFactory.getAuthData();
//    $scope.menu = $scope.user ? buildMenu($scope.user.permisos) : authFactory.getMenu();
    $scope.$on('$stateChangeStart',
        function (event, toState, toParams, fromState, fromParams) {

//            if (authFactory.isAuthenticated()) {
//                authFactory.updateExpiresTime();
//            } else {
//                $scope.user = undefined;
//                $scope.menu = [];
//                authFactory.removeMenu();
//            }
//
//            if (toState.name === 'login' && authFactory.isAuthenticated()) {
//                event.preventDefault();
//                $state.go('home');
//            }
//            if (toState.data && toState.data.isFree) {
//                return;
//            }
//            if (!authFactory.isAuthenticated()) {
//                event.preventDefault();
//                $state.go('landingPage');
//            }
        });

    $scope.$on('authChanged', function () {
        $scope.user = authFactory.getAuthData();
        $scope.menu = buildMenu($scope.user.permission);
    });

    $scope.logOut = function () {
        $scope.user = authFactory.logout();
        $scope.menu = [];
        $state.go('home');
    }

    function buildMenu(permissions) {
        var resultMenu = [];

        var items = $filter('filter')(permissions, function (permission) {
            return permission.esItemMenu;
        });

        angular.forEach(items, function (item) {
            if (item.funcionalidad.grupoFuncionalidad && !$filter('filter')(resultMenu,function (itemMenu) {
                return itemMenu.name == item.funcionalidad.grupoFuncionalidad.nombre;
            }).length) {
                resultMenu.push({
                    dropdown: true,
                    name: item.funcionalidad.grupoFuncionalidad.nombre,
                    subItems: []
                })
            }
        });

        angular.forEach(items, function (item) {
            if (!item.funcionalidad.grupoFuncionalidad) {
                resultMenu.push({
                    name: item.funcionalidad.nombre,
                    ref: item.funcionalidad.estadoAsociado
                })
            } else {
                for (var i = 0; i < resultMenu.length; i++) {
                    if (item.funcionalidad.grupoFuncionalidad.nombre == resultMenu[i].name) {
                        resultMenu[i].subItems.push({
                                name: item.funcionalidad.nombre,
                                ref: item.funcionalidad.estadoAsociado
                            }
                        )
                    }
                }
            }
        });
        authFactory.setMenu(resultMenu);
        return resultMenu;
    }

    function performSubmit(toExecute, form) {
        if (form.$valid) {
            toExecute();
        } else {
            angular.forEach(form.$error, function (field) {
                angular.forEach(field, function (errorField) {
                    errorField.$setTouched();
                })
            });
            $timeout
        }
    }
}]);
