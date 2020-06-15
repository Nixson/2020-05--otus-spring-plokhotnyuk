package ru.diasoft.nixson.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.diasoft.nixson.config.LocaleProps;

import java.util.Locale;

@Component
public class BundleServiceImpl implements BundleService {
    private final MessageSource messageSource;
    private final Locale locale;

    public BundleServiceImpl(MessageSource messageSource, LocaleProps localeProps) {
        this.messageSource = messageSource;
        locale = localeProps.getLocale();
    }

    @Override
    public String get(String key) {
        return messageSource.getMessage(key,new Object[]{},locale);
    }

    @Override
    public String get(String key, Object... args) {
        return messageSource.getMessage(key,args,locale);
    }
}
