package sn.ashia.projekt.project;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sn.ashia.projekt.exception.EntityNotFoundException;
import sn.ashia.projekt.patcher.Patcher;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final Patcher<Project> patcher;

    public List<ProjectDTO> find(Pageable pageable) {
        return projectMapper.toDTO(findLatestProjects(pageable));
    }

    public List<ProjectDTO> find(long managerId, Pageable pageable) {
        return projectMapper.toDTO(findLatestProjectsByManagerId(managerId, pageable));
    }

    public List<Project> findLatestProjects(Pageable pageable) {
        return projectRepository.findByOrderByCreatedDateDesc(pageable);
    }

    public List<Project> findLatestProjectsByManagerId(Long managerId, Pageable pageable) {
        return projectRepository.findByManagersIdOrderByCreatedDateDesc(managerId, pageable);
    }

    public Project findById(Long id) throws EntityNotFoundException {
        return projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("project with id " + id + " not found"));
    }

    public ProjectDTO findByIdToDTO(long id) throws EntityNotFoundException {
        return projectMapper.toDTO(findById(id));
    }

    public void save(Project project) {
        projectRepository.save(project);
    }

    public ProjectDTO save(ProjectDTO projectDTO) {
        Project project = projectMapper.toEntity(projectDTO);
        save(project);
        return projectMapper.toDTO(project);
    }

    public ProjectDTO update(ProjectDTO projectDTO) throws EntityNotFoundException, IllegalAccessException {
        Project project = findById(projectDTO.id());
        patcher.patch(project, projectMapper.toEntity(projectDTO));
        save(project);
        return projectMapper.toDTO(project);
    }
}
