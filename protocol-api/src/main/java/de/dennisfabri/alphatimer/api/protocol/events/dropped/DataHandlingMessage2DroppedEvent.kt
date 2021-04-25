package de.dennisfabri.alphatimer.api.protocol.events.dropped

import de.dennisfabri.alphatimer.api.protocol.data.Bytes

data class DataHandlingMessage2DroppedEvent(private val message: String, private val data: Bytes) : InputDataDroppedEvent {
    constructor(message: String, data: ByteArray) : this(message, Bytes(data))
}
