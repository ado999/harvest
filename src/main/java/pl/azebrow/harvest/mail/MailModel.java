package pl.azebrow.harvest.mail;

import lombok.Data;
import pl.azebrow.harvest.enums.RecoveryType;
import pl.azebrow.harvest.model.PasswordRecoveryToken;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
public class MailModel {

    private final static String PWD_RECOVERY_TEMPLATE = "recovery.ftl";
    private final static String PWD_CREATION_TEMPLATE = "creation.ftl";
    private final RecoveryType type;
    private final String to;
    private final String name;
    private final String token;
    private final LocalDateTime expiryTime;
    private final String stringCode;

    private final String templateName;

    public MailModel(PasswordRecoveryToken token, RecoveryType type) {
        var account = token.getAccount();
        this.to = account.getEmail();
        this.name = account.getFirstName();
        this.stringCode = account.getEmployee() == null ? null : account.getEmployee().getCode();
        this.type = type;
        this.token = token.getToken();
        this.expiryTime = token.getExpiryDate();
        templateName = switch (type) {
            case PASSWORD_RECOVERY -> PWD_RECOVERY_TEMPLATE;
            case PASSWORD_CREATION -> PWD_CREATION_TEMPLATE;
        };
    }

    public Map<String, Object> getModel() {
        Map<String, Object> model = new HashMap<>();
        model.put("name", name);
        model.put("token", token);
        model.put("expiryTime", expiryTime);
        model.put("qrCode", stringCode);
        return model;
    }

    public String getSubject() {
        return type.getSubject();
    }

    public String getTemplateName() {
        return templateName;
    }
}
