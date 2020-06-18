package ru.diasoft.nixson.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.diasoft.nixson.TestConfig;
import ru.diasoft.nixson.config.LocaleProps;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тестирование BundleServiceImpl")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = ConfigFileApplicationContextInitializer.class, classes = {BundleServiceImpl.class, TestConfig.class})
public class BundleServiceImplTest {
    @Autowired
    private BundleService bundleService;

    @Test
    void get() {
        assertThat(bundleService.get("test1"))
                .isEqualTo("Test");
    }

    @Test
    void getArgs() {
        assertThat(bundleService.get("test2", "done"))
                    .isEqualTo("Test result: done");
    }
}
