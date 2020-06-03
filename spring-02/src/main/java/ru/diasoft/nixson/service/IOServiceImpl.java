package ru.diasoft.nixson.service;

import java.io.PrintStream;
import java.util.Scanner;

public class IOServiceImpl implements IOService {
    private final Scanner scanner;
    private final PrintStream printStream;
    public IOServiceImpl(Scanner scanner, PrintStream printStream) {
        this.scanner = scanner;
        this.printStream = printStream;
    }
    @Override
    public void write(String data) {
        printStream.print(data);
    }

    @Override
    public void writeLn(String data) {
        printStream.println(data);
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
