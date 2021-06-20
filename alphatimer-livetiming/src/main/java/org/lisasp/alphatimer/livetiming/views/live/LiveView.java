package org.lisasp.alphatimer.livetiming.views.live;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.lisasp.alphatimer.livetiming.components.TextLabel;
import org.lisasp.alphatimer.livetiming.data.entity.Heat;
import org.lisasp.alphatimer.livetiming.data.entity.Lane;
import org.lisasp.alphatimer.livetiming.data.service.CurrentHeatService;
import org.lisasp.alphatimer.livetiming.data.service.HeatService;
import org.lisasp.alphatimer.livetiming.views.main.MainView;

@Route(value = "", layout = MainView.class)
@PageTitle("Live")
public class LiveView extends Div {

    private TextLabel name = new TextLabel();

    private Grid<Lane> grid = new Grid<>(Lane.class, false);

    private Binder<Heat> binder = new Binder<>(Heat.class);

    private CurrentHeatService heatService;

    public LiveView(CurrentHeatService heatService) {
        this.heatService = heatService;

        addClassName("live-view");

        add(createTitle());
        add(createFormLayout());

        binder.bindInstanceFields(this);

        clearForm();

        heatService.subscribe(currentHeat -> getUI().ifPresent(ui -> ui.access(() -> binder.readBean(currentHeat))));
    }

    private Component createTitle() {
        return new H3("Live Timing");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.addFormItem(name, "Heat");
        // formLayout.add(heat);
        return formLayout;
    }

    private void clearForm() {

        this.binder.setBean(heatService.getCurrentHeat());
    }
}
