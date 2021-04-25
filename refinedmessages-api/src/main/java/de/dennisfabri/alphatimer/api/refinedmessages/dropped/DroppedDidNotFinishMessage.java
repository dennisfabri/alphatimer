package de.dennisfabri.alphatimer.api.refinedmessages.dropped;

import de.dennisfabri.alphatimer.api.protocol.events.messages.DataHandlingMessage;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DroppedDidNotFinishMessage extends DroppedRefinedMessage {
    public DroppedDidNotFinishMessage(DataHandlingMessage message) {
        super(message);
    }
}
