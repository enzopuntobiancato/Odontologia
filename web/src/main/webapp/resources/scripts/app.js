'use strict';

var odontologiaApp = angular.module('odontologiaApp', [
//    'ngSanitize',
    'ui.router',
    'oc.lazyLoad',
    'ui.bootstrap',
    'Pagination',
    'sapo.directives',
    'sapo.services',
    'angular-loading-bar',
    'auth.services'
//    'ngAnimate',
//    'ngMaterial'
//    'ui.select'
]);


odontologiaApp.config(['$urlRouterProvider',
    '$stateProvider',
    '$ocLazyLoadProvider', 'cfpLoadingBarProvider', '$httpProvider',
    function ($urlRouterProvider, $stateProvider, $ocLazyLoadProvider, cfpLoadingBarProvider, $httpProvider) {

        $httpProvider.interceptors.push('authHttpRequestInterceptor');

        cfpLoadingBarProvider.loadingBarTemplate = '<div id="bar-container"></div><div id="loading-bar"><div class="bar"><div class="peg"></div></div></div></div>';



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
            .state('home', {
                url: '/',
                templateUrl: 'views/home/home.html',
                controller: 'HomeCtrl',
                data: {
                   isFree: true
                },
                resolve: {
                    loadMyModule: ['$ocLazyLoad', function ($ocLazyLoad) {
                        //lazyload de un modulo
                        return $ocLazyLoad.load('homeModule');
                    }],
                    initializeData: ['loadMyModule', '$http', '$q', function(loadMyModule, $http, $q) {
                        var deferred = $q.defer();
                        $http({
                            url:'api/commons/initializeData',
                            method: 'POST'
                        })
                            .success(function(response){
                                if (response) {
                                    deferred.resolve('Inicialización de datos ejecutada con éxito.');
                                } else {
                                    deferred.resolve(undefined);
                                }

                            })
                            .error(function() {
                                deferred.resolve('Error durante la ejecución de la inicialización de datos');
                            })
                        return deferred.promise;
                    }]
                }
            })
            .state('login', {
                url: '/login',
                templateUrl: 'views/login/login.html',
                data: {
                    isFree: true
                },
                controller: 'loginCtrl'
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
                params: {execQuery: false, execQuerySamePage: false},
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
                    materiaResponse: ['loadMyModule', '$stateParams', 'MateriaSrv', function (loadMyModule, $stateParams, service) {
                        return service.findById($stateParams.id);
                    }]

                }
            })
            .state('materia.view', {
                url: '/view/:id',
                templateUrl: 'views/materia/view.html',
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
                templateUrl: 'views/practicaOdontologica/query.html',
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
                templateUrl: 'views/practicaOdontologica/create.html',
                controller: 'PracticaOdontologicaCtrl_Create',
                resolve: {
                    gruposPracticaResponse: ['CommonsSrv', function (commons) {
                        return commons.getGruposPractica();
                    }]
                }
            })
            .state('practicaOdontologica.edit', {
                url: '/edit/:id',
                templateUrl: 'views/practicaOdontologica/edit.html',
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
                templateUrl: 'views/practicaOdontologica/view.html',
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
                templateUrl: 'views/trabajoPractico/query.html',
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
                templateUrl: 'views/trabajoPractico/create.html',
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
                templateUrl: 'views/trabajoPractico/edit.html',
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
                templateUrl: 'views/trabajoPractico/view.html',
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
                templateUrl: 'views/catedra/query.html',
                params: {execQuery: false, execQuerySamePage: false},
                controller: 'CatedraCtrl_Index',
                resolve: {

                }
            })
            .state('catedra.create', {
                url: '/create',
                templateUrl: 'views/catedra/create.html',
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
                    practicasResponse: ['loadMyModule', 'CatedraSrv', function(loadMyModule, service){
                        return service.getPracticas();
                    }]
                }
            })
            .state('catedra.edit', {
                url: '/edit/:id',
                templateUrl: 'views/catedra/edit.html',
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
                    practicasResponse: ['loadMyModule', 'CatedraSrv', function(loadMyModule, service){
                        return service.getPracticas();
                    }],
                    catedraResponse: ['loadMyModule', '$stateParams', 'CatedraSrv', function(loadMyModule, $stateParams, service){
                        return service.findById($stateParams.id);
                    }]
                }
            })
            .state('catedra.view', {
                url: '/view/:id',
                templateUrl: 'views/catedra/view.html',
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
                }
            ]
        });

    }]);

angular.module('homeModule', []);
angular.module('materiaModule', []);
angular.module('practicaOdontologicaModule', []);
angular.module('catedraModule', []);
angular.module('trabajoPracticoModule', []);


odontologiaApp.controller('AppController', ['$scope', '$state', 'authFactory', function($scope, $state, authFactory){

    $scope.user = authFactory.getAuthData();
    $scope.$on('$stateChangeStart',
        function(event, toState, toParams, fromState, fromParams){

            if(authFactory.isAuthenticated()){
                authFactory.updateExpiresTime();
            }

            if (toState.name === 'login' && authFactory.isAuthenticated()) {
                event.preventDefault();
                $state.go('home');
            }
            if (toState.data && toState.data.isFree) {
                return;
            }
            if (!authFactory.isAuthenticated()) {
                event.preventDefault();
                $state.go('login');
            }
        });

    $scope.$on('authChanged', function(){
        $scope.user = authFactory.getAuthData();
    });

    $scope.logOut = function() {
        $scope.user = authFactory.logout();
        $state.go('home');
    }



}]);

odontologiaApp.controller('loginCtrl', ['$scope', 'authFactory','$state', function($scope, authFactory, $state){

    $scope.user = {};
    $scope.loginData = {
        userNotFound: false
    }
    $scope.login = function (user) {
        $scope.loginData.userNotFound = false;
        authFactory.login(user).success(function (data) {
            if (data) {
                $scope.loginData.userNotFound = false;
                authFactory.setAuthData(data);
                $state.go('home');
            } else {
                 $scope.loginData.userNotFound = true;
            }

        }).error(function () {
                // Error handling
            });
    };
}])
