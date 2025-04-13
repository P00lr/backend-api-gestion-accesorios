package com.universidad.tecno.api_gestion_accesorios.services;

import java.util.List;
import java.util.Optional;

import com.universidad.tecno.api_gestion_accesorios.entities.User;

public interface UserService {
    List<User> findAll();
    Optional<User> findById(Long id);
    User save(User user);
    Optional<User> update(Long id, User user);
    boolean deleteById(Long id);

}
