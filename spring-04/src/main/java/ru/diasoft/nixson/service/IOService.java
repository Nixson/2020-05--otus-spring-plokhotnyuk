package ru.diasoft.nixson.service;


public interface IOService {
    void writeText(String data);
    void write(String data);
    void write(String data, Object... args);
    void writeLn(String data);
    void writeLn(String data, Object... args);
    String read();
    Integer readInt();
}
