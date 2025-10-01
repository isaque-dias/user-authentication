package com.authentication.spring_authentication.services;

import com.authentication.spring_authentication.config.SecurityUtils;
import com.authentication.spring_authentication.dto.request.UserRequest;
import com.authentication.spring_authentication.dto.response.UserResponse;
import com.authentication.spring_authentication.exceptions.EmailAlreadyExistsException;
import com.authentication.spring_authentication.exceptions.RoleNotFoundException;
import com.authentication.spring_authentication.exceptions.UsernameAlreadyExistsException;
import com.authentication.spring_authentication.model.Role;
import com.authentication.spring_authentication.model.User;
import com.authentication.spring_authentication.repositories.RoleRepository;
import com.authentication.spring_authentication.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public UserResponse registerUser(UserRequest dto){

        if(userRepository.existsByEmail(dto.getEmail())){
            throw new EmailAlreadyExistsException("Email " + dto.getEmail() + " already exists.");
        }

        if(userRepository.existsByUsername(dto.getUsername())){
            throw new UsernameAlreadyExistsException("Username " + dto.getUsername() + " already exists.");
        }

        User user = toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RoleNotFoundException("ROLE_USER not found"));

        user.getRoles().add(userRole);

        User savedUser = userRepository.save(user);

        return toResponse(savedUser);

    }


    public UserResponse getProfile(){
        String username = SecurityUtils.getUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return toResponse(user);
    }


    @Transactional
    public UserResponse updateProfile(UserRequest dto){
        String username = SecurityUtils.getUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        user.setName(dto.getName());
        user.setUsername(dto.getUsername());

        if(dto.getPassword() != null && dto.getPassword().isEmpty()){
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        User updatedUser = userRepository.save(user);
        return toResponse(updatedUser);
    }


    public void deleteProfile(){
        String username = SecurityUtils.getUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        userRepository.delete(user);
    }


    private User toEntity(UserRequest dto){
        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .roles(new HashSet<>())
                .build();
    }

    private UserResponse toResponse(User user){
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }

}
