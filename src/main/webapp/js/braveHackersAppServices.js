'use strict';

/* Services */


// Demonstrate how to register services
// In this case it is a simple value service.
angular.module('braveHackers.services', ['ngResource'])
        .value('version', '0.2')
        .factory('Line', function($resource) {
            return $resource('/BraveHackers/crowds/lineService/addline',
                    {alt: 'json'},
            {
                get: {method: 'JSONP', params: {visibility: '@self'}}, 
                replies: {method: 'JSONP', params: {visibility: '@self', comments: '@comments'}}
            }
            );
        })
        ;