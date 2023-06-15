package com.security.service.impl;

import com.security.dto.UserDTO;
import com.security.exceptions.InternalServerException;
import com.security.exceptions.InvalidRequestException;
import com.security.exceptions.ResourceNotFoundException;
import com.security.model.Role;
import com.security.model.User;
import com.security.repositories.RoleRepository;
import com.security.repositories.UserRepository;
import com.security.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public UserDTO addUser(UserDTO userDTO) {
        log.info("Invoke addUser method.");
        validUserRequest(userDTO);
        try {
            User user = User.buildEntity(userDTO);
            Role role = roleRepository.findByRoleName("ROLE_USER");
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            user.setRoles(roles);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            log.info("End addUser method.");
            return UserDTO.buildDTO(user);
        } catch (Exception ex) {
            log.error("Exception while add user.", ex);
            throw new InternalServerException("Exception while add user.");
        }
    }

    private void validUserRequest(UserDTO userDTO) {
        if (userDTO == null) throw new InvalidRequestException("Request is null or empty.");
        Boolean isUserExists = userRepository.existsByEmail(userDTO.getEmail());
        if (isUserExists) {
            throw new InvalidRequestException("User is already registered with email: " + userDTO.getEmail());
        }
    }

    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        log.info("Invoke updateUser method.");
        validateUpdateRequest(userDTO, userId);
        try {
            User user = User.buildEntity(userDTO);
            user.setId(userId);
            userRepository.save(user);
            log.info("End updateUser method.");
            return UserDTO.buildDTO(user);
        } catch (Exception ex) {
            log.error("Exception while update user.", ex);
            throw new InternalServerException("Exception while update user.");
        }
    }

    @Override
    public List<UserDTO> getUsers() {
        log.info("Invoke getUsers method.");
        try {
            List<User> userList = userRepository.findAll();
            log.info("End getUsers method.");
            return userList.stream().map(UserDTO::buildDTO).toList();
        } catch (Exception ex) {
            log.error("Exception while get all users.", ex);
            throw new InternalServerException("Exception while get all users.");
        }
    }

    @Override
    public UserDTO getUserById(Long userId) {
        log.info("Invoke getUserById method.");
        User user = getUserByUserId(userId);
        if (user == null) {
            throw new ResourceNotFoundException("User not found by userId : " + userId);
        }
        UserDTO userDTO = UserDTO.buildDTO(user);
        log.info("End getUserById method.");
        return userDTO;
    }

    private void validateUpdateRequest(UserDTO userDTO, Long userId) {
        if (userDTO == null) {
            throw new InvalidRequestException("Request is null.");
        }
        if (!Objects.equals(userDTO.getId(), userId)) {
            throw new InvalidRequestException("DepartmentId is not match with request.");
        }
        User user = getUserByUserId(userId);
        if (user == null) {
            throw new ResourceNotFoundException("User not found by userId : " + userId);
        }
    }

    private User getUserByUserId(Long userId) {
        log.info("Invoke getUserByUserId method by userId : {}", userId);
        Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.orElse(null);
    }

    @Override
    public User findByEmail(String username) {
        return userRepository.findByEmail(username);
    }

    public void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception ex) {
            log.error("Exception while authenticate user.", ex);
        }
    }
}
