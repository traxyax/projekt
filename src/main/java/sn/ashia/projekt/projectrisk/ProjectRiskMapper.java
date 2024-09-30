package sn.ashia.projekt.projectrisk;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(
        componentModel = "spring"
)
public interface ProjectRiskMapper {
    ProjectRiskDTO toDTO(ProjectRisk entity);
    ProjectRisk toEntity(ProjectRiskDTO dto);

    List<ProjectRiskDTO> toDTO(List<ProjectRisk> entities);
    List<ProjectRisk> toEntity(List<ProjectRiskDTO> dtos);
}
