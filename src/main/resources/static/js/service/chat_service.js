/**
 * Created by sheng on 4/14/2016.
 */
'use strict';

/* Services */

    app.factory('ChatService', ['$rootScope', function($rootScope) {
        var stompClient = null;

        var wrappedSocket = {

            init: function(url) {
                var socket = new SockJS(url);
                stompClient = Stomp.over(socket);
            },
            connect: function(successCallback, errorCallback) {

                stompClient.connect({}, function(frame) {
                    $rootScope.$apply(function() {
                        successCallback(frame);
                    });
                }, function(error) {
                    $rootScope.$apply(function(){
                        errorCallback(error);
                    });
                });
            },
            subscribe : function(destination, callback) {
                stompClient.subscribe(destination, function(message) {
                    $rootScope.$apply(function(){
                        callback(message);
                    });
                });
            },
            send: function(destination, headers, object) {
                stompClient.send(destination, headers, object);
            }
        }

        return wrappedSocket;

    }]);