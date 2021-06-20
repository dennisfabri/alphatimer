package org.lisasp.alphatimer.api.protocol.events.messages

import org.lisasp.alphatimer.api.protocol.events.messages.enums.TimeInfo
import org.lisasp.alphatimer.api.protocol.events.messages.enums.TimeMarker

data class DataHandlingMessage2(
    val original: String,
    val lane: Byte,
    val currentLap: Byte,
    val timeInMillis: Int,
    val timeInfo: TimeInfo,
    val timeMarker: TimeMarker
) : Message
