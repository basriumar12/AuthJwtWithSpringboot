package com.example.auth.repository;

import com.example.auth.model.ModelUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<ModelUser, Long> {
    ModelUser findByEmail(String email);
    ModelUser findByUserName(String userName);


}