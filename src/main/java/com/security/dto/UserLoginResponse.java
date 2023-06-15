package com.security.dto;

import com.security.model.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final String token;
    private UserDTO user;

    public UserLoginResponse(String token, User user) {
        this.token = token;
        this.user = UserDTO.buildDTO(user);
    }
}