package com.coffeebeans.technical_task.security;


import com.coffeebeans.technical_task.entity.Client;
import com.coffeebeans.technical_task.repo.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;


@RestController
@Slf4j
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    ClientRepository clientRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken)authentication;
        Map<String,Object> map=token.getPrincipal().getAttributes();
       String email= token.getPrincipal().getAttributes().get("email").toString();
        if(clientRepository.findByEmail(email).isPresent()){
            Client client= clientRepository.findByEmail(email).get();
            client.setJwtExpired(false);
            clientRepository.save(client);

        }
        else{
            BCryptPasswordEncoder bCryptPasswordEncoder= new BCryptPasswordEncoder();
            Client client= new Client();
            client.setName(map.get("given_name").toString());
            client.setEmail(map.get("email").toString());
            clientRepository.save(client);

        }
        UserDetails userDetails = new User(map.get("given_name").toString(),"123",new ArrayList<>());
        String jwtToken ="Bearer "+jwtUtil.generateToken(userDetails);

        httpServletResponse.sendRedirect("/api/v1/client/oauthController/"+jwtToken+"/"+map.get("email").toString()+"/"+map.get("given_name").toString());

    }


}
