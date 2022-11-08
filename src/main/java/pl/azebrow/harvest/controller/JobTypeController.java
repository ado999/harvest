package pl.azebrow.harvest.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.azebrow.harvest.enums.RoleEnum;
import pl.azebrow.harvest.request.JobTypeRequest;
import pl.azebrow.harvest.request.JobTypeUpdateRequest;
import pl.azebrow.harvest.response.JobTypeResponse;
import pl.azebrow.harvest.response.JobUnitResponse;
import pl.azebrow.harvest.service.JobTypeService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/job-type")
@Secured({RoleEnum.Constants.ADMIN, RoleEnum.Constants.STAFF})
@RequiredArgsConstructor
@Validated
public class JobTypeController {

    private final JobTypeService jobTypeService;

    private final ModelMapper mapper;

    @GetMapping
    public Page<JobTypeResponse> getJobTypeList(
            @RequestParam(required = false, defaultValue = "false") boolean showDisabled,
            @RequestParam(required = false) Pageable pageable
    ) {
        var jobTypes = jobTypeService.getJobTypeList(showDisabled, pageable);
        return jobTypes
                .map(j -> mapper.map(j, JobTypeResponse.class));
    }

    @GetMapping("/units")
    public Collection<JobUnitResponse> getJobUnits() {
        var jobUnits = jobTypeService.getJobUnits();
        return jobUnits
                .stream()
                .map(u -> mapper.map(u, JobUnitResponse.class))
                .collect(Collectors.toList());
    }

    @Secured({RoleEnum.Constants.ADMIN})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void postJobType(
            @Valid @RequestBody JobTypeRequest jobTypeRequest
    ) {
        jobTypeService.addJobType(jobTypeRequest);
    }

    @Secured({RoleEnum.Constants.ADMIN})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void setDisabled(
            @NotNull @Min(1) @PathVariable Long id,
            @Valid @RequestBody JobTypeUpdateRequest updateRequest
    ) {
        jobTypeService.setDisabled(id, updateRequest);
    }

}
