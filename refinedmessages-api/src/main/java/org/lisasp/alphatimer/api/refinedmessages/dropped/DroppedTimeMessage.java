package org.lisasp.alphatimer.api.refinedmessages.dropped;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.lisasp.alphatimer.api.protocol.events.messages.DataHandlingMessage;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DroppedTimeMessage extends DroppedRefinedMessage {
    public DroppedTimeMessage(DataHandlingMessage message) {
        super(message);
    }
}
