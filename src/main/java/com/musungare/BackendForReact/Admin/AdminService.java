package com.musungare.BackendForReact.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public ResponseEntity<Admin> getAdmin(String email) {
        Admin byEmail = adminRepository.findByEmail(email);

        if (byEmail != null) {

            return ResponseEntity.ok(byEmail);
        } else {

            return ResponseEntity.notFound().build();
        }
    }

}
