angular.module('braveHackers', ['braveHackers.filters', 'braveHackers.services', 'braveHackers.directives', 'braveHackers.controllers', 'ngRoute', 'ngAnimate'])
        .config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/home', {templateUrl: 'partials/home.html', controller: 'HomeCtrl'});
        $routeProvider.when('/view1', {templateUrl: 'partials/partial1.html', controller: 'MyCtrl1'});
        $routeProvider.when('/view2', {templateUrl: 'partials/partial2.html', controller: 'MyCtrl2'});
        $routeProvider.when('/view3', {templateUrl: 'partials/partial3.html', controller: 'MyCtrl3'});
        $routeProvider.when('/geomap', {templateUrl: 'partials/geomap.html', controller: 'GeomapCtrl'});
        $routeProvider.when('/editLine', {templateUrl: 'partials/editLine.html', controller: 'EditLineCtrl'});
        $routeProvider.when('/editLineCount', {templateUrl: 'partials/editLineCount.html', controller: 'EditLineCountCtrl'});
        $routeProvider.otherwise({redirectTo: '/geomap'});
    }]);

