package pl.azebrow.harvest.mail;

import lombok.Data;
import lombok.Getter;
import pl.azebrow.harvest.model.Account;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
public class MailModel {
    private Account account;

    private Type mailType;
    private String to;
    private String name;
    private String token;
    private LocalDateTime expiryTime;
    private String stringCode;

    public MailModel(Account account, Type type) {
        this.mailType = type;
        this.account = account;
        this.to = account.getEmail();
        this.name = account.getFirstName();
        this.stringCode = account.getEmployee() != null ? account.getEmployee().getCode() : null;
    }

    public Map<String, Object> getModel() {
        Map<String, Object> model = new HashMap<>();
        model.put("name", name);
        model.put("token", token);
        model.put("expiryTime", expiryTime);
        model.put("qrCode", stringCode);
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
