package com.coffeebeans.technical_task.entity;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue
    int id ;

    String name;

    String email;

    String secret;


    //to check if client's jwt is expired or not
    boolean jwtExpired=true;

}
