package sn.ashia.projekt.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Set<User> findByRolesContaining(UserRole userRole);
}
