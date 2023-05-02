package com.example.application.views.about;

import com.example.application.functionalities.CreatingSQLFile;
import com.example.application.functionalities.InsertingIntoRWATable;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@PageTitle("About")
@Route(value = "about", layout = MainLayout.class)
@AnonymousAllowed
public class AboutView extends VerticalLayout {
    private TextField filePathField;
    private ComboBox<String> separatorComboBox;
    private TextField textField;
    private Div sqlFileDiv;
    private static final Logger logger = Logger.getLogger(AboutView.class);
    private InsertingIntoRWATable insert;
    private CreatingSQLFile creatingSQLFile;

    public AboutView() {
        // Inicjalizacja pól formularza
        filePathField = new TextField("Podaj ścieżkę do pliku do wczytania");
        separatorComboBox = new ComboBox<>("Separator", Arrays.asList(";", ",", ".", ":", "=", "\t"));
        textField = new TextField("Nazwa RWA");
        sqlFileDiv = new Div();

        // Ustawienie nazwy pliku SQL na pustą wartość
        sqlFileDiv.setText("Plik SQL:");

        // Dodanie pól do formularza
        add(filePathField, separatorComboBox, textField, sqlFileDiv);

        // Dodanie przycisku do obsługi formularza
        Button submitButton = new Button("Pobierz plik SQL");
        submitButton.addClickListener(e -> {
            try {
                setPath(filePathField.getValue());
//                setSeparator(separatorComboBox.getValue());
                setVersionName(textField.getValue());
                generateValidSQLFile();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        add(submitButton);
    }
    private void generateValidSQLFile() throws IOException {
        // Pobranie wartości pól formularza
        String filePath = filePathField.getValue();
        String separator = separatorComboBox.getValue();
        String text = textField.getValue();

        // Walidacja ścieżki pliku
        if (filePath == null || filePath.isEmpty()) {
            Notification.show("Wprowadź ścieżkę pliku");
            return;
        }

        // Walidacja separatora
        if (separator == null || separator.isEmpty()) {
            Notification.show("Wybierz separator");
            return;
        }

        // Walidacja tekstu
        if (text == null || text.isEmpty()) {
            Notification.show("Wprowadź tekst");
            return;
        }

        // Odczytanie pliku
        File file = new File(filePath);
        if (!file.exists()) {
            Notification.show("Plik nie istnieje");
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(separator);
                List<String> escapedValues = escapeValues(values);
                String row = String.join(",", escapedValues) + "\n";
                stringBuilder.append(row);
            }
        } catch (IOException e) {
            Notification.show("Błąd odczytu pliku");
        }
        CreatingSQLFile creatingSQLFile1 = new CreatingSQLFile();
        creatingSQLFile1.locationOfSavingFiles.getFileName();
        sqlFileDiv.removeAll();
        //

//        sqlFileDiv.add(creatingSQLFile1.create(Collections.singletonList(filePathField.getValue()),textField.getValue()));

    }
    void  setPath(String value) {
        String filePath = filePathField.getValue();
        if (!filePath.contains(File.separator) && !filePath.contains(".") || filePath.contains("\u202A")) {
            logger.error("Wrong path format specified: " + filePath);
//            System.exit(0);
        }
    }

//    void setSeparator() {
//        String separator = separatorComboBox.getValue();
//
//    }

   void setVersionName(String value) {
        String text = textField.getValue();
        if (text.isBlank() || text == null || text.isEmpty()) {
            logger.error("Version name is invalid: " + text);
//            System.exit(0);
        }
    }

    private List<String> escapeValues(String[] values) {
        return Arrays.asList(values).stream()
                .map(value -> "'" + value +"'")
                .toList();
    }

}
