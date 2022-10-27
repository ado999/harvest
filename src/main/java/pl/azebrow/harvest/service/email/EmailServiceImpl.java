package pl.azebrow.harvest.service.email;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import pl.azebrow.harvest.enums.RecoveryType;
import pl.azebrow.harvest.mail.MailModel;
import pl.azebrow.harvest.model.Account;
import pl.azebrow.harvest.model.AccountStatus;
import pl.azebrow.harvest.model.PasswordRecoveryToken;
import pl.azebrow.harvest.service.AccountStatusService;
import pl.azebrow.harvest.utils.QrGenerator;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_RELATED;

@Service
@Profile("!test")
public class EmailServiceImpl implements EmailService {

    private final AccountStatusService accountStatusService;
    private final Configuration emailConfig;
    private final JavaMailSender emailSender;
    private final QrGenerator qrGenerator;

    private final static String QR_CODE_CONTENT_ID = "qr-code";
    private final static String IMAGE_PNG_MIME_TYPE = "image/png";

    private final String sourceAddress;

    public EmailServiceImpl(AccountStatusService accountStatusService,
                            JavaMailSender emailSender,
                            @Qualifier("freeMarkerConfig") Configuration emailConfig,
                            @Qualifier("sourceEmailAddress") String sourceAddress,
                            QrGenerator qrGenerator) {
        this.accountStatusService = accountStatusService;
        this.emailSender = emailSender;
        this.emailConfig = emailConfig;
        this.sourceAddress = sourceAddress;
        this.qrGenerator = qrGenerator;
    }

    @Async
    @Override
    public void sendRecoveryEmail(PasswordRecoveryToken recoveryToken, boolean newlyCreatedAccount) {
        RecoveryType type;
        if (newlyCreatedAccount) {
            type = RecoveryType.PASSWORD_CREATION;
        } else {
            type = RecoveryType.PASSWORD_RECOVERY;
        }
        var mailModel = new MailModel(recoveryToken, type);
        var mimeMessage = emailSender.createMimeMessage();
        var account = recoveryToken.getAccount();
        try {
            fillMessage(mimeMessage, mailModel);
        } catch (MessagingException | TemplateException | IOException e) {
            e.printStackTrace();
            accountStatusService.setStatus(account, AccountStatus.ERROR_SENDING_EMAIL);
        }
        send(mimeMessage, account);
    }

    private void fillMessage(MimeMessage message, MailModel model)
            throws MessagingException, TemplateException, IOException {
        var helper = new MimeMessageHelper(message, MULTIPART_MODE_RELATED, UTF_8.name());
        helper.setFrom(sourceAddress);
        helper.setTo(model.getTo());
        helper.setSubject(model.getSubject());
        var html = processMessageToHtml(model);
        helper.setText(html, true);
        var code = model.getStringCode();
        if (code != null) {
            attachQrCode(helper, code);
        }
    }

    private String processMessageToHtml(MailModel mailModel)
            throws IOException, TemplateException {
        var template = emailConfig.getTemplate(mailModel.getTemplateName());
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, mailModel.getModel());
    }

    private void attachQrCode(MimeMessageHelper msgHelper, String code) {
        var qrBytes = qrGenerator.generate(code);
        var dataSource = new ByteArrayDataSource(qrBytes, IMAGE_PNG_MIME_TYPE);
        try {
            msgHelper.addInline(QR_CODE_CONTENT_ID, dataSource);
        } catch (MessagingException e) {
            System.err.printf("Error attaching QR code '%s'%n", code);
        }
    }

    private void send(MimeMessage message, Account account) {
        var status = AccountStatus.CONFIRMATION_EMAIL_SENT;
        try {
            emailSender.send(message);
        } catch (MailSendException e) {
            status = AccountStatus.EMAIL_NONEXISTENT;
            e.printStackTrace();
        } finally {
            accountStatusService.setStatus(account, status);
        }
    }
}
