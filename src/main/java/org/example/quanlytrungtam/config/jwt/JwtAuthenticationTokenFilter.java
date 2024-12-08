package org.example.quanlytrungtam.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.quanlytrungtam.user.User;
import org.example.quanlytrungtam.user.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private JwtService jwtService;

    @Autowired
    @Lazy
    private UserServiceInterface userService;

    public JwtAuthenticationTokenFilter(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String jwt = getJwtFromRequest(request);
        if (jwt != null && jwtService.validateJwtToken(jwt)) {
            String username = jwtService.getUsernameFromJwtToken(jwt);
            if (username != null) {
                UserDetails userDetails = userService.loadUserByUsername(username);
                Integer id = ((User) userDetails).getId();
                String jwtInRedis = (String) redisTemplate.opsForValue().get("user:" + id + ":tokens");
                if (jwt.equals(jwtInRedis)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    authentication.setDetails(userDetails);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    System.out.println(jwtInRedis);
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            return authorization.replace("Bearer ", "");
        }
        return null;
    }
}

