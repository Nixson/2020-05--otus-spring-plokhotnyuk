package ru.diasoft.nixson.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.diasoft.nixson.util.BundleUtil;
import ru.diasoft.nixson.util.IOUtil;
import ru.diasoft.nixson.util.IOUtilImpl;

import java.io.PrintStream;
import java.util.Scanner;

@EnableConfigurationProperties(LocaleProps.class)
@Configuration
public class ApplicationConfig {

    @Bean
    IOUtil ioService(BundleUtil bundleUtil) {
        return new IOUtilImpl(new Scanner(System.in), new PrintStream(System.out), bundleUtil);
    }

    @Bean
    public MessageSource messageSource(LocaleProps localeProps) {
        var ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename(localeProps.getPath());
        ms.setDefaultEncoding("UTF-8");
        return ms;
    }

}
