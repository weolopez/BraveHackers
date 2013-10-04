angular.module('braveHackers', ['braveHackers.controllers', 'ngRoute', 'ngAnimate'])
        .config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/geomap', {templateUrl: 'partials/geomap.html', controller: 'GeomapCtrl'});
        $routeProvider.when('/editLine', {templateUrl: 'partials/editLine.html', controller: 'EditLineCtrl'});
        $routeProvider.when('/editLineCount', {templateUrl: 'partials/editLineCount.html', controller: 'EditLineCountCtrl'});
        $routeProvider.otherwise({redirectTo: '/geomap'});
    }]);

