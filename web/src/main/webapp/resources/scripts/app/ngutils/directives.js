var directives = angular.module('sapo.directives',[]);

directives.directive('sameAs', function() {
        return {
            require: 'ngModel',
            link: function(scope, elem, attrs, ngModel) {
                ngModel.$parsers.unshift(validate);

                // Force-trigger the parsing pipeline.
                scope.$watch(attrs.sameAs, function() {
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

directives.directive('showFocus', function($timeout) {
    return function(scope, element, attrs) {
        scope.$watch(attrs.showFocus,
            function (newValue) {
                $timeout(function() {
                    newValue && element.focus() && element.select();
                });
            },true);
    };
});




