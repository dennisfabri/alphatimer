package org.lisasp.alphatimer.api.protocol.events;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.lisasp.alphatimer.api.protocol.Characters;

import java.time.LocalDateTime;

@Value
@RequiredArgsConstructor
public class BytesInputEvent implements DataInputEvent {

    private final LocalDateTime timestamp;
    private final String competition;
    private final byte[] data;

    public boolean checkIfMessage() {
        if (!reachesMinimumMessageLength()) {
            return false;
        }
        if (!startsWithStartOfMessage()) {
            return false;
        }
        return endsWithEndOfMessage();
    }

    private boolean reachesMinimumMessageLength() {
        return data.length > 2;
    }

    private byte firstEntry() {
        return data[0];
    }

    private byte lastEntry() {
        return data[data.length - 1];
    }

    private boolean startsWithStartOfMessage() {
        return firstEntry() == Characters.SOH_StartOfHeader;
    }

    private boolean endsWithEndOfMessage() {
        return lastEntry() == Characters.EOT_EndOfText;
    }
}
