package sn.ashia.projekt.project;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        uses = {ProjectMapper.class}
)
public interface ProjectMapper {
    ProjectDTO toDTO(Project entity);
    Project toEntity(ProjectDTO dto);

    List<ProjectDTO> toDTO(List<Project> entities);
    List<Project> toEntity(List<ProjectDTO> dtos);

    Set<ProjectDTO> toDTO(Set<Project> entities);
    Set<Project> toEntity(Set<ProjectDTO> dtos);
}
