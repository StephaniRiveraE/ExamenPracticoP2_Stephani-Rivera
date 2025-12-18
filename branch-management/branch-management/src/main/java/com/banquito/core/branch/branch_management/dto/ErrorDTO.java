package com.banquito.core.branch.branch_management.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class ErrorDTO {
    private String code;
    private String message;
    private LocalDateTime timestamp;
}