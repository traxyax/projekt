package sn.ashia.projekt.project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import sn.ashia.projekt.user.User;

import java.time.Instant;
import java.util.Set;

public record ProjectDTO(
        Long id,
        @NotBlank
        String title,
        @NotNull
        Integer durationMonths,
        String genderMarker,
        String priorityTheme,
        String mainPartner,
        Double amountFef,
        Double amountCofinancing,
        String type,
        ProjectStatus status,
        Instant createdDate,
        Instant lastModifiedDate,
        Set<User> managers
) { }
