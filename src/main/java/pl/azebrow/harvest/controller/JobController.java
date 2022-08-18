package pl.azebrow.harvest.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.azebrow.harvest.constants.RoleEnum;
import pl.azebrow.harvest.model.Job;
import pl.azebrow.harvest.request.JobRequest;
import pl.azebrow.harvest.request.SpecificationRequest;
import pl.azebrow.harvest.response.JobResponse;
import pl.azebrow.harvest.service.JobService;
import pl.azebrow.harvest.specification.SearchCriteria;
import pl.azebrow.harvest.specification.SpecificationBuilder;

import javax.crypto.SealedObject;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/job")
@Secured({RoleEnum.Constants.ADMIN, RoleEnum.Constants.STAFF})
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    private final ModelMapper mapper;

    @GetMapping("/{id}")
    public JobResponse getJobById(
            @PathVariable Long id
    ) {
        var job = jobService.getJobById(id);
        return mapper.map(job, JobResponse.class);
    }

    @GetMapping
    public Page<JobResponse> searchJobs(
            @RequestParam(defaultValue = "{}") Map<String, Object> params,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "25") Integer size
    ){
        SpecificationBuilder<Job> sb = new SpecificationBuilder<>(Job.class);
        params.forEach(sb::with);

        return jobService
                .findJobs(sb.build(), PageRequest.of(page, size, Sort.by("date")))
                .map(j -> mapper.map(j, JobResponse.class));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void postJob(
            @RequestBody JobRequest jobRequest
    ) {
        jobService.addOrUpdateJob(jobRequest, null);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void putJob(
            @PathVariable Long id,
            @RequestBody JobRequest jobRequest
    ) {
        jobService.addOrUpdateJob(jobRequest, id);
    }

}
