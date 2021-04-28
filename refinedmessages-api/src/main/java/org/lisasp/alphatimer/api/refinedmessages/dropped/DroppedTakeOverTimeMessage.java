package org.lisasp.alphatimer.api.refinedmessages.dropped;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.lisasp.alphatimer.api.protocol.events.messages.DataHandlingMessage;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DroppedTakeOverTimeMessage extends DroppedRefinedMessage {
    public DroppedTakeOverTimeMessage(DataHandlingMessage message) {
        super(message);
    }
}
