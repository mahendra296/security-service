package com.security.dto;

import com.security.model.User;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CurrentLoggedInUser {

    private Long id;
    private String userName;
    private String password;
    private Boolean isAdmin;
    private List<RoleDTO> roles;

    public static CurrentLoggedInUser build(User user, Boolean isAdmin) {
        return CurrentLoggedInUser.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .isAdmin(isAdmin)
                .roles(RoleDTO.build(user.getRoles()))
                .build();
    }
}
