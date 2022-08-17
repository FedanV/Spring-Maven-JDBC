package com.foxminded.vitaliifedan.task7.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ResourcesUtils {

    private ResourcesUtils() {
    }

    public static Properties loadPropertiesFromResources(String fileName) throws IOException {
        try(InputStream resourceAsStream = ResourcesUtils.class.getClassLoader().getResourceAsStream(fileName)) {
            if(resourceAsStream == null) {
                throw new IOException("File " + fileName + " not found.");
            }
            Properties properties = new Properties();
            properties.load(resourceAsStream);
            return properties;
        }
    }

    public static String loadTextFileFromResources(String fileName) throws IOException {
        Path path;
        try {
            path = Path.of(Objects.requireNonNull(ResourcesUtils.class.getClassLoader().getResource(fileName)).toURI());
        } catch (URISyntaxException e) {
            throw new IOException(e);
        }
        try (Stream<String> lines = Files.lines(path)) {
            return lines.collect(Collectors.joining("\n"));
        }
    }
}
