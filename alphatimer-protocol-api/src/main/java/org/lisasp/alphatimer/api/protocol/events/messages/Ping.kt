package org.lisasp.alphatimer.api.protocol.events.messages

import org.lisasp.alphatimer.api.protocol.data.Bytes

data class Ping(val data: Bytes) : Message {
    constructor(data: ByteArray) : this(Bytes(data))
}
