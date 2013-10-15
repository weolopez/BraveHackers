angular.module('iLoveCrowdsApp', ['ngRoute', 'ngAnimate', 'AngularGM','timer', 'ngStorage'])
        .config(['$routeProvider', function($routeProvider) {
                $routeProvider.when('/splash', {templateUrl: 'partials/splash.html', controller: 'SplashCtrl'});
                $routeProvider.when('/login', {templateUrl: 'partials/login.html', controller: 'LoginCtrl'});
                $routeProvider.when('/apps', {templateUrl: 'partials/apps.html', controller: 'AppsCtrl'});
                $routeProvider.when('/crowds', {templateUrl: 'partials/crowds.html', controller: 'CrowdsCtrl'});
                $routeProvider.otherwise({redirectTo: '/splash'});
            }])
        .directive('eatClick', function() {
            return function(scope, element, attrs) {
                $(element).click(function(event) {
                console.log('eat click');
                    event.preventDefault();
                });
            }
        })
        .animation('.animate-enter', function() {
            return {
                enter: function(element, done) {
                    $('#splash-screen').find('a').each(function(i) {
                        $(this).delay(i * 400).fadeIn();
                    });
                },
                leave: function(element, done) {
                    $('#splash-screen').find('a').each(function(i) {
                        $(this).hide();
                    });
                    jQuery(element).css({
                        position: 'absolute',
                        'z-index': 101,
                        top: 0,
                        opacity: 1
                    });
                    jQuery(element).animate({
                        top: -600,
                        opacity: 0
                    }, done);
                }
            };
        })
        .controller('SplashCtrl', function($scope, $http, $location, $rootScope, $localStorage) {
            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(function(position) {
                    $localStorage.position = angular.copy(position);
                });
            } else {
                x.innerHTML = "Geolocation is not supported by this browser.";
            }
        })
        .controller('LoginCtrl', function($scope, $http, $location, $rootScope) {
        })
        .controller('CrowdsCtrl', function($scope, $http, $location, $rootScope, $window) {
        })
        .controller('AppsCtrl', function($scope, $location) {
        })
        ;

