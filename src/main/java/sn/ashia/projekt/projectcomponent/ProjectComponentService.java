package sn.ashia.projekt.projectcomponent;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import sn.ashia.projekt.exception.ConflictException;
import sn.ashia.projekt.exception.EntityNotFoundException;
import sn.ashia.projekt.patcher.Patcher;
import sn.ashia.projekt.project.Project;
import sn.ashia.projekt.project.ProjectService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProjectComponentService {
    private final ProjectComponentRepository projectComponentRepository;
    private final ProjectComponentMapper projectComponentMapper;
    private final Patcher<ProjectComponent> patcher;

    private final ProjectService projectService;

    public List<ProjectComponentDTO> find(Long projectId) {
        return projectComponentMapper.toDTO(projectComponentRepository.findByProject_Id(projectId));
    }

    public ProjectComponent findById(long id) throws EntityNotFoundException {
        return projectComponentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("could not find project component with id " + id));
    }

    public ProjectComponentDTO findByIdToDTO(long id) throws EntityNotFoundException {
        return projectComponentRepository.findById(id)
                .map(projectComponentMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("could not find project component with id " + id));
    }

    public void save(ProjectComponent projectComponent, Long projectId) throws EntityNotFoundException, ConflictException {
        if (projectId != null) {
            Project project = projectService.findById(projectId);
            projectComponent.setProject(project);
        }

        try {
            projectComponentRepository.save(projectComponent);
        } catch (DataIntegrityViolationException ex) {
            ProjectComponentExceptionHandler.handleDataIntegrityViolation(ex);
        }
    }

    public ProjectComponentDTO save(ProjectComponentDTO projectComponentDTO) throws EntityNotFoundException, ConflictException {
        ProjectComponent projectComponent = projectComponentMapper.toEntity(projectComponentDTO);
        save(projectComponent, projectComponentDTO.projectId());
        return projectComponentMapper.toDTO(projectComponent);
    }

    public ProjectComponentDTO update(ProjectComponentDTO projectComponentDTO) throws EntityNotFoundException, IllegalAccessException, ConflictException {
        ProjectComponent projectComponent = findById(projectComponentDTO.id());
        patcher.patch(projectComponent, projectComponentMapper.toEntity(projectComponentDTO));
        save(projectComponent, null);
        return projectComponentMapper.toDTO(projectComponent);
    }

    public void delete(long id) throws EntityNotFoundException {
        findById(id);
        projectComponentRepository.deleteById(id);
    }
}
