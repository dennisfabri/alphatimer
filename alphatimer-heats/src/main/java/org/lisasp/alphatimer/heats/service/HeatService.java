package org.lisasp.alphatimer.heats.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lisasp.alphatimer.api.refinedmessages.RefinedMessage;
import org.lisasp.alphatimer.api.refinedmessages.accepted.OfficialEndMessage;
import org.lisasp.alphatimer.api.refinedmessages.accepted.StartMessage;
import org.lisasp.alphatimer.api.refinedmessages.accepted.TimeMessage;
import org.lisasp.alphatimer.api.refinedmessages.accepted.UsedLanesMessage;
import org.lisasp.alphatimer.api.refinedmessages.dropped.DroppedRefinedMessage;
import org.lisasp.alphatimer.heats.api.enums.HeatStatus;
import org.lisasp.alphatimer.heats.api.HeatDto;
import org.lisasp.alphatimer.heats.domain.Heat;
import org.lisasp.alphatimer.jre.date.DateTimeFacade;
import org.lisasp.alphatimer.messaging.ExceptionCatchingNotifier;
import org.lisasp.alphatimer.messaging.Notifier;

import java.util.function.Consumer;

@RequiredArgsConstructor
@Slf4j
public class HeatService implements Consumer<RefinedMessage> {

    private static final int laneCount = 8;

    private final DataRepository repository;

    private final DateTimeFacade dateTime;

    private Heat heat;

    private HeatDto currentHeat = null;

    private final Notifier<HeatDto> notifier = new ExceptionCatchingNotifier<>();

    private void notifyListeners() {
        if (currentHeat != null) {
            notifier.accept(currentHeat);
        }
    }

    public void register(Consumer<HeatDto> listener) {
        if (currentHeat != null) {
            listener.accept(currentHeat);
        }
        notifier.register(listener);
    }

    @Override
    public void accept(RefinedMessage message) {
        log.info("Received message '{}'.", message);

        if (message.getEvent() <= 0 || message.getHeat() <= 0) {
            return;
        }

        HeatDto heatDto = repository.findHeat(message.getCompetition(),
                                              message.getEvent(),
                                              message.getHeat()).orElseGet(() -> repository.createHeat(message.getCompetition(),
                                                                                                       message.getEvent(),
                                                                                                       message.getHeat(),
                                                                                                       laneCount,
                                                                                                       0,
                                                                                                       0));

        Heat heat = new Heat(heatDto);
        if (message instanceof DroppedRefinedMessage) {
            // Nothing to do
        } else if (message instanceof StartMessage) {
            heat.start((StartMessage) message);
        } else if (message instanceof UsedLanesMessage) {
            heat.usedLanes((UsedLanesMessage) message);
        } else if (message instanceof TimeMessage) {
            heat.touch((TimeMessage) message);
        } else if (message instanceof OfficialEndMessage) {
            heat.finish((OfficialEndMessage) message);
        } else {
            log.warn("Did not apply message '{}'.", message);
        }

        HeatDto updatedHeat = heat.createDto();
        if (!updatedHeat.equals(heatDto)) {
            repository.save(updatedHeat);

            if (updatedHeat.getStatus() != HeatStatus.Open) {
                currentHeat = updatedHeat;
                notifyListeners();
            }
        }
    }
}
