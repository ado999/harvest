package pl.azebrow.harvest.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.azebrow.harvest.constant.RoleEnum;
import pl.azebrow.harvest.service.ReportService;
import pl.azebrow.harvest.settlement.EmployeeSettlementResponse;

import javax.validation.constraints.Min;


@RestController
@RequestMapping("/api/v1/report")
@Secured({RoleEnum.Constants.STAFF, RoleEnum.Constants.ADMIN})
@RequiredArgsConstructor
@Validated
public class ReportController {

    private final ReportService reportService;

    private final ModelMapper mapper;

    @GetMapping("/employee/{employeeId}")
    public EmployeeSettlementResponse getEmployeeSettlement(
            @Min(1) @PathVariable Long employeeId,
            //todo make it localdate and apply validation
            @RequestParam String from,
            @RequestParam String to
    ) {
        var response = reportService.getEmployeeSettlement(employeeId, from, to);
        return mapper.map(response, EmployeeSettlementResponse.class);
    }

    @GetMapping(value = "/employee/{employeeId}/report",
            produces = "application/vnd.ms-excel")
    public ResponseEntity<Resource> generateEmployeeSettlementReport(
            @Min(1) @PathVariable Long employeeId,
            //todo as above
            @RequestParam String from,
            @RequestParam String to
    ) {
        var data = reportService.generateEmployeeSettlementReport(employeeId, from, to);
        var resource = new ByteArrayResource(data);
        return ResponseEntity.ok()
                .body(resource);
    }

    @GetMapping(value = "/locations/report",
            produces = "application/vnd.ms-excel")
    public ResponseEntity<Resource> generateLocationSettlementReport(
            //todo as above
            @RequestParam String from,
            @RequestParam String to
    ) {
        var data = reportService.generateLocationSettlementReport(from, to);
        var resource = new ByteArrayResource(data);
        return ResponseEntity.ok()
                .body(resource);
    }

}
