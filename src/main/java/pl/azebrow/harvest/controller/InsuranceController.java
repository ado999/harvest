package pl.azebrow.harvest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.azebrow.harvest.request.InsuranceRequest;
import pl.azebrow.harvest.response.InsuranceResponse;
import pl.azebrow.harvest.service.InsuranceService;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/insurance")
@RequiredArgsConstructor
public class InsuranceController {

    private final InsuranceService insuranceService;

    private final ModelMapper mapper;

    @Operation(summary = "Get employee insurance policies by employee id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "No employee found with the given id")
    })
    @GetMapping
    public Collection<InsuranceResponse> getEmployeePolicies(
            @RequestParam Long employeeId
    ) {
        var insuranceList = insuranceService.getEmployeePolicies(employeeId);
        return insuranceList
                .stream()
                .map(i -> mapper.map(i, InsuranceResponse.class))
                .collect(Collectors.toList());
    }

    @Operation(summary = "Add employee's insurance policy")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "404", description = "No employee found with the given id")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void addPolicy(
            @RequestBody InsuranceRequest insuranceRequest
    ) {
        insuranceService.addPolicy(insuranceRequest);
    }


    @Operation(summary = "Remove insurance policy")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Success"),
            @ApiResponse(responseCode = "404", description = "No policy found with the given id")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deletePolicy(
            @PathVariable Long id
    ) {
        insuranceService.removePolicy(id);
    }

}
