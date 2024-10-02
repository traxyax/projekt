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

    public User findById(long id) throws EntityNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("could not find user with id: " + id));
    }

    public UserDTO findByIdToDTO(long id) throws EntityNotFoundException {
        return userMapper.toDTO(findById(id));
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

    public UserDTO update(UserDTO userDTO) throws EntityNotFoundException, ConflictException, IllegalAccessException {
        Optional<User> existingUser = userRepository.findById(userDTO.id());
        if (existingUser.isEmpty()) {
            throw new EntityNotFoundException("user with id " + userDTO.id() + " not found");
        }

        User user = existingUser.get();
        patcher.patch(user, userMapper.toEntity(userDTO));
        save(user);
        return userMapper.toDTO(user);
    }
}
