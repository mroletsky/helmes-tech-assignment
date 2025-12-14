package com.example.helmestechassignment.controller;

import com.example.helmestechassignment.dto.UserSectorResponse;
import com.example.helmestechassignment.dto.UserSectorSaveRequest;
import com.example.helmestechassignment.service.UserSectorService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user-sectors")
public class UserSectorController {

    private final UserSectorService userSectorService;

    public UserSectorController(UserSectorService userSectorService) {
        this.userSectorService = userSectorService;
    }

    // Save / Update
    @PostMapping
    public UserSectorResponse save(@Valid @RequestBody UserSectorSaveRequest request) {
        return userSectorService.saveUserSectors(request);
    }

    // Refill form
    @GetMapping("/{username}")
    public UserSectorResponse get(@PathVariable String username) {
        return userSectorService.getUserData(username);
    }
}

