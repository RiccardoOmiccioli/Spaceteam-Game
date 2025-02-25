package it.unibo.spaceteam.utils.json;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class JsonReader {

    public static String readJsonFile(String fileName) {
        StringBuilder jsonContent = new StringBuilder();

        try (InputStream inputStream = JsonReader.class.getResourceAsStream("/json/" + fileName)) {
            assert inputStream != null;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonContent.append(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonContent.toString();
    }

}
