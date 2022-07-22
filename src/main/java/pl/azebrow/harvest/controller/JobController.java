package pl.azebrow.harvest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.azebrow.harvest.constants.RoleEnum;
import pl.azebrow.harvest.request.JobRequest;
import pl.azebrow.harvest.service.JobService;

@RestController
@RequestMapping("/api/v1/job")
@Secured({RoleEnum.Constants.ADMIN, RoleEnum.Constants.STAFF})
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @PostMapping
    public void postJob(
            @RequestBody JobRequest jobRequest
    ) {
        jobService.addOrUpdateJob(jobRequest, null);
    }

    @PutMapping("/{id}")
    public void putJob(
            @PathVariable Long id,
            @RequestBody JobRequest jobRequest
    ) {
        jobService.addOrUpdateJob(jobRequest, id);
    }

}
