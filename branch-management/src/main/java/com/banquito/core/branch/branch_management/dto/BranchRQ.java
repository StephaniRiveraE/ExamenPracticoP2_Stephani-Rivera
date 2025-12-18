package com.banquito.core.branch.branch_management.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BranchRQ {
    @NotBlank
    private String name;
    @Email
    @NotBlank
    private String emailAddress;
    @NotBlank
    private String phoneNumber;
}