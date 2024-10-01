package sn.ashia.projekt.componentactivity;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(
        componentModel = "spring"
)
public interface ComponentActivityMapper {
    ComponentActivityDTO toDTO(ComponentActivity entity);
    ComponentActivity toEntity(ComponentActivityDTO dto);

    List<ComponentActivityDTO> toDTO(List<ComponentActivity> entities);
    List<ComponentActivity> toEntity(List<ComponentActivityDTO> dtos);
}
