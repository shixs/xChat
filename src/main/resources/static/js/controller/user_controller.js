/**
 * Created by sheng on 4/13/2016.
 */
app.controller('UserController',['UserService','$scope',function(UserService,$scope){
    var self = this;
    self.requestUser={user_name:'',user_password:''};
    self.responseUser={user_id:null,user_name:'',user_email:'',user_role:''};
    self.userList = [];
    self.doLogin = function(ruser){
        UserService.login(ruser)
            .then(
                function(d){
                    self.responseUser = d;
                },
                function(errResponse){
                    console.error('Failed login');
                }
            );
    };
    self.getUserList = function(){
        UserService.getUserList()
            .then(
                function(d){
                    self.userList = d;
                    return self.userList;
                },
                function(errResponse){
                    console.error('Error while fetching user list.')
                }
            );
    }
}]);