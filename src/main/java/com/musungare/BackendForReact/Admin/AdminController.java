package com.musungare.BackendForReact.Admin;

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

    @GetMapping("/getAdmin/{email}/{password}")
     public ResponseEntity<Admin> getAdmin(@PathVariable String email,@PathVariable String password) {
        return adminService.getAdmin(email,password);
    }
}
