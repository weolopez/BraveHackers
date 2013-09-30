<%-- 
    Document   : phonefeatures
    Created on : Sep 30, 2013, 4:40:13 PM
    Author     : mlopez
--%>

<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE html>
<html manifest="compass.appcache">
<head>
<meta charset=utf-8 />
<title>Cardinal Direction Compass</title>
<style>
  .pointer {
    height: 0;
    width: 0;
    border-left: 2em solid transparent;
    border-right: 2em solid transparent;
    border-bottom: 8em solid black;
    margin: -10px 0 0 -40px;
    top: 40%;
    left: 50%;
    position: absolute;
  }
  .n {
    top: 20%;
    left:50%;
    position:absolute;
    font:5em Helvetica;
    margin: 0 0 0 -40px;
  }
  </style>
  <script src="js/lib/jquery/jquery.js"></script>
</head>
<body>
  <div class="n">N<br><br><br><br>S</div>
  <div class="pointer"></div>
<script>
$(document).ready(function() {

  var rotate = function (deg) {  
      $(".n").css({ "-moz-transform": "rotate(0deg)"});
      $(".n").css({ "-moz-transform": "rotate(" + deg + "deg)"});
    
      $(".n").css({ "-o-transform": "rotate(0deg)"});
      $(".n").css({ "-o-transform": "rotate(" + deg + "deg)"});
    
      $(".n").css({ "-ms-transform": "rotate(0deg)"});
      $(".n").css({ "-ms-transform": "rotate(" + deg + "deg)"});
    
      $(".n").css({ "-webkit-transform": "rotate(0deg)"});
      $(".n").css({ "-webkit-transform": "rotate(" + deg + "deg)"});
    
      $(".n").css({ "transform": "rotate(0deg)"});
      $(".n").css({ "transform": "rotate(" + deg + "deg)"});
  };
  if (window.DeviceOrientationEvent) {
    window.addEventListener("deviceorientation", function (e) {
      rotate(360 - e.alpha);
    }, false);
  }
  
});
</script>
  </body>
</html>
