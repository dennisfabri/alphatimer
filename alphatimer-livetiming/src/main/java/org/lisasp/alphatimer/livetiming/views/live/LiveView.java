package org.lisasp.alphatimer.livetiming.views.live;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import org.apache.commons.lang3.NotImplementedException;
import org.lisasp.alphatimer.heats.current.entity.LaneEntity;
import org.lisasp.alphatimer.heats.current.service.CurrentHeatService;
import org.lisasp.alphatimer.heats.current.api.HeatDto;
import org.lisasp.alphatimer.livetiming.components.TextLabel;
import org.lisasp.alphatimer.livetiming.views.main.MainView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Route(value = "", layout = MainView.class)
@PageTitle("Live")
public class LiveView extends Div {

    private TextLabel name = new TextLabel();
    private TextLabel started = new TextLabel();

    private Grid<LaneEntity> grid = new Grid<>(LaneEntity.class, false);

    private Binder<HeatDto> binder = new Binder<>(HeatDto.class);

    private CurrentHeatService heatService;

    public LiveView(CurrentHeatService heatService) {
        this.heatService = heatService;

        addClassName("live-view");

        add(createTitle());
        add(createFormLayout());

        binder.forField(name).bind(HeatDto::createName, null);
        binder.forField(started).withConverter(new Converter<String, LocalDateTime>() {
            @Override
            public Result<LocalDateTime> convertToModel(String s, ValueContext valueContext) {
                throw new NotImplementedException();
            }

            @Override
            public String convertToPresentation(LocalDateTime localDateTime, ValueContext valueContext) {
                if (localDateTime == null) {
                    return "";
                }
                Locale locale = Locale.getDefault();
                if (VaadinService.getCurrentRequest() != null) {
                    locale = VaadinService.getCurrentRequest().getLocale();
                }
                DateTimeFormatter formatter =DateTimeFormatter.ofPattern("hh:mm dd.MM.yyyy");
                // DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).localizedBy(locale);
                return formatter.format(localDateTime);
            }
        }).bind(HeatDto::getStarted, null);
        binder.bindInstanceFields(this);

        heatService.register(currentHeat -> getUI().ifPresent(ui -> ui.access(() -> binder.readBean(currentHeat))));
    }

    private Component createTitle() {
        return new H3("Live Timing");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.addFormItem(name, "Heat");
        formLayout.addFormItem(started, "Started");
        // formLayout.add(heat);
        return formLayout;
    }
}
