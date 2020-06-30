package ru.diasoft.nixson.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.PrintStream;
import java.util.Scanner;

public class IOUtilImpl implements IOUtil {
    private final Scanner scanner;
    private final PrintStream printStream;
    private final BundleUtil bundleUtil;

    public IOUtilImpl(Scanner scanner,PrintStream printStream,BundleUtil bundleUtil) {
        this.scanner = scanner;
        this.printStream = printStream;
        this.bundleUtil = bundleUtil;
    }

    @Override
    public void writeText(String data) {
        printStream.println(data);
    }

    @Override
    public void write(String data) {
        printStream.print(bundleUtil.get(data));
    }

    @Override
    public void write(String data, Object... args) {
        printStream.print(bundleUtil.get(data, args));
    }

    @Override
    public void writeLn(String data) {
        printStream.println(bundleUtil.get(data));
    }

    @Override
    public void writeLn(String data, Object... args) {
        printStream.println(bundleUtil.get(data, args));
    }

    @Override
    public String read() {
        return scanner.nextLine().trim();
    }

    @Override
    public Long readInt() {
        return scanner.nextLong();
    }

    @Override
    public boolean isNumber() {
        return scanner.hasNextLong();
    }
}
