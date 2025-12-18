package com.banquito.core.branch.branch_management.repositorio;

import com.banquito.core.branch.branch_management.modelo.Branch;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepository extends MongoRepository<Branch, String> {
}