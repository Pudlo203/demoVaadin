package com.example.application.views.form;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@PageTitle("Form")
@Route(value = "form", layout = MainLayout.class)
@AnonymousAllowed
public class FormView extends VerticalLayout {

    public FormView() {
        try (BufferedReader br = new BufferedReader(new FileReader("logs/application.log"))) {
            String line;
            while ((line = br.readLine()) != null) {
                Label logLine = new Label(line);
                logLine.getStyle().set("line-height", "1.2"); // css - mniejsze przerwy pomiędzy liniami
                add(logLine);
            }
        } catch (IOException e) {
            System.err.println("Plik coś error");
            System.exit(0);
        }
    }

}