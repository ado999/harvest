package pl.azebrow.harvest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

import java.util.Arrays;

@Configuration
@EnableAsync
public class EmailConfig {

    @Autowired
    private Environment env;

    @Autowired
    private ConfigurableApplicationContext ctx;

    @Value("${spring.mail.username:}")
    private String sourceEmailAddress;

    @Value("${spring.mail.password:}")
    private String emailPassword;

    @Bean(name = "freeMarkerConfig")
    public FreeMarkerConfigurationFactoryBean freeMarkerConfiguration(ResourceLoader resourceLoader) {
        FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
        bean.setTemplateLoaderPath("classpath:/templates/");
        return bean;
    }

    @Bean(name = "sourceEmailAddress")
    public String sourceEmailAddress() {
        if (isMailCredentialsRequired() && !areMailCredentialsProvided()) {
            System.err.println("No email credentials provided. " +
                    "Set \"spring.mail.username\" and \"spring.mail.password\" " +
                    "or set \"no-mail\" as active profile.");
            ctx.close();
            System.exit(1);
        }
        return sourceEmailAddress;
    }

    private Boolean isMailCredentialsRequired() {
        return Arrays
                .stream(env.getActiveProfiles())
                .noneMatch(p -> p.equals("test") || p.equals("no-mail"));
    }

    private Boolean areMailCredentialsProvided() {
        return !(sourceEmailAddress.isEmpty() || emailPassword.isEmpty());
    }

}
