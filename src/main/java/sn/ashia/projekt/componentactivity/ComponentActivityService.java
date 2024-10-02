package sn.ashia.projekt.componentactivity;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import sn.ashia.projekt.exception.ConflictException;
import sn.ashia.projekt.exception.EntityNotFoundException;
import sn.ashia.projekt.patcher.Patcher;
import sn.ashia.projekt.projectcomponent.ProjectComponent;
import sn.ashia.projekt.projectcomponent.ProjectComponentService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ComponentActivityService {
    private final ComponentActivityRepository componentActivityRepository;
    private final ComponentActivityMapper componentActivityMapper;
    private final Patcher<ComponentActivity> patcher;

    private final ProjectComponentService projectComponentService;

    public List<ComponentActivityDTO> find(long componentId) {
        return componentActivityMapper.toDTO(componentActivityRepository.findByComponent_Id(componentId));
    }

    public ComponentActivity findById(long id) throws EntityNotFoundException {
        return componentActivityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("could not find component activity with id " + id));
    }

    public ComponentActivityDTO findByIdToDTO(long id) throws EntityNotFoundException {
        return componentActivityRepository.findById(id)
                .map(componentActivityMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("could not find component activity with id " + id));
    }

    public void save(ComponentActivity componentActivity) throws ConflictException {
        try {
            componentActivityRepository.save(componentActivity);
        } catch (DataIntegrityViolationException ex) {
            ComponentActivityExceptionHandler.handleDataIntegrityViolation(ex);
        }
    }

    public ComponentActivityDTO save(ComponentActivityDTO componentActivityDTO) throws ConflictException, EntityNotFoundException {
        ComponentActivity componentActivity = componentActivityMapper.toEntity(componentActivityDTO);
        if (componentActivityDTO.componentId() != null) {
            ProjectComponent projectComponent = projectComponentService.findById(componentActivityDTO.componentId());
            componentActivity.setComponent(projectComponent);
        }
        save(componentActivity);
        return componentActivityMapper.toDTO(componentActivity);
    }

    public ComponentActivityDTO update(ComponentActivityDTO componentActivityDTO) throws EntityNotFoundException, IllegalAccessException, ConflictException {
        ComponentActivity componentActivity = findById(componentActivityDTO.id());
        if (componentActivityDTO.componentId() != null) {
            ProjectComponent projectComponent = projectComponentService.findById(componentActivityDTO.componentId());
            componentActivity.setComponent(projectComponent);
        }
        patcher.patch(componentActivity, componentActivityMapper.toEntity(componentActivityDTO));
        save(componentActivity);
        return componentActivityMapper.toDTO(componentActivity);
    }

    public void delete(long id) throws EntityNotFoundException {
        findById(id);
        componentActivityRepository.deleteById(id);
    }
}
