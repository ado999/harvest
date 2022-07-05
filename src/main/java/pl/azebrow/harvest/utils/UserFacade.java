package pl.azebrow.harvest.utils;

import org.springframework.stereotype.Component;
import pl.azebrow.harvest.model.User;

public interface UserFacade {
    User getCaller();
}

