package org.lisasp.alphatimer.refinedmessages;

import org.lisasp.alphatimer.api.protocol.events.messages.enums.KindOfTime;
import org.lisasp.alphatimer.api.protocol.events.messages.enums.MessageType;
import org.lisasp.alphatimer.api.protocol.events.messages.enums.TimeType;
import org.lisasp.alphatimer.api.refinedmessages.accepted.enums.RefinedKindOfTime;
import org.lisasp.alphatimer.api.refinedmessages.accepted.enums.RefinedMessageType;
import org.lisasp.alphatimer.api.refinedmessages.accepted.enums.RefinedTimeType;

class EnumUtils {
    RefinedKindOfTime convertKindOfTime(KindOfTime kindOfTime) {
        switch (kindOfTime) {
            case Finish:
                return RefinedKindOfTime.Finish;
            case SplitTime:
                return RefinedKindOfTime.SplitTime;
        }
        throw new IllegalArgumentException();
    }

    RefinedTimeType convertTimeType(TimeType timeType) {
        switch (timeType) {
            case ManualTime:
                return RefinedTimeType.ManualTime;
            case CorrectedTime:
                return RefinedTimeType.CorrectedTime;
            case Empty:
                return RefinedTimeType.Normal;
            case DeletedTime:
                return RefinedTimeType.DeletedTime;
        }
        throw new IllegalArgumentException();
    }

    RefinedMessageType convertMessageType(MessageType messageType) {
        switch (messageType) {
            case CurrentRaceResults:
                return RefinedMessageType.CurrentRaceResult;
            case PreviousRaceResults:
                return RefinedMessageType.PreviousRaceResult;
            case OnLineTime:
                return RefinedMessageType.Live;
        }
        throw new IllegalArgumentException();
    }

}
