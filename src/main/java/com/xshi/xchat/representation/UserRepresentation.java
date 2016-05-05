package com.xshi.xchat.representation;

import com.xshi.xchat.model.User;

/**
 * Created by sheng on 4/13/2016.
 */
public class UserRepresentation {
    private int user_id;
    private String user_name;
    private String user_email;
    private String user_role;

    public UserRepresentation(User user){
        this.user_id = user.getUser_id();
        this.user_name = user.getUser_name();
        this.user_email = user.getUser_email();
        this.user_role = user.getUser_role();
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    @Override
    public String toString(){
        return "User with user name= " + this.user_name;
    }
}
