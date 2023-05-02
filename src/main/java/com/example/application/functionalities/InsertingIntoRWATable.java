package pl.zeto.koszalin.functionalities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import pl.zeto.koszalin.csv.CsvReader;
import pl.zeto.koszalin.csv.CsvReaderFactory;
import pl.zeto.koszalin.validations.CSVFileValid;

public class InsertingIntoRWATable {

    // private static final Logger logger = Logger.getLogger(InsertingIntoRWATable.class);

    @Getter
    @Setter
    private boolean header;
    @Getter
    @Setter
    private char columnSeparator;
    @Getter
    @Setter
    private String versionName;
    @Getter
    @Setter
    private String filePath;

    public InsertingIntoRWATable() {
        loadDefaultValues();
    }

    void loadDefaultValues() {
        setHeader(false);
        setVersionName("");
        columnSeparator = ';';
        filePath = null;

    }

    public void headerCheckboxAction() {
        setHeader(!isHeader());
    }

    public void DoSequence() throws IOException {

        File file = new File(getFilePath());

        CSVFileValid csvFileValid = new CSVFileValid(file, String.valueOf(getColumnSeparator()));

        if (csvFileValid.isValid() == false) {
            System.exit(0);
        }
        CsvReader reader = CsvReaderFactory.createCSVReader();
        MyPreparingSQLList prepare = new MyPreparingSQLList();
        CreatingSQLFile createSQLFile = new CreatingSQLFile();

        List<String> varList = reader.csvRead(getFilePath());
        List<String> sqlList = new ArrayList<>(prepare.getSQLStrings(varList, String.valueOf(getColumnSeparator())));
        createSQLFile.create(sqlList, getVersionName());

    }

}