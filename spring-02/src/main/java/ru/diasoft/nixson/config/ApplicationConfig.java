package ru.diasoft.nixson.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.diasoft.nixson.service.IOService;
import ru.diasoft.nixson.service.IOServiceImpl;

import java.io.PrintStream;
import java.util.Scanner;

@Configuration
public class ApplicationConfig {
    @Bean
    IOService ioService() {
        return new IOServiceImpl(new Scanner(System.in), new PrintStream(System.out));
    }
}
