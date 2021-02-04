package com.songr.services.contract.controller;

import com.songr.services.contract.resource.UserResource;
import com.songr.services.contract.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public UserResource signup(@RequestParam(name = "email", value = "email", required = true) String email,
                               @RequestParam(name = "username", value = "username", required = true) String username,
                               @RequestParam(name = "password", value = "password", required = true) String password) {
        //TODO now it just returns what it got, needs to generate token and return that as well
        UserResource ur = new UserResource();
        ur.setEmail(email);
        ur.setUserName(username);
        ur.setPassword(password);
        ur.setToken("randomtesttokenfor" + username);
        return null;
    }

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public UserResource login(@RequestParam(name = "username", value = "username", required = true) String username,
                              @RequestParam(name = "password", value = "password", required = true) String password) {
        //TODO check if exists and generate token, return everything
        //Should return the token and user resource
        UserResource ur = new UserResource();
        ur.setEmail("test@test");
        ur.setUserName(username);
        ur.setPassword(password);
        ur.setToken("randomtesttokenfor" + username);
        return ur;
    }

    @RequestMapping(value = "/signout", method = RequestMethod.POST)
    public String logout(@RequestParam(name = "token", value = "token", required = true) String password) {
        //TODO I think only deleting the token from the db
        return null;
    }
}
