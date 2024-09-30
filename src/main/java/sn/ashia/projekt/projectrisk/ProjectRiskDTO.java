package sn.ashia.projekt.projectrisk;

import sn.ashia.projekt.enumeration.GlobalStatus;

public record ProjectRiskDTO(
        Long id,
        String cause,
        String measure,
        Integer impact,
        Integer probability,
        Integer criticality,
        GlobalStatus status,
        Long projectId
) { }
