'use strict';

/* Controllers */

angular.module('braveHackers.controllers', ['AngularGM', 'ngResource']).
        controller('MyCtrl1', function() {
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
        .controller('GeomapCtrl', function($scope, angulargmContainer, $resource) {
            $scope.map;
            $scope.options = {
                map: {
                    center: new google.maps.LatLng($scope.lat, $scope.lng),
                    zoom: 21,
                    mapTypeId: google.maps.MapTypeId.ROADMAP
                },
                marker: {
                    clickable: false,
                    draggable: true
                }
            };

            $scope.dropPin = function() {
                var line = new $resource('/BraveHackers/crowds/lineService/addline');
                line.lat = $scope.lat;
                line.lng = $scope.lng;
                line.type = 'food';
                line.count = 3;
                line.vote = 3;

                line.save($scope.addPin);
            }

            $scope.addPin = function(pin, putResponseHeaders) {
                $scope.houses.push({
                    name: pin.id,
                    lat: $scope.lat,
                    lng: $scope.lng,
                    icon: 'spring-hot.png'
                })
                alert("Please Move Pin to the head of the line.");
            }

            $scope.$watch('location', function(newVal) {
                if (newVal !== undefined)
                    console.log('newPointlat:' + newVal.lat + ' lng:' + newVal.lng);
            });


            var x = document.getElementById("demo");

            $scope.lat = 46;
            $scope.lng = -122;

            function showPosition(position)
            {
                $scope.lat = position.coords.latitude;
                $scope.lng = position.coords.longitude;
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


            $scope.houses = [{
                    name: 'myHouse',
                    lat: 46,
                    lng: -122
                }];

            $scope.setHouseLocation = function(house, marker) {
                var position = marker.getPosition();
                house.lat = position.lat();
                house.lng = position.lng();
            };
            $scope.clickDrag = function(e) {
                var a = e;
            }

        })
        ;

