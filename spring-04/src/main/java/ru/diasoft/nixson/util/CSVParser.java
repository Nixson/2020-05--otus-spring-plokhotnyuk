package ru.diasoft.nixson.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CSVParser {
    public static List<String> parse(String filePath) {
        InputStream resource = CSVParser.class.getResourceAsStream(filePath);
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resource))) {
            return bufferedReader.lines().collect(Collectors.toList());
        } catch (IOException e){
            return Collections.emptyList();
        }
    }
}
