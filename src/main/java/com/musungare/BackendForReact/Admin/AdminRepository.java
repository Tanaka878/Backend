package com.musungare.BackendForReact.Admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByEmail(String email);

}
