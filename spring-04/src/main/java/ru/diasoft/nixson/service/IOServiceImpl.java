package ru.diasoft.nixson.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.PrintStream;
import java.util.Scanner;

public class IOServiceImpl implements IOService {
    private final Scanner scanner;
    private final PrintStream printStream;
    private final BundleService bundleService;

    public IOServiceImpl(Scanner scanner,PrintStream printStream,BundleService bundleService) {
        this.scanner = scanner;
        this.printStream = printStream;
        this.bundleService = bundleService;
    }

    @Override
    public void writeText(String data) {
        printStream.println(data);
    }

    @Override
    public void write(String data) {
        printStream.print(bundleService.get(data));
    }

    @Override
    public void write(String data, Object... args) {
        printStream.print(bundleService.get(data, args));
    }

    @Override
    public void writeLn(String data) {
        printStream.println(bundleService.get(data));
    }

    @Override
    public void writeLn(String data, Object... args) {
        printStream.println(bundleService.get(data, args));
    }

    @Override
    public String read() {
        return scanner.nextLine();
    }

    @Override
    public Integer readInt() {
        return scanner.nextInt();
    }
}
