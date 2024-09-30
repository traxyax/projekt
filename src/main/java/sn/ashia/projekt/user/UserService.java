package sn.ashia.projekt.user;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import sn.ashia.projekt.exception.ConflictException;
import sn.ashia.projekt.exception.EntityNotFoundException;
import sn.ashia.projekt.patcher.Patcher;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final Patcher<User> patcher;

    public List<UserDTO> find() {
        return userMapper.toDTO(findAll());
    }

    public List<UserDTO> find(Set<UserRole> roles) {
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

    public List<User> findByRoles(Set<UserRole> userRoles) {
        return userRoles.stream()
                .map(userRepository::findByRolesContaining)
                .flatMap(List::stream)
                .collect(Collectors.toList());
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

    public UserDTO update(UserRequest userRequest) throws EntityNotFoundException, ConflictException, IllegalAccessException {
        Optional<User> existingUser = userRepository.findById(userRequest.id());
        if (existingUser.isEmpty()) {
            throw new EntityNotFoundException("user with id " + userRequest.id() + " not found");
        }

        User user = existingUser.get();
        patcher.patch(user, userMapper.toEntity(userRequest));
        save(user);
        return userMapper.toDTO(user);
    }
}
