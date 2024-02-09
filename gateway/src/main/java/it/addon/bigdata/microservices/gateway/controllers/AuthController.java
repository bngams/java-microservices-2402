package it.addon.bigdata.microservices.gateway.controllers;

import it.addon.bigdata.microservices.gateway.conf.jwt.JwtTokenUtil;
import it.addon.bigdata.microservices.gateway.models.User;
import it.addon.bigdata.microservices.gateway.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("")
    public ResponseEntity<String> login(@RequestBody User partialUser) {
        if(this.authService.authenticate(partialUser.getUsername(), partialUser.getPassword())) {
            return ResponseEntity.ok(this.jwtTokenUtil.generateToken(partialUser.getUsername()));
        }
        else
            return ResponseEntity.status(401).body("Unknown user");
    }
}
