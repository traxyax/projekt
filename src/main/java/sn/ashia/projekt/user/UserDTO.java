package sn.ashia.projekt.user;

import java.util.Set;

public record UserDTO(
        Long id,
        String name,
        String email,
        Set<UserRole> roles
) { }
