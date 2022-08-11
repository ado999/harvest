package pl.azebrow.harvest.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.azebrow.harvest.constants.RoleEnum;
import pl.azebrow.harvest.model.Role;
import pl.azebrow.harvest.repository.RoleRepository;

@Configuration
public class RoleConfig {

    @Bean
    public CommandLineRunner roleCreator(RoleRepository roleRepository) {
        return args -> {
            for (RoleEnum r : RoleEnum.values()) {
                if (!roleRepository.existsByName(r.getName())) {
                    Role role = new Role();
                    role.setName(r.getName());
                    roleRepository.save(role);
                }
            }
        };
    }

}
