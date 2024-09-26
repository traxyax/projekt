package sn.ashia.projekt.project;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByOrderByCreatedDateDesc(Pageable pageable);
    List<Project> findByManagersIdOrderByCreatedDateDesc(Long managerId, Pageable pageable);
}
