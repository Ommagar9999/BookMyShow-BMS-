package com.omizon.BMS.controller;


import com.omizon.BMS.Service.UserService;
import com.omizon.BMS.dto.LoginRequest;
import com.omizon.BMS.dto.UserRequest;
import com.omizon.BMS.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {


    private  final UserService userService;

     @PostMapping("/register")
    private ResponseEntity<User>   register (@RequestBody UserRequest  request)
    {
          return ResponseEntity.ok(userService.register(request));

    }

    @PostMapping("/login")
    private ResponseEntity<User> login(@RequestBody LoginRequest request)
    {

        return ResponseEntity.ok(userService.login(request));

    }



  @GetMapping
    private ResponseEntity<List<User>> getAllUsers()
    {

        return ResponseEntity.ok(userService.getAllUser());

    }



    @PostMapping("/{id}")
    private ResponseEntity<User> getUsersById(@PathVariable Long id)
    {

        return ResponseEntity.ok(userService.getUserById(id));

    }







}
