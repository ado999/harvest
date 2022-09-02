package pl.azebrow.harvest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.azebrow.harvest.constant.RoleEnum;
import pl.azebrow.harvest.request.EmployeeRequest;
import pl.azebrow.harvest.request.EmployeeUpdateRequest;
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

    @Operation(summary = "Get employees",
            parameters = @Parameter(name = "showDisabled", description = "Tells whether or not to show disabled employee accounts"))
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

    @Operation(summary = "Get employee account by code (meant to be encoded as QR code)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "No employee found with the given code")
    })
    @GetMapping("/code/{code}")
    public EmployeeResponse getEmployeeByCode(
            @PathVariable String code) {
        var employee = employeeService.getEmployeeByCode(code);
        return mapper.map(employee, EmployeeResponse.class);
    }

    @Operation(summary = "Get employee account by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "No employee found with the given code")
    })

    @GetMapping("/id/{id}")
    public EmployeeResponse getEmployeeById(
            @PathVariable Long id) {
        var employee = employeeService.getEmployeeById(id);
        return mapper.map(employee, EmployeeResponse.class);
    }

    @Operation(summary = "Search employees by string in first name, last name, email or code")
    @GetMapping("/search/{query}")
    public Collection<EmployeeResponse> searchEmployee(
            @PathVariable String query) {
        var employees = employeeService.searchEmployees(query);
        return employees
                .stream()
                .map(e -> mapper.map(e, EmployeeResponse.class))
                .collect(Collectors.toList());
    }

    @Operation(summary = "Create employee account")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Account created successfully"),
            @ApiResponse(responseCode = "409", description = "Email is being used")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createEmployeeAccount(
            @RequestBody EmployeeRequest request) {
        accountService.createEmployee(request);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/id/{id}")
    public void updateEmployee(
            @PathVariable Long id,
            @RequestBody EmployeeUpdateRequest updateRequest
    ) {
        employeeService.updateEmployee(id, updateRequest);
    }

}
