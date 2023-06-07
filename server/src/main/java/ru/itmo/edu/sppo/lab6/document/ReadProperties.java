package ru.itmo.edu.sppo.lab6.document;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class ReadProperties {
    private static final String NAME_PROPERTIES = "server.properties";
    private static final Pattern ENV_TEMPLATE = Pattern.compile("^\\$\\{(.*)}$");

    private ReadProperties() {
    }

    public static String read(String address) {
        try (InputStream inputStream = ReadProperties.class.getClassLoader().getResourceAsStream(NAME_PROPERTIES)) {
            Properties properties = new Properties();
            properties.load(inputStream);
            return checkEnvVariable(properties.getProperty(address));
        } catch (IOException e) {
            log.error("Не удалось считать данные из properties файла");
            throw new RuntimeException(e);
        }
    }

    private static String checkEnvVariable(String name) {
        Matcher matcher = ENV_TEMPLATE.matcher(name);
        return matcher.find() ? System.getenv(matcher.group(1)) : name;
    }
}
