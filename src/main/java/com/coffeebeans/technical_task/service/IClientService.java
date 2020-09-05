package com.coffeebeans.technical_task.service;

import com.coffeebeans.technical_task.dto.ClientDto;
import com.coffeebeans.technical_task.dto.LoginDto;
import com.coffeebeans.technical_task.entity.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IClientService  extends UserDetailsService {



    ResponseEntity<?> authenticateClient(LoginDto loginDto);
    ResponseEntity<?> addClient(ClientDto clientDto);

}
