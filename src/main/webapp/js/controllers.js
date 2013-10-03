'use strict';

/* Controllers */

angular.module('myApp.controllers', ['AngularGM', 'ngDragDrop']).
        controller('MyCtrl1', function() {
        })
        .controller('MyCtrl2', function() {
        })
        .controller('MyCtrl3', function() {
        })
        .controller('MyCtrl4', function() {
        })
        .controller('GeomapCtrl', function($scope, angulargmContainer) {
            $scope.map;
            $scope.pin = {
                title: 'picture'
            };
            $scope.list1 = {title: 'AngularJS - Drag Me'};
            $scope.list2 = {};

            initialize();
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
            function initialize() {
                $scope.overlay = new google.maps.OverlayView();
                $scope.overlay.draw = function() {
                };
                //     var map = new google.maps.Map($('#dragMarkerMap'), $scope.options);
                //    $scope.overlay.setMap(map);
            }

            $scope.touchStartFn = function(e) {

                var scope = $scope;
                var a = e;
                // if (scope.overlay.getMap() === undefined) scope.overlay.setMap(angulargmContainer.getMap('dragMarkerMap'));

                var point = new google.maps.Point(e.pageX, e.pageY);
                $scope.location = $scope.overlay.getProjection().fromContainerPixelToLatLng(point);
                
                $scope.$apply();
                console.log('e.pageX:'+e.pageX+' e.pageY:'+e.pageY);
                
               // placeMarker(ll);

            }
            $scope.$watch('location', function(newVal) {
                if (newVal !== undefined)
                console.log('newPointlat:'+newVal.lat+' lng:'+newVal.lng);
            });
            
            function placeMarker(location) {
                var map = $scope.map;
               /* var marker = new google.maps.Marker({
                    position: location,
                    map: map,
                    icon: 'spring-hot.png'
                });*/
                console.log('lat:'+location.lat+' lng:'+location.lng);
                $scope.houses.push({
                    name: 'myRoom',
                    position: location,
                    icon: 'spring-hot.png'
                })
            }
            var x = document.getElementById("demo");

            $scope.lat = 46;
            $scope.lng = -122;

            function showPosition(position)
            {
                $scope.lat = position.coords.latitude;
                $scope.lng = position.coords.longitude;
                $scope.center = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
                $scope.zoom = 19;
                $scope.overlay.setMap(angulargmContainer.getMap('dragMarkerMap'));
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

