package ru.diasoft.nixson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.diasoft.nixson.config.LocaleProps;

@SpringBootApplication
@EnableConfigurationProperties(LocaleProps.class)
class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
