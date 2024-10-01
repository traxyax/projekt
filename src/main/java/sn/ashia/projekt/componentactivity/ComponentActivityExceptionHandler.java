package sn.ashia.projekt.componentactivity;

import org.springframework.dao.DataIntegrityViolationException;
import sn.ashia.projekt.exception.ConflictException;

public class ComponentActivityExceptionHandler {
    public static void handleDataIntegrityViolation(DataIntegrityViolationException ex) throws ConflictException {
        String error = ex.getMessage();

        if (error.contains("component_activity_number_project_component_id_uk")) {
            throw new ConflictException("an activity with this number already exists");
        }

        throw new ConflictException("an " + ex.getClass().getSimpleName() + " occured");
    }
}
