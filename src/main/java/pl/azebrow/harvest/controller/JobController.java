package pl.azebrow.harvest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.azebrow.harvest.constant.RoleEnum;
import pl.azebrow.harvest.model.Job;
import pl.azebrow.harvest.request.JobRequest;
import pl.azebrow.harvest.response.JobResponse;
import pl.azebrow.harvest.service.JobService;
import pl.azebrow.harvest.specification.SpecificationBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/job")
@Secured({RoleEnum.Constants.ADMIN, RoleEnum.Constants.STAFF})
@RequiredArgsConstructor
@Validated
public class JobController {

    private final JobService jobService;

    private final ModelMapper mapper;

    @Operation(summary = "Get job by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "No job found with the given id")
    })
    @GetMapping("/{id}")
    public JobResponse getJobById(
            @NotNull @Min(1) @PathVariable Long id
    ) {
        var job = jobService.getJobById(id);
        return mapper.map(job, JobResponse.class);
    }

    @Operation(summary = "Get paged job list with filtering", parameters = {
            @Parameter(name = "params", description = "KV pairs specifying requested jobs (optional, see examples)", examples =
            @ExampleObject(name = "keys", description = "All possible filters",
                    value = "{\"location\":1,\"employee\":1,\"jobType\":1,\"approver\":1,\"dateFrom\":\"2022-01-01\",\"dateTo\":\"2022-01-01\"}"))
    })
    @GetMapping
    public Page<JobResponse> searchJobs(
            @RequestParam(defaultValue = "{}") Map<String, Object> params,
            @PositiveOrZero @RequestParam(defaultValue = "0") Integer page,
            @Positive @RequestParam(defaultValue = "25") Integer size
    ) {
        var sb = new SpecificationBuilder();
        params.forEach(sb::with);

        return jobService
                .findJobsWithPagination(sb.build(Job.class), PageRequest.of(page, size, Sort.by("date")))
                .map(j -> mapper.map(j, JobResponse.class));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void postJob(
            @Valid @RequestBody JobRequest jobRequest
    ) {
        jobService.addJob(jobRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void putJob(
            @NotNull @Min(1) @PathVariable Long id,
            @Valid @RequestBody JobRequest jobRequest
    ) {
        jobService.updateJob(jobRequest, id);
    }

}
