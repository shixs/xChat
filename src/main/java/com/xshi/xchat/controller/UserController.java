package com.xshi.xchat.controller;

import com.xshi.xchat.model.UMessage;
import com.xshi.xchat.model.User;
import com.xshi.xchat.representation.UserRepresentation;
import com.xshi.xchat.security.PasswordHash;
import com.xshi.xchat.services.UserService;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by sheng on 4/12/2016.
 */
@RestController
@RequestMapping
public class UserController {
    private static final Logger log = LoggerContext.getContext().getLogger(UserController.class.getName());

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordHash passwordHash;

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public UMessage chat(UMessage message) throws Exception{
        Date date = new Date();
        return new UMessage(message,new Timestamp(date.getTime()));
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUser(@PathVariable("id") int id) {
        log.info("Fetching User with id " + id);
        User user = userService.findUser(id);
        if (user == null) {
            log.info("User with id " + id + "not found.");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{id}/password", method = RequestMethod.PUT)
    public ResponseEntity<User> reSetPassword(@PathVariable("id") int id, @RequestParam(value = "password") String password) {
        log.info("Resetting user password with user id " + id);
        User user = userService.findUser(id);
        try {
            String hashedPassword = passwordHash.createHash(password);
            String salt = passwordHash.getPasswordSalt();
            user.setUser_password(hashedPassword);
            user.setUser_password_salt(salt);
        } catch (NoSuchAlgorithmException e) {
            log.error("Failed resetting password: " + e.getMessage());
            return new ResponseEntity<User>(HttpStatus.CONFLICT);
        } catch (InvalidKeySpecException e) {
            log.error("Failed resetting password: " + e.getMessage());
            return new ResponseEntity<User>(HttpStatus.CONFLICT);
        }
        userService.updateUser(user);
        return new ResponseEntity<User>(HttpStatus.OK);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "http://localhost:8080")
    public ResponseEntity<UserRepresentation> login(@RequestBody User user) {
        log.info("User login with name " + user.getUser_name());
        User currUser = userService.findUserByName(user.getUser_name());
        if (currUser == null) {
            return new ResponseEntity<UserRepresentation>(HttpStatus.NOT_FOUND);
        }
        try {
            if (passwordHash.validatePassword(user.getUser_password(), currUser.getUser_password())) {
                return new ResponseEntity<UserRepresentation>(new UserRepresentation(currUser), HttpStatus.OK);
            } else {
                return new ResponseEntity<UserRepresentation>(HttpStatus.CONFLICT);
            }
        } catch (NoSuchAlgorithmException e) {
            log.error("Failed login with the user name = " + user.getUser_name() + "\n" + e.getMessage());
            return new ResponseEntity<UserRepresentation>(HttpStatus.CONFLICT);
        } catch (InvalidKeySpecException e) {
            log.error("Failed login with the user name = " + user.getUser_name() + "\n" + e.getMessage());
            return new ResponseEntity<UserRepresentation>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<List<User>> listAllUsers() {
        log.info("Fetching All registered user...");
        List<User> users = userService.findAllUser();
        if (users.isEmpty()) {
            return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/user",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder uriComponentsBuilder) {
        log.info("Creating User " + user.getUser_name());
        if (userService.isUserExist(user)) {
            log.info("A User with name " + user.getUser_name() + "already exist.");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        userService.addUser(user);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponentsBuilder.path("/{id}").buildAndExpand(user.getUser_id()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
}
