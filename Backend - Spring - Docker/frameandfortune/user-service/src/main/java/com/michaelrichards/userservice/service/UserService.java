package com.michaelrichards.userservice.service;

import com.michaelrichards.userservice.dto.UserRequest;
import com.michaelrichards.userservice.dto.UserResponse;
import com.michaelrichards.userservice.entity.User;
import com.michaelrichards.userservice.exceptions.EmailAlreadyTakenException;
import com.michaelrichards.userservice.exceptions.UsernameAlreadyTakenException;
import com.michaelrichards.userservice.mapper.UserMapper;
import com.michaelrichards.userservice.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private User findUserById(Long userId){
        return userRepository.findById(userId).orElseThrow(()->new EntityNotFoundException("User with id:" + userId +" not found"));
    }

    public UserResponse createUser(UserRequest userRequest){
        checkIfEmailAlreadyTaken(userRequest.email());
        checkIfUsernameAlreadyTaken(userRequest.username());
        User savedUser = saveUser(userRequest, false);
        return UserMapper.toUserResponse(savedUser);
    }

    public List<UserResponse> getAllUsersPaged(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize);
        return userRepository.findAll(pageable)
                .stream()
                .map(UserMapper::toUserResponse)
                .toList();
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toUserResponse)
                .toList();
    }

    public UserResponse getUserById(Long userId) {
        User user = findUserById(userId);
        return UserMapper.toUserResponse(user);
    }

    public UserResponse updateUserById(Long userId, UserRequest userRequest){
        User user = findUserById(userId);
        User updatedUser = saveUser(userRequest, true);
        return UserMapper.toUserResponse(updatedUser);
    }


    private User saveUser(UserRequest userRequest, boolean userExists) {
        User user = User.builder()
                .firstName(userRequest.firstName())
                .lastName(userRequest.lastName())
                .birthDate(userRequest.birthDate())
                .lastSeen(LocalDateTime.now())
                .lastUpdatedTime(LocalDateTime.now())
                .build();

        if(!userExists){
            user.setCreatedTime(LocalDateTime.now());
            user.setEmail(userRequest.email());
            user.setUsername(userRequest.username());
        }


        return userRepository.save(user);
    }

    private void checkIfUsernameAlreadyTaken(String username) {
        if (userRepository.existsByUsernameIgnoreCase(username))
            throw new UsernameAlreadyTakenException("Username: "+ username +" is already taken");
    }

    private void checkIfEmailAlreadyTaken(String email) {
        if (userRepository.existsByEmailIgnoreCase(email))
            throw new EmailAlreadyTakenException("Email: " + email + " is already taken");
    }

    private void checkIfCorrectAge(LocalDate birthday){

    }
}
