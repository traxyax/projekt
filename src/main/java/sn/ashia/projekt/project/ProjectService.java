package sn.ashia.projekt.project;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sn.ashia.projekt.exception.EntityNotFoundException;
import sn.ashia.projekt.user.UserMapper;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    private final UserMapper userMapper;

    public List<ProjectDTO> find(Pageable pageable) {
        return projectMapper.toDTO(findLatestProjects(pageable));
    }

    public List<ProjectDTO> find(Long managerId, Pageable pageable) {
        return projectMapper.toDTO(findLatestProjectsByManagerId(managerId, pageable));
    }

    public List<Project> findLatestProjects(Pageable pageable) {
        return projectRepository.findByOrderByCreatedDateDesc(pageable);
    }

    public List<Project> findLatestProjectsByManagerId(Long managerId, Pageable pageable) {
        return projectRepository.findByManagersIdOrderByCreatedDateDesc(managerId, pageable);
    }

    public ProjectDTO findById(Long id) throws EntityNotFoundException {
        return projectRepository.findById(id)
                .map(projectMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("project with id " + id + " not found"));
    }

    public void save(Project project) {
        projectRepository.save(project);
    }

    public ProjectDTO save(ProjectDTO projectDTO) {
        Project project = projectMapper.toEntity(projectDTO);
        save(project);
        return projectMapper.toDTO(project);
    }

    public ProjectDTO update(ProjectDTO projectDTO) throws EntityNotFoundException {
        return projectRepository.findById(projectDTO.id())
                .map(project -> {
                    project.setTitle(projectDTO.title());
                    project.setDurationMonths(projectDTO.durationMonths());
                    project.setGenderMarker(projectDTO.genderMarker());
                    project.setPriorityTheme(projectDTO.priorityTheme());
                    project.setMainPartner(projectDTO.mainPartner());
                    project.setAmountFef(projectDTO.amountFef());
                    project.setAmountCofinancing(projectDTO.amountCofinancing());
                    project.setType(projectDTO.type());
                    project.setStatus(projectDTO.status());
                    project.setManagers(userMapper.toEntity(projectDTO.managers()));
                    save(project);
                    return projectMapper.toDTO(project);
                })
                .orElseThrow(() -> new EntityNotFoundException("project with id " + projectDTO.id() + " not found")
                );
    }
}
