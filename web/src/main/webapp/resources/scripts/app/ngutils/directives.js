var directives = angular.module('sapo.directives', ['ui.bootstrap']);

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
}];

directives.directive('submitValidate', submitValidate);

directives.directive('summaryError', function () {
    return {
        restrict: 'E',
        scope: {
            var: '='
        },
        template: ' <div ng-if="var"><div class="alert alert-danger" role="alert"><i class="fa fa-exclamation-circle"></i><strong> ¡Datos erróneos!</strong> Verifique el formulario y complete los datos correctamente.</div></div>',
        controller: function ($scope) {
        }
    }
});

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

directives.directive('showErrors', function ($timeout) {
    return {
        restrict: 'A',
        require: '^form',
        link: function (scope, el, attrs, formCtrl) {
            var dir = function() {
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
            $timeout(dir, 10);
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
};

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

directives.directive('datePicker', function () {
    return {
        priority: 1,
        restrict: 'E',
        scope: {
            ngModel: '=',
            maxDate: '=',
            minDate: '=',
            isRequired: '=',
            modelName: '=',
            label: '='
        },
        templateUrl: 'views/commons/datepicker.html',
        controller: function ($scope) {
            if (!$scope.minDate) {
                $scope.minDate = new Date('01/01/1900');
            } else {
               if (!$scope.minDate instanceof Date) {
                   $scope.minDate = new Date($scope.minDate);
               }
            }

            if (!$scope.maxDate) {
                $scope.maxDate = new Date();
            }

            $scope.formats = ['dd-MMMM-yyyy', 'dd-MM-yyyy', 'dd.MM.yyyy', 'shortDate'];
            $scope.format = $scope.formats[1];

            $scope.dateOptions = {
                formatYear: 'yy',
                startingDay: 1
            };

            $scope.open = function($event) {
                $event.preventDefault();
                $event.stopPropagation();

                $scope.opened = true;
            };
        }
    }
});



