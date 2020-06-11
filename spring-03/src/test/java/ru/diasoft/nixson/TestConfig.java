package ru.diasoft.nixson;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.diasoft.nixson.config.ApplicationConfig;
import ru.diasoft.nixson.config.LocaleProps;
import ru.diasoft.nixson.service.BundleService;
import ru.diasoft.nixson.service.BundleServiceImpl;
import ru.diasoft.nixson.service.IOService;
import ru.diasoft.nixson.service.IOServiceImpl;

import java.io.PrintStream;
import java.util.Scanner;

@Configuration
@Import(ApplicationConfig.class)
@EnableConfigurationProperties(LocaleProps.class)
public class TestConfig {
    @Bean
    public MessageSource messageSource() {
        var ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("messages-test");
        ms.setDefaultEncoding("UTF-8");
        return ms;
    }

    @Bean
    IOService ioService() {
        return new IOServiceImpl(new Scanner(System.in), new PrintStream(System.out), new BundleServiceImpl(messageSource(),new LocaleProps()));
    }

}
