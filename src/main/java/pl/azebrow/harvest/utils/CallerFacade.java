package pl.azebrow.harvest.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.azebrow.harvest.exeption.CallerUnspecifiedException;
import pl.azebrow.harvest.model.User;

@Component
public class CallerFacade {

    public User getCaller() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        if(principal instanceof User user){
            return user;
        } else {
            throw new CallerUnspecifiedException();
        }
    }
}