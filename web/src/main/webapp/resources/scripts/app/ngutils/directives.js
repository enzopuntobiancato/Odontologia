var directives = angular.module('sapo.directives', []);

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

directives.directive('showErrors', function () {
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


//odontologiaApp
//    .controller('datepickerCtrl', function ($scope) {
//
//        $scope.clear = function () {
//            $scope.dt = null;
//        };
//
//        // Disable weekend selection
//        $scope.disabled = function (date, mode) {
//            return ( mode === 'day' && ( date.getDay() === 0 || date.getDay() === 6 ) );
//        };
//
//        $scope.toggleMin = function () {
//            $scope.minDate = $scope.minDate ? null : new Date();
//        };
//        $scope.toggleMin();
//
//        $scope.open = function ($event) {
//            $event.preventDefault();
//            $event.stopPropagation();
//
//            $scope.opened = true;
//        };
//
//        $scope.dateOptions = {
//            formatYear: 'yyyy',
//            startingDay: 1
//        };
//
//        $scope.formats = ['dd/MM/yyyy', 'dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
//        $scope.format = $scope.formats[0];
//    })
//
//odontologiaApp
//    .directive('datePicker', function () {
//        return {
//            templateUrl: 'views/commons/datepicker.html'
//        }
//    })



