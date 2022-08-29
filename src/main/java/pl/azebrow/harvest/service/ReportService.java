package pl.azebrow.harvest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportService {

    private final EmployeeService employeeService;
    private final JobService jobService;
    private final SettlementService settlementService;
    private final LocationService locationService;

    public void getEmployeeSettlement(Long employeeId,
                                      LocalDateTime dateFrom,
                                      LocalDateTime dateTo,
                                      List<Long> locationIds) {
        var employee = employeeService.getEmployeeById(employeeId);
        var jobs = jobService.findJobs()
    }
}
