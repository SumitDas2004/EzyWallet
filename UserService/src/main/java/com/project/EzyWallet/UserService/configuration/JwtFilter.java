package com.project.EzyWallet.UserService.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.EzyWallet.UserService.entity.User;
import com.project.EzyWallet.UserService.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtService jwtService;

    @Autowired
    UserDetailsService userDetailsService;

    private final RequestMatcher loginRequestMatcher = new AntPathRequestMatcher("/login");
    private final RequestMatcher registerRequestMatcher = new AntPathRequestMatcher("/register");
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(this.loginRequestMatcher.matches(request) || this.registerRequestMatcher.matches(request)){
            filterChain.doFilter(request, response);
            return ;
        }

        try {
            String authToken = request.getHeader("Authorization");
            if (authToken != null && authToken.startsWith("Bearer ")) {
                String token = authToken.substring(7);
                if (SecurityContextHolder.getContext().getAuthentication() == null && !jwtService.isExpired(token)) {
                    String username = jwtService.extractUsername(token);
                    if (username != null) {
                        UserDetails user = userDetailsService.loadUserByUsername(username);
                        if (user != null && !((User)user).isLoggedOut()) {
                            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());
                            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                        }
                    }
                }
            }
            filterChain.doFilter(request, response);
        }catch(Exception e){
            response.setStatus(401);
            Map<String, Object> map = new HashMap<>();
            map.put("status", 0);
            map.put("message", "Please login.");
            String json = new ObjectMapper().writeValueAsString(map);
            response.getWriter().write(json);
        }
    }

}
