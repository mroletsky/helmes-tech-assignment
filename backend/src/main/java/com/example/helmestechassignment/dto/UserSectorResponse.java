package com.example.helmestechassignment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UserSectorResponse {
    private Long userId;
    private String username;
    private boolean agreeToTerms;
    private List<Long> sectorIds;
}

