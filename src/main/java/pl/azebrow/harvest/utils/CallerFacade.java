package pl.azebrow.harvest.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.azebrow.harvest.exeption.CallerUnspecifiedException;
import pl.azebrow.harvest.model.Account;

@Component
public class CallerFacade {

    public Account getCaller() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        if(principal instanceof Account account){
            return account;
        } else {
            throw new CallerUnspecifiedException();
        }
    }
}
