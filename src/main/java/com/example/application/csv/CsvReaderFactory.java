package com.example.application.csv;

public class CsvReaderFactory {

    public static CsvReader createCSVReader(){
        return new CsvReaderImpl();
    }

}
