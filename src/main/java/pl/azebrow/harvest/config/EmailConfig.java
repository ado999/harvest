package pl.azebrow.harvest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

@Configuration
public class EmailConfig {

    @Value("${spring.mail.username}")
    private String sourceEmailAddress;

    @Bean(name = "freeMarkerConfig")
    public FreeMarkerConfigurationFactoryBean freeMarkerConfiguration(ResourceLoader resourceLoader){
        FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
        bean.setTemplateLoaderPath("classpath:/templates/");
        return bean;
    }

    @Bean(name = "sourceEmailAddress")
    public String sourceEmailAddress(){
        return sourceEmailAddress;
    }

}