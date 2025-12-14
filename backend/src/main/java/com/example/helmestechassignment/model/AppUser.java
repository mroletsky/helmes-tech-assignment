package com.example.helmestechassignment.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "app_user")
public class AppUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, nullable = false) private String username;
    @Column(name = "agree_to_terms") private boolean agreeToTerms = false;
}

