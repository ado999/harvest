package pl.azebrow.harvest.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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

    @PostMapping
    public void addPolicy(
            @RequestBody InsuranceRequest insuranceRequest
    ) {
        insuranceService.addPolicy(insuranceRequest);
    }

    @DeleteMapping("/{id}")
    public void deletePolicy(
            @PathVariable Long id
    ) {
        insuranceService.removePolicy(id);
    }

}
