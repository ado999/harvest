package pl.azebrow.harvest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import pl.azebrow.harvest.exeption.EntityDisabledException;
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
        Account approver = callerFacade.getCaller();
        Employee employee = employeeService.getEmployeeById(jobRequest.getEmployeeId());
        Location location = locationService.getLocationById(jobRequest.getLocationId());
        JobType jobType = jobTypeService.getJobTypeById(jobRequest.getJobTypeId());
        if (location.getDisabled()) {
            throw new EntityDisabledException(String.format("Location \"%s\" is disabled", location.getDescription()));
        }
        if (jobType.getDisabled()) {
            throw new EntityDisabledException(String.format("Job type \"%s\" is disabled", jobType.getTitle()));
        }
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

    public Job getJobById(Long id) {
        return jobRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Job with id \"%d\" not found!", id)
                );
    }

    public Page<Job> findJobs(Specification<Job> specs, PageRequest pageRequest){
        return jobRepository.findAll(specs, pageRequest);
    }
}
