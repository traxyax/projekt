package sn.ashia.projekt.projectcomponent;

import org.springframework.dao.DataIntegrityViolationException;
import sn.ashia.projekt.exception.ConflictException;

public class ProjectComponentExceptionHandler {
    public static void handleDataIntegrityViolation(DataIntegrityViolationException ex) throws ConflictException {
        String error = ex.getMessage();

        if (error.contains("project_component_number_project_id_uk")) {
            throw new ConflictException("a component with this number already exists");
        }

        throw new ConflictException("an " + ex.getClass().getSimpleName() + " occured");
    }
}
