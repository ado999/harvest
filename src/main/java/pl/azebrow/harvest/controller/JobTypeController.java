package pl.azebrow.harvest.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.azebrow.harvest.constants.RoleEnum;
import pl.azebrow.harvest.request.JobTypeRequest;
import pl.azebrow.harvest.response.JobTypeResponse;
import pl.azebrow.harvest.service.JobTypeService;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/job-type")
@Secured({RoleEnum.Constants.ADMIN})
@RequiredArgsConstructor
public class JobTypeController {

    private final JobTypeService jobTypeService;

    private final ModelMapper mapper;

    @GetMapping
    public Collection<JobTypeResponse> getJobTypeList() {
        var jobTypes = jobTypeService.getJobTypeList();
        return jobTypes
                .stream()
                .map(j -> mapper.map(j, JobTypeResponse.class))
                .collect(Collectors.toList());
    }

    @PostMapping
    public void postJobType(
            @RequestBody JobTypeRequest jobTypeRequest
    ) {
        jobTypeService.addJobType(jobTypeRequest);
    }

}
