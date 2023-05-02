package pl.zeto.koszalin.validations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import lombok.Getter;
import lombok.Setter;

public class CSVFileValid {

    private static final Logger logger = Logger.getLogger(CSVFileValid.class);
    private static final List<String> SEPARATORS = Arrays.asList(";", ",", ".", ":", "=", "\t");
    boolean previousLineIsEmpty = false;
    boolean currentLineIsEmpty = false;

    @Getter
    @Setter
    boolean valid = true;

    public CSVFileValid(File file, String separator) {
        String fileName = file.getName();
        try {
            if (!checkFileFormat(file)) {
                if (isValid()) {
                    logger.debug("File format is valid: " + fileName);
                } else {
                    logger.error("Invalid file format: " + fileName);
                }
            } else if (!checkSeparator(separator)) {
                if (isValid()) {
                    logger.debug("Correct separator: " + separator);
                } else {
                    logger.error("Incorrect separator: " + separator);
                }
            } else if (!checkColumnNumber(file, separator)) {
                if (isValid()) {
                    logger.debug("Correct number of columns in CSV file: " + fileName);
                } else {
                    logger.error("Incorrect number of columns in CSV file: " + fileName);
                }
            } else if (!checkEmptyLines(file)) {
                if (isValid()) {
                    logger.debug("No empty lines in CSV file: " + fileName);
                } else {
                    logger.error("There is one or more empty lines in CSV file: " + fileName);
                }
            } else {
                logger.debug("Success: " + fileName);
            }
        } catch (IOException e) {
            logger.error("CSV file validation error: " + e.getMessage());
            System.exit(0);
        }
    }

    private boolean checkFileFormat(File file) {
        String fileName = file.getName();
        Pattern pattern = Pattern.compile(".+\\.csv$");
        Matcher matcher = pattern.matcher(fileName);
        setValid(matcher.matches());
        return isValid();
    }

    private boolean checkColumnNumber(File file, String separator) throws IOException {
        FileReader reader = new FileReader(file);
        BufferedReader bReader = new BufferedReader(reader);

        while (bReader.ready()) {
            String[] values = bReader.readLine().split(separator);
            setValid(values.length == 7);
        }
        bReader.close();
        reader.close();
        return isValid();
    }

    private boolean checkSeparator(String separator) {

        if (!SEPARATORS.contains(separator)) {
            setValid(false);
        }

        return isValid();
    }

    private boolean checkEmptyLines(File file) throws IOException {

        BufferedReader bReader = new BufferedReader(new FileReader(file));
        bReader.lines().forEach(l -> {
            previousLineIsEmpty = currentLineIsEmpty;
            if (l.contains(";;;;;")) {
                currentLineIsEmpty = true;
            } else {
                currentLineIsEmpty = false;
            }

            if (!currentLineIsEmpty && previousLineIsEmpty) {
                setValid(false);
            }
        });
        bReader.close();

        return isValid();
    }
}
