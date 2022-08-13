package pl.azebrow.harvest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.azebrow.harvest.exeption.ResourceNotFoundException;
import pl.azebrow.harvest.model.JobType;
import pl.azebrow.harvest.model.JobUnit;
import pl.azebrow.harvest.repository.JobTypeRepository;
import pl.azebrow.harvest.request.JobTypeRequest;
import pl.azebrow.harvest.request.JobTypeUpdateRequest;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobTypeService {

    private final JobTypeRepository jobTypeRepository;


    public Collection<JobType> getJobTypeList(boolean showDisabled) {
        return jobTypeRepository
                .findAll()
                .stream()
                .filter(j -> showDisabled || !j.getDisabled())
                .collect(Collectors.toList());
    }

    public void addJobType(JobTypeRequest jobTypeRequest) {
        JobUnit jobUnit = JobUnit.valueOf(jobTypeRequest.getJobUnit());
        JobType jobType = JobType
                .builder()
                .title(jobTypeRequest.getTitle())
                .unit(jobUnit)
                .defaultRate(jobTypeRequest.getDefaultRate())
                .disabled(false)
                .build();

        jobTypeRepository.save(jobType);
    }

    public void setDisabled(Long id, JobTypeUpdateRequest updateRequest) {
        JobType jobType = getJobTypeById(id);
        jobType.setDisabled(updateRequest.getDisabled());
        jobTypeRepository.save(jobType);
    }

    public JobType getJobTypeById(Long id) {
        return jobTypeRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Job type with id \"%d\" not found!", id)
                );
    }
}
