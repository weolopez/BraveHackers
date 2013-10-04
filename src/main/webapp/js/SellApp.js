angular.module('sellApp', ['ngRoute', 'ngAnimate', 'AngularGM', 'ngResource', 'timer'])
        .config(['$routeProvider', function($routeProvider) {
                $routeProvider.when('/geomap', {templateUrl: 'partials/geomap.html', controller: 'GeomapCtrl'});
                $routeProvider.when('/editLine', {templateUrl: 'partials/editSell.html', controller: 'EditLineCtrl'});
                $routeProvider.when('/editLineCount', {templateUrl: 'partials/editLineCount.html', controller: 'EditLineCountCtrl'});
                $routeProvider.otherwise({redirectTo: '/geomap'});
            }])
        .controller('EditLineCtrl', function($scope, $http, $location, $rootScope) {
            $scope.pin = $rootScope.selectedPin;

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
        .controller('GeomapCtrl', function($scope, angulargmContainer, $http, $location, $rootScope) {
            $scope.types = [
                {
                    name: "Selling",
                    icon: "icon-dollar"
                },
                {
                    name: "See Buyers",
                    icon: "icon-shopping-cart"
                }
            ];
            $scope.map;
            $scope.pins = [];
            $scope.options = {
                map: {
                    center: new google.maps.LatLng($scope.lat, $scope.lng),
                    zoom: 21,
                    mapTypeId: google.maps.MapTypeId.SATELLITE
                },
                marker: {
                    clickable: false,
                    draggable: true
                }
            };

            $scope.getLines = function() {
                var dataToSend = {};
                $http({
                    url: "/BraveHackers/crowds/lineService/getlines",
                    method: "GET",
                    data: dataToSend
                }).success(function(data) {
                    $scope.pins = data;
                    $scope.addPins();
                }).error(function(data) {
                    console.log('error:' + data);
                });
            }

            $scope.dropPin = function(type) {

                var dataToSend = {lat: $scope.lat, lng: $scope.lng, type: type, count: "0", vote: "0"};
                $http({
                    url: "/BraveHackers/crowds/lineService/addline",
                    method: "PUT",
                    data: dataToSend
                }).success(function(data, status, success, requestObject) {
                    var dataToSend = requestObject.data;
                    dataToSend.id = data;
                    $scope.pins.push(dataToSend);
                    $scope.addPin(dataToSend);
                    console.log('success:' + data);
                }).error(function(data) {
                    console.log('error:' + data);
                });

            }

            $scope.addPins = function() {
                angular.forEach($scope.pins, function(value) {
                    $scope.houses.push({
                        name: value.id,
                        lat: value.lat,
                        lng: value.lng,
                        options: {
                            icon: 'img/favicon.png'
                        }
                    })
                })
            }

            $scope.addPin = function(dataToSend) {
                console.log('$scope.lat:' + $scope.lat);
                console.log('$scope.lng:' + $scope.lng);
                $scope.houses.push({
                    name: dataToSend.id,
                    lat: dataToSend.lat,
                    lng: dataToSend.lng,
                    options: {
                        icon: dataToSend.type
                    }
                })
            }

            var x = document.getElementById("demo");

            $scope.lat;
            $scope.lng;

            $scope.latP;
            $scope.lngP;
            function showPosition(position)
            {
                $scope.latP = position.coords.latitude;
                $scope.lngP = position.coords.longitude;
                $scope.lat = $scope.latP;
                $scope.lng = $scope.lngP;
                $scope.center = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
                $scope.zoom = 19;
                $scope.map = angulargmContainer.getMap('dragMarkerMap');

                $scope.$apply();
            }
            ;



            if (navigator.geolocation)
            {
                navigator.geolocation.getCurrentPosition(showPosition);
            }
            else {
                x.innerHTML = "Geolocation is not supported by this browser.";
            }


            $scope.houses = [];
            $scope.getInfo = function(house, marker) {
                console.log("looking for marker: " + house.name);

                angular.forEach($scope.pins, function(value, key) {
                    console.log("looking: " + house.name + "and" + value.id);
                    if (value.id === house.name) {
                        //alert("found it:" + house.name);
                        console.log("foundit: " + house.name);
                        $rootScope.selectedPin = value;
                        $location.url("/editLine");
                    }
                });

            }

            $scope.setHouseLocation = function(house, marker) {
                var position = marker.getPosition();
                house.lat = position.lat();
                house.lng = position.lng();

                angular.forEach($scope.houses, function(value, key) {
                    if (value.name === house.name) {
                        console.log("foundit: " + house.name);
                    }
                });
            };
            $scope.selectedType = function(type) {
                if (type.name === "See Buyers") {
                    
                    return;
                }
                if ($scope.type === undefined) {
                    $scope.types.unshift({
                        name: "Add",
                        icon: "icon-plus"
                    })
                    $scope.type = type.name;
                    return;
                }

                if (type.name === "Add")
                    $scope.dropPin($scope.type);
                else
                    $scope.type = type.name;

                if ($scope.pins === undefined)
                    $scope.getLines();
                else {
                    //TODO ADD MORE PINS
                }
                /*     $('.add-new-marker').fadeOut().remove();
                 $('li.active').removeClass('active');
                 $(this).parent('li').addClass('active');
                 $('#app').append('<a href="#" class="add-new-marker"><i class="icon-plus"></i> New Marker</a>');
                 $('.add-new-marker').fadeIn();*/
            }
            function success() {
                console.log("SUCCESS");
            }
        })
        ;

