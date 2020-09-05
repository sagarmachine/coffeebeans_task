package com.coffeebeans.technical_task.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {



    @NotNull
     @Email
    String email;

    @NotNull
    String secret;

    @NotNull
    String name;

}
