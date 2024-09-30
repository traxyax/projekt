package sn.ashia.projekt.user;

import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(
        componentModel = "spring"
)
public interface UserMapper {
    UserDTO toDTO(User entity);
    User toEntity(UserDTO dto);

    List<UserDTO> toDTO(List<User> entities);
    List<User> toEntity(List<UserDTO> dtos);

    Set<UserDTO> toDTO(Set<User> entities);
    Set<User> toEntity(Set<UserDTO> dtos);
}
