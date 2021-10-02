package org.lisasp.alphatimer.livetiming.views.live;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
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
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.lisasp.alphatimer.heats.api.HeatDto;
import org.lisasp.alphatimer.heats.api.LaneDto;
import org.lisasp.alphatimer.heats.service.HeatService;
import org.lisasp.alphatimer.livetiming.components.TextLabel;
import org.lisasp.alphatimer.livetiming.model.HeatModel;
import org.lisasp.alphatimer.livetiming.model.LaneModel;
import org.lisasp.alphatimer.livetiming.views.main.MainView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;

@Slf4j
@PageTitle("Live")
@Route(value = "", layout = MainView.class)
public class LiveView extends Div {

    private final TextLabel event = new TextLabel();
    private final TextLabel heat = new TextLabel();
    private final TextLabel started = new TextLabel();

    private HeatDto currentHeatDto;

    private final Grid<LaneModel> grid = new Grid<>(LaneModel.class, false);

    private final Binder<HeatModel> binder = new Binder<>(HeatModel.class);

    private final HeatService heatService;

    public LiveView(HeatService heatService) {
        this.heatService = heatService;

        addClassName("live-view");

        add(createTitle());
        add(createHeatView());
        add(createLaneView());

        binder.forField(event).bind(HeatModel::getEvent, null);
        binder.forField(heat).bind(HeatModel::getHeat, null);
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
        }).bind(HeatModel::getStarted, null);
        binder.bindInstanceFields(this);

        heatService.register(heatDto -> {
                                 currentHeatDto = heatDto;
                                 updateData();
                             }
        );

        addAttachListener(attachEvent -> {
            updateData();
        });
    }

    private void updateData() {
        if (currentHeatDto == null) {
            return;
        }
        getUI().ifPresent(ui -> ui.access(() -> {
            HeatModel currentHeat = new HeatModel(currentHeatDto);
            binder.readBean(currentHeat);
            grid.setItems(Arrays.asList(Arrays.stream(currentHeatDto.getLanes()).map(lane -> new LaneModel(currentHeatDto.getEvent(),
                                                                                                           currentHeatDto.getHeat(),
                                                                                                           currentHeatDto.getLapCount(),
                                                                                                           lane)).sorted(Comparator.comparingInt(LaneModel::getNumber)).toArray(
                    LaneModel[]::new)));
        }));
    }

    private Component createTitle() {
        return new H3("Live Timing");
    }

    private Component createHeatView() {
        FormLayout formLayout = new FormLayout();
        formLayout.addFormItem(event, "Event");
        formLayout.addFormItem(heat, "Heat");
        formLayout.addFormItem(started, "Started");

        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("1em", 1),
                new FormLayout.ResponsiveStep("40em", 2),
                new FormLayout.ResponsiveStep("60em", 3));
        return formLayout;
    }

    private Component createLaneView() {
        Grid.Column<LaneModel> number = grid.addColumn(LaneModel::getNumber)
                                            .setFlexGrow(1)
                                            .setHeader("#")
                                            .setSortProperty("number");
        Grid.Column<LaneModel> name = grid.addColumn(LaneModel::getName)
                                          .setFlexGrow(2)
                                          .setHeader("Name");
        Grid.Column<LaneModel> time = grid.addColumn(LaneModel::getTime)
                                          .setFlexGrow(1)
                                          .setHeader("Time");
        Grid.Column<LaneModel> status = grid.addColumn(LaneModel::getStatus)
                                            .setFlexGrow(1)
                                            .setHeader("Status");
        GridSortOrder<LaneModel> order = new GridSortOrder<>(number, SortDirection.ASCENDING);
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
