package pl.zeto.koszalin.functionalities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;
import java.util.function.Predicate;

import org.apache.log4j.Logger;
import pl.zeto.koszalin.database.DbConnection;

public class ConsoleUI {

    private static final Logger logger = Logger.getLogger(ConsoleUI.class);

    public void StartApplication() throws IOException {

        appName();
        menu();

    }

    void appName() {
        System.out.println("|------------------------------------------------------------|");
        System.out.println("|       Aplikacja do konwersji plików CSV na pliki SQL       |");
        System.out.println("|------------------------------------------------------------|");
    }

    void menu() throws IOException {
        System.out.println("\nWybierz opcję :");
        System.out.println("(0) Pomoc.");
        System.out.println("(1) Przetwarzanie danych z pliku csv.");
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        try {
            choice = scanner.nextInt();
        } catch (Exception e) {
//            System.out.println("Została wybrana nieprawidłowa opcja. Prawidłowy format wyboru to cyfra, np. 1");
            logger.error("Została wybrana nieprawidłowa opcja. Prawidłowy format wyboru to cyfra, np. 1");
        }

        if (choice == 0) {
            help();
        } else if (choice == 1) {
            choiceFileOrDatabase();
        } else {
//            System.out.println("Wybrano niewłaściwą opcję : " + choice + " Właściwa opcja jest ujęta w nawiasie obok wyboru");
            logger.error("Wybrano niewłaściwą opcję : " + choice + " Właściwa opcja jest ujęta w nawiasie obok wyboru");
        }
    }

    void help() {
        System.out.println("Wyświetlam pomoc");
    }

    void choiceFileOrDatabase() throws IOException {
        System.out.println("\nW jaki sposób chcesz przetworzyć dane :");
        System.out.println("(1) Do pliku (.sql)");
        System.out.println("(2) Bezpośrednio do bazy danych");
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        try {
            choice = scanner.nextInt();
        } catch (Exception e) {
//            System.out.println("Została wybrana nieprawidłowa opcja. Prawidłowy format wyboru to cyfra, np. 1");
            logger.error("Została wybrana nieprawidłowa opcja. Prawidłowy format wyboru to cyfra, np. 1");
        }

        if (choice == 1) {
            InsertingIntoRWATable insert = new InsertingIntoRWATable();
            setPath(insert);
            setSeparator(insert);
            setVersionName(insert);

            try {
                insert.DoSequence();
            } catch (IOException e) {
                logger.error("Failed to process csv file and create sql file");
                e.printStackTrace();
                System.exit(0);
            }
        } else if (choice == 2) {
            connectToDatabase();
        } else {
//            System.out.println("Wybrano niewłaściwy sposób przetwarzania danych.");
            logger.error("Wybrano niewłaściwy sposób przetwarzania danych.");
        }
    }

    void setPath(InsertingIntoRWATable insert) {
        System.out.println("\n Podaj ścieżkę do pliku do wczytania : ");
        Scanner scanner = new Scanner(System.in);
        String inputFilePath = scanner.nextLine();

        if (!inputFilePath.contains(File.separator) && !inputFilePath.contains(".") || inputFilePath.contains("\u202A")) {
            logger.error("Wrong path format specified: " + inputFilePath);
            System.exit(0);
        }
        Path path = Paths.get(inputFilePath);
        insert.setFilePath(String.valueOf(path));
    }

    void setSeparator(InsertingIntoRWATable insert) {
        System.out.println("\n Podaj separator w pliku csv : ");
        Scanner scanner = new Scanner(System.in);
        char separator = scanner.nextLine().charAt(0);
        insert.setColumnSeparator(separator);
    }

    void setVersionName(InsertingIntoRWATable insert) {
        System.out.println("\n Podaj nazwę wersji RWA : ");
        Scanner scanner = new Scanner(System.in);
        String versionName = scanner.nextLine();
        if (versionName.isBlank() || versionName == null || versionName.isEmpty()) {
            logger.error("Version name is invalid: " + versionName);
            System.exit(0);
        }
        insert.setVersionName(versionName);
    }

    void connectToDatabase() throws IOException {
        System.out.println("Łączenie z bazą danych poprzez :");
        System.out.println("(1) Plik konfiguracyjny");
        System.out.println("(2) Ręczne podanie wartości");
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        try {
            choice = scanner.nextInt();
        } catch (Exception e) {
            logger.error("Choice for type of Database connection is invalid : " + choice);
        }

        if (choice == 1) {
            connectToDbByExternalFile();
        } else if (choice == 2) {
            connectToDbByPassword();
        } else {
//            System.out.println("Wybrano niełaściwy sposób połączenia do bazy danych : " + choice);
            logger.error("Wybrano niełaściwy sposób połączenia do bazy danych : " + choice);
        }

    }


	void connectToDbByExternalFile() throws IOException {
		System.out.println("Podaj ścieżkę do pliku połączeniowego :");
		Scanner scanner = new Scanner(System.in);
		String path = scanner.nextLine();
		File file = null;
		try {
			file = new File(path);
		} catch (Exception e) {
			logger.error("Invalid file path to configuration database connection file : " + path);
		}

		Properties prop = new Properties();
		prop.load(new FileInputStream(file));
		String url = prop.getProperty("url");
		String name = prop.getProperty("name");
		String password = prop.getProperty("password");

		// DbConnection connect = DbConnection.getInstance();
		// connect.getConnection();
		System.out.println(" URL = " + url + "\n Name = " + name + "\n Password = " + password);
		System.out.println("Połączenie z bazą danych jeszcze nie jest możliwe");

	}

    void connectToDbByPassword() {
        System.out.println("Podaj adres URL do bazy danych ");
        Scanner scanner = new Scanner(System.in);
        String url = scanner.nextLine();
       checkValidDb(url);

        System.out.println("Podaj nazwę użytkownika ");
        scanner = new Scanner(System.in);
        String username = scanner.nextLine();
       checkValidDb(username);

        System.out.println("Podaj hasło ");
        scanner = new Scanner(System.in);
        String password = scanner.nextLine();
        checkValidDb(password);

//         DbConnection connect = DbConnection.getInstance();
//         connect.getConnection();
        System.out.println(" URL = " + url + "\n Name = " + username + "\n Password = " + password);
        System.out.println("Połączenie z bazą danych jeszcze nie jest możliwe");

    }
    void checkValidDb (String value) {
        if (value.isBlank() || value == null || value.isEmpty()) {
            logger.error(" name is invalid: " + value);
            //jak tak to co
        }

    }
}
