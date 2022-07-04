package pl.azebrow.harvest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.azebrow.harvest.constants.Roles;
import pl.azebrow.harvest.request.EmployeeRequest;
import pl.azebrow.harvest.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
@Secured({Roles.ADMIN, Roles.STAFF})
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> createEmployeeAccount(
            @RequestBody EmployeeRequest dto) {
        userService.createEmployee(dto);
        return ResponseEntity.ok(null);
    }

}
