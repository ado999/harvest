package pl.azebrow.harvest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.azebrow.harvest.exeption.ResourceNotFoundException;
import pl.azebrow.harvest.model.*;
import pl.azebrow.harvest.repository.JobRepository;
import pl.azebrow.harvest.request.JobRequest;
import pl.azebrow.harvest.utils.CallerFacade;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class JobService {

    private final CallerFacade callerFacade;
    private final EmployeeService employeeService;
    private final LocationService locationService;
    private final JobTypeService jobTypeService;
    private final JobRepository jobRepository;

    public void addOrUpdateJob(JobRequest jobRequest, Long id) {
        User approver = callerFacade.getCaller();
        Employee employee = employeeService.getEmployeeById(jobRequest.getEmployeeId());
        Location location = locationService.getLocationById(jobRequest.getLocationId());
        JobType jobType = jobTypeService.getJobTypeById(jobRequest.getJobTypeId());
        BigDecimal rate = jobRequest.getRate();
        BigDecimal quantity = jobRequest.getQuantity();
        BigDecimal totalAmount = rate.multiply(quantity);
        Job job = Job.builder()
                .id(id)
                .location(location)
                .employee(employee)
                .approver(approver)
                .jobType(jobType)
                .rate(rate)
                .quantity(quantity)
                .totalAmount(totalAmount)
                .build();
        jobRepository.save(job);
    }

    public void addOrUpdateJob(JobRequest jobRequest) {
        addOrUpdateJob(jobRequest, null);
    }

    private Job getJobById(Long id) {
        return jobRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Job with id \"%d\" not found!", id)
                );
    }
}
