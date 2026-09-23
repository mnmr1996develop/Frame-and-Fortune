package com.michaelrichards.userservice.controller;

import com.michaelrichards.userservice.dto.ExistsResponse;
import com.michaelrichards.userservice.dto.UserRequest;
import com.michaelrichards.userservice.dto.UserResponse;
import com.michaelrichards.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

    @PatchMapping("/{userId}/lastSeen")
    public ResponseEntity<LocalDateTime> updateLastSeenById(@PathVariable("userId") Long userId){
        return ResponseEntity.ok().body(service.updateLastSeen(userId));
    }

    @GetMapping("/{userId}/exists")
    public ResponseEntity<ExistsResponse> getAllUsersByUserId(
            @PathVariable("userId") Long userId
    ){
        ExistsResponse response = ExistsResponse.builder()
                .exists(service.existsById(userId)).build();
       return ResponseEntity.ok().body(response);
    }

    @PatchMapping
    public ResponseEntity<UserResponse> toggleFollowing(
            @RequestParam Long userId
    ){
        HttpStatus status = HttpStatus.ACCEPTED;
        return ResponseEntity.status(status).body(service.togglePrivacy(userId));
    }




}
