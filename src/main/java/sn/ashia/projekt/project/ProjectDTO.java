package sn.ashia.projekt.project;

import sn.ashia.projekt.projectsetting.ProjectSettingDTO;
import sn.ashia.projekt.user.UserDTO;

import java.time.Instant;
import java.util.Set;

public record ProjectDTO(
        Long id,
        String title,
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
        ProjectSettingDTO setting,
        Set<UserDTO> managers
) { }
