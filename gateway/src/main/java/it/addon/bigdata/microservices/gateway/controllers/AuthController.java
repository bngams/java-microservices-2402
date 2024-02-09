package it.addon.bigdata.microservices.gateway.controllers;

import it.addon.bigdata.microservices.gateway.models.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("")
    public void login(@RequestBody User user) {
        throw new RuntimeException("TODO");
        // generate token
    }
}
