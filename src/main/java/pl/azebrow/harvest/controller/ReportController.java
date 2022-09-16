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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.function.Supplier;


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
            @NotNull @Min(1) @PathVariable Long employeeId,
            @NotNull @Past @RequestParam LocalDate dateFrom,
            @NotNull @RequestParam LocalDate dateTo
    ) {
        var response = reportService.getEmployeeSettlement(employeeId, dateFrom, dateTo);
        return mapper.map(response, EmployeeSettlementResponse.class);
    }

    @GetMapping(value = "/employee/{employeeId}/report",
            produces = "application/vnd.ms-excel")
    public ResponseEntity<Resource> generateEmployeeSettlementReport(
            @NotNull @Min(1) @PathVariable Long employeeId,
            @NotNull @Past @RequestParam LocalDate dateFrom,
            @NotNull @RequestParam LocalDate dateTo
    ) {
        return prepareResponseReport(() -> reportService.generateEmployeeSettlementReport(employeeId, dateFrom, dateTo));
    }

    @GetMapping(value = "/locations/report",
            produces = "application/vnd.ms-excel")
    public ResponseEntity<Resource> generateLocationSettlementReport(
            @NotNull @Past @RequestParam LocalDate dateFrom,
            @NotNull @RequestParam LocalDate dateTo
    ) {
        return prepareResponseReport(() -> reportService.generateLocationSettlementReport(dateFrom, dateTo));
    }

    private ResponseEntity<Resource> prepareResponseReport(Supplier<byte[]> supplier){
        var resource = new ByteArrayResource(supplier.get());
        return ResponseEntity.ok(resource);
    }

}
