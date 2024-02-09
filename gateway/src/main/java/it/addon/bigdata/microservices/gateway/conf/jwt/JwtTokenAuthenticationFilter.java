package it.addon.bigdata.microservices.gateway.conf.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    private final JwtConfig jwtConfig;

    public JwtTokenAuthenticationFilter(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // GET TOKEN
        // DECODE TOKEN
        String username = "admin";
        List<String> authorities = new ArrayList();
        // authorities.add("ROLE_ADMIN");
        // authorities.add("ADMIN");

        // DO SPRING SECU AUTH

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                username, null, authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

        // Authenticate the user
        // Now, user is authenticated
        SecurityContextHolder.getContext().setAuthentication(auth);

        // DO REQUEST
        // go to the next filter in the filter chain
        chain.doFilter(request, response);
    }
}
