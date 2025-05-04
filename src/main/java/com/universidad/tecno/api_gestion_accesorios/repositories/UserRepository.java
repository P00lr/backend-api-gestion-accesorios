package com.universidad.tecno.api_gestion_accesorios.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.universidad.tecno.api_gestion_accesorios.entities.User;

public interface UserRepository extends CrudRepository<User, Long>{
    Optional<User> findByUsername(String name);
}
