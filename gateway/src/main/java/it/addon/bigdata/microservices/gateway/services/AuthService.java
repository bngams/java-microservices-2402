package it.addon.bigdata.microservices.gateway.services;

import it.addon.bigdata.microservices.gateway.models.User;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class AuthService {

    /**
     * Fake authenticate process
     * @param username
     * @param password
     * @return
     */
    public boolean authenticate(String username, String password) {
        // hard coding the users. All passwords must be encoded.
        // we can use a database where we can query the users instead
        final List<User> appUsers = Arrays.asList(
                new User(1, "admin", "admin", "ADMIN"),
                new User(2, "user", "user", "USER")
        );

        // simulate auth and
        for (User appUser : appUsers) {
            if (appUser.getUsername().equals(username)
                    && appUser.getPassword().equals(password))
                return true;
        }
        return false;
    }
}
