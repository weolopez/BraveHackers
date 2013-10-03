'use strict';

/* Controllers */

angular.module('myApp.controllers', ['AngularGM', 'ngTouch']).
        controller('MyCtrl1', function() {
        })
        .controller('MyCtrl2', function() {
        })
        .controller('MyCtrl3', function() {
        })
        .controller('MyCtrl4', function() {
        })
        .controller('GeomapCtrl', function($scope) {
            var overlay;
            
            $scope.dragObj = {
                helper: 'clone',
                stop: function(e) {
                    var point = new google.maps.Point(e.pageX,e.pageY);
                    var ll = overlay.getProjection().fromContainerPixelToLatLng(point);
                }
            }
            var x = document.getElementById("demo");

            $scope.lat = 46;
            $scope.lng = -122;

            function showPosition(position)
            {
                $scope.lat = position.coords.latitude;
                $scope.lng = position.coords.longitude;
                $scope.center = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
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
        
