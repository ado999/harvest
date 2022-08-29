package pl.azebrow.harvest.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.azebrow.harvest.constant.RoleEnum;
import pl.azebrow.harvest.service.ReportService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/report")
@Secured({RoleEnum.Constants.STAFF, RoleEnum.Constants.ADMIN})
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    private final ModelMapper mapper;

    @GetMapping("/employee/{employeeId}")
    public void getEmployeeSettlement(
            @PathVariable Long employeeId,
            @RequestParam LocalDate from,
            @RequestParam(required = false) LocalDate to,
            @RequestParam(defaultValue = "[]") Long[] location
    ) {
        LocalDateTime dateTo = to != null ? to.atTime(LocalTime.MAX) : LocalDateTime.MAX;
        LocalDateTime dateFrom = from.atTime(LocalTime.MIN);
        List<Long> locationIds = Arrays.stream(location).toList();
        reportService.getEmployeeSettlement(employeeId, dateFrom, dateTo, locationIds);
    }

}
