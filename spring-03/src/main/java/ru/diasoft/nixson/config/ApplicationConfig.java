package ru.diasoft.nixson.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.diasoft.nixson.service.BundleService;
import ru.diasoft.nixson.service.IOService;
import ru.diasoft.nixson.service.IOServiceImpl;

import java.io.PrintStream;
import java.util.Scanner;

@EnableConfigurationProperties
@Configuration
public class ApplicationConfig {

    @Bean
    IOService ioService(BundleService bundleService) {
        return new IOServiceImpl(new Scanner(System.in), new PrintStream(System.out), bundleService);
    }

    @Bean
    @ConfigurationProperties(prefix = "question")
    public QuestionProps questionProps() {
        return new QuestionProps();
    }

    @Bean
    public MessageSource messageSource(LocaleProps localeProps) {
        var ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename(localeProps.getPath());
        ms.setDefaultEncoding("UTF-8");
        return ms;
    }

}
