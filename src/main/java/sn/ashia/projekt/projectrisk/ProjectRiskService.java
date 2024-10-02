package sn.ashia.projekt.projectrisk;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.ashia.projekt.exception.EntityNotFoundException;
import sn.ashia.projekt.patcher.Patcher;
import sn.ashia.projekt.project.Project;
import sn.ashia.projekt.project.ProjectService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProjectRiskService {
    private final ProjectRiskRepository projectRiskRepository;
    private final ProjectRiskMapper projectRiskMapper;
    private final Patcher<ProjectRisk> patcher;

    private final ProjectService projectService;

    public List<ProjectRiskDTO> find(Long projectId) {
        return projectRiskMapper.toDTO(projectRiskRepository.findByProject_Id(projectId));
    }

    public ProjectRisk findById(long id) throws EntityNotFoundException {
        return projectRiskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("could not find project risk with id " + id));
    }

    public ProjectRiskDTO findByIdToDTO(long id) throws EntityNotFoundException {
        return projectRiskMapper.toDTO(findById(id));
    }

    public void save(ProjectRisk projectRisk) throws EntityNotFoundException {
        projectRiskRepository.save(projectRisk);
    }

    public ProjectRiskDTO save(ProjectRiskDTO projectRiskDTO) throws EntityNotFoundException {
        ProjectRisk projectRisk = projectRiskMapper.toEntity(projectRiskDTO);
        if (projectRiskDTO.projectId() != null) {
            Project project = projectService.findById(projectRiskDTO.id());
            projectRisk.setProject(project);
        }
        save(projectRisk);
        return projectRiskMapper.toDTO(projectRisk);
    }

    public ProjectRiskDTO update(ProjectRiskDTO projectRiskDTO) throws EntityNotFoundException, IllegalAccessException {
        ProjectRisk projectRisk = findById(projectRiskDTO.id());
        if (projectRiskDTO.projectId() != null) {
            Project project = projectService.findById(projectRiskDTO.id());
            projectRisk.setProject(project);
        }
        patcher.patch(projectRisk, projectRiskMapper.toEntity(projectRiskDTO));
        save(projectRisk);
        return projectRiskMapper.toDTO(projectRisk);
    }

    public void delete(long id) throws EntityNotFoundException {
        ProjectRisk projectRisk = findById(id);
        projectRiskRepository.delete(projectRisk);
    }
}
