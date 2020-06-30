package ru.diasoft.nixson.util;


public interface IOUtil {
    void writeText(String data);
    void write(String data);
    void write(String data, Object... args);
    void writeLn(String data);
    void writeLn(String data, Object... args);
    String read();
    Long readInt();
    boolean isNumber();
}
