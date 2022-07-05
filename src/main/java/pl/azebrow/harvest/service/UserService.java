package pl.azebrow.harvest.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.azebrow.harvest.constants.RoleEnum;
import pl.azebrow.harvest.exeption.EmailAlreadyTakenException;
import pl.azebrow.harvest.exeption.RoleNotFoundException;
import pl.azebrow.harvest.exeption.UserNotFoundException;
import pl.azebrow.harvest.model.Employee;
import pl.azebrow.harvest.model.Role;
import pl.azebrow.harvest.model.User;
import pl.azebrow.harvest.repository.RoleRepository;
import pl.azebrow.harvest.repository.UserRepository;
import pl.azebrow.harvest.request.UserRequest;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final ModelMapper mapper;

    public void createEmployee(UserRequest dto) {
        validateEmail(dto.getEmail());
        Role userRole = findRole(RoleEnum.USER);
        User user = createUser(dto, userRole);
        Employee employee = Employee.builder()
                .code(UUID.randomUUID().toString())
                .build();
        user.setEmployee(employee);
        userRepository.save(user);
    }

    public void createStaffAccount(UserRequest dto) {
        validateEmail(dto.getEmail());
        Role staffRole = findRole(RoleEnum.STAFF);
        User user = createUser(dto, staffRole);
        userRepository.save(user);
    }

    public void updateAccount(Long id, UserRequest dto) {
        User user = userRepository
                .findById(id)
                .orElseThrow(
                        () -> new UserNotFoundException(String.format("User with id \"%d\" not found", id)));
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getFirstName());
    }

    private void validateEmail(String email){
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyTakenException(String.format("Email \"%s\" already exists!", email));
        }
    }

    private Role findRole(RoleEnum role){
        return roleRepository
                .findByName(role.getName())
                .orElseThrow(
                        () -> new RoleNotFoundException(String.format("Role \"%s\" not found!", RoleEnum.USER))
                );
    }

    private User createUser(UserRequest dto, Role role){
        return User.builder()
                .email(dto.getEmail())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .roles(List.of(role))
                .build();
    }
}
