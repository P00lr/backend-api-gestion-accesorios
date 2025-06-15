package com.universidad.tecno.api_gestion_accesorios.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.universidad.tecno.api_gestion_accesorios.dto.dashboard.LatestUserDTO;
import com.universidad.tecno.api_gestion_accesorios.entities.User;

public interface UserRepository extends CrudRepository<User, Long>{
    Optional<User> findByUsername(String name);
    Page<User> findAll(Pageable pageable);

    //dashboard
     @Query("SELECT COUNT(u) FROM User u")
    Long getTotalUsersCount();

    @Query("SELECT NEW com.universidad.tecno.api_gestion_accesorios.dto.dashboard.LatestUserDTO( u.id, u.name, u.email) FROM User u ORDER BY u.id DESC")
    List<LatestUserDTO> findLatestUsers(Pageable pageable);
}
