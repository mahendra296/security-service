package com.security.controller;

import com.security.dto.UserDTO;
import com.security.dto.UserLoginRequestDTO;
import com.security.dto.UserLoginResponse;
import com.security.exceptions.InvalidRequestException;
import com.security.model.User;
import com.security.service.UserService;
import com.security.service.impl.CustomUserDetailService;
import com.security.utils.CommonUtils;
import com.security.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.security.utils.ResponseUtils.successResponseWithBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailService userAuthService;

    @GetMapping("/hello")
    public ResponseEntity<Object> hello() {
        return successResponseWithBody("Hello");
    }

    @PostMapping("/user/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginRequestDTO userLoginRequestDTO) throws Exception {

        userService.authenticate(userLoginRequestDTO.getEmail(), userLoginRequestDTO.getPassword());
        final UserDetails userDetails = userAuthService.loadUserByUsername(userLoginRequestDTO.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);
        User user = userService.findByEmail(userLoginRequestDTO.getEmail());
        return ResponseEntity.ok(new UserLoginResponse(token, user));
    }

    @PostMapping("/user/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) throws Exception {
        if (!CommonUtils.isEmailIsValid(userDTO.getEmail())) {
            throw new InvalidRequestException("Email is not valid.");
        }
        return successResponseWithBody(userService.addUser(userDTO));
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable("userId") Long userId,
                                             @RequestBody UserDTO userDTO) {
        userDTO = userService.updateUser(userId, userDTO);
        return successResponseWithBody(userDTO);
    }

    @GetMapping("/user")
    public ResponseEntity<Object> getUsers() {
        List<UserDTO> userDTOList = userService.getUsers();
        return successResponseWithBody(userDTOList);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Object> getDepartments(@PathVariable("userId") Long userId) {
        UserDTO departmentDTO = userService.getUserById(userId);
        return successResponseWithBody(departmentDTO);
    }
}
