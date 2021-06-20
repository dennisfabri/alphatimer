package org.lisasp.alphatimer.livetiming.views.times;

import java.util.Optional;

import org.lisasp.alphatimer.livetiming.data.entity.Heat;
import org.lisasp.alphatimer.livetiming.data.service.HeatService;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;

import org.lisasp.alphatimer.livetiming.views.live.LiveView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import org.lisasp.alphatimer.livetiming.views.main.MainView;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.component.textfield.TextField;

@Route(value = "times/:heatID?/:action?(edit)", layout = MainView.class)
@RouteAlias(value = "times", layout = MainView.class)
@PageTitle("Times")
public class TimesView extends Div implements BeforeEnterObserver {

    private final String HEAT_ID = "heatID";
    private final String HEAT_EDIT_ROUTE_TEMPLATE = "times/%d/edit";

    private Grid<Heat> grid = new Grid<>(Heat.class, false);

    private IntegerField id;
    private IntegerField event;
    private IntegerField heatname;
    private TextField lanes;

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private BeanValidationBinder<Heat> binder;

    private Heat heat;

    private HeatService heatService;

    public TimesView(@Autowired HeatService heatService) {
        this.heatService = heatService;
        addClassNames("times-view", "flex", "flex-col", "h-full");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        // grid.addColumn("id").setAutoWidth(true);
        grid.addColumn("event").setAutoWidth(true);
        grid.addColumn("heat").setAutoWidth(true);
        // grid.addColumn("lanes").setAutoWidth(true);
        grid.setItems(query -> heatService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setSortableColumns();
        grid.setHeightFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(HEAT_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(TimesView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Heat.class);

        // Bind fields. This where you'd define e.g. validation rules
        binder.forField(event).bind("event");
        binder.forField(heatname).bind("heat");

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.heat == null) {
                    this.heat = new Heat();
                }
                binder.writeBean(this.heat);

                // heatService.update(this.heat);
                clearForm();
                refreshGrid();
                Notification.show("Heat details stored.");
                UI.getCurrent().navigate(TimesView.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the heat details.");
            }
        });

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Integer> heatId = event.getRouteParameters().getInteger(HEAT_ID);
        if (heatId.isPresent()) {
            Optional<Heat> heatFromBackend = heatService.get(heatId.get());
            if (heatFromBackend.isPresent()) {
                populateForm(heatFromBackend.get());
            } else {
                Notification.show(String.format("The requested heat was not found, ID = %d", heatId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(TimesView.class);
            }
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("flex flex-col");
        editorLayoutDiv.setWidth("400px");

        Div editorDiv = new Div();
        editorDiv.setClassName("p-l flex-grow");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        id = new IntegerField("Id");
        event = new IntegerField("Event");
        heatname = new IntegerField("Heat");
        lanes = new TextField("Lanes");
        Component[] fields = new Component[]{id, event, heatname, lanes};

        for (Component field : fields) {
            ((HasStyle) field).addClassName("full-width");
        }
        formLayout.add(fields);
        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("w-full flex-wrap bg-contrast-5 py-s px-l");
        buttonLayout.setSpacing(true);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setId("grid-wrapper");
        // wrapper.setWidthFull();
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getLazyDataView().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Heat value) {
        this.heat = value;
        binder.readBean(this.heat);
    }
}
