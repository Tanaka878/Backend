package com.musungare.BackendForReact.Admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByEmail(String email);
    List<Admin> findAllByOrderByIdDesc();

}
