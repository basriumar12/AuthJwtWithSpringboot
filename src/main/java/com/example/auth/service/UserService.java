package com.example.auth.service;


import com.example.auth.model.ModelRole;
import com.example.auth.model.ModelUser;
import com.example.auth.repository.RoleRepository;
import com.example.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;



    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public ModelUser findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public ModelUser findUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public ModelUser saveUser(ModelUser user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(true);
        ModelRole userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<ModelRole>(Arrays.asList(userRole)));
        return userRepository.save(user);
    }

}