package sn.ashia.projekt.componentactivity;

import sn.ashia.projekt.enumeration.GlobalStatus;

public record ComponentActivityDTO(
        Long id,
        Integer number,
        String objective,
        GlobalStatus status,
        Long componentId
) { }
