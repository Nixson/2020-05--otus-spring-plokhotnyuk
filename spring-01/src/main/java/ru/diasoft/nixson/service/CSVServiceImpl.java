package ru.diasoft.nixson.service;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVServiceImpl implements CSVService {
    @Override
    public List<List<String>> parse(String filePath) {
        List<List<String>> response = new ArrayList<List<String>>();
        try {
            URL url = getClass().getResource(filePath);
            Path path = Paths.get(url.toURI());
            List<String> fileLines = Files.readAllLines(path, StandardCharsets.UTF_8);
            for (String fileLine : fileLines) {
                response.add(new ArrayList<String>(Arrays.asList(fileLine.split(";"))));
            }
        } catch (Exception e) {
        }

        return response;
    }
}
