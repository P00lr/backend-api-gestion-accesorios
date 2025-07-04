package com.universidad.tecno.api_gestion_accesorios.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.universidad.tecno.api_gestion_accesorios.dto.PasswordChangeDto;
import com.universidad.tecno.api_gestion_accesorios.dto.user.UserWithRolesAndPermissionsDto;
import com.universidad.tecno.api_gestion_accesorios.entities.User;
import com.universidad.tecno.api_gestion_accesorios.repositories.UserRepository;
import com.universidad.tecno.api_gestion_accesorios.services.interfaces.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/page/{page}")
    public ResponseEntity<?> listPageable(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 7);
        Page<User> users = userService.paginateAll(pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping()
    public List<User> listUser() { 
        return userService.getAllUsers();
    }
   /*  @GetMapping
    public List<User> getUsers() {
        return userService.findAll();
    } */

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/with-permissions/{id}")
    public ResponseEntity<UserWithRolesAndPermissionsDto> getUserWithRolesAndPermissions(@PathVariable Long id) {
        UserWithRolesAndPermissionsDto result = userService.getUserWithRolesAndPermissions(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            return validation(result);
        }
        return userService.update(id, user)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean deleted = userService.deleteById(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    private ResponseEntity<Map<String, String>> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), "El campo " + error.getField() + " " + error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

    // ------------CAMBIO DE CONTRASEÑA---------------------------
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeDto dto) {
        Optional<User> userOpt = userRepository.findById(dto.getUserId());
        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = userOpt.get();
        if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

}
