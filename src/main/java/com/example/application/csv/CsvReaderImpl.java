package pl.zeto.koszalin.csv;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

class CsvReaderImpl implements CsvReader {

	// private static final Logger logger = Logger.getLogger(CsvReaderImpl.class);

	@Override
	public List<String> csvRead(String file) {
		List<String> records = new ArrayList<>();
		try (CSVReader csvReader = new CSVReader(new FileReader(file))) {
			String[] line;
			while ((line = csvReader.readNext()) != null) {
				records.add(Arrays.asList(line) + "\n");
			}
		} catch (IOException | CsvValidationException e) {
			// logger.error("Error: " + e.getMessage());
		}
		return records;
	}
}
