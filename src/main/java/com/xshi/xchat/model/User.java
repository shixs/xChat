package com.xshi.xchat.model;

import javax.persistence.*;

/**
 * Created by sheng on 4/12/2016.
 */
@Entity
@Table(name = "xchatusers")
@NamedQuery(name = "User.findByName", query = "select u from User u where u.user_name = :user_name")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer user_id;
    private String user_name;
    private String user_password;
    private String user_email;
    @Column
    private String user_role;
    private String user_password_salt;
    public User(){}
    public User(String name, String pwd){
        this.user_name = name;
        this.user_password = pwd;
    }

    public User(User user){
        this.user_id = user.getUser_id();
        this.user_name = user.getUser_name();
        this.user_email = user.getUser_email();
        this.user_role = user.getUser_role();

    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }

    public String getUser_password_salt() {
        return user_password_salt;
    }

    public void setUser_password_salt(String user_password_salt) {
        this.user_password_salt = user_password_salt;
    }

    @Override
    public String toString(){
        return "User [id = " + user_id + ",name = " + user_name;
    }
}
