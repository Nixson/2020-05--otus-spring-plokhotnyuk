package ru.diasoft.nixson.utils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.diasoft.nixson.util.CSVParser;

import java.util.List;

@DisplayName("Тестирование CSVParser")
public class CSVParserTest {

    @DisplayName("Загрузка файла")
    @Test
    void parse() {
        List<String> strList = CSVParser.parse("/questions.csv");
        Assertions
                .assertThat(strList)
                .isNotEmpty()
                .asList()
                .hasSize(1)
                .element(0).asString().contains("Answer1");

    }
}
