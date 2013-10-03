'use strict';

/* Directives */


angular.module('myApp.directives', [])
        .directive('appVersion', ['version', function(version) {
                return function(scope, elm, attrs) {
                    elm.text(version);
                };
            }]).directive('bhDraggable', function() {
    return {
        restrict: 'A',
        link: function(scope, elm, attrs) {
            var options = scope.$eval(attrs.andyDraggable); //allow options to be passed in
            elm.draggable(options);
        }
    };
});
;
