package pl.azebrow.harvest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.azebrow.harvest.exeption.EntityDisabledException;
import pl.azebrow.harvest.exeption.ResourceNotFoundException;
import pl.azebrow.harvest.model.Job;
import pl.azebrow.harvest.repository.JobRepository;
import pl.azebrow.harvest.request.JobRequest;
import pl.azebrow.harvest.utils.CallerFacade;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class JobService {

    private final CallerFacade callerFacade;
    private final EmployeeService employeeService;
    private final LocationService locationService;
    private final JobTypeService jobTypeService;
    private final JobRepository jobRepository;

    public void addJob(JobRequest jobRequest) {
        var job = createJobFromRequest(jobRequest);
        jobRepository.save(job);
        employeeService.updateEmployeeBalance(job.getEmployee().getId(), job.getTotalAmount());
    }

    public void updateJob(JobRequest jobRequest, Long id) {
        var oldJob = getJobById(id);
        var updatedJob = createJobFromRequest(jobRequest);
        updatedJob.setId(id);
        employeeService.updateEmployeeBalance(oldJob.getEmployee().getId(), oldJob.getTotalAmount().negate());
        employeeService.updateEmployeeBalance(updatedJob.getEmployee().getId(), updatedJob.getTotalAmount());
        jobRepository.save(updatedJob);
    }

    private Job createJobFromRequest(JobRequest jobRequest) {
        var approver = callerFacade.getCaller();
        var employee = employeeService.getEmployeeById(jobRequest.getEmployeeId());
        var location = locationService.getLocationById(jobRequest.getLocationId());
        var jobType = jobTypeService.getJobTypeById(jobRequest.getJobTypeId());
        if (location.getDisabled()) {
            throw new EntityDisabledException(String.format("Location \"%s\" is disabled", location.getDescription()));
        }
        if (jobType.getDisabled()) {
            throw new EntityDisabledException(String.format("Job type \"%s\" is disabled", jobType.getTitle()));
        }
        var rate = jobRequest.getRate();
        var quantity = jobRequest.getQuantity();
        var totalAmount = rate.multiply(quantity);
        return Job.builder()
                .location(location)
                .employee(employee)
                .approver(approver)
                .jobType(jobType)
                .rate(rate)
                .quantity(quantity)
                .totalAmount(totalAmount)
                .build();
    }

    public Job getJobById(Long id) {
        return jobRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Job with id \"%d\" not found!", id)
                );
    }

    public Page<Job> findJobsWithPagination(Specification<Job> specs, Pageable pageable) {
        return jobRepository.findAll(specs, pageable);
    }

    public List<Job> findJobs(Specification<Job> specs) {
        return jobRepository.findAll(specs);
    }
}
