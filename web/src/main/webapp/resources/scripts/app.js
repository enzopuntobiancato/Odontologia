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
    '$mdThemingProvider', '$mdIconProvider', '$translateProvider',
    function ($urlRouterProvider, $stateProvider, $ocLazyLoadProvider, cfpLoadingBarProvider, $httpProvider, $mdThemingProvider, $mdIconProvider, $translateProvider) {

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
            .state('persona.datosUsuario', {
                url: '/datosUsuario',
                templateUrl: 'views/persona/datosUsuario.html',
                controller: 'PersonaCtrl_DatosUsuario',
                controllerAs: 'vm',
                resolve: {
                    personaResponse: ['loadMyModule', 'authFactory', 'PersonaSrv', function (loadMyModule, authFactory, personaSrv) {
                        var user = authFactory.getAuthData();
                        return personaSrv.findByUser(user.nombreUsuario, user.authToken);
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
                controllerAs: 'vm',
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
                    $scope.imgIdx = Math.floor(Math.random() * (47 - 1 + 1)) + 1;
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
                controllerAs: 'vm',
                resolve: {
                    personaResponse: ['loadMyModule', '$stateParams', 'UsuarioSrv', function (loadMyModule, $stateParams, service) {
                        return service.findPersona($stateParams.id);
                    }],
                    imageResponse: ['loadMyModule', 'personaResponse', 'UsuarioSrv', function (loadMyModule, personaResponse, service) {
                        var person = personaResponse.data;
                        return service.findUserImage(person.usuario.imagenId);
                    }],
                    usuarioResponse: ['loadMyModule', '$stateParams', 'UsuarioSrv', function(loadMyModule, $stateParams, service) {
                         return service.findByIdAsUsuarioLogueadoBean($stateParams.id);
                    }],
                    personaEmumsResponse: ['loadMyModule', 'CommonsSrv', function(loadMyModule, commons) {
                         return commons.getPersonaEnums();
                    }]
                }
            })
            .state('usuario.view', {
                url: '/view/:id',
                templateUrl: 'views/usuario/usuarioView.html',
                resolve: {
                    personaResponse: ['loadMyModule', '$stateParams', 'UsuarioSrv', function (loadMyModule, $stateParams, service) {
                        return service.findPersona($stateParams.id);
                    }],
                    usuarioResponse: ['loadMyModule', '$stateParams', 'UsuarioSrv', function (loadMyModule, $stateParams, service) {
                        return service.findByIdAsUsuarioLogueadoBean($stateParams.id);
                    }],
                    imageResponse: ['loadMyModule', 'personaResponse', 'UsuarioSrv', function (loadMyModule, personaResponse, service) {
                        var person = personaResponse.data;
                        return service.findUserImage(person.usuario.imagenId);
                    }]
                },
                controller: function ($scope, $state, personaResponse, usuarioResponse, imageResponse) {
                    $scope.persona = personaResponse.data;
                    $scope.usuario = usuarioResponse.data;
                    $scope.image = imageResponse.data;
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
                resolve: module('pacienteModule')
            })
            .state('paciente.index', {
                url: '/',
                templateUrl: 'views/paciente/pacienteQuery.html',
                params: {execQuery: false, execQuerySamePage: false},
                controller: 'PacienteCtrl_Index',
                controllerAs: 'vm',
                resolve: {
                    tiposDocumentoResponse: ['CommonsSrv', function (commons) {
                        return commons.getTiposDocumento();
                    }],
                    sexosResponse: ["CommonsSrv", function (commons) {
                        return commons.getSexos();
                    }]
                }
            })
            .state('paciente.create', {
                url: '/create',
                templateUrl: 'views/paciente/pacienteEditCreate.html',
                controller: 'PacienteCtrl_EditCreate',
                controllerAs: 'vm',
                data: {
                    updating : false
                },
                resolve: {
                    nivelesResponse: ['CommonsSrv', function (commons) {
                        return commons.getNiveles();
                    }],
                    tiposDocumentoResponse:['CommonsSrv', function(commons){
                        return commons.getTiposDocumento();
                    }],
                    sexosResponse:["CommonsSrv", function(commons){
                        return commons.getSexos();
                    }],
                    provinciaResponse: ["CommonsSrv", function(commons){
                        return commons.getProvincias();
                    }],
                    ciudadesResponse: ['CommonsSrv', function(commons){
                        return commons.getCiudades();
                    }],
                    barriosResponse: ['CommonsSrv', function(commons){
                        return commons.getBarrios();
                    }],
                    estadosResponse: ['CommonsSrv', function(commons){
                        return commons.getEstadosCivil();
                    }],
                    trabajosResponse: ['CommonsSrv', function(commons){
                        return commons.getTrabajos();
                    }],
                    obrasSocialesResponse: ['CommonsSrv', function(commons){
                        return commons.getObrasSociales();
                    }],
                    nivelesEstudioResponse: ['CommonsSrv', function(commons){
                        return commons.getNivelesEstudio();
                    }],
                    nacionalidadesResponse: ['CommonsSrv', function(commons){
                        return commons.getNacionalidaes();
                    }],
                    pacienteResponse:['$stateParams','PacienteSrv', function($stateParams,service){
                        return service.initPaciente();
                    }]
                }
            })
            .state('paciente.edit', {
                url: '/edit/:id',
                templateUrl: 'views/paciente/pacienteEditCreate.html',
                controller: 'PacienteCtrl_EditCreate',
                controllerAs: 'vm',
                data: {
                    updating : false
                },
                resolve:{
                    nivelesResponse: ['CommonsSrv', function (commons) {
                        return commons.getNiveles();
                    }],
                    tiposDocumentoResponse:['CommonsSrv', function(commons){
                        return commons.getTiposDocumento();
                    }],
                    sexosResponse:["CommonsSrv", function(commons){
                        return commons.getSexos();
                    }],
                    provinciaResponse: ["CommonsSrv", function(commons){
                        return commons.getProvincias();
                    }],
                    ciudadesResponse: ['CommonsSrv', function(commons){
                        return commons.getCiudades();
                    }],
                    barriosResponse: ['CommonsSrv', function(commons){
                        return commons.getBarrios();
                    }],
                    estadosResponse: ['CommonsSrv', function(commons){
                        return commons.getEstadosCivil();
                    }],
                    trabajosResponse: ['CommonsSrv', function(commons){
                        return commons.getTrabajos();
                    }],
                    obrasSocialesResponse: ['CommonsSrv', function(commons){
                        return commons.getObrasSociales();
                    }],
                    nivelesEstudioResponse: ['CommonsSrv', function(commons){
                        return commons.getNivelesEstudio();
                    }],
                    nacionalidadesResponse: ['CommonsSrv', function(commons){
                        return commons.getNacionalidaes();
                    }],
                    pacienteResponse:['$stateParams','PacienteSrv', function($stateParams,service){
                        return service.findById($stateParams.id);
                    }]
                }
            })
            .state('paciente.view', {
                url: '/view/:id',
                templateUrl: 'views/paciente/pacienteView.html',
                controllerAs: 'vm',
                resolve: {
                    pacienteResponse:['$stateParams','PacienteSrv',function($stateParams,service){
                        return service.findById($stateParams.id);
                    }]
                },
                controller: function ($scope, $state,pacienteResponse, DeleteRestoreSrv) {
                    var vm = this;
                    vm.paciente = pacienteResponse.data;
                    vm.edit = edit;
                    vm.goIndex = goIndex;
                    vm.mostrar = mostrar;
                    vm.openDeleteDialog = openDeleteDialog;
                    vm.openRestoreDialog = openRestoreDialog;
                    vm.tabs = [true, false, false, false];
                    vm.tabsTitulos = ["Datos personales", "Antecedentes", "Diagnósticos", "Atenciones"];
                    vm.titulo = vm.updating ? "Editar " +  vm.paciente.apellido : "Registrar nuevo paciente";
                    vm.tabTitulo = vm.tabsTitulos[0];

                    function edit(pacienteId){
                        $state.go('^.edit', {id:pacienteId});
                    }
                    function goIndex() {
                        $state.go('^.index');
                    }

                    //BAJA Y RESTAURAR
                    function openDeleteDialog(event) {
                        var nombre = vm.paciente.apellido + ", " + vm.paciente.nombre;
                        DeleteRestoreSrv.delete(event, vm.paciente.id, nombre, null,null,null);
                    }

                    function openRestoreDialog(event) {
                        var nombre = vm.paciente.apellido + ", " + vm.paciente.nombre;
                        DeleteRestoreSrv.restore(event, vm.paciente.id, nombre, null);
                    }

                    function mostrar(tabId){
                        for(var i = 0; i < vm.tabs.length; i++){
                            if(i == tabId){
                                vm.tabs[i] = true;
                                vm.tabTitulo = vm.tabsTitulos[i];
                            }else{
                                vm.tabs[i] = false;
                            }
                        }
                    }
                }
            })
            .state('historiaClinica', {
                url: '/historiaClinica',
                template: '<ui-view/>',
                abstract: true,
                resolve: module('historiaClinicaModule')
            })
            .state('historiaClinica.index', {
                url: '/',
                templateUrl: 'views/historiaClinica/historiaClinicaQuery.html',
                params: {execQuery: false, execQuerySamePage: false},
                controller: 'HistoriaClinicaCtrl_Index',
                controllerAs: 'vm',
                resolve: {
                    tiposDocumentoResponse:['CommonsSrv', function(commons){
                        return commons.getTiposDocumento();
                    }],
                    sexosResponse:["CommonsSrv", function(commons){
                        return commons.getSexos();
                    }]
                }
            })
            .state('historiaClinica.create', {
                url: '/create',
                templateUrl: 'views/historiaClinica/historiaClinicaCreate.html',
                controller: 'HistoriaClinicaCtrl_Create',
                controllerAs: 'vm'
            })
            .state('historiaClinica.edit', {
                url: '/edit/:id',
                templateUrl: 'views/historiaClinica/historiaClinicaEdit.html',
                controller: 'HistoriaClinicaCtrl_Edit',
                controllerAs: 'vm',
                resolve: {historiaClinicaResponse:['$stateParams','HistoriaClinicaSrv',function($stateParams,service){
                    return service.findById($stateParams.id);
                }]}
            })
            .state('historiaClinica.paciente', {
                url:'/card/:id',
                templateUrl: 'views/historiaClinica/historiaClinicaPaciente.html',
                controllerAs:'vm',
                resolve: {pacienteResponse:['$stateParams','HistoriaClinicaSrv',function($stateParams,service){
                    return service.findById($stateParams.id);
                }]},
                controller: function($scope, $state, pacienteResponse){
                    var vm = this;
                    vm.paciente = pacienteResponse.data;
                    vm.goHistoriaClinica = goHistoriaClinica;
                    vm.goDatosPersonales = goDatosPersonales;
                    vm.goIndex = goIndex;

                    //Navegación a otros estados
                    function goHistoriaClinica(idPaciente){
                        $state.go('historiaClinica.view',{id : idPaciente});
                    }

                    function goDatosPersonales(idPaciente){
                        $state.go('paciente.view',{id : idPaciente});
                    }
                    function goIndex() {
                        $state.go('^.index');
                    }
                }
            })
            .state('historiaClinica.view', {
                url: '/view/:id',
                templateUrl: 'views/historiaClinica/historiaClinicaView.html',
                controllerAs: 'vm',
                resolve: {historiaClinicaResponse:['$stateParams','HistoriaClinicaSrv',function($stateParams,service){
                    return service.findById($stateParams.id);
                }]},
                controller: function($scope,$state,historiaClinicaResponse){
                    var vm = this;
                    vm.paciente = historiaClinicaResponse.data;
                    vm.edit = edit;
                    vm.goIndex = goIndex;
                    vm.goToCard = goToCard;

                    function edit(pacienteId){
                        $state.go('^.edit', {id:pacienteId});
                    }
                    function goIndex() {
                        $state.go('^.index');
                    }
                    function goToCard(pacienteId){
                        $state.go('^.paciente', {id: pacienteId});
                    }
                }
            })
            .state('asignacion', {
                url: '/asignacion',
                template: '<ui-view/>',
                abstract: true,
                resolve: module('asignacionModule')
            })
            .state('asignacion.create', {
                url: '/create',
                templateUrl: 'views/asignacion/asignacionEditCreate.html',
                controller: 'AsignacionCtrl_EditCreate',
                controllerAs: 'vm',
                data: {
                    updating : false
                },
                resolve:  {
                    tiposDocumentoResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, commons) {
                        return commons.getTiposDocumento();
                    }],
                    catedrasResponse: ['loadMyModule','AsignacionSrv', function (loadMyModule, service) {
                        return service.findAllCatedras();
                    }],
                    trabajosPracticosResponse: ['loadMyModule', 'AsignacionSrv', function (loadMyModule, service){
                        return service.findAllTrabajosPracticos();
                    }],
                    sexosResponse: ['loadMyModule', 'CommonsSrv', function (loadMyModule, commons) {
                        return commons.getSexos();
                    }]
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
                        url('/persona/personaSrv.js'),
                        url('/persona/datosUsuarioCtrl.js')
                    ]
                },
                {
                    name: 'pacienteModule',
                    files: [
                        url('/paciente/pacienteSrv.js'),
                        url('/paciente/pacienteIndexCtrl.js'),
                        url('/paciente/pacienteEditCreateCtrl.js')
                    ]
                },
                {
                    name: 'historiaClinicaModule',
                    files: [
                        url('/historiaClinica/historiaClinicaSrv.js'),
                        url('/historiaClinica/historiaClinicaIndexCtrl.js'),
                        url('/historiaClinica/historiaClinicaCreateCtrl.js'),
                        url('/historiaClinica/historiaClinicaEditCtrl.js')
                    ]
                },
                {
                    name: 'asignacionModule',
                    files: [
                        url('/asignacion/asignacionSrv.js'),
                        url('/asignacion/asignacionEditCreateCtrl.js')
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
angular.module('historiaClinicaModule',[]);


odontologiaApp.controller('AppController', ['$scope', '$state', 'authFactory', '$filter', '$mdSidenav',
    function ($scope, $state, authFactory, $filter, $mdSidenav) {

        $scope.performSubmit = performSubmit;
        $scope.handleError = handleError;
        $scope.menuOpen = false;
        $scope.selectedMenuItem = {};
        $scope.validationErrorFromServer = {
            error: false,
            data: {}
        };
        $scope.session = {};

        retrieveSession();

        function retrieveSession() {
            $scope.session.user = authFactory.getAuthData();
            $scope.session.image = authFactory.getImage();
            if ($scope.session.user && !angular.isDefined($scope.session.image) && $scope.session.user.imagenId) {
                authFactory.lookForImage($scope.session.user.imagenId).then(function (response) {
                    $scope.session.image = response.data;
                });
            }
            $scope.menu = authFactory.getMenu();
        }

        $scope.toggleSidenav = function (menuId) {
            $mdSidenav(menuId).toggle();
        };

        $scope.show = "false";
        $scope.showFilters = function () {
            $scope.show = true;
        };
        $scope.menu = authFactory.getMenu() ? authFactory.getMenu() : buildMenu($scope.session.user);

        $scope.$on('$stateChangeStart',
            function (event, toState, toParams, fromState, fromParams) {
                $scope.validationErrorFromServer.error = false;
                if (toState.name == 'landingPage' && !$scope.session.user) {
                    $scope.menuOpen = false;
                } else {
                    $scope.menuOpen = true;
                }
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
            retrieveSession();
            $scope.selectedMenuItem = {};
            $scope.menu = buildMenu($scope.session.user);
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

        $scope.menuItemClick = function(item) {
            if (item.dropdown) {
                item.open = !item.open;
            } else {
                $scope.selectedMenuItem = item;
                $state.go(item.ref);
            }
        }


        function buildMenu(user) {
            var resultMenu = [];

            var items = user ? $filter('filter')(user.permisos, function (permission) {
                return permission.esItemMenu;
            }) : [];

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
            authFactory.setMenu(resultMenu);
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
                // Other error, go to error page
                $state.go('error', {response: data});
            }
        }
    }]);
