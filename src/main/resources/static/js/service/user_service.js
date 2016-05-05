/**
 * Created by sheng on 4/13/2016.
 */
'use strict';
app.factory('UserService',['$http','$q',function($http,$q){
    return{
        login: function(user){
            return $http.post('http://localhost:8080/login',user)
                .then(
                    function(response){
                        return response.data;
                    },
                    function(errResponse){
                        console.error('failed login');
                        return $q.reject(errResponse);
                    }
                );
        },
        getUserList:function(){
            return $http.get('http://localhost:8080/user')
                .then(
                    function(response){
                        return response.data;
                    },
                    function(errResponse){
                        return $q.reject(errResponse);
                    }
                );
        }
    };
}]);