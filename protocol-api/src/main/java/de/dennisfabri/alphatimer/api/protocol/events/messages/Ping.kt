package de.dennisfabri.alphatimer.api.protocol.events.messages

import de.dennisfabri.alphatimer.api.protocol.data.Bytes

data class Ping(val data: Bytes) : Message {
    constructor(data: ByteArray) : this(Bytes(data))
}
