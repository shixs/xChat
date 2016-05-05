/**
 * Created by sheng on 4/14/2016.
 */
app.controller('ChatController',['$scope','ChatService', function($scope,ChatService){
    var self = this;
    self.message = '';
    self.messages = [];
    self.username = '';
    self.sendMessage = function(m){
        console.info(m);
        ChatService.send("/app/chat",{},JSON.stringify({'content':m}));
    };

    self.initStompClient = function() {
        ChatService.init('/chat');

        ChatService.connect(function(frame) {

            self.username = frame.headers['user-name'];

            ChatService.subscribe("/topic/messages", function(message) {
                console.info(message.body);
                self.messages.push(JSON.parse(message.body));
                console.info(self.messages);
            });

        }, function(error) {
            console.error('error', 'Error', 'Connection error ' + error);
        });
    };

    self.showDate = function(timestamp){
        var readableDate = new Date(timestamp).toLocaleString();
        return readableDate;
    }

    self.initStompClient();
}]);