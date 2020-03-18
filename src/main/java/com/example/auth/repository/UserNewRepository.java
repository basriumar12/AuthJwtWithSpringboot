package com.example.auth.repository;

import com.example.auth.model.ModelUser;
import com.example.auth.model.ModelUserNew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserNewRepository extends JpaRepository<ModelUserNew, Long> {


}