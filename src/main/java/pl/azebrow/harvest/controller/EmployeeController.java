package pl.azebrow.harvest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.azebrow.harvest.constants.RoleEnum;
import pl.azebrow.harvest.request.UserRequest;
import pl.azebrow.harvest.service.EmployeeService;
import pl.azebrow.harvest.service.UserService;

@RestController
@RequestMapping("/api/v1/employee")
@Secured({RoleEnum.Constants.ADMIN, RoleEnum.Constants.STAFF})
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getEmployeeByCode(
            @RequestParam String code) {
        return ResponseEntity.ok(employeeService.findEmployeeByCode(code));
    }

    @PostMapping
    public ResponseEntity<?> createEmployeeAccount(
            @RequestBody UserRequest dto) {
        userService.createEmployee(dto);
        return ResponseEntity.ok(null);
    }

}
