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

        // 1. get the authentication header
        // Tokens are supposed to be passed in the authentication header
        String header = request.getHeader(jwtConfig.getHeader());

        // 2. validate the header and check if token present
        // To check token presence, check if prefix is present
        if(header == null || !header.startsWith(jwtConfig.getPrefix())) {
            // If not valid, go to the next filter theen return;
            chain.doFilter(request, response);
            return;
        }

        // if we stop up there, user has no token (it can only access public parts)
        // if we reach the next, then a token is present and we need to check it

        // 3. Get the token
        String token = header.replace(jwtConfig.getPrefix(), "");

        // 4. Validate the token
        Claims claims = Jwts.parser()
                .setSigningKey(jwtConfig.getSecret())
                .parseClaimsJws(token)
                .getBody();

        // get username from token
        String username = claims.getSubject();
        if(username != null) {
            // get authorities from token
            List<String> authorities = (List<String>) claims.get("authorities");

            // 5. Create auth object
            // UsernamePasswordAuthenticationToken:
            // A built-in object, used by spring to represent the current authenticated / being authenticated user.
            // It needs a list of authorities, which has type of GrantedAuthority interface, where SimpleGrantedAuthority is an implementation of that interface
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    username, null, authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

            // UserDetails

            // 6. Authenticate the user
            // Now, user is authenticated
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        // go to the next filter in the filter chain
        chain.doFilter(request, response);

    }
}
