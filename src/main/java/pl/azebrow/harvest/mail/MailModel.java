package pl.azebrow.harvest.mail;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Builder
@Getter
public class MailModel {
    private Type mailType;
    private String to;
    private String name;
    private String token;
    private LocalDateTime expiryTime;

    public Map<String, Object> getModel() {
        Map<String, Object> model = new HashMap<>();
        model.put("name", name);
        model.put("token", token);
        model.put("expiryTime", expiryTime);
        return model;
    }

    public String getSubject(){
        return mailType.getSubject();
    }

    public enum Type{
        PASSWORD_RECOVERY("Password recovery"),
        PASSWORD_CREATION("Password creation");

        @Getter private final String subject;

        Type(String subject) {
            this.subject = subject;
        }
    }
}
