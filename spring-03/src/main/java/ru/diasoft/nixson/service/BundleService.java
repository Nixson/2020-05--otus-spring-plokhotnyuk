package ru.diasoft.nixson.service;

public interface BundleService {
    String get(String key);
    String get(String key, Object ...args);
}
