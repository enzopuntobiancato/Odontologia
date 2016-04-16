/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 22/03/16
 * Time: 22:08
 * To change this template use File | Settings | File Templates.
 */
(function(){
    var templateInput =
        '<md-input-container flex-gt-sm> ' +
            '<label>{{ label }} </label>'+
            '<input name="name" ng-model="model" required="req" md-maxlength="max">'+
            '<div ng-messages="form.$error" ng-show="form.$touched" multiple>'+
            '<div ng-messages-include="error-messages">'+
            '</div> </div>' +
            '</md-input-container>' ;
    var directive = function(){
      return {
          restrict: 'E',
          template: templateInput,
          scope: {
              label: '@',
              name: '@',
              model: '=',
              req: '@',
              max: '@',
              form: '@'
          }

      };
    };

    angular.module('customDirectivesModule')
        .directive('customInput',directive);
}());
