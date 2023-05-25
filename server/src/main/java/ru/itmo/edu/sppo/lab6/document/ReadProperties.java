package ru.itmo.edu.sppo.lab6.document;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class ReadProperties {
    private static final String NAME_PROPERTIES = "server.properties";

    public String read(String address) {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(NAME_PROPERTIES);
            Properties properties = new Properties();
            properties.load(inputStream);

            String value = properties.getProperty(address);

            if (inputStream != null) {
                inputStream.close();
            }
            return value;
        } catch (IOException e) {
            log.error("Не удалось считать данные из properties файла");
            throw new RuntimeException(e);
        }
    }
}
