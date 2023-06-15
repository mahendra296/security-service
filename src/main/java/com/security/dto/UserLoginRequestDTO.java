package com.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 5926468583005150707L;

    private String email;
    private String password;
}