package com.security.service;

import com.security.dto.UserDTO;
import com.security.model.User;

import java.util.List;

public interface UserService {

    UserDTO addUser(UserDTO userDTO);

    UserDTO updateUser(Long userId, UserDTO userDTO);

    List<UserDTO> getUsers();

    UserDTO getUserById(Long userId);

    User findByEmail(String email);

    void authenticate(String email, String password) throws Exception;
}
