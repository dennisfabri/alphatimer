package org.lisasp.alphatimer.api.refinedmessages.dropped;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.lisasp.alphatimer.api.protocol.events.messages.DataHandlingMessage;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DroppedReadyToStartMessage extends DroppedRefinedMessage {
    public DroppedReadyToStartMessage(DataHandlingMessage message) {
        super(message);
    }
}
