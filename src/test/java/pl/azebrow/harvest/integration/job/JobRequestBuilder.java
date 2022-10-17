package pl.azebrow.harvest.integration.job;

import pl.azebrow.harvest.request.JobRequest;

import java.math.BigDecimal;

public class JobRequestBuilder {
    private Long locationId = 1L;
    private Long employeeId = 1L;
    private Long jobTypeId = 1L;
    private BigDecimal quantity = BigDecimal.ONE;
    private BigDecimal rate = BigDecimal.ONE;

    public JobRequestBuilder locationId(Long locationId){
        this.locationId = locationId;
        return this;
    }

    public JobRequestBuilder employeeId(Long employeeId){
        this.employeeId = employeeId;
        return this;
    }

    public JobRequestBuilder jobTypeId(Long jobTypeId){
        this.jobTypeId = jobTypeId;
        return this;
    }

    public JobRequestBuilder quantity(BigDecimal quantity){
        this.quantity = quantity;
        return this;
    }

    public JobRequestBuilder rate(BigDecimal rate){
        this.rate = rate;
        return this;
    }

    public JobRequest jobRequest(){
        var request = new JobRequest();
        request.setLocationId(locationId);
        request.setEmployeeId(employeeId);
        request.setJobTypeId(jobTypeId);
        request.setQuantity(quantity);
        request.setRate(rate);
        return request;
    }

}
