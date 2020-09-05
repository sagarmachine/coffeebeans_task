package com.coffeebeans.technical_task.security;


import com.coffeebeans.technical_task.repo.ClientRepository;
import com.coffeebeans.technical_task.service.IClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;



@Slf4j
@Component
public class AuthorizationFilter extends OncePerRequestFilter {


    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    IClientService clientService;

    @Autowired
    ClientRepository clientRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {


        String authorization= httpServletRequest.getHeader("Authorization");


        if(authorization!=null && authorization.length()>7){

            String jwtToken = authorization.substring(7);

            String email = jwtUtil.getUsernameFromToken(jwtToken);
            UserDetails user =clientService.loadUserByUsername(email);

            if(user!=null && jwtUtil.validateToken(jwtToken,user) && SecurityContextHolder.getContext().getAuthentication()==null && !clientRepository.findByEmail(user.getUsername()).get().isJwtExpired() ){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(),null,new ArrayList<>() );
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
               SecurityContext context = SecurityContextHolder.createEmptyContext();
                context.setAuthentication(usernamePasswordAuthenticationToken);
                SecurityContextHolder.setContext(context);
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

filterChain.doFilter(httpServletRequest, httpServletResponse);
    }



}
