package sn.ashia.projekt.projectrisk;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRiskRepository extends JpaRepository<ProjectRisk, Long> {
    List<ProjectRisk> findByProject_Id(Long projectId);
}
