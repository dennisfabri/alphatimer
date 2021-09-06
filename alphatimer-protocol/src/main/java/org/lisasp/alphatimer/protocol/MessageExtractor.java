package org.lisasp.alphatimer.protocol;

import lombok.RequiredArgsConstructor;
import lombok.Synchronized;

import java.util.Optional;

@RequiredArgsConstructor
class MessageExtractor {

    private final ByteStorage storage = new ByteStorage();

    @Synchronized("storage")
    Optional<byte[]> put(byte entry) {
        storage.append(entry);
        return optional(parseMessage());
    }

    private Optional<byte[]> optional(byte[] data) {
        if (data == null || data.length == 0) {
            return Optional.empty();
        }
        return Optional.of(data);
    }

    private byte[] parseMessage() {
        if (storage.endsWithStartOfMessage()) {
            return storage.extractAllButLatestEntry();
        }
        if (storage.endsWithEndOfMessage()) {
            return storage.extractByteArray();
        }
        if (storage.isFull()) {
            return storage.extractByteArray();
        }
        return new byte[0];
    }
    
    Optional<byte[]> shutdown() {
        return optional(storage.extractByteArray());
    }
}
