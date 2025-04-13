package com.universidad.tecno.api_gestion_accesorios.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.universidad.tecno.api_gestion_accesorios.entities.User;
import com.universidad.tecno.api_gestion_accesorios.repositories.UserRepository;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> update(Long id, User user) {
        return userRepository.findById(id).map(existingUser -> {
            if (user.getName() != null)
                existingUser.setName(user.getName());
            if (user.getUsername() != null)
                existingUser.setUsername(user.getUsername());
            if (user.getPassword() != null)
                existingUser.setPassword(user.getPassword());
            if (user.getEmail() != null)
                existingUser.setEmail(user.getEmail());
            return userRepository.save(existingUser);
        });
    }

    @Override
    public boolean deleteById(Long id) {
        Optional<User> userOp = userRepository.findById(id);
        if (userOp.isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
}
