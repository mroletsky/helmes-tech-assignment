package com.example.helmestechassignment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.AssertTrue;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserSectorSaveRequest {

    @NotBlank
    private String username;

    @AssertTrue(message = "User must agree to terms")
    private boolean agreeToTerms;

    @NotEmpty
    private List<Long> sectorIds;
}

