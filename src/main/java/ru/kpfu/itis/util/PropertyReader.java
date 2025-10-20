package ru.kpfu.itis.util;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.util.Properties;

@UtilityClass
public class PropertyReader {

    private final Properties properties;

    static {
        properties = new Properties();
        try {
            properties.load(PropertyReader.class
                    .getClassLoader()
                    .getResourceAsStream("app.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load app.properties", e);
        }
    }

    public String getProperties(String key) {
        return properties.getProperty(key);
    }
}