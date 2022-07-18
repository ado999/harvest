package pl.azebrow.harvest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.azebrow.harvest.constants.RoleEnum;
import pl.azebrow.harvest.request.JobTypeRequest;
import pl.azebrow.harvest.service.JobTypeService;

@RestController
@RequestMapping("/api/v1/job-type")
@Secured({RoleEnum.Constants.ADMIN})
@RequiredArgsConstructor
public class JobTypeController {

    private final JobTypeService jobTypeService;

    @PostMapping
    public void postJobType(
            @RequestBody JobTypeRequest jobTypeRequest
    ) {
        jobTypeService.addJobType(jobTypeRequest);
    }

}
