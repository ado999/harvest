package pl.azebrow.harvest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.azebrow.harvest.enums.RoleEnum;
import pl.azebrow.harvest.request.InsuranceRequest;
import pl.azebrow.harvest.response.InsuranceResponse;
import pl.azebrow.harvest.service.InsuranceService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/v1/insurance")
@Secured({RoleEnum.Constants.ADMIN, RoleEnum.Constants.STAFF})
@RequiredArgsConstructor
@Validated
public class InsuranceController {

    private final InsuranceService insuranceService;

    private final ModelMapper mapper;

    @Operation(summary = "Get employee insurance policies by employee id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "No employee found with the given id")
    })
    @GetMapping("/employee/{id}")
    public Page<InsuranceResponse> getEmployeePolicies(
            @NotNull @Min(1) @PathVariable Long id,
            @PageableDefault Pageable pageable
    ) {
        var insurancePage = insuranceService.getEmployeePolicies(id, pageable);
        return insurancePage
                .map(i -> mapper.map(i, InsuranceResponse.class));
    }

    @Operation(summary = "Add employee's insurance policy")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "404", description = "No employee found with the given id")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void addPolicy(
            @Valid @RequestBody InsuranceRequest insuranceRequest
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
            @NotNull @Min(1) @PathVariable Long id
    ) {
        insuranceService.removePolicy(id);
    }

}
