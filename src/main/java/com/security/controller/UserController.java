package com.security.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.security.utils.ResponseUtils.successResponseWithBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    @GetMapping("/hello")
    public ResponseEntity<Object> hello() {
        return successResponseWithBody("Hello");
    }

    @PostMapping("/user")
    public ResponseEntity<Object> addUser() {
        return successResponseWithBody("");
    }
}
