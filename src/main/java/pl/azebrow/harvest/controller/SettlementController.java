package pl.azebrow.harvest.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.azebrow.harvest.constant.RoleEnum;
import pl.azebrow.harvest.response.EmployeeSettlementResponse;
import pl.azebrow.harvest.service.SettlementService;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/settlement")
@Secured({RoleEnum.Constants.STAFF, RoleEnum.Constants.ADMIN})
@RequiredArgsConstructor
public class SettlementController {

    private final SettlementService settlementService;

    private final ModelMapper mapper;

    @GetMapping
    public Collection<EmployeeSettlementResponse> getEmployeeSettlement(
            @RequestParam Long employeeId
    ) {
        var settlements = settlementService.getEmployeeSettlement(employeeId);
        return settlements
                .stream()
                .map(s -> mapper.map(s, EmployeeSettlementResponse.class))
                .collect(Collectors.toList());
    }


}
