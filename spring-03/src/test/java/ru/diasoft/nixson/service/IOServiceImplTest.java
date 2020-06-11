package ru.diasoft.nixson.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тестирование IOServiceImpl")
public class IOServiceImplTest {
    @Mock
    BundleService bundleService;
    @Test
    void writeText() {
        final String outputString = "myOutputString";
        OutputStream os = new ByteArrayOutputStream();
        final Scanner scanner = new Scanner(new ByteArrayInputStream(new byte[]{}));
        final PrintStream printStream = new PrintStream(os);
        IOService questionIOService = new IOServiceImpl(scanner, printStream, bundleService);
        questionIOService.writeText(outputString);

        assertThat(os.toString()).contains(outputString);
    }

    @Test
    void read() {
        final String inputString = "myInputString";
        final Scanner scanner = new Scanner(new ByteArrayInputStream(inputString.getBytes()));
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PrintStream printStream = new PrintStream(outputStream);
        IOService questionIOService = new IOServiceImpl(scanner, printStream, bundleService);

        assertThat(questionIOService.read()).contains(inputString);
    }
}
