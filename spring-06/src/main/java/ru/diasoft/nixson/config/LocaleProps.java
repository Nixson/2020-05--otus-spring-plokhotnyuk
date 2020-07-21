package ru.diasoft.nixson.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@Getter
@Setter
@ConfigurationProperties(prefix = "application")
public class LocaleProps {
    private Locale locale = new Locale("en");
    private String path = "classpath:/i18n/messages";
}
