package ru.diasoft.nixson.service;


public interface IOService {
    void write(String data);
    void writeLn(String data);
    String read();
    Integer readInt();
}
