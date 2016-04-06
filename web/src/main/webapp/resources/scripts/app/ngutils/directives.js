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




