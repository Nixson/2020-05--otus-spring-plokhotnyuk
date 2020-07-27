package ru.diasoft.nixson.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@EnableConfigurationProperties(LocaleProps.class)
@Configuration
public class ApplicationConfig {

    @Bean
    public MessageSource messageSource(LocaleProps localeProps) {
        var ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename(localeProps.getPath());
        ms.setDefaultEncoding("UTF-8");
        return ms;
    }

}
