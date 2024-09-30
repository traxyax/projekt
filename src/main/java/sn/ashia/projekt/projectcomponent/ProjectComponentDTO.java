package sn.ashia.projekt.projectcomponent;

import sn.ashia.projekt.enumeration.GlobalStatus;

public record ProjectComponentDTO(
        Long id,
        Integer number,
        String objective,
        GlobalStatus status,
        Long projectId
) { }
