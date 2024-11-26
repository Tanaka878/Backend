package com.musungare.BackendForReact.Admin;

import com.musungare.BackendForReact.DTO.AdminData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/admin")

public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/getAdmin/")
     public ResponseEntity<Admin> getAdmin(@RequestBody AdminData admin) {
        return adminService.getAdmin(admin.getEmail(), admin.getPassword());
    }
}
