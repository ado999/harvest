package pl.azebrow.harvest.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.azebrow.harvest.constant.RoleEnum;
import pl.azebrow.harvest.service.ReportService;
import pl.azebrow.harvest.settlement.EmployeeSettlementResponse;

@RestController
@RequestMapping("/api/v1/report")
@Secured({RoleEnum.Constants.STAFF, RoleEnum.Constants.ADMIN})
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    private final ModelMapper mapper;

    @GetMapping("/employee/{employeeId}")
    public EmployeeSettlementResponse getEmployeeSettlement(
            @PathVariable Long employeeId,
            @RequestParam String from,
            @RequestParam String to
    ) {
        var response = reportService.getEmployeeSettlement(employeeId, from, to);
        return mapper.map(response, EmployeeSettlementResponse.class);
    }

    @GetMapping("/employee/{employeeId}/report")
    public void generateEmployeeSettlementReport(
            @PathVariable Long employeeId,
            @RequestParam String from,
            @RequestParam String to
    ) {
        reportService.generateEmployeeSettlementReport(employeeId, from, to);
    }

}
