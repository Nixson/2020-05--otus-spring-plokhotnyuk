package ru.diasoft.nixson.service;

import java.util.List;

public interface CSVService {
    List<List<String>> parse(String filePath);
}
