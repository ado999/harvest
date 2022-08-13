package pl.azebrow.harvest.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.azebrow.harvest.constants.RoleEnum;
import pl.azebrow.harvest.request.AccountRequest;
import pl.azebrow.harvest.response.EmployeeResponse;
import pl.azebrow.harvest.service.AccountService;
import pl.azebrow.harvest.service.EmployeeService;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/employee")
@Secured({RoleEnum.Constants.ADMIN, RoleEnum.Constants.STAFF})
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final AccountService accountService;

    private final ModelMapper mapper;

    @GetMapping
    public Collection<EmployeeResponse> getEmployees(
            @RequestParam(required = false, defaultValue = "false") boolean showDisabled
    ) {
        var employees = employeeService.getAllEmployees(showDisabled);
        return employees
                .stream()
                .map(e -> mapper.map(e, EmployeeResponse.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/code/{code}")
    public EmployeeResponse getEmployeeByCode(
            @PathVariable String code) {
        var employee = employeeService.getEmployeeByCode(code);
        return mapper.map(employee, EmployeeResponse.class);
    }

    @GetMapping("/id/{id}")
    public EmployeeResponse getEmployeeById(
            @PathVariable Long id) {
        var employee = employeeService.getEmployeeById(id);
        return mapper.map(employee, EmployeeResponse.class);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createEmployeeAccount(
            @RequestBody AccountRequest dto) {
        accountService.createEmployee(dto);
    }

}
