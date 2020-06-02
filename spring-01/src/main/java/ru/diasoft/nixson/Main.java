package ru.diasoft.nixson;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.diasoft.nixson.service.ViewService;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        ViewService service = context.getBean(ViewService.class);
        service.view();
    }
}
