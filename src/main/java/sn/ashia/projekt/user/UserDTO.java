package sn.ashia.projekt.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record UserDTO(
        Long id,
        String name,
        @Email
        @NotEmpty
        String email,
        @NotEmpty
        Set<UserRole> roles
) { }
