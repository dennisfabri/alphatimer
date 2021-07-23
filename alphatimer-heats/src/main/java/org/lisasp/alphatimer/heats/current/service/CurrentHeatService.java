package org.lisasp.alphatimer.heats.current.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lisasp.alphatimer.api.refinedmessages.RefinedMessage;
import org.lisasp.alphatimer.api.refinedmessages.accepted.OfficialEndMessage;
import org.lisasp.alphatimer.api.refinedmessages.accepted.StartMessage;
import org.lisasp.alphatimer.api.refinedmessages.dropped.DroppedRefinedMessage;
import org.lisasp.alphatimer.heats.current.api.HeatDto;
import org.lisasp.alphatimer.heats.current.domain.Heat;
import org.lisasp.alphatimer.jre.date.DateTimeFacade;
import org.lisasp.alphatimer.messaging.ExceptionCatchingNotifier;
import org.lisasp.alphatimer.messaging.Notifier;

import java.util.function.Consumer;

@RequiredArgsConstructor
@Slf4j
public class CurrentHeatService implements Consumer<RefinedMessage> {

    private static final int laneCount = 8;

    private final CurrentDataRepository repository;

    private final DateTimeFacade dateTime;

    private Heat heat;

    private HeatDto currentHeat = null;

    private final Notifier<HeatDto> notifier = new ExceptionCatchingNotifier<>();

    private void notifyListeners() {
        notifier.accept(currentHeat);
    }

    public void register(Consumer<HeatDto> listener) {
        if (currentHeat != null) {
            listener.accept(currentHeat);
        }
        notifier.register(listener);
    }

    @Override
    public void accept(RefinedMessage message) {
        HeatDto heatDto = repository.findHeat(message.getCompetition(), message.getEvent(), message.getHeat()).orElseGet(() -> repository.createHeat(message.getCompetition(), message.getEvent(), message.getHeat(), message.getTimestamp(), laneCount));

        Heat heat = new Heat(heatDto);
        if (message instanceof DroppedRefinedMessage) {
            // Nothing to do
        } else if (message instanceof StartMessage) {
            heat.apply((StartMessage) message);
        } else if (message instanceof OfficialEndMessage) {
            heat.apply((OfficialEndMessage) message);
        } else {
            log.warn("Did not apply message '%s'.", message);
        }

        HeatDto updatedHeat = heat.createDto();
        if (!updatedHeat.equals(currentHeat)) {
            currentHeat = updatedHeat;

            repository.save(currentHeat);
            notifyListeners();
        }
    }
}
