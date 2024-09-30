package sn.ashia.projekt.projectcomponent;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectComponentRepository extends JpaRepository<ProjectComponent, Long> {
    List<ProjectComponent> findByProject_Id(long projectId);
}
