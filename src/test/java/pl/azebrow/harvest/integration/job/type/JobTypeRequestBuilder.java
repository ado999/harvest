package pl.azebrow.harvest.integration.job.type;

import pl.azebrow.harvest.request.JobTypeRequest;

import java.math.BigDecimal;

public class JobTypeRequestBuilder {

    private String title;
    private String jobUnit;
    private BigDecimal defaultRate;

    public JobTypeRequestBuilder title(String title) {
        this.title = title;
        return this;
    }

    public JobTypeRequestBuilder jobUnit(String jobUnit) {
        this.jobUnit = jobUnit;
        return this;
    }

    public JobTypeRequestBuilder defaultRate(BigDecimal defaultRate) {
        this.defaultRate = defaultRate;
        return this;
    }

    public JobTypeRequest jobTypeRequest() {
        var request = new JobTypeRequest();
        request.setTitle(title);
        request.setJobUnit(jobUnit);
        request.setDefaultRate(defaultRate);
        return request;
    }
}
