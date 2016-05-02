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

directives.directive('validateLength', function() {
    return {
        require: 'ngModel',
        link: function(scope, element, attrs, ngModel) {

            scope.$watch(function () { return ngModel.$modelValue && ngModel.$modelValue.length; }, function() {
                ngModel.$validate(); // validate again when array changes
            });

            ngModel.$validators.length = function() {
                var arr = ngModel.$modelValue;
                if(!arr) { return false; }

                return arr.length > 0;
            };

        }
    };
});

// CUSTOM INPUTS DIRECTIVES
var templateInput =
    '<md-input-container flex-gt-sm> ' +
        '<label> {{ ctrl.label }} </label>'+
        '<input name="{{ ctrl.name }}" ng-model="ctrl.model"  ng-required="{{ ctrl.req }}" md-maxlength="{{ ctrl.max }}" ng-pattern="ctrl.pat" minlength="{{ ctrl.min }}" ng-disabled="ctrl.disabled" type="{{ ctrl.type }}" aria-label="{{ ctrl.label }}">'+
        '<div ng-messages="ctrl.form.$error" ng-show="ctrl.form.$touched ||  ctrl.submitted  && ctrl.form.$invalid" multiple> ' +
              '<div ng-messages-include="error-messages"></div>' +
//            '<div ng-message="required">Es obligatorio.</div>'+
//            '<div ng-message="email">No tiene formato de e-mail.</div> '+
//            '<div ng-message="valid">No tiene formato de fecha.</div>'+
//            '<div ng-message="md-maxlength">Ingresó más caracteres de los permitidos.</div>'+
//            '<div ng-message="minlength">Ingresó menos caracteres que los requeridos.</div>'+
//            '<div ng-if="{{ ctrl.pat.toString() == 'zzz/([a-zA-Z ]+)/ }}" ng-message="pattern">Solo se permiten números.</div>'+
//            '<div ng-if="ctrl.pat.toString() == ([a-zA-Z ]+) " ng-message="pattern">Solo se permiten letras.</div>'+
        '</div> ' +
        '</md-input-container>' ;
var inputController = function(){
    var ctrl = this;

    function init(){
        if(ctrl.pattern != 'undefined'){
            ctrl.pat = new RegExp(ctrl.pattern);
//            alert(ctrl.pat.toString());
        }
    }

    init();
};

var directiveInput = function(){
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
            disabled: '=',
            form: '=',
            submitted: '=',
            type: '@'
        },
        controller: inputController,
        controllerAs: 'ctrl',
        bindToController: true,
        link: function(scope, element, attrs){
        }
    };
};

var templateSelect =
        '<md-input-container flex-gt-sm>'+
            '<label> {{ label }}</label>'+
            '<md-select ng-model="model" name="{{ name }}" ng-required="{{ req }}" aria-label="{{ label }}" ng-disabled="{{ disabled }}" ng-attr-ng-model-options="{{ trackBy }}">'+
               '<md-option ng-repeat="item in items" ng-value="item"> {{ item.nombre }}</md-option>'+
            '</md-select>'+
            '<div ng-messages="form.$error" ng-show="form.$touched || submitted && form.$invalid " multiple class="errors">'+
                '<div ng-messages-include="error-messages"></div>' +
            '</div>'+
        '</md-input-container>';


var directiveSelect = function($compile){
    return {
        restrict: 'E',
        template: templateSelect,
        scope: {
            label: '@',
            name: '@',
            model: '=',
            trackBy:'@',
            req: '@',
            items: '=',
            disabled: '@',
            form: '=',
            submitted: '='
        },
        link: function(scope, elem, attrs){
            var container = angular.element(elem.children()[0]);
            var select = angular.element(container.children()[1]);
//            scope.$watch('trackBy', function(){
//                select.attr('ng-model-options','{trackBy: \'$value.id\'}');
//            });
        }
    };
};

//var selectController = function(){
//    var ctrl = this;
//    function init(){
//        if(angular.isDefined(ctrl.trackBy)){
//            ctrl.trackBy = '{trackBy: \'$value.'+ctrl.trackBy +'\'}';
//        }else{
//            ctrl.trackBy = '{}';
//        }
//    }
//    init();
//};

directives.directive('simpleDirective', [function () {
    return {
        restrict: 'E',
        template: '<div class="nwcard">'+
            '<h2 class="h3">{{header}}</h2>'+
            '<p class="hidden">{{description}}</p>'+
            '</div>',
        scope:{
            header: '@',
            description: '@'
        },
        link: function (scope, elem, attrs) {
            scope.$watch('header', function(){
//               alert(scope.header);
               var zeroElem = elem.children()[0];
                elem.attr('id',scope.header);
                var title = angular.element(elem.children()[0]);
                title.attr('id','zeroElem');

//                zeroElem.attr('id','elemento0');
//                firstElem.attr('id','elemento1');
            });
           /* elem.on('click',function(){
                    elem.attr('id',scope.header);
                }
            );*/
        }
    }
}]);

directives.directive('validatedInput',directiveInput);
directives.directive('validatedSelect', directiveSelect);

/* DIRECTIVES ODONTOGRAMA */

var piezaDentalController = function(){

}

var piezaDentalDirective = function(){
    return{
        restrict: 'E',
        templateUrl: 'views/odontograma/piezaDental.html',
        scope:{
            piezaDental: '='
        },
        controller: piezaDentalController,
        controllerAs: 'vm',
        bindToController: true
    }
}

directives.directive('piezaDental', piezaDentalDirective);

//FILTERS
directives.filter('noDefinido',function(){
    return function(input){
        if(angular.isUndefined(input) || input == null){
            return 'No definido';
        }
        return input;
    }
})




