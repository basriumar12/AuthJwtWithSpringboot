package com.example.auth.repository;

import com.example.auth.model.ModelRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<ModelRole, Integer> {
    ModelRole findByRole(String role);

}