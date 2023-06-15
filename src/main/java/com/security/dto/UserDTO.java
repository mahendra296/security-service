package com.security.dto;

import com.security.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String userName;
    private String password;
    private String email;
    private String gender;
    private int age;
    private List<RoleDTO> roles;

    public static UserDTO buildDTO(User user) {
        UserDTO userDTO = null;
        if (user != null) {
            userDTO = UserDTO.builder()
                    .id(user.getId())
                    .userName(user.getUserName())
                    .password(user.getPassword())
                    .email(user.getEmail())
                    .gender(user.getGender())
                    .age(user.getAge())
                    .roles(RoleDTO.build(user.getRoles()))
                    .build();
        }
        return userDTO;
    }
}
