package ru.nixson.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import ru.nixson.domain.Registration;
import ru.nixson.domain.Student;

@Configuration
public class IntegrationConfig {

    @Bean
    public DirectChannel regRequestChannel() {
        return MessageChannels.direct().get();
    }

    @Bean
    public PublishSubscribeChannel reqResponseChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public PublishSubscribeChannel successStudentChannel() { return MessageChannels.publishSubscribe().get(); }

    @Bean
    public PublishSubscribeChannel badStudentChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public PublishSubscribeChannel checkEvaluationPolicy() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public PublishSubscribeChannel registrationChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public IntegrationFlow checkBadStudentFlow() {
        return IntegrationFlows.from("regRequestChannel")
                .handle("badStudentService", "check")
                .<Student, Boolean>route(Student::isBad, mapping -> mapping
                                .subFlowMapping(true, sf -> sf.channel("badStudentChannel"))
                                .subFlowMapping(false, sf -> sf.channel("checkEvaluationPolicy")))
                .get();
    }

    @Bean
    public IntegrationFlow badStudentFlow() {
        return IntegrationFlows.from("badStudentChannel")
                .<Student, Registration>transform(student -> Registration.builder()
                        .student(student)
                        .success(false)
                        .build())
                .channel("reqResponseChannel")
                .get();

    }

    @Bean
    public IntegrationFlow successStudentFlow() {
        return IntegrationFlows.from("successStudentChannel")
                .<Student, Registration>transform(student -> Registration.builder()
                        .student(student)
                        .success(true)
                        .build())
                .channel("reqResponseChannel")
                .get();

    }

    @Bean
    public IntegrationFlow checkEvaluationPolicyFlow() {
        return IntegrationFlows.from("checkEvaluationPolicy")
                .handle("evaluationPolicyService", "check")
                .<Student, Boolean>route(Student::isSuccess, mapping -> mapping
                    .subFlowMapping(false, sf -> sf.channel("successStudentChannel"))
                    .subFlowMapping(true, sf -> sf.channel("registrationChannel")))
                .get();
    }

    @Bean
    public IntegrationFlow registrationFlow() {
        return IntegrationFlows.from("registrationChannel")
                .handle("registrationService", "registration")
                .channel("reqResponseChannel")
                .get();
    }


}
