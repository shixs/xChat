package com.xshi.xchat.services;

import com.xshi.xchat.dao.UserDao;
import com.xshi.xchat.model.User;
import com.xshi.xchat.security.PasswordHash;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

/**
 * Created by sheng on 4/12/2016.
 */
@Component
public class UserService {
    private static final Logger log = LoggerContext.getContext().getLogger(UserService.class.getName());

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordHash passwordHash;

    @Transactional
    public void addUser(User user){
        try {
            String hashedPassword = passwordHash.createHash(user.getUser_password());
            String salt = passwordHash.getPasswordSalt();
            user.setUser_password(hashedPassword);
            user.setUser_password_salt(salt);
            user.setUser_role("USER");
        } catch (NoSuchAlgorithmException e) {
            log.error("Failed hashing the given password: " + e.getMessage());
        } catch (InvalidKeySpecException e) {
            log.error("Failed hashing the given password: " + e.getMessage());
        }
        userDao.create(user);
    }

    @Transactional
    public void updateUser(User user){
        userDao.update(user);
    }

    @Transactional
    public User findUser(Object id){
        return userDao.find(id);
    }

    @Transactional
    public User findUserByName(String name){
        return userDao.findByName(name);
    }

    public boolean isUserExist(User user){
        return this.findUserByName(user.getUser_name()) == null ? false : true;
    }

    @Transactional
    public List<User> findAllUser(){
        return userDao.findAll();
    }

    @Transactional
    public void removeUser(User user){
        userDao.remove(user);
    }
}
