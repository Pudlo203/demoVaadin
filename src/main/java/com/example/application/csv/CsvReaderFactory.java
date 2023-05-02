package pl.zeto.koszalin.csv;

public class CsvReaderFactory {

    public static CsvReader createCSVReader(){
        return new CsvReaderImpl();
    }

}
