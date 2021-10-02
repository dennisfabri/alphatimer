package org.lisasp.alphatimer.datatests;

import lombok.extern.slf4j.Slf4j;
import org.lisasp.alphatimer.legacy.LegacySerialization;
import org.lisasp.alphatimer.legacy.dto.Heat;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class TestData {

    private static final boolean verbose = true;

    private static final String TEST_DATA_DIRECTORY = String.format("%s/resources/", TestData.class.getPackageName().replaceAll("\\.", "/"));

    private static final String TEST_OUTPUT_DIRECTORY = "target/test-output/";

    private static String readFile(String file) throws IOException {
        try (InputStream is = TestData.class.getModule().getResourceAsStream(file);
             InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }

    private static String determineFilename(String filename, String suffix) {
        return String.format("%s%s.%s", TEST_DATA_DIRECTORY, filename, suffix);
    }

    public String readLegacyData(String filename) throws IOException {
        return readFile(determineFilename(filename, "ez"));
    }

    public byte[] readSerialInput(String filename) throws IOException {
        try (InputStream is = TestData.class.getClassLoader().getResourceAsStream(determineFilename(filename, "serial"))) {
            return is.readAllBytes();
        }
    }

    public void writeToDebug(Heat[] actual, String filename) throws IOException {
        if (verbose) {
            new File(TEST_OUTPUT_DIRECTORY).mkdirs();

            try (var os = new FileOutputStream(TestData.TEST_OUTPUT_DIRECTORY + filename + ".xml");
                 PrintStream ps = new PrintStream(os)) {
                ps.println(LegacySerialization.toXML(actual));
            }
        }
    }
}
