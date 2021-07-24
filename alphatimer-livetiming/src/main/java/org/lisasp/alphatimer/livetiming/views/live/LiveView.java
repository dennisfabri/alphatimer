package org.lisasp.alphatimer.livetiming.views.live;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.lisasp.alphatimer.heats.current.api.HeatDto;
import org.lisasp.alphatimer.heats.current.api.LaneDto;
import org.lisasp.alphatimer.heats.current.service.CurrentHeatService;
import org.lisasp.alphatimer.livetiming.components.TextLabel;
import org.lisasp.alphatimer.livetiming.views.main.MainView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

@Slf4j
@PageTitle("Live")
@Route(value = "", layout = MainView.class)
public class LiveView extends Div {

    private TextLabel name = new TextLabel();
    private TextLabel started = new TextLabel();

    private Grid<LaneDto> grid = new Grid<>(LaneDto.class, false);

    private Binder<HeatDto> binder = new Binder<>(HeatDto.class);

    private CurrentHeatService heatService;

    public LiveView(CurrentHeatService heatService) {
        this.heatService = heatService;

        addClassName("live-view");

        add(createTitle());
        add(createHeatView());
        add(createLaneView());

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
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm dd.MM.yyyy");
                // DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).localizedBy(locale);
                return formatter.format(localDateTime);
            }
        }).bind(HeatDto::getStarted, null);
        binder.bindInstanceFields(this);

        heatService.register(currentHeat -> getUI().ifPresent(ui -> ui.access(() -> {
                                 binder.readBean(currentHeat);
                                 grid.setItems(Arrays.asList(currentHeat.getLanes()));
                             }))
        );
    }

    private Component createTitle() {
        return new H3("Live Timing");
    }

    private Component createHeatView() {
        FormLayout formLayout = new FormLayout();
        formLayout.addFormItem(name, "Heat");
        formLayout.addFormItem(started, "Started");
        // formLayout.add(heat);
        return formLayout;
    }

    private Component createLaneView() {
        // grid.setHeight("100%");
        // grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS);
        // grid.addComponentColumn(lane -> createCard(lane));
        // grid.setSortableColumns("number");
        Grid.Column<LaneDto> number = grid.addColumn(LaneDto::getNumber)
                                          .setFlexGrow(1)
                                          .setHeader("#")
                                          .setSortProperty("number");
        Grid.Column<LaneDto> time = grid.addColumn(LaneDto::getTimeInMillis)
                                          .setFlexGrow(1)
                                          .setHeader("Time");
        Grid.Column<LaneDto> status = grid.addColumn(LaneDto::getStatus)
                                          .setFlexGrow(1)
                                          .setHeader("Status");
        GridSortOrder<LaneDto> order = new GridSortOrder<>(number, SortDirection.ASCENDING);
        return grid;
    }

    private HorizontalLayout createCard(LaneDto person) {
        HorizontalLayout card = new HorizontalLayout();
        card.addClassName("card");
        card.setSpacing(false);
        card.getThemeList().add("spacing-s");

        VerticalLayout description = new VerticalLayout();
        description.addClassName("description");
        description.setSpacing(false);
        description.setPadding(false);

        HorizontalLayout header = new HorizontalLayout();
        header.addClassName("header");
        header.setSpacing(false);
        header.getThemeList().add("spacing-s");

        Span name = new Span("" + person.getNumber());
        name.addClassName("laneNumber");
        Span date = new Span("" + person.getTimeInMillis());
        date.addClassName("time");
        Span status = new Span("" + person.getStatus());
        status.addClassName("status");
        header.add(name, date, status);

        card.add(header);
        return card;
    }
}
