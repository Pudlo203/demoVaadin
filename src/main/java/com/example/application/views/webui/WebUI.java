package com.example.application.views.webui;

import com.example.application.functionalities.InsertingIntoRWATable;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.io.IOException;

@PageTitle("WEBUI")
@Route(value = "webui", layout = MainLayout.class)
@AnonymousAllowed
public class WebUI  extends VerticalLayout {
    private TextField filePathField;
    private TextField separatorField;
    private TextField versionNameField;
    private Checkbox connectToDbCheckbox;
    private Button submitButton;

    public WebUI() {
        H1 title = new H1("Aplikacja do konwersji plików CSV na pliki SQL");

        FormLayout form = new FormLayout();
        form.addFormItem(filePathField = new TextField("Ścieżka do pliku"), "filePath");
        form.addFormItem(separatorField = new TextField("Separator w pliku csv"), "separator");
        form.addFormItem(versionNameField = new TextField("Nazwa wersji RWA"), "versionName");
//        form.addFormItem(connectToDbCheckbox = new Checkbox("Połącz z bazą danych"), "connectToDb");
        form.addFormItem(submitButton = new Button("Przetwórz plik"), "submitButton");

        submitButton.addClickListener(event -> {
            if (connectToDbCheckbox.getValue()) {
//                connectToDatabase();
            } else {
                try {
                    convertFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        add(title, form);
        setAlignItems(Alignment.CENTER);
    }

    private void convertFile() throws IOException {
        String filePath = filePathField.getValue();
        String separator = separatorField.getValue();
        String versionName = versionNameField.getValue();

        // wykonaj konwersję pliku
        InsertingIntoRWATable table = new InsertingIntoRWATable();
        table.DoSequence();
    }

//    private void connectToDatabase() {
//        Dialog dialog = new Dialog();
//        FormLayout form = new FormLayout();
//        TextField serverField = new TextField("Adres serwera");
//        TextField portField = new TextField("Port");
//        TextField dbNameField = new TextField("Nazwa bazy danych");
//        TextField usernameField = new TextField("Nazwa użytkownika");
//        PasswordField passwordField = new PasswordField("Hasło");
//        form.addFormItem(serverField, "server");
//        form.addFormItem(portField, "port");
//        form.addFormItem(dbNameField, "dbName");
//        form.addFormItem(usernameField, "username");
//        form.addFormItem(passwordField, "password");
//
//        Button connectButton = new Button("Połącz");
//        connectButton.addClickListener(event -> {
//            String server = serverField.getValue();
//            String port = portField.getValue();
//            String dbName = dbNameField.getValue();
//            String username = usernameField.getValue();
//            String password = passwordField.getValue();
//
//            // nawiąż połączenie z bazą danych
//            dialog.close();
//        });
//
//        dialog.add(form, connectButton);
//        dialog.open();
//    }

}
