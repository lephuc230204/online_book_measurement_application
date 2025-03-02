package com.example.online_book_measurement_application.repository;


import com.example.online_book_measurement_application.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public  interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);

}
