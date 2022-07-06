package pl.azebrow.harvest.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import pl.azebrow.harvest.mail.MailModel;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class EmailService {

    private final static String PWD_RECOVERY_TEMPLATE = "recovery.ftl";
    private final static String PWD_CREATION_TEMPLATE = "creation.ftl";
    private final JavaMailSender emailSender;
    private final Configuration emailConfig;
    private final String sourceAddress;

    public EmailService(
            JavaMailSender emailSender,
            @Qualifier("freeMarkerConfig") Configuration emailConfig,
            @Qualifier("sourceEmailAddress") String sourceAddress) {
        this.emailSender = emailSender;
        this.emailConfig = emailConfig;
        this.sourceAddress = sourceAddress;
    }

    public void sendEmail(MailModel mailModel) throws MessagingException, IOException, TemplateException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                message,
                MimeMessageHelper.MULTIPART_MODE_RELATED,
                StandardCharsets.UTF_8.name());
        String templateName = switch (mailModel.getMailType()){
            case PASSWORD_RECOVERY -> PWD_RECOVERY_TEMPLATE;
            case PASSWORD_CREATION -> PWD_CREATION_TEMPLATE;
        };
        Template template = emailConfig.getTemplate(templateName);
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, mailModel.getModel());

        helper.setFrom(sourceAddress);
        helper.setTo(mailModel.getTo());
        helper.setSubject(mailModel.getSubject());
        helper.setText(html, true);
        emailSender.send(message);
    }

}
