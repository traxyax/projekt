package sn.ashia.projekt.user;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED
)
public interface UserMapper {
    UserDTO toDTO(User entity);
    User toEntity(UserDTO dto);

    Set<UserDTO> toDTO(List<User> entities);
    Set<User> toEntity(List<UserDTO> dtos);

    Set<UserDTO> toDTO(Set<User> entities);
    Set<User> toEntity(Set<UserDTO> dtos);
}
