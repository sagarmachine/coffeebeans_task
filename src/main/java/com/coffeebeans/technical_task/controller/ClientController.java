package com.coffeebeans.technical_task.controller;


import com.coffeebeans.technical_task.dto.ClientDto;
import com.coffeebeans.technical_task.dto.LoginDto;
import com.coffeebeans.technical_task.entity.Client;
import com.coffeebeans.technical_task.repo.ClientRepository;
import com.coffeebeans.technical_task.security.JWTUtil;
import com.coffeebeans.technical_task.service.IBindingErrorService;
import com.coffeebeans.technical_task.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/client")
public class ClientController {


    @Autowired
    IBindingErrorService bindingErrorService;


    @Autowired
    ClientRepository clientRepository;

    @Autowired
    IClientService clientService;


    @Autowired
    JWTUtil jwtUtil;

    @PostMapping(value = "/register")
    public  ResponseEntity<?>  registerClient(@Valid @RequestBody ClientDto clientDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
           return  bindingErrorService.getErrorResponse(bindingResult);
        }

        return clientService.addClient(clientDto);
    }


    @PostMapping(value="/authenticate")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDto loginDto, BindingResult bindingResult)
    {

        if(bindingResult.hasErrors()){
            bindingErrorService.getErrorResponse(bindingResult);
        }

        return clientService.authenticateClient(loginDto);
    }


    @PostMapping("/logout")
    public String logout(HttpServletRequest httpServletRequest){

        String authorization= httpServletRequest.getHeader("Authorization");
        String jwtToken = authorization.substring(7);

        String email = jwtUtil.getUsernameFromToken(jwtToken);
        Client client = clientRepository.findByEmail(email).get();
        client.setJwtExpired(true);
        clientRepository.save(client);

        return "Logged Out";
    }

    @GetMapping(value="/securedpoint")
    public String getSecuredPoint(){
        return "Secured Point Accessed";
    }


    @RequestMapping("/oauthController/{jwt}/{email}/{name}")
    @ResponseBody
    public ResponseEntity<?> oauthController(@PathVariable("jwt")String jwt,@PathVariable("email")String email,@PathVariable("name")String name){

        return new ResponseEntity<>("OAuth Successful \n"+email+"  "+name+"\n jwt token created :"+jwt, HttpStatus.ACCEPTED);

    }



}
