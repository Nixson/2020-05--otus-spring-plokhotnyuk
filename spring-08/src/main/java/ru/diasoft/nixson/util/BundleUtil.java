package ru.diasoft.nixson.util;

public interface BundleUtil {
    String get(String key);
    String get(String key, Object ...args);
}
