package com.security.dto;

import com.security.model.Role;
import lombok.Builder;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Data
@Builder
public class RoleDTO {
    private Long id;
    private String roleName;

    public static RoleDTO build(Role role) {
        return RoleDTO.builder()
                .id(role.getId())
                .roleName(role.getRoleName())
                .build();
    }

    public static List<RoleDTO> build(Set<Role> roles) {
        if (!CollectionUtils.isEmpty(roles)) {
            return roles.stream().map(RoleDTO::build).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
