package pl.azebrow.harvest.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import pl.azebrow.harvest.mail.MailModel;
import pl.azebrow.harvest.model.AccountStatus;
import pl.azebrow.harvest.utils.QrGenerator;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class EmailService {

    private final static String QR_CODE_CONTENT_ID = "qr-code";
    private final static String MIME_TYPE = "image/png";

    private final static String PWD_RECOVERY_TEMPLATE = "recovery.ftl";
    private final static String PWD_CREATION_TEMPLATE = "creation.ftl";
    private final JavaMailSender emailSender;
    private final Configuration emailConfig;
    private final String sourceAddress;

    private final QrGenerator qrGenerator;

    private AccountService accountService = null;

    public EmailService(
            JavaMailSender emailSender,
            @Qualifier("freeMarkerConfig") Configuration emailConfig,
            @Qualifier("sourceEmailAddress") String sourceAddress,
            QrGenerator qrGenerator) {
        this.emailSender = emailSender;
        this.emailConfig = emailConfig;
        this.sourceAddress = sourceAddress;
        this.qrGenerator = qrGenerator;
    }

    public void initComponent(AccountService accountService) {
        this.accountService = accountService;
    }

    public void sendEmail(MailModel mailModel) {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(
                    message,
                    MimeMessageHelper.MULTIPART_MODE_RELATED,
                    StandardCharsets.UTF_8.name());
        } catch (MessagingException e) {
            e.printStackTrace();
            accountService.setAccountStatus(mailModel.getTo(), AccountStatus.ERROR_SENDING_EMAIL);
            return;
        }
        String templateName = switch (mailModel.getMailType()) {
            case PASSWORD_RECOVERY -> PWD_RECOVERY_TEMPLATE;
            case PASSWORD_CREATION -> PWD_CREATION_TEMPLATE;
        };
        Template template;
        try {
            template = emailConfig.getTemplate(templateName);
        } catch (IOException e) {
            e.printStackTrace();
            accountService.setAccountStatus(mailModel.getTo(), AccountStatus.ERROR_SENDING_EMAIL);
            return;
        }
        String html;
        try {
            html = FreeMarkerTemplateUtils.processTemplateIntoString(template, mailModel.getModel());
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
            accountService.setAccountStatus(mailModel.getTo(), AccountStatus.ERROR_SENDING_EMAIL);
            return;
        }

        try {
            helper.setFrom(sourceAddress);
            helper.setTo(mailModel.getTo());
            helper.setSubject(mailModel.getSubject());
            helper.setText(html, true);
            byte[] qrBytes = qrGenerator.generate(mailModel.getStringCode());
            helper.addInline(QR_CODE_CONTENT_ID, new ByteArrayDataSource(qrBytes, MIME_TYPE));
        } catch (MessagingException e) {
            e.printStackTrace();
            accountService.setAccountStatus(mailModel.getTo(), AccountStatus.ERROR_SENDING_EMAIL);
            return;
        }
        new Thread(() -> {
            try {
                emailSender.send(message);
            } catch (MailSendException e) {
                e.printStackTrace();
                accountService.setAccountStatus(mailModel.getTo(), AccountStatus.EMAIL_NONEXISTENT);
                return;
            } catch (MailException e) {
                e.printStackTrace();
                accountService.setAccountStatus(mailModel.getTo(), AccountStatus.ERROR_SENDING_EMAIL);
                return;
            }
            accountService.setAccountStatus(mailModel.getTo(), AccountStatus.CONFIRMATION_EMAIL_SENT);
        }).start();
    }
}
