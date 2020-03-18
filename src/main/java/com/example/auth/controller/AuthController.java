package com.example.auth.controller;

import com.example.auth.model.AuthBody;
import com.example.auth.model.ModelUser;
import com.example.auth.model.ModelUserNew;
import com.example.auth.repository.UserNewRepository;
import com.example.auth.repository.UserRepository;
import com.example.auth.security.JwtTokenProvider;
import com.example.auth.service.MyUserDetailsService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserRepository users;

    @Autowired
    UserNewRepository userRepo;



    @Autowired
    private MyUserDetailsService userService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthBody data) {
        try {
            String username = data.getEmail();
            System.out.println(data.getEmail() + " mantap");
            System.out.println(data.getPassword() + " mantap");
            boolean sip = BCrypt.checkpw(data.getPassword(),this.users.findByEmail(data.getEmail()).getPassword());
            if(!sip) return ok("error, password salahhhh awokwokwok");
            System.out.println(sip + " mantap");
            String token = jwtTokenProvider.createToken(username, this.users.findByEmail(username).getRoles());
            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);
            return ok(model);
        } catch (AuthenticationException e) {
            return ok("Invalid email/password supplied "+e);
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody ModelUser user) {
        ModelUser userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            throw new BadCredentialsException("User with username: " + user.getEmail() + " already exists");
        }
        userService.saveUser(user);
        Map<Object, Object> model = new HashMap<>();
        model.put("message", "User registered successfully");
        return ok(model);
    }

//    // membuat data baru
//    @PostMapping("/")
//    public ModelUserNew add(@RequestParam("first_name") String firstName, @RequestParam("last_name") String lastName, @RequestParam("address") String address) {
//
//        ModelUserNew modelUser = new ModelUserNew();
//        modelUser.setFirstName(firstName);
//        modelUser.setLastName(lastName);
//        modelUser.setAddress(address);
//
//        return userRepo.save(modelUser);
//    }
//
//    // mengubah data
//    @PutMapping("/{id}")
//    public ModelUserNew update(@PathVariable("id") Long id, @RequestParam("first_name") String firstName, @RequestParam("last_name") String lastName, @RequestParam("address") String address) {
//
//        ModelUserNew modelUser = new ModelUserNew();
//        modelUser.setId(id);
//        modelUser.setFirstName(firstName);
//        modelUser.setLastName(lastName);
//        modelUser.setAddress(address);
//
//        return userRepo.save(modelUser);
//    }
//
//    // menghapus data
//    @DeleteMapping("/{id}")
//    public void delete(@PathVariable("id") Long id) {
//
//        ModelUserNew modelUser = new ModelUserNew();
//        modelUser.setId(id);
//
//        userRepo.delete(modelUser);
//    }
//
//    // mengambil satu data user
//    @GetMapping("/{id}")
//    public Optional<ModelUserNew> find(@PathVariable("id") Long id){
//        return userRepo.findById(id);
//    }

}
