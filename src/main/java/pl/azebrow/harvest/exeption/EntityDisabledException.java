package pl.azebrow.harvest.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EntityDisabledException extends RuntimeException {
    public EntityDisabledException(String message) {
        super(message);
    }
}
