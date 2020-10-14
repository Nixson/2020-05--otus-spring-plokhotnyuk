package ru.nixson;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.PublishSubscribeChannel;
import ru.nixson.domain.Student;
import ru.nixson.gateway.StudentGateway;

import javax.annotation.PostConstruct;
import java.util.Random;

@SpringBootApplication
@IntegrationComponentScan
@RequiredArgsConstructor
public class SpringIntegrationApplication {

    @Qualifier("successStudentChannel")
    private final PublishSubscribeChannel successStudentChannel;

    @Qualifier("badStudentChannel")
    private final PublishSubscribeChannel badStudentChannel;

    @PostConstruct
    public void subscribeOnChannels() {
        successStudentChannel.subscribe(message ->
                System.out.printf("[SUCCESS STUDENT] Студент %s  прошел все зачеты ", ((Student) message.getPayload()).getName()));
        badStudentChannel.subscribe(message ->
                System.out.printf("[BAD STUDENT] Студент %s  не сдал ни одного зачета ", ((Student) message.getPayload()).getName()));
    }


    public static void main(String[] args) {
        final ConfigurableApplicationContext context = SpringApplication.run(SpringIntegrationApplication.class, args);
        final StudentGateway gateway = context.getBean(StudentGateway.class);

        final Student student = Student.builder()
                .name("Васильева Виктория")
                .evaluation(2)
                .group("ЭПИ-11")
                .build();

        final Student student1 = Student.builder()
                .name("Агафонов Герман")
                .evaluation(5)
                .group("ЭПИ-11")
                .build();


        for (int i = 0; i < 5; i++) {
            if(student.getEvaluation() <= i){
                student.setEvaluation(i);
            }
            if(student1.getEvaluation() <= i){
                student1.setEvaluation(i);
            }
            System.out.println(gateway.registration(student));
            System.out.println(gateway.registration(student1));
        }
    }

}
