package com.foxminded.vitaliifedan.task7.utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ResourcesUtils {

    private ResourcesUtils() {
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
