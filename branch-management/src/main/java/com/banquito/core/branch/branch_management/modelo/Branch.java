package com.banquito.core.branch.branch_management.modelo;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "branch")
public class Branch {
    @Id
    private String id;
    private String emailAddress;
    private String name;
    private String phoneNumber;
    private String state;
    private LocalDateTime creationDate;
    private LocalDateTime lastModifiedDate;
    private List<BranchHoliday> branchHolidays;
}