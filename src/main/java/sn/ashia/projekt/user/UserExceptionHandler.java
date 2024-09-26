package sn.ashia.projekt.user;

import org.springframework.dao.DataIntegrityViolationException;
import sn.ashia.projekt.exception.ConflictException;

public class UserExceptionHandler {
    public static void handleDataIntegrityViolation(DataIntegrityViolationException ex) throws ConflictException {
        if (ex.getMessage().contains("users_email_key")) {
            throw new ConflictException("an account with this email already exists");
        } else {
            throw new ConflictException("an " + ex.getClass().getSimpleName() + " occured");
        }
    }
}
