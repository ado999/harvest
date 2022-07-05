package pl.azebrow.harvest.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.azebrow.harvest.request.InsuranceRequest;
import pl.azebrow.harvest.response.InsuranceResponse;
import pl.azebrow.harvest.service.InsuranceService;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/insurance")
@RequiredArgsConstructor
public class InsuranceController {

    private final InsuranceService insuranceService;

    @GetMapping
    public Collection<InsuranceResponse> getEmployeePolicies(
            @RequestParam Long employeeId
    ) {
        return insuranceService.getEmployeePolicies(employeeId);
    }

    @PostMapping
    public void addPolicy(
            @RequestBody InsuranceRequest insuranceRequest
    ) {
        insuranceService.addPolicy(insuranceRequest);
    }

    @DeleteMapping
    public void deletePolicy(
            @RequestBody InsuranceRequest insuranceRequest
    ) {
        insuranceService.removePolicy(insuranceRequest);
    }

}
