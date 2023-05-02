package com.example.application.views.dialogs;

import com.example.application.data.sprawy.SprawaServiceJDBC;
import com.example.application.data.sprawy.Sprawy;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@AnonymousAllowed
@PageTitle("Zad")
@Route(value = "dialog", layout = MainLayout.class)
public class Zad extends FormLayout {

    private TextField numerSprawy = new TextField("Numer sprawy");
    private TextField tytul = new TextField("Tytuł");
    private DateTimePicker data = new DateTimePicker("Data i godzina");
    private SprawaServiceJDBC sprawaServiceJDBC;

    private Button save = new Button("Zapisz");

    public Zad() {
        add(numerSprawy, tytul, data, save);
        save.addClickListener(event -> save());

//        ComboBox<String> comboBox = new ComboBox<>("Browser");
//        comboBox.setAllowCustomValue(true);
//        comboBox.setItems("Chrome", "Edge", "Firefox", "Safari");
//        comboBox.setHelperText("Select or type a browser");
//
//        Checkbox checkbox = new Checkbox();
//        checkbox.setLabel("I accept");
//
//        add(checkbox, comboBox);
    }

    private void save() {
        Sprawy sprawa = new Sprawy(numerSprawy.getValue(), tytul.getValue(), data.getValue());
        sprawaServiceJDBC.save(sprawa);
        numerSprawy.clear();
        tytul.clear();
        data.clear();
    }
}
