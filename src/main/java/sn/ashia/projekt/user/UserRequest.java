package sn.ashia.projekt.user;

import jakarta.validation.constraints.Email;

import java.util.Set;

public record UserRequest(
        Long id,
        String name,
        @Email
        String email,
        Set<UserRole> roles
) { }

