package com.example.application.views.newPage;

import com.example.application.data.sprawy.Sprawy;
import com.example.application.data.sprawy.SprawyRepository;
import com.example.application.data.sprawy.SprawyService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.apache.log4j.Logger;
import com.vaadin.flow.component.dialog.Dialog;


import java.awt.*;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


@AnonymousAllowed
@PageTitle("NewPage")
@Route(value = "newpage", layout = MainLayout.class)
public class NewPage extends VerticalLayout {
    private static final Logger logger = Logger.getLogger(NewPage.class);

    public NewPage() {
        Grid<Sprawy> grid = new Grid<>(Sprawy.class, false);
        grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
        grid.addColumn(Sprawy::getId).setHeader("Id");
        grid.addColumn(Sprawy::getNrSprawy).setHeader("Numer sprawy");
        grid.addColumn(Sprawy::getTemat).setHeader("Temat");
        grid.addColumn(Sprawy::getData).setHeader("Data");

        grid.addComponentColumn(sprawy -> {
            Button button = new Button("Szczegóły");
            button.addClickListener(event -> {
                Dialog dialog = new Dialog();
                Optional<Sprawy> sprawa = SprawyService.get(sprawy.getId());
                dialog.setCloseOnEsc(true);
                dialog.setCloseOnOutsideClick(true);

                VerticalLayout layout = new VerticalLayout();
                layout.add((Collection<Component>) new Label("ID: " + sprawa.get().getId()));
                layout.add((Collection<Component>) new Label("Numer sprawy: " + sprawa.get().getNrSprawy()));
                layout.add((Collection<Component>) new Label("Temat: " + sprawa.get().getTemat()));
                layout.add((Collection<Component>) new Label("Data: " + sprawa.get().getData()));

                layout.setPadding(false);
                layout.setAlignItems(FlexComponent.Alignment.STRETCH);
                layout.getStyle().set("min-width", "800px")
                        .set("max-width", "100%").set("height", "100%");

                dialog.add(layout);
                dialog.open();
                dialog.setDraggable(true);
                dialog.setResizable(true);
            });
            return button;
        }).setHeader("Szczegóły");

        //
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        grid.addItemClickListener(event ->
                logger.debug(("Clicked Item: " + event.getItem())));


        List<Sprawy> sprawyList = SprawyService.getSprawy();
        grid.setItems(sprawyList);
        add(grid);
//        setAlignItems(Alignment.CENTER);
        // Wyłączenie trybu "bez głowy"
//        UI.getCurrent().getPage().executeJs("window.close = function() { window.history.back(); }");
    }

}
