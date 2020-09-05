package com.coffeebeans.technical_task.repo;

import com.coffeebeans.technical_task.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository  extends JpaRepository<Client,Integer> {

    Optional<Client> findByEmail(String email);

}
