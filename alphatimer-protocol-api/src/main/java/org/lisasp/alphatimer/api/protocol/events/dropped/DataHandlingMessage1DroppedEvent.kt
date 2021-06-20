package org.lisasp.alphatimer.api.protocol.events.dropped

import org.lisasp.alphatimer.api.protocol.data.Bytes

data class DataHandlingMessage1DroppedEvent(private val message: String, private val data: Bytes) : InputDataDroppedEvent {
    constructor(message: String, data: ByteArray) : this(message, Bytes(data))
}
