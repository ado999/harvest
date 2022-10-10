package pl.azebrow.harvest.integration.login;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class FormURLEncodedHttpEntityBuilder {

    public final static String USERNAME = "username";
    public final static String PASSWORD = "password";

    private final MultiValueMap<Object, Object> params = new LinkedMultiValueMap<>();

    public FormURLEncodedHttpEntityBuilder withParam(Object key, Object value) {
        params.add(key, value);
        return this;
    }

    public HttpEntity<?> build() {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return new HttpEntity<MultiValueMap<?, ?>>(params, headers);
    }
}
