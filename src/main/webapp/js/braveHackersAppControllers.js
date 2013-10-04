'use strict';

/* Controllers */

angular.module('braveHackers.controllers', ['AngularGM', 'ngResource'])

        .controller('HomeCtrl', function() {
        })
        .controller('MyCtrl1', function() {
        })
        .controller('MyCtrl2', function($scope, $location) {
            $scope.hello = 'hello world';
            $scope.submit = function() {
                backent.put({username: $scope.hello});
                $location.url('#/view2');
            }
        })
        .controller('MyCtrl3', function() {
        })
        .controller('MyCtrl4', function() {
        })
        .controller('GeomapCtrl', function($scope, angulargmContainer, $http) {
            $scope.map;
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
            
            $scope.getLines = function(type) {
                var dataToSend = {};
                $http({
                    url: "/BraveHackers/crowds/lineService/getlines",
                    method: "GET",
                    data: dataToSend
                }).success(function(data) {
                    $scope.addPin(data);
                    console.log('success:' + data);
                }).error(function(data) {
                    console.log('error:' + data);
                });
            }
            
            $scope.dropPin = function() {

                var dataToSend = {lat: $scope.lat, lng: $scope.lng, type: "FINALLY", count: "3", vote: "8"};
                $http({
                    url: "/BraveHackers/crowds/lineService/addline",
                    method: "PUT",
                    data: dataToSend
                }).success(function(data) {
                    $scope.addPin(data, $scope.latP, $scope.lngP);
                    console.log('success:' + data);
                }).error(function(data) {
                    console.log('error:' + data);
                });

            }
                
            $scope.addPin = function(pinID, lat, lng) {
                $scope.houses.push({
                    name: pinID,
                    lat: lat,
                    lng: lng,
                    options: 'img/favicon.png'
                })
                alert("Please Move Pin to the head of the line.");
            }

            $scope.$watch('location', function(newVal) {
                if (newVal !== undefined)
                    console.log('newPointlat:' + newVal.lat + ' lng:' + newVal.lng);
            });


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
            $scope.clickDrag = function(e) {
                var a = e;
            }

        })
        ;

