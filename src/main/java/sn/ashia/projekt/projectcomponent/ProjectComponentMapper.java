package sn.ashia.projekt.projectcomponent;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(
        componentModel = "spring"
)
public interface ProjectComponentMapper {
    ProjectComponentDTO toDTO(ProjectComponent entity);
    ProjectComponent toEntity(ProjectComponentDTO dto);

    List<ProjectComponentDTO> toDTO(List<ProjectComponent> entities);
    List<ProjectComponent> toEntity(List<ProjectComponentDTO> dtos);
}
