'use strict';


// Declare app level module which depends on filters, and services
angular.module('myApp', ['myApp.filters', 'myApp.services', 'myApp.directives', 'myApp.controllers']).
        config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/view1', {templateUrl: 'partials/partial1.html', controller: 'MyCtrl1'});
        $routeProvider.when('/view2', {templateUrl: 'partials/partial2.html', controller: 'MyCtrl2'});
        $routeProvider.when('/view3', {templateUrl: 'partials/partial3.html', controller: 'MyCtrl3'});
        $routeProvider.when('/view4', {templateUrl: 'partials/partial4.html', controller: 'MyCtrl4'});
        $routeProvider.when('/signup', {templateUrl: 'partials/signup.html', controller: 'SignupCtrl'});
        $routeProvider.when('/auth',   {templateUrl: 'partials/auth.html', controller: 'AuthCtrl'});        
        $routeProvider.when('/geomap', {templateUrl: 'partials/geomap.html', controller: 'GeomapCtrl'});
        $routeProvider.otherwise({redirectTo: '/view1'});
    }]);
