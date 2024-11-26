package com.musungare.BackendForReact.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/getAdmin/{email}")
     public ResponseEntity<Admin> getAdmin(@PathVariable String email) {
        return adminService.getAdmin(email);
    }
}
