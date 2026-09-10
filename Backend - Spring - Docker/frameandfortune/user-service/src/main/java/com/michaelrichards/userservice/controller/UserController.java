package com.michaelrichards.userservice.controller;

import com.michaelrichards.userservice.dto.UserRequest;
import com.michaelrichards.userservice.dto.UserResponse;
import com.michaelrichards.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService service;

    @GetMapping(params = {"pageNumber", "pageSize"})
    public ResponseEntity<List<UserResponse>> getAllUsers(
            @RequestParam("pageNumber") int pageNumber,
            @RequestParam("pageSize") int pageSize
    ){
        return ResponseEntity.ok(service.getAllUsersPaged(pageNumber, pageSize));
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createUser(userRequest));
    }

    @GetMapping(params = {"pageNumber"})
    public ResponseEntity<List<UserResponse>> getAllUsers(
            @RequestParam int pageNumber
    ){
        return ResponseEntity.ok(service.getAllUsersPaged(pageNumber, 20));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(

    ){
        return ResponseEntity.ok(service.getAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("userId") Long userId){
        return ResponseEntity.ok().body(
                service.getUserById(userId)
        );
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUserById(@PathVariable("userId") Long userId, @RequestBody UserRequest userRequest){
        return ResponseEntity.ok().body(
                service.updateUserById(userId, userRequest)
        );
    }




}
