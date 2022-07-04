package pl.azebrow.harvest.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.azebrow.harvest.constants.Roles;
import pl.azebrow.harvest.model.Employee;
import pl.azebrow.harvest.model.Role;
import pl.azebrow.harvest.model.User;
import pl.azebrow.harvest.repository.RoleRepository;
import pl.azebrow.harvest.repository.UserRepository;
import pl.azebrow.harvest.request.EmployeeRequest;
import pl.azebrow.harvest.response.exeption.EmailAlreadyTakenException;
import pl.azebrow.harvest.response.exeption.RoleNotFoundException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final ModelMapper mapper;

    public void createEmployee(EmployeeRequest dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyTakenException(String.format("Email \"%s\" already exists!", dto.getEmail()));
        }
        Role userRole = roleRepository
                .findByName(Roles.USER)
                .orElseThrow(
                        () -> new RoleNotFoundException(String.format("Role \"%s\" not found!", Roles.USER))
                );
        User user = User.builder()
                .email(dto.getEmail())
                .roles(List.of(userRole))
                .build();
        Employee employee = Employee.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .code(UUID.randomUUID().toString())
                .build();
        user.setEmployee(employee);
        userRepository.save(user);
    }
}
