package com.coffeebeans.technical_task.service;

import com.coffeebeans.technical_task.dto.ClientDto;
import com.coffeebeans.technical_task.dto.LoginDto;
import com.coffeebeans.technical_task.entity.Client;
import com.coffeebeans.technical_task.exception.AuthenticationException;
import com.coffeebeans.technical_task.exception.ClientAlreadyExistException;
import com.coffeebeans.technical_task.repo.ClientRepository;
import com.coffeebeans.technical_task.security.JWTUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Optional;


@Service
public class ClientServiceImpl implements IClientService {


    @Autowired
    ClientRepository clientRepository;

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Client> clientOptional= clientRepository.findByEmail(email);

        if(!clientOptional.isPresent())
            return null;
        return new User(clientOptional.get().getEmail(),clientOptional.get().getSecret(), new ArrayList<>());

    }

    @Override
    public ResponseEntity<?> authenticateClient(LoginDto loginDto) {

        Authentication token= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getSecret(),new ArrayList<>())) ;
        if(!token.isAuthenticated()){
            throw  new AuthenticationException("credentials does not match");
        }

        UserDetails user =loadUserByUsername(loginDto.getEmail());
        String jwtToken ="Bearer "+jwtUtil.generateToken(user);
        Client client= clientRepository.findByEmail(loginDto.getEmail()).get();
        client.setJwtExpired(false);
        clientRepository.save(client);
        return new ResponseEntity<>("Authentication Successful \n"+client.getEmail()+"  "+client.getName()+"\n jwt token created :"+jwtToken, HttpStatus.ACCEPTED);

    }

    @Override
    public ResponseEntity<?> addClient(ClientDto clientDto) {

           Optional<Client> clientOptional= clientRepository.findByEmail(clientDto.getEmail());

           if(clientOptional.isPresent())
                throw  new ClientAlreadyExistException("client with email '"+clientDto.getEmail()+" already exist");

        ModelMapper mapper = new ModelMapper();
        Client client = mapper.map(clientDto, Client.class);
        client.setJwtExpired(false);
        client.setSecret(bCryptPasswordEncoder.encode(client.getSecret()));
        clientRepository.save(client);
        UserDetails user =new User(client.getEmail(),client.getSecret(),new ArrayList<>());
        String jwtToken ="Bearer "+jwtUtil.generateToken(user);
        return new ResponseEntity<>("Registration Successful \n"+client.getEmail()+"  "+client.getName()+"\n jwt token created :"+jwtToken, HttpStatus.ACCEPTED);

    }
}
