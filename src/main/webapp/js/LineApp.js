angular.module('lineApp', ['ngRoute', 'ngAnimate', 'leaflet-directive', 'mongolabResourceHttp', 'timer', 'ngStorage'])
        .config(['$routeProvider', function($routeProvider) {
                $routeProvider.when('/geomap', {templateUrl: 'partials/geomap.html', controller: 'GeomapCtrl', resolve: {
                        allLines: function(Lines) {
                            return Lines.all();
                        }
                    }});
                $routeProvider.when('/editLine:id', {templateUrl: 'partials/editLine.html', controller: 'EditLineCtrl', resolve: {
                        line: function(Line, $route) {
                            return Line.query({'line.name': $route.current.params.id});
                        }
                    }});
                $routeProvider.when('/editLineCount', {templateUrl: 'partials/editLineCount.html', controller: 'EditLineCountCtrl'});
                $routeProvider.otherwise({redirectTo: '/geomap'});
            }])
        .constant('MONGOLAB_CONFIG', {API_KEY: '50f36e05e4b0b9deb24829a0', DB_NAME: 'weolopez'})
        .factory('Lines', function($mongolabResourceHttp) {
            return $mongolabResourceHttp('Lines');
        })
        .factory('Line', function($mongolabResourceHttp) {
            return $mongolabResourceHttp('Line');
        })
        .controller('EditLineCtrl', function($scope, $location, $localStorage, line) {
            $scope.line = line[0];
            $scope.$storage = $localStorage;
            if ($scope.line === undefined) {
                $scope.line = {};
                $scope.line.name = $localStorage.currentLine;
                $scope.line.index = 0;
                $scope.line.rate = 2000;
            }
            $scope.peopleInFront = function() {
                if ($scope.line.index == 0) {
                    $scope.showCounter = true;
                }
            }
            $scope.editLine = function() {
                //alert("clicked submit");
                $http.get("/BraveHackers/crowds/lineService/addSmiley",
                        {params:
                                    {lineId: $scope.pin.id, count: $scope.pin.count}
                        })
                        .success(function(data, status, success, requestObject) {
                            console.log('success:' + data);
                            $location.url("/editLineCount");
                        })
                        .error(function(data) {
                            console.log('error:' + data);
                            $location.url("/editLineCount");
                        });
            }
        })
        .controller('EditLineCountCtrl', function($scope, $http, $location, $rootScope, $window) {

            $scope.pin = $rootScope.selectedPin;


            if ($scope.pin.count === '0')
                $window.location.href("/BraveHackers");

            $scope.reduceLine = function() {
                if ($scope.pin.count === undefined)
                    $window.location.href("/BraveHackers");
                if ($scope.pin.count === '0')
                    $window.location.href("/BraveHackers");
                $scope.pin.count = $scope.pin.count - 1;
                $http.get("/BraveHackers/crowds/lineService/setNewLineCount",
                        {params:
                                    {lineId: $scope.pin.id, count: $scope.pin.count}
                        })
                        .success(function(data, status, success, requestObject) {
                            console.log('success:' + data);
                        })
                        .error(function(data) {
                            console.log('error:' + data);
                        });
                //alert("EditLineCountCtrl");
            }
        })
        .controller('GeomapCtrl', function($scope, $http, $location, $rootScope, $localStorage, $sessionStorage, allLines) {
            $scope.allLines = allLines[0];
            $scope.types = $scope.allLines.types;
            $scope.$storage = $localStorage;
            $scope.centerLat = $localStorage.position.coords.latitude;
            $scope.centerLng = $localStorage.position.coords.longitude;

            angular.extend($scope, $scope.allLines.base);


            $scope.selectedType = function(index) {
                $scope.selectedIndex = index;
                $scope.markers = {};
                angular.forEach($scope.allLines.serverResponse, function(marker, key) {
                    var m = {};
                    var redMarker = L.AwesomeMarkers.icon({
                        icon: $scope.types[$scope.selectedIndex].icon,
                        color: 'red'
                    })
                    marker.icon = redMarker;
                    m[key] = marker;
                    if (marker.title === $scope.allLines.types[index].name) {
                        angular.extend($scope.markers, m);
                    }
                });
            };

            $scope.$on('leafletDirectiveMap.click', function(e, args) {
                if ($scope.types[$scope.selectedIndex] === undefined)
                    alert("Please select a line type.");
                var count = Object.keys($scope.markers).length;
                var lat = args.leafletEvent.latlng.lat;
                var lng = args.leafletEvent.latlng.lng;
                var name = $scope.types[$scope.selectedIndex].name + count.toString();
                var marker = {};
                var redMarker = L.AwesomeMarkers.icon({
                    icon: $scope.types[$scope.selectedIndex].icon,
                    color: 'red'
                })
                marker[name] = {
                    lat: lat,
                    lng: lng,
                    title: $scope.types[$scope.selectedIndex].name,
                    focus: false,
                    draggable: true,
                    icon: redMarker
                }
                angular.extend($scope.markers, marker);
                angular.extend($scope.allLines.serverResponse, marker);
                console.log($scope.allLines);
                $scope.allLines.$saveOrUpdate(changeSuccess, changeSuccess, changeError, changeError);
            });

            $scope.$on('leafletDirectiveMarker.click', function(e, args) {
                console.log(args + " :MarkerName: " + args.markerName);
                $localStorage.currentLine = args.leafletEvent.target.options.title;
                $location.url("/editLine:" + args.leafletEvent.target.options.title);
            });

            function changeSuccess() {
                console.log("SUCCESS");
            }
            ;
            function changeError() {
                console.log("changeError");
            }
            ;
        })
        ;

