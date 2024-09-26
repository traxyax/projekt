package sn.ashia.projekt.user;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import sn.ashia.projekt.exception.ConflictException;
import sn.ashia.projekt.exception.EntityNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Set<UserDTO> find(Set<UserRole> roles) {
        if (roles == null || roles.isEmpty()) {
            return userMapper.toDTO(new HashSet<>(findAll()));
        }
        return userMapper.toDTO(findByRoles(roles));
    }

    public UserDTO findById(Long id) throws EntityNotFoundException {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isEmpty()) throw new EntityNotFoundException("user not found");
        return userMapper.toDTO(existingUser.get());
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Set<User> findByRoles(Set<UserRole> userRoles) {
        return userRoles.stream()
                .map(userRepository::findByRolesContaining)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    public void save(User user) throws ConflictException {
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            UserExceptionHandler.handleDataIntegrityViolation(ex);
        }
    }

    public UserDTO save(UserDTO userDTO) throws ConflictException {
        User user = userMapper.toEntity(userDTO);
        save(user);
        return userMapper.toDTO(user);
    }

    public void saveIfEmpty(User user) throws ConflictException {
        Optional<User> existingUser = findByEmail(user.getEmail());
        if (existingUser.isEmpty()) {
            save(user);
        }
    }

    public UserDTO update(UserDTO userDTO) throws EntityNotFoundException, ConflictException {
        Optional<User> existingUser = userRepository.findById(userDTO.id());
        if (existingUser.isEmpty()) {
            throw new EntityNotFoundException("user with id " + userDTO.id() + " not found");
        }

        User user = existingUser.get();
        user.setEmail(userDTO.email());
        user.setRoles(userDTO.roles());
        save(user);
        return userMapper.toDTO(user);
    }
}
