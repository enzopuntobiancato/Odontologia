'use strict';

var odontologiaApp = angular.module('odontologiaApp', [
    'ui.router',
    'ngSanitize',
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
    'ngFileUpload',
    'pascalprecht.translate',
    'md.data.table'
]);

odontologiaApp.config(['$urlRouterProvider', '$stateProvider', '$ocLazyLoadProvider', 'cfpLoadingBarProvider', '$httpProvider',
    '$mdThemingProvider', '$mdIconProvider', '$translateProvider', '$mdDateLocaleProvider',
    function ($urlRouterProvider, $stateProvider, $ocLazyLoadProvider, cfpLoadingBarProvider, $httpProvider, $mdThemingProvider, $mdIconProvider, $translateProvider, $mdDateLocaleProvider) {

        var customBlueMap = $mdThemingProvider.extendPalette('light-blue', {
            'contrastDefaultColor': 'light',
            'contrastDarkColors': ['50'],
            '50': 'ffffff'
        });
        $translateProvider
            .useStaticFilesLoader({
                prefix: 'resources/i18n/locale-',
                suffix: '.json'}
        )
            .preferredLanguage('es');

        $mdThemingProvider.definePalette('customBlue', customBlueMap);
        $mdThemingProvider.theme('default')
            .primaryPalette('customBlue', {
                'default': '500',
                'hue-1': '50'
            })
            .accentPalette('pink');
        $mdThemingProvider.setDefaultTheme('default');

        $mdThemingProvider.theme('altTheme')
            .primaryPalette('grey');

        $mdIconProvider
            .iconSet('social', 'img/icons/sets/social-icons.svg', 24)
            .iconSet('device', 'img/icons/sets/device-icons.svg', 24)
            .iconSet('communication', 'img/icons/sets/communication-icons.svg', 24)
            .defaultIconSet('img/icons/sets/core-icons.svg', 24);

        $httpProvider.interceptors.push('authHttpRequestInterceptor');

        $mdDateLocaleProvider.formatDate = function (date) {
            if (date) {
                var day = date.getDate();
                var monthIndex = date.getMonth();
                var year = date.getFullYear();

                return day + '/' + (monthIndex + 1) + '/' + year;
            } else {
                return '';
            }
        };

        $mdDateLocaleProvider.parseDate = function (dateString) {
            var parts = dateString.split("/");
            var date = new Date(parts[2], parts[1] - 1, parts[0]);
            return date;
        };

        cfpLoadingBarProvider.loadingBarTemplate = '<div id="bar-container"></div><div id="loading-bar"><div class="bar"><div class="peg"></div></div></div></div>';

        $httpProvider.defaults.transformResponse.push(function (responseData, status) {
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
                data: {
                    isFree: true
                },
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
            .state('selectRol', {
                url: '/selectRol',
                templateUrl: 'views/home/selectRol.html',
                controllerAs: 'vm',
                data: {
                    fullPage: true
                },
                resolve: {
                    rolesResponse: ['$http', 'authFactory', function ($http, authFactory) {
                        return $http({
                            url: 'api/usuario/findRolesByUser',
                            method: 'GET',
                            params: {id: authFactory.getAuthData().id}
                        })
                    }]
                },
                controller: function ($scope, rolesResponse, authFactory, $state) {
                    var vm = this;
                    vm.data = {
                        roles: rolesResponse.data
                    }
                    vm.selectRol = selectRol;
                    function selectRol(rol) {
                        authFactory.selectRol(rol)
                            .then(function (response) {
                                var user = response.data;
                                user.missingRole = false;
                                authFactory.setAuthData(user, authFactory.getImage());
                                if (user.firstLogin) {
                                    authFactory.communicateAuthChangedMissingStep();
                                    $state.go('persona.firstLogin');
                                } else {
                                    authFactory.communicateAuthChanged();
                                    $state.go('home');
                                }
                            });
                    }
                }
            })
            .state('error', {
                url: '/error',
                params: {response: undefined},
                template: '<md-card><div ng-bind-html="deliverHtml()"></div></md-card>',
                controller: function ($scope, $stateParams, $sce) {
                    $scope.deliverHtml = function () {
                        return $sce.trustAsHtml($stateParams.response);
                    }
                }
            })
            .state('home', {
                url: '/home',
                templateUrl: 'views/home/home.html',
                controller: 'HomeCtrl',
                data: {
                    nombrePaquete: 'Home'
                },
                resolve: {
                    loadMyModule: ['$ocLazyLoad', function ($ocLazyLoad) {
                        //lazyload de un modulo
                        return $ocLazyLoad.load('homeModule');
                    }]}

            })
            .state('estadisticas', {
                url: '/estadisticas',
                templateUrl: 'views/estadisticas/estadisticas.html',
                data: {
                    fullPage: true,
                    nombrePaquete: 'Estadísticas',
                    nombreCasoUso: 'Estadísticas'
                },
                controller: function ($scope) {
                }
            })
            .state('persona', {
                url: '/persona',
                template: '<ui-view/>',
                abstract: true,
                data: {
                    nombrePaquete: 'Usuarios'
                },
                resolve: module('personaModule')
            })
            .state('persona.firstLogin', {
                url: '/firstLogin',
                templateUrl: 'views/persona/firstLogin.html',
                controller: 'PersonaCtrl_FirstLogin',
                data: {
                    nombreCasoUso: 'Primer login'
                },
                controllerAs: 'vm',
                resolve: {
                    personaResponse: ['loadMyModule', 'authFactory', 'PersonaSrv', function (loadMyModule, authFactory, personaSrv) {
                        var user = authFactory.getAuthData();
                        return personaSrv.findByUser(user.nombreUsuario, user.authToken, user.rol.key);
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
            .state('persona.datosUsuario', {
                url: '/datosUsuario',
                templateUrl: 'views/persona/datosUsuario.html',
                controller: 'PersonaCtrl_DatosUsuario',
                controllerAs: 'vm',
                data: {
                    nombreCasoUso: 'Datos del usuario'
                },
                resolve: {
                    personaResponse: ['loadMyModule', 'authFactory', 'PersonaSrv', function (loadMyModule, authFactory, personaSrv) {
                        var user = authFactory.getAuthData();
                        return personaSrv.findByUser(user.nombreUsuario, user.authToken, user.rol.key);
                    }],
                    imageResponse: ['loadMyModule', 'personaResponse', 'PersonaSrv', function (loadMyModule, personaResponse, service) {
                        var person = personaResponse.data;
                        return service.findUserImage(person.usuario.imagenId);
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
                data: {
                    nombrePaquete: "Materias",
                    entity: "materia"
                },
                abstract: true,
                resolve: module('materiaModule')
            })

            .state('materia.index', {
                url: '/',
                templateUrl: 'views/materia/materiaQuery.html',
                controller: 'MateriaCtrl_Index',
                data: {
                    nombreCasoUso: 'Consultar materias'
                },
                params: {execQuery: false, execQuerySamePage: false},
                resolve: {
                    nivelesResponse: ['CommonsSrv', function (commons) {
                        return commons.getNiveles();
                    }]
                }
            })
            .state('materia.create', {
                url: '/create',
                templateUrl: 'views/materia/materiaCreate.html',
                controller: 'MateriaCtrl_Create',
                data: {
                    nombreCasoUso: 'Crear materia'
                },
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
                data: {
                    nombreCasoUso: 'Modificar materia'
                },
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
                data: {
                    nombreCasoUso: 'Ver materia'
                },
                resolve: {
                    materiaResponse: ['loadMyModule', '$stateParams', 'MateriaSrv', function (loadMyModule, $stateParams, service) {
                        return service.findById($stateParams.id);
                    }]
                },
                controller: function ($scope, $state, materiaResponse) {
                    $scope.materia = materiaResponse.data;
                    $scope.imgIdx = Math.floor(Math.random() * (47 - 1 + 1)) + 1;
                    $scope.goIndex = function () {
                        $state.go('^.index');
                    }
                }

            })
            .state('practicaOdontologica', {
                url: '/practicaOdontologica',
                template: '<ui-view/>',
                abstract: true,
                data: {
                    nombrePaquete: 'Prácticas odontológicas',
                    entity: 'Práctica odontológica'
                },
                resolve: module('practicaOdontologicaModule')
            })
            .state('practicaOdontologica.index', {
                url: '/',
                templateUrl: 'views/practicaOdontologica/practicaOdontologicaQuery.html',
                params: {execQuery: false, execQuerySamePage: false},
                controller: 'PracticaOdontologicaCtrl_Index',
                data: {
                    nombreCasoUso: 'Consultar prácticas odontológicas'
                },
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
                data: {
                    nombreCasoUso: 'Crear práctica odontológica'
                },
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
                data: {
                    nombreCasoUso: 'Modificar práctica odontológica'
                },
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
                data: {
                    nombreCasoUso: 'Ver práctica odontológica'
                },
                resolve: {
                    practicaResponse: ['loadMyModule', '$stateParams', 'PracticaOdontologicaSrv', function (loadMyModule, $stateParams, service) {
                        return service.findById($stateParams.id);
                    }]
                },
                controller: function ($scope, $state, practicaResponse) {
                    $scope.practica = practicaResponse.data;
                    $scope.imgIdx = Math.floor(Math.random() * (47 - 1 + 1)) + 1;
                    $scope.goIndex = function () {
                        $state.go('^.index');
                    }
                }

            })
            .state('trabajoPractico', {
                url: '/trabajoPractico',
                template: '<ui-view/>',
                abstract: true,
                data: {
                    nombrePaquete: 'Trabajos prácticos',
                    entity: 'trabajo práctico'
                },
                resolve: module('trabajoPracticoModule')
            })
            .state('trabajoPractico.index', {
                url: '/',
                templateUrl: 'views/trabajoPractico/trabajoPracticoQuery.html',
                params: {execQuery: false, execQuerySamePage: false},
                controller: 'TrabajoPracticoCtrl_Index',
                data: {
                    nombreCasoUso: 'Consultar trabajo práctico'
                },
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
                data: {
                    nombreCasoUso: 'Crear trabajo práctico'
                },
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
                controllerAs: 'vm',
                data: {
                    nombreCasoUso: 'Modificar trabajo práctico'
                },
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
                data: {
                    nombreCasoUso: 'Ver trabajo práctico'
                },
                resolve: {
                    trabajoPracticoResponse: ['loadMyModule', '$stateParams', 'TrabajoPracticoSrv', function (loadMyModule, $stateParams, service) {
                        return service.findById($stateParams.id);
                    }]
                },
                controller: function ($scope, $state, trabajoPracticoResponse) {
                    $scope.trabajoPractico = trabajoPracticoResponse.data;
                    $scope.imgIdx = Math.floor(Math.random() * (47 - 1 + 1)) + 1;
                    $scope.goIndex = function () {
                        $state.go('^.index');
                    }
                }

            })
            .state('trabajoPractico.viewTPsInfo', {
                url: '/verInfoTps',
                templateUrl: 'views/trabajoPractico/viewTPsInfo.html',
                controller: 'TrabajoPracticoCtrl_ViewInfo',
                controllerAs: 'vm',
                data: {
                    nombreCasoUso: 'Consultar información de trabajos prácticos'
                },
                resolve: {
                    catedrasResponse: ['loadMyModule', 'TrabajoPracticoSrv', function (loadMyModule, service) {
                        return service.findAllCatedras();
                    }]
                }
            })
            .state('catedra', {
                url: '/catedra',
                template: '<ui-view/>',
                abstract: true,
                data: {
                    nombrePaquete: 'Cátedras',
                    entity: 'cátedra'
                },
                resolve: module('catedraModule')
            })
            .state('catedra.index', {
                url: '/',
                templateUrl: 'views/catedra/catedraQuery.html',
                params: {execQuery: false, execQuerySamePage: false},
                controller: 'CatedraCtrl_Index',
                data: {
                    nombreCasoUso: 'Consultar cátedras'
                },
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
                data: {
                    nombreCasoUso: 'Crear cátedra'
                },
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
                data: {
                    nombreCasoUso: 'Modificar cátedra'
                },
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
                data: {
                    nombreCasoUso: 'Ver cátedra'
                },
                resolve: {
                    catedraResponse: ['loadMyModule', '$stateParams', 'CatedraSrv', function (loadMyModule, $stateParams, service) {
                        return service.findById($stateParams.id);
                    }]
                },
                controller: function ($scope, $state, catedraResponse) {
                    $scope.catedra = catedraResponse.data;
                    $scope.imgIdx = Math.floor(Math.random() * (47 - 1 + 1)) + 1;
                    $scope.goIndex = function () {
                        $state.go('^.index');
                    }
                }
            })
            .state('usuario', {
                url: '/usuario',
                template: '<ui-view/>',
                abstract: true,
                data: {
                    nombrePaquete: 'Usuarios',
                    entity: 'usuario'
                },
                resolve: module('usuarioModule')
            })
            .state('usuario.index', {
                url: '/',
                templateUrl: 'views/usuario/usuarioQuery.html',
                params: {execQuery: false, execQuerySamePage: false},
                controller: 'UsuarioCtrl_Index',
                data: {
                    nombreCasoUso: 'Consultar usuarios'
                },
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
                data: {
                    nombreCasoUso: 'Crear usuario'
                },
                resolve: {
                    rolesResponse: ['loadMyModule', 'UsuarioSrv', function (loadMyModule, service) {
                        return service.getRoles();
                    }],
                    tiposDocResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, service) {
                        return service.getTiposDocumento();
                    }],
                    sexosResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, commons) {
                        return commons.getSexos();
                    }],
                    isAlumno: ['loadMyModule', function (loadMyModule) {
                        return false;
                    }]
                }
            })
            .state('usuario.registerAlumno', {
                url: '/createAlumno',
                templateUrl: 'views/usuario/usuarioCreate.html',
                controller: 'UsuarioCtrl_Create',
                data: {
                    nombreCasoUso: 'Crear alumno'
                },
                resolve: {
                    rolesResponse: ['loadMyModule', 'UsuarioSrv', function (loadMyModule, service) {
                        return service.getRoles();
                    }],
                    tiposDocResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, service) {
                        return service.getTiposDocumento();
                    }],
                    sexosResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, commons) {
                        return commons.getSexos();
                    }],
                    isAlumno: ['loadMyModule', function (loadMyModule) {
                        return true;
                    }]
                }
            })
            .state('usuario.edit', {
                url: '/edit/:id',
                templateUrl: 'views/usuario/usuarioEdit.html',
                controller: 'UsuarioCtrl_Edit',
                controllerAs: 'vm',
                data: {
                    nombreCasoUso: 'Modificar usuario'
                },
                resolve: {
                    rolesResponse: ['loadMyModule', 'UsuarioSrv', function (loadMyModule, service) {
                        return service.getRoles();
                    }],
                    usuarioResponse: ['loadMyModule', '$stateParams', 'UsuarioSrv', function (loadMyModule, $stateParams, service) {
                        return service.findUsuarioView($stateParams.id);
                    }],
                    imageResponse: ['loadMyModule', 'usuarioResponse', 'UsuarioSrv', function (loadMyModule, usuarioResponse, service) {
                        var usuario = usuarioResponse.data;
                        return service.findUserImage(usuario.imagenId);
                    }],
                    personaEmumsResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, commons) {
                        return commons.getPersonaEnums();
                    }]
                }
            })
            .state('usuario.view', {
                url: '/view/:id',
                templateUrl: 'views/usuario/usuarioView.html',
                data: {
                    nombreCasoUso: 'Ver usuario'
                },
                resolve: {
                    usuarioResponse: ['loadMyModule', '$stateParams', 'UsuarioSrv', function (loadMyModule, $stateParams, service) {
                        return service.findUsuarioView($stateParams.id);
                    }],
                    imageResponse: ['loadMyModule', 'usuarioResponse', 'UsuarioSrv', function (loadMyModule, usuarioResponse, service) {
                        var usuario = usuarioResponse.data;
                        return service.findUserImage(usuario.imagenId);
                    }]
                },
                controller: function ($scope, $state, usuarioResponse, imageResponse) {
                    $scope.usuario = usuarioResponse.data;
                    $scope.image = imageResponse.data;
                    $scope.autoridad = $scope.usuario.autoridadDTO;
                    $scope.alumno = $scope.usuario.alumnoDTO;
                    $scope.profesor = $scope.usuario.profesorDTO;


                    $scope.goIndex = function () {
                        $state.go('^.index');
                    }
                    $scope.hasFile = function () {
                        var img = $scope.image;
                        return img &&
                            angular.isDefined(img) &&
                            img != null &&
                            img.size > 0;
                    }
                }
            })
            .state('paciente', {
                url: '/paciente',
                template: '<ui-view/>',
                abstract: true,
                data: {
                    nombrePaquete: 'Pacientes',
                    entity: 'paciente'
                },
                resolve: module('pacienteModule')
            })
            .state('paciente.index', {
                url: '/',
                templateUrl: 'views/paciente/pacienteQuery.html',
                data: {
                    nombreCasoUso: 'Consultar pacientes'
                },
                params: {execQuery: false, execQuerySamePage: false},
                controller: 'PacienteCtrl_Index',
                controllerAs: 'vm',
                resolve: {
                    tiposDocumentoResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, commons) {
                        return commons.getTiposDocumento();
                    }],
                    sexosResponse: ['loadMyModule', "CommonsSrv", function (loadMyModule, commons) {
                        return commons.getSexos();
                    }]
                }
            })
            .state('paciente.create', {
                url: '/create',
                templateUrl: 'views/paciente/pacienteCreate.html',
                data: {
                    nombreCasoUso: 'Crear paciente'
                },
                controller: 'PacienteCtrl_EditCreate',
                controllerAs: 'vm',
                resolve: {
                    nivelesResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, commons) {
                        return commons.getNiveles();
                    }],
                    tiposDocumentoResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, commons) {
                        return commons.getTiposDocumento();
                    }],
                    sexosResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, commons) {
                        return commons.getSexos();
                    }],
                    provinciaResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, commons) {
                        return commons.getProvincias();
                    }],
                    ciudadesResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, commons) {
                        return commons.getCiudades();
                    }],
                    barriosResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, commons) {
                        return commons.getBarrios();
                    }],
                    estadosResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, commons) {
                        return commons.getEstadosCivil();
                    }],
                    trabajosResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, commons) {
                        return commons.getTrabajos();
                    }],
                    obrasSocialesResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, commons) {
                        return commons.getObrasSociales();
                    }],
                    nivelesEstudioResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, commons) {
                        return commons.getNivelesEstudio();
                    }],
                    nacionalidadesResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, commons) {
                        return commons.getNacionalidaes();
                    }],
                    pacienteResponse: ['loadMyModule', '$stateParams', 'PacienteSrv', function (loadMyModule, $stateParams, service) {
                        return service.initPaciente();
                    }],
                    imagenResponse: ['loadMyModule', function (loadMyModule) {
                        return {data: null};
                    }]
                }
            })
            .state('paciente.odontograma', {
                url: '/odontograma',
                templateUrl: 'views/hc/odontograma.html',
                controller: 'OdontogramaCtrl',
                controllerAs: 'vm',
                resolve: {
                    odontogramaResponse: ['loadMyModule', 'PacienteSrv', function (loadMyModule, service) {
                        return service.initOdontograma();
                    }],
                    hallazgosResponse: ['loadMyModule', 'PacienteSrv', function (loadMyModule, service) {
                        return service.findHallazgos();
                    }]
                }
            })
            .state('historiaClinica', {
                url: '/historiaClinica/:id?editing',
                templateUrl: 'views/hc/historiaClinicaMain.html',
                controller: 'HistoriaClinicaCtrl_Main',
                controllerAs: 'vm',
                data: {
                    fullPage: true,
                    nombrePaquete: 'Historia clínica'
                },
                resolve: {
                    loadMyModule: ['$ocLazyLoad', function ($ocLazyLoad) {
                        //lazyload de un modulo
                        return $ocLazyLoad.load('historiaClinicaModule');
                    }],
                    pacienteResponse: ['loadMyModule', '$stateParams', 'HistoriaClinicaSrv', function (loadMyModule, $stateParams, service) {
                        return service.findPacienteLightById($stateParams.id);
                    }]
                }
            })
            .state('historiaClinica.datosPersonalesView', {
                url: '/datosPersonales/view',
                templateUrl: 'views/hc/datosPersonales/datosPersonales.html',
                controllerAs: 'vm',
                data: {
                    fullPage: true,
                    nombreCasoUso: 'Ver historia clínica'
                },
                controller: function ($scope, pacienteResponse, imagenResponse) {
                    var vm = this;
                    vm.paciente = pacienteResponse.data;
                    vm.image = imagenResponse.data;
                    vm.hasFile = hasFile;

                    function hasFile() {
                        var img = vm.image;
                        return img &&
                            angular.isDefined(img) &&
                            img != null &&
                            img.size > 0;
                    }
                },
                resolve: {
                    pacienteResponse: ['loadMyModule', '$stateParams', 'HistoriaClinicaSrv', function (loadMyModule, $stateParams, service) {
                        return service.findPacienteById($stateParams.id);
                    }],
                    imagenResponse: ['loadMyModule', 'pacienteResponse', 'PacienteSrv', function (loadMyModule, pacienteResponse, service) {
                        var paciente = pacienteResponse.data;
                        return service.findPacienteImage(paciente.imagenId);
                    }]
                }
            })
            .state('historiaClinica.antecedentesView', {
                url: '/antecedentes/view',
                templateUrl: 'views/hc/antecedentes/antecedentes.html',
                controllerAs: 'vm',
                controller: function ($scope, pacienteResponse) {
                    var vm = this;
                    vm.paciente = pacienteResponse.data;
                },
                data: {
                    fullPage: true,
                    nombreCasoUso: 'Ver historia clínica'
                },
                resolve: {
                    pacienteResponse: ['loadMyModule', '$stateParams', 'HistoriaClinicaSrv', function (loadMyModule, $stateParams, service) {
                        return service.findPacienteById($stateParams.id);
                    }]
                }
            })
            .state('historiaClinica.diagnosticoView', {
                url: '/diagnostico/view',
                templateUrl: 'views/hc/diagnostico/diagnosticoView.html',
                controllerAs: 'vm',
                controller: 'DiagnosticoCtrl_View',
                data: {
                    fullPage: true,
                    nombreCasoUso: 'Ver historia clínica'
                },
                resolve: {
                    pacienteResponse: ['loadMyModule', '$stateParams', 'HistoriaClinicaSrv', function (loadMyModule, $stateParams, service) {
                        return service.findPacienteById($stateParams.id);
                    }],
                    estadosDiagnosticoResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, commons) {
                        return commons.getEstadosDiagnostico();
                    }],
                    odontogramaResponse: ['loadMyModule', '$stateParams', 'DiagnosticoSrv', function (loadMyModule, $stateParams, service) {
                        return service.findOdontogramaUriById($stateParams.id);
                    }]
                }
            })
            .state('historiaClinica.atencionesView', {
                url: '/atenciones/view',
                templateUrl: 'views/hc/atenciones/atenciones.html',
                controller: 'AtencionCtrl_View',
                controllerAs: 'vm',
                data: {
                    fullPage: true,
                    nombreCasoUso: 'Ver historia clínica'
                },
                resolve: {
                    catedrasResponse: ['loadMyModule', 'AtencionSrv', function (loadMyModule, service) {
                        return service.getCatedras();
                    }]
                }
            })
            .state('historiaClinica.documentacionesView', {
                url: '/documentaciones/view',
                templateUrl: 'views/hc/documentaciones/documentaciones.html',
                controllerAs: 'vm',
                controller: function ($scope, docResponse, $window, $location) {
                    var vm = this;
                    vm.documentaciones = docResponse.data;

                    vm.openInNewTab = function (file) {
                        var base = $location.protocol() + '://' + $location.host() + ':' + $location.port() + "/Odontologia-web/api/file/";
                        if (file.extension == 'PDF') {
                            $window.open(base + 'pdf?id=' + file.id);
                        } else {
                            $window.open(base + 'image?id=' + file.id);
                        }
                    }

                },
                data: {
                    fullPage: true,
                    nombreCasoUso: 'Ver historia clínica'
                },
                resolve: {
                    docResponse: ['loadMyModule', 'HistoriaClinicaSrv', '$stateParams', function (loadMyModule, service, $stateParams) {
                        return service.findDocumentacionesByPaciente($stateParams.id);
                    }]
                }
            })
            .state('historiaClinica.datosPersonalesEdit', {
                url: '/datosPersonales/edit',
                templateUrl: 'views/hc/datosPersonales/datosPersonalesEdit.html',
                controller: 'PacienteCtrl_EditCreate',
                controllerAs: 'vm',
                data: {
                    fullPage: true,
                    nombreCasoUso: 'Editar historia clínica'
                },
                resolve: {
                    nivelesResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, commons) {
                        return commons.getNiveles();
                    }],
                    tiposDocumentoResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, commons) {
                        return commons.getTiposDocumento();
                    }],
                    sexosResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, commons) {
                        return commons.getSexos();
                    }],
                    provinciaResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, commons) {
                        return commons.getProvincias();
                    }],
                    ciudadesResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, commons) {
                        return commons.getCiudades();
                    }],
                    barriosResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, commons) {
                        return commons.getBarrios();
                    }],
                    estadosResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, commons) {
                        return commons.getEstadosCivil();
                    }],
                    trabajosResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, commons) {
                        return commons.getTrabajos();
                    }],
                    obrasSocialesResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, commons) {
                        return commons.getObrasSociales();
                    }],
                    nivelesEstudioResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, commons) {
                        return commons.getNivelesEstudio();
                    }],
                    nacionalidadesResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, commons) {
                        return commons.getNacionalidaes();
                    }],
                    pacienteResponse: ['loadMyModule', '$stateParams', 'PacienteSrv', function (loadMyModule, $stateParams, service) {
                        return service.findPacienteLightById($stateParams.id);
                    }],
                    imagenResponse: ['loadMyModule', 'pacienteResponse', 'PacienteSrv', function (loadMyModule, pacienteResponse, service) {
                        var paciente = pacienteResponse.data;
                        return service.findPacienteImage(paciente.imagenId);
                    }]
                }
            })
            .state('historiaClinica.antecedentesEdit', {
                url: '/antecedentes/edit',
                templateUrl: 'views/hc/antecedentes/antecedentesEdit.html',
                controller: 'PacienteCtrl_EditCreate',
                controllerAs: 'vm',
                data: {
                    fullPage: true,
                    nombreCasoUso: 'Editar historia clínica'
                },
                resolve: {
                    nivelesResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, commons) {
                        return commons.getNiveles();
                    }],
                    tiposDocumentoResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, commons) {
                        return commons.getTiposDocumento();
                    }],
                    sexosResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, commons) {
                        return commons.getSexos();
                    }],
                    provinciaResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, commons) {
                        return commons.getProvincias();
                    }],
                    ciudadesResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, commons) {
                        return commons.getCiudades();
                    }],
                    barriosResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, commons) {
                        return commons.getBarrios();
                    }],
                    estadosResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, commons) {
                        return commons.getEstadosCivil();
                    }],
                    trabajosResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, commons) {
                        return commons.getTrabajos();
                    }],
                    obrasSocialesResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, commons) {
                        return commons.getObrasSociales();
                    }],
                    nivelesEstudioResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, commons) {
                        return commons.getNivelesEstudio();
                    }],
                    nacionalidadesResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, commons) {
                        return commons.getNacionalidaes();
                    }],
                    pacienteResponse: ['loadMyModule', '$stateParams', 'PacienteSrv', function (loadMyModule, $stateParams, service) {
                        return service.findById($stateParams.id);
                    }],
                    imagenResponse: ['loadMyModule', function(loadMyModule) {
                        return {data: null};
                    }]
                }
            })
            .state('historiaClinica.diagnosticoEdit', {
                url: '/diagnostico/edit',
                templateUrl: 'views/hc/diagnostico/diagnosticoEdit.html',
                controller: 'DiagnosticoCtrl_Edit',
                controllerAs: 'vm',
                data: {
                    fullPage: true,
                    nombreCasoUso: 'Editar historia clínica'
                },
                resolve: {
                    pacienteId: ['loadMyModule', '$stateParams', function (loadMyModule, $stateParams) {
                        return $stateParams.id;
                    }],
                    diagnosticosResponse: ['loadMyModule', '$stateParams', 'DiagnosticoSrv', function (loadMyModule, $stateParams, service) {
                        return service.findOpenDiagnosticos($stateParams.id);
                    }],
                    finalStatesResponse: ['loadMyModule', 'DiagnosticoSrv', function (loadMyModule, service) {
                        return service.findFinalStates();
                    }],
                    hallazgosResponse: ['loadMyModule', '$stateParams', 'PacienteSrv', function (loadMyModule, $stateParams, service) {
                        return service.findHallazgos();
                    }],
                    odontogramaResponse: ['loadMyModule', '$stateParams', 'DiagnosticoSrv', function (loadMyModule, $stateParams, service) {
                        return service.findOdontogramaById($stateParams.id);
                    }],
                    practicasResponse: ['loadMyModule', 'DiagnosticoSrv', function (loadMyModule, service) {
                        return service.findPracticas();
                    }]
                }
            })
            .state('historiaClinica.documentacionesEdit', {
                url: '/documentaciones/edit',
                templateUrl: 'views/hc/documentaciones/documentacionesEdit.html',
                controllerAs: 'vm',
                controller: 'DocumentacionesEdit_Ctrl',
                data: {
                    fullPage: true,
                    nombreCasoUso: 'Editar historia clínica'
                },
                resolve: {
                    pacienteId: ['loadMyModule', '$stateParams', function (loadMyModule, $stateParams) {
                        return $stateParams.id;
                    }],
                    docResponse: ['loadMyModule', 'HistoriaClinicaSrv', '$stateParams', function (loadMyModule, service, $stateParams) {
                        return service.findDocumentacionesByPaciente($stateParams.id);
                    }]
                }
            })
            .state('profesor', {
                url: '/profesor',
                template: '<ui-view/>',
                abstract: true,
                data: {
                    nombrePaquete: 'Profesores',
                    entity: 'profesor'
                },
                resolve: module('profesorModule')
            })
            .state('profesor.editCatedrasProfesor', {
                url: '/editCatedrasProfesor.html',
                templateUrl: 'views/profesor/editCatedrasProfesor.html',
                controller: 'CatedrasProfesorCtrl_Edit',
                data: {
                    nombreCasoUso: 'Asignar cátedra a profesor'
                },
                controllerAs: 'vm',
                resolve: {
                    materiasResponse: ['loadMyModule', 'ProfesorSrv', function (loadMyModule, service) {
                        return service.findAllMaterias();
                    }]
                }
            })
            .state('asignacion', {
                url: '/asignacion',
                template: '<ui-view/>',
                abstract: true,
                data: {
                    nombrePaquete: 'Asignaciones de paciente',
                    entity: 'asignación de paciente'
                },
                resolve: module('asignacionModule')
            })
            .state('asignacion.view', {
                url: '/view/:id',
                templateUrl: 'views/asignacion/asignacionView.html',
                data: {
                    nombreCasoUso: 'Ver asignación'
                },
                controllerAs: 'vm',
                resolve: {
                    asignacionResponse: ['$stateParams', 'AsignacionSrv', function ($stateParams, service) {
                        return service.findById($stateParams.id);
                    }],
                    atencionResponse: ['$stateParams', 'AsignacionSrv', 'asignacionResponse', function ($stateParams, service, asignacionResponse) {
                       var asignacion = asignacionResponse.data;
                       if(asignacion == null || !angular.isDefined(asignacion)){
                           return {data: null};
                       }
                       return service.findAtencionByAsignacion(asignacion.id);
                    }]},
                controller: function ($scope, $state, asignacionResponse, atencionResponse, $location, $window) {
                    var vm = this;
                    vm.asignacion = asignacionResponse.data;
                    vm.atencion = atencionResponse.data;


                    $scope.$parent.$parent.nombreCasoUso = "Ver asignación"
                    vm.goIndex = goIndex;
                    vm.verHistoriaClinica = verHistoriaClinica;
                    vm.printAtencion = printAtencion;

                    vm.colorMouseOver = colorMouseOver;
                    vm.colorMouseLeave = colorMouseLeave;
                    vm.colorIcon = '#00B0FF';

                    vm.imgIdx = Math.floor(Math.random() * (47 - 1 + 1)) + 1;
                    function goIndex() {
                        $state.go('^.index');
                    }

                    function verHistoriaClinica(){
                        $state.go('historiaClinica', {id : vm.asignacion.idPaciente});
                    }

                    function colorMouseOver() {
                        vm.colorIcon = '#E91E63';
                    }

                    function colorMouseLeave() {
                        vm.colorIcon = '#00B0FF';
                    }

                    function printAtencion() {
                        var id = vm.atencion.id;
                        var base = $location.protocol() + '://' + $location.host() + ':' + $location.port() + "/Odontologia-web/api/asignacion/printAtencionRealizada";
                        $window.open(base + '?id=' + id);
                    }
                }
            })
            .state('asignacion.index', {
                url: '/',
                templateUrl: 'views/asignacion/asignacionQuery.html',
                controller: 'AsignacionCtrl_Index',
                controllerAs: 'vm',
                data: {
                    fullPage: true,
                    nombreCasoUso: 'Consultar asignaciones'
                },
                params: {execQuery: false, execQuerySamePage: false},
                resolve: {
                    practicasResponse: ['loadMyModule', 'AsignacionSrv', function (loadMyModule, service) {
                        return service.getPracticas();
                    }],
                    tiposDocumentoResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, commons) {
                        return commons.getTiposDocumento();
                    }],
                    trabajosPracticosResponse: ['loadMyModule', 'AsignacionSrv', function (loadMyModule, service) {
                        return service.getTrabajosPracticos();
                    }],
                    estadosAsignacionResponse: ['loadMyModule', 'AsignacionSrv', function (loadMyModule, service) {
                        return service.getEstadosAsignaciones();
                    }]
                }
            })
            .state('asignacion.autorizar', {
                url: '/autorizar',
                templateUrl: 'views/asignacion/asignacionAutorizar.html',
                controller: 'AsignacionCtrl_Autorizar',
                controllerAs: 'vm',
                data: {
                    fullPage: true,
                    nombreCasoUso: 'Autorizar asignaciones'
                },
                resolve: {
                    practicasResponse: ['loadMyModule', 'AsignacionSrv', function (loadMyModule, service) {
                        return service.getPracticas();
                    }],
                    estadosAsignacionResponse: ['loadMyModule', 'AsignacionSrv', function (loadMyModule, service) {
                        return service.getEstadosAsignaciones();
                    }],
                    tiposDocumentoResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, commons) {
                        return commons.getTiposDocumento();
                    }],
                    catedrasResponse: ['loadMyModule', 'authFactory', 'AsignacionSrv', function (loadMyModule, authFactory, service) {
                        var user = authFactory.getAuthData();
                        if (user && user.profesor) {
                            return service.findCatedrasByProfesor(user.id);
                        } else {
                            return service.getCatedras();
                        }
                    }]
                }
            })
            .state('asignacion.create', {
                url: '/create',
                templateUrl: 'views/asignacion/asignacionEditCreate.html',
                controller: 'AsignacionCtrl_EditCreate',

                controllerAs: 'vm',
                data: {
                    updating: false,
                    nombreCasoUso: 'Crear asignación'
                },
                resolve: {
                    tiposDocumentoResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, commons) {
                        return commons.getTiposDocumento();
                    }],
                    sexosResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, commons) {
                        return commons.getSexos();
                    }],
                    catedrasResponse: ['loadMyModule', 'AsignacionSrv', function (loadMyModule, service) {
                        return service.getCatedras();
                    }],
                    asignacionResponse: ['$stateParams', 'loadMyModule', 'AsignacionSrv',
                        function ($stateParams, loadMyModule, service) {
                            return null;
                        }]
                }
            })
            .state('asignacion.edit', {
                url: '/edit/:id',
                templateUrl: 'views/asignacion/asignacionEditCreate.html',
                controller: 'AsignacionCtrl_EditCreate',
                controllerAs: 'vm',
                data: {
                    updating: true,
                    nombreCasoUso: 'Modificar asignación'
                },
                resolve: {
                    tiposDocumentoResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, commons) {
                        return commons.getTiposDocumento();
                    }],
                    sexosResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, commons) {
                        return commons.getSexos();
                    }],
                    catedrasResponse: ['loadMyModule', 'AsignacionSrv', function (loadMyModule, service) {
                        return service.getCatedras();
                    }],
                    asignacionResponse: ['$stateParams', 'loadMyModule', 'AsignacionSrv', function ($stateParams, loadMyModule, service) {
                        return service.findById($stateParams.id);
                    }]
                }
            })
            .state('atencion', {
                url: '/atencion',
                template: '<ui-view/>',
                abstract: true,
                data: {
                    nombrePaquete: 'Atenciones'
                },
                resolve: module('atencionModule')
            })
            .state('atencion.create', {
                url: '/create/:idAsignacion',
                templateUrl: 'views/atencion/atencionCreate.html',
                controllerAs: 'vm',
                controller: 'AtencionCtrl_Create',
                data: {
                    nombreCasoUso: 'Crear atención'
                },
                resolve: {
                    asignacionResponse: ['loadMyModule', '$stateParams', 'AtencionSrv', function (loadMyModule, $stateParams, service) {
                        return service.findAsignacion($stateParams.idAsignacion);
                    }]
                }
            })
            .state('permisos', {
                url: '/permisos',
                template: '<ui-view/>',
                abstract: true,
                data: {
                    nombrePaquete: 'Soporte'
                },
                resolve: module('permisosModule')
            })
            .state('permisos.administrate', {
                url: '/administrar',
                templateUrl: 'views/permisos/permisos.html',
                controllerAs: 'vm',
                data: {
                    nombreCasoUso: 'Administrar permisos de rol'
                },
                controller: 'PermisosCtrl',
                resolve: {
                    funcionalidadesResponse: ['loadMyModule', 'PermisosSrv', function (loadMyModule, service) {
                        return service.findAllFuncionalidades();
                    }],
                    rolesResponse: ['loadMyModule', 'PermisosSrv', function (loadMyModule, service) {
                        return service.findAllRoles();
                    }]
                }
            })
            .state('backup', {
                url: '/backup',
                template: '<ui-view/>',
                abstract: true,
                data: {
                    nombrePaquete: 'Soporte'
                },
                resolve: module('backupModule')
            })
            .state('backup.administrate', {
                url: '/administrar',
                templateUrl: 'views/backup/administrar.html',
                data: {
                    nombreCasoUso: 'Respaldo de datos de sistema'
                },
                controllerAs: 'vm',
                controller: 'BackupCtrl'
            })
            .state('bonoConsulta', {
                url: '/bonoConsulta',
                template: '<div></div>',
                data: {
                    nombrePaquete: 'Pacientes',
                    nombreCasoUso: 'Emitir bono de consulta'
                },
                controller: function ($scope, $mdDialog, $state, $window, $location) {
                    $scope.showAdvanced = function () {
                        $mdDialog.show({
                            controller: DialogController,
                            templateUrl: 'views/bono.html',
                            parent: angular.element(document.body)
                        })
                            .then(function () {
                                var base = $location.protocol() + '://' + $location.host() + ':' + $location.port() + "/Odontologia-web/api/commons/getBono";
                                $window.open(base);
                                $state.go('home');
                            }, function () {
                                $state.go('home');
                            });
                    };
                    $scope.showAdvanced();

                    function DialogController($scope, $mdDialog) {
                        $scope.aceptar = function () {
                            $mdDialog.hide();
                        };
                        $scope.cancelar = function () {
                            $mdDialog.cancel();
                        };
                    }
                }

            })

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
                        url('/trabajoPractico/trabajoPracticoEditCtrl.js'),
                        url('/trabajoPractico/trabajoPracticoViewInfoCtrl.js')
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
                        url('/persona/personaSrv.js'),
                        url('/persona/datosUsuarioCtrl.js')
                    ]
                },
                {
                    name: 'pacienteModule',
                    files: [
                        url('/paciente/pacienteSrv.js'),
                        url('/paciente/pacienteIndexCtrl.js'),
                        url('/paciente/pacienteEditCreateCtrl.js'),
                        url('/hc/diagnostico/odontogramaCtrl.js')
                    ]
                },
                {
                    name: 'historiaClinicaModule',
                    files: [
                        url('/historiaClinica/historiaClinicaSrv.js'),
                        url('/hc/historiaClinicaMainCtrl.js'),
                        url('/hc/diagnostico/diagnosticoSrv.js'),
                        url('/hc/diagnostico/diagnosticoEditCtrl.js'),
                        url('/hc/diagnostico/diagnosticoViewCtrl.js'),
                        url('/hc/atenciones/atencionesViewCtrl.js'),
                        url('/hc/documentaciones/documentacionesEditCtrl.js')
                    ]
                },
                {
                    name: 'asignacionModule',
                    files: [
                        url('/asignacion/asignacionSrv.js'),
                        url('/asignacion/asignacionEditCreateCtrl.js'),
                        url('/asignacion/asignacionIndexCtrl.js'),
                        url('/asignacion/asignacionAutorizarCtrl.js')
                    ]
                },
                {
                    name: 'profesorModule',
                    files: [
                        url('/profesor/profesorSrv.js'),
                        url('/profesor/editCatedrasProfesorCtrl.js')]
                },
                {
                    name: 'atencionModule',
                    files: [
                        url('/atencion/atencionSrv.js'),
                        url('/atencion/atencionCreateCtrl.js')
                    ]
                },
                {
                    name: 'permisosModule',
                    files: [
                        url('/permisos/permisosCtrl.js'),
                        url('/permisos/permisosSrv.js')
                    ]
                },
                {
                    name: 'backupModule',
                    files: [
                        url('/backup/backupCtrl.js'),
                        url('/backup/backupSrv.js')
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
angular.module('historiaClinicaModule', ['pacienteModule', 'atencionModule']);
angular.module('atencionModule', []);
angular.module('profesorModule', []);
angular.module('permisosModule', []);
angular.module('backupModule', []);


odontologiaApp.controller('AppController', ['$scope', '$state', 'authFactory', '$filter', '$mdSidenav', '$q', '$mdDialog',
    function ($scope, $state, authFactory, $filter, $mdSidenav, $q, $mdDialog) {

        $scope.performSubmit = performSubmit;
        $scope.handleError = handleError;
        $scope.menuOpen = false;
        $scope.selectedMenuItem = {};
        $scope.validationErrorFromServer = {
            error: false,
            data: {}
        };
        $scope.session = {};
        $scope.casoUso = null;
        $scope.showAyudaContextual = showAyudaContextual;
        $scope.mostrarAyuda = false;

        $scope.nombreCasoUso = null;

        retrieveSession();

        function retrieveSession() {
            $scope.session.user = authFactory.getAuthData();
            $scope.session.image = authFactory.getImage();
            if ($scope.session.user && !angular.isDefined($scope.session.image) && $scope.session.user.imagenId) {
                authFactory.lookForImage($scope.session.user.imagenId).then(function (response) {
                    $scope.session.image = response.data;
                });
            }
            retrieveMenu().then(function (response) {
                $scope.menu = response;
            });
        }

        $scope.toggleSidenav = function (menuId) {
            $mdSidenav(menuId).toggle();
        };

        $scope.show = "false";
        $scope.showFilters = function () {
            $scope.show = true;
        };

        $scope.$on('$stateChangeStart',
            function (event, toState, toParams, fromState, fromParams) {

                $scope.validationErrorFromServer.error = false;
                if ($scope.session.user) {
                    $scope.mostrarAyuda = toState.name == 'home' || toState.name == 'selectRol' ? false : true;
                    if (toState.name == 'landingPage' || toState.name.startsWith('historiaClinica')) {
                        $scope.menuOpen = false;
                    } else {
                        $scope.menuOpen = true;
                    }
                } else {
                    $scope.menuOpen = false;
                }
                if (authFactory.isAuthenticated()) {
                    authFactory.updateExpiresTime();
                } else {
                    $scope.user = undefined;
                    $scope.menu = [];
                }

                if (toState.name === 'landingPage' && authFactory.isAuthenticated()) {
                    event.preventDefault();
                    $state.go('home');
                }
                if (toState.data && toState.data.isFree) {
                    return;
                }
                if (!authFactory.isAuthenticated()) {
                    event.preventDefault();
                    $state.go('landingPage');
                }
                if (toState.data && toState.data.fullPage) {
                    $scope.menuOpen = false;
                } else {
                    $scope.menuOpen = true;
                }
            });

        $scope.$on('$stateChangeSuccess',
            function (event, toState, toParams, fromState, fromParams) {
                $scope.nombrePaquete = toState.data ? toState.data.nombrePaquete : null;
                $scope.nombreCasoUso = toState.data ? toState.data.nombreCasoUso : null;
                if (toState.data && toState.data.fullPage) {
                    $mdSidenav('left').close();
                }
            })

        $scope.$on('authChanged', function () {
            retrieveSession();
            $scope.selectedMenuItem = {};
            $scope.menu = null;
            retrieveMenu().then(function (response) {
                $scope.menu = response;
            });
        });

        function retrieveMenu() {
            var deferred = $q.defer();
            if ($scope.session.user && !$scope.session.user.missingRole) {
                if ($scope.menu && $scope.menu.length) {
                    deferred.resolve($scope.menu);
                } else {
                    authFactory.getPermisos($scope.session.user.rol.key)
                        .then(function (response) {
                            deferred.resolve(buildMenu(response.data));
                        })
                }
            }
            return deferred.promise;
        }

        $scope.goHome = goHome;

        $scope.$on('authChangedMissingStep', function () {
            retrieveSession();
            $scope.selectedMenuItem = {};
        });

        $scope.userHasImage = function () {
            var file = $scope.session.image;
            return file &&
                angular.isDefined(file) &&
                file != null &&
                file.size > 0
        }

        $scope.logOut = function () {
            $scope.session = authFactory.logout();
            $scope.selectedMenuItem = -1;
            $scope.menu = [];
            $state.go('landingPage');
        };

        $scope.menuItemClick = function (item) {
            if (item.dropdown) {
                item.open = !item.open;
            } else {
                $scope.selectedMenuItem = item;
                $state.go(item.ref);
            }
        }

        function showAyudaContextual() {
            var paquete = $state.current.data.nombrePaquete;
            var casoUso = $state.current.data.nombreCasoUso;
            var entity = $state.current.data.entity;

            if (casoUso == 'Consultar información de trabajos prácticos'
                || casoUso == 'Asignar cátedra a profesor'
                || casoUso == 'Respaldo de datos de sistema'
                || casoUso == 'Administrar permisos de rol'
                || casoUso == 'Autorizar asignaciones'
                || casoUso == 'Estadísticas') {
                showAyuda(casoUso);
                return;
            }

            if (paquete == 'Cátedras' || paquete == 'Trabajos prácticos' || paquete == 'Usuarios'
                || paquete == 'Materias' || paquete == 'Prácticas odontológicas') {
                showAyudaABM(paquete, casoUso, entity);
                return;
            }

            if(paquete == 'Asignaciones de paciente'){
                showAyudaAsignacion(casoUso, paquete);
                return;
            }

            if(paquete == 'Historia clínica' || casoUso == 'Consultar pacientes'){
                showAyudaPaciente(casoUso, paquete);
                return;
            }
        }

        function showAyuda(casoUso) {
            var nombreArchivo = $filter('nombrarArchivo')(casoUso);
            var imagenConsulta = 'resources/img/ayuda/' + nombreArchivo + '.png';

            $mdDialog.show({
                templateUrl: 'views/ayuda/ayuda.html',
                parent: angular.element(document.body),
                targetEvent: event,
                locals: {
                    casoUso: casoUso,
                    imagenConsulta: imagenConsulta
                },
                clickOutsideToClose: true,
                controller: function DialogController($scope, $mdDialog, casoUso, imagenConsulta) {
                    $scope.casoUso = casoUso;
                    $scope.imagenConsulta = imagenConsulta;

                    $scope.esConsultarTrabajoPractico = casoUso === 'Consultar información de trabajos prácticos' ? true : false;
                    $scope.esAsignarProfesorCatedra = casoUso === 'Asignar cátedra a profesor' ? true : false;
                    $scope.esRespaldoDatos = casoUso === 'Respaldo de datos de sistema' ? true : false;
                    $scope.esAdministrarPermisos = casoUso === 'Administrar permisos de rol' ? true : false;
                    $scope.esAutorizarAsignaciones = casoUso === 'Autorizar asignaciones' ? true : false;
                    $scope.esEstadistica = casoUso === 'Estadísticas' ? true : false;

                    $scope.close = function () {
                        $mdDialog.hide();
                    };
                }
            }).then(function () {
                }, function () {
                });
        }

        function showAyudaABM(paquete, casoUso, entity) {
            var nombreArchivoRegistrar = $filter('nombrarArchivo')(entity, 0);
            var nombreArchivoBaja = $filter('nombrarArchivo')(entity, 1);
            var nombreArchivoConsulta = $filter('nombrarArchivo')(entity, 2);

            var imagenConsulta = 'resources/img/ayuda/' + nombreArchivoConsulta + '.png';
            var imagenBaja = 'resources/img/ayuda/' + nombreArchivoBaja + '.png';
            var imagenRegistrar = 'resources/img/ayuda/' + nombreArchivoRegistrar + '.png';

            $mdDialog.show({
                templateUrl: 'views/ayuda/ayudaABM.html',
                parent: angular.element(document.body),
                targetEvent: event,
                locals: {
                    paquete: paquete,
                    casoUso: casoUso,
                    entities: paquete,
                    imagenConsulta: imagenConsulta,
                    imagenBaja: imagenBaja,
                    imagenRegistrar: imagenRegistrar,
                    entity: entity
                },
                clickOutsideToClose: true,
                controller: function DialogController($scope, $mdDialog, paquete, casoUso, entities, imagenConsulta, imagenBaja, imagenRegistrar, entity) {
                    $scope.paquete = paquete;
                    $scope.casoUso = casoUso;
                    $scope.entities = entities;
                    $scope.imagenConsulta = imagenConsulta;
                    $scope.imagenBaja = imagenBaja;
                    $scope.imagenRegistrar = imagenRegistrar;
                    $scope.entity = entity;
                    $scope.esCatedra = entity === 'cátedra' ? true : false;
                    $scope.esUsuario = entity === 'usuario' ? true : false;

                    $scope.close = function () {
                        $mdDialog.hide();
                    };
                }
            }).then(function () {
                }, function () {
                });
        }

        function showAyudaAsignacion(paquete, casoUso){

            $mdDialog.show({
                templateUrl: 'views/ayuda/ayudaAsignacion.html',
                parent: angular.element(document.body),
                targetEvent: event,
                locals: {
                    casoUso: casoUso,
                    paquete: paquete
                },
                clickOutsideToClose: true,
                controller: function DialogController($scope, $mdDialog, casoUso, paquete) {
                    $scope.casoUso = casoUso;
                    $scope.paquete = paquete;

                    $scope.close = function () {
                        $mdDialog.hide();
                    };
                }
            }).then(function () {
                }, function () {
                });

        }

        function showAyudaPaciente(paquete, casoUso){
            $mdDialog.show({
                templateUrl: 'views/ayuda/ayudaPaciente.html',
                parent: angular.element(document.body),
                targetEvent: event,
                locals: {
                    casoUso: casoUso,
                    paquete: paquete
                },
                clickOutsideToClose: true,
                controller: function DialogController($scope, $mdDialog, casoUso, paquete) {
                    $scope.casoUso = casoUso;
                    $scope.paquete = paquete;

                    $scope.close = function () {
                        $mdDialog.hide();
                    };
                }
            }).then(function () {
                }, function () {
                });
        }

        function buildMenu(permisos) {
            var resultMenu = [];

            var items = $filter('filter')(permisos, function (permission) {
                return permission.funcionalidad.esItemMenu;
            });

            angular.forEach(items, function (item) {
                if (item.funcionalidad.grupoFuncionalidad && !$filter('filter')(resultMenu,function (itemMenu) {
                    return itemMenu.name == item.funcionalidad.grupoFuncionalidad;
                }).length) {
                    resultMenu.push({
                        dropdown: true,
                        name: item.funcionalidad.grupoFuncionalidad,
                        open: false,
                        subItems: []
                    })
                }
            });

            angular.forEach(items, function (item) {
                if (!item.funcionalidad.grupoFuncionalidad) {
                    resultMenu.push({
                        name: item.funcionalidad.nombre,
                        ref: item.funcionalidad.estadoAsociado,
                        id: item.funcionalidad.id
                    })
                } else {
                    for (var i = 0; i < resultMenu.length; i++) {
                        if (item.funcionalidad.grupoFuncionalidad == resultMenu[i].name) {
                            resultMenu[i].subItems.push({
                                    name: item.funcionalidad.nombre,
                                    ref: item.funcionalidad.estadoAsociado,
                                    id: item.funcionalidad.id,
                                    group: item.funcionalidad.grupoFuncionalidad
                                }
                            )
                        }
                    }
                }
            });
            return resultMenu;
        }

        function performSubmit(toExecute, form) {
            $scope.validationErrorFromServer.error = false;
            $scope.validationErrorFromServer.data = {};
            if (form.$valid) {
                toExecute();
            } else {
                angular.forEach(form.$error, function (field) {
                    angular.forEach(field, function (errorField) {
                        console.log(field.name);
                        errorField.$setTouched();
                    })
                });
            }
        }

        function handleError(data, status) {
            if (status === 1000) {
                // Our error response
                $scope.validationErrorFromServer.error = true;
                var message = [];
                var msgs = Object.keys(data);
                for (var i = 0; i < msgs.length; i++) {
                    message.push(data[msgs[i]]);
                }
                $scope.validationErrorFromServer.data = message;
            } else {
                $state.go('error', {response: data});
            }
        }

        function goHome() {
            $state.go('home');
        }
    }]);
