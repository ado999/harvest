package pl.azebrow.harvest.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.azebrow.harvest.constants.RoleEnum;
import pl.azebrow.harvest.request.UserRequest;
import pl.azebrow.harvest.response.EmployeeResponse;
import pl.azebrow.harvest.service.AccountService;
import pl.azebrow.harvest.service.EmployeeService;

@RestController
@RequestMapping("/api/v1/employee")
@Secured({RoleEnum.Constants.ADMIN, RoleEnum.Constants.STAFF})
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final AccountService accountService;

    private final ModelMapper mapper;

    @GetMapping
    public EmployeeResponse getEmployeeByCode(
            @RequestParam String code) {
        var employee = employeeService.getEmployeeByCode(code);
        return mapper.map(employee, EmployeeResponse.class);
    }

    @GetMapping("/{id}")
    public EmployeeResponse getEmployeeById(
            @PathVariable Long id) {
        var employee = employeeService.getEmployeeById(id);
        return mapper.map(employee, EmployeeResponse.class);
    }

    @PostMapping
    public ResponseEntity<?> createEmployeeAccount(
            @RequestBody UserRequest dto) {
        accountService.createEmployee(dto);
        return ResponseEntity.ok(null);
    }

}
