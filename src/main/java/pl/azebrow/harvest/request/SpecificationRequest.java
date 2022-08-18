package pl.azebrow.harvest.request;

import lombok.Data;

import java.util.Map;

@Data
public class SpecificationRequest {
    private Map<String, Object> params;
}
