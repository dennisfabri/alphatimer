package de.dennisfabri.alphatimer.api.protocol.events.messages

import de.dennisfabri.alphatimer.api.protocol.events.messages.enums.TimeInfo
import de.dennisfabri.alphatimer.api.protocol.events.messages.enums.TimeMarker

data class DataHandlingMessage2(
    val original: String,
    val lane: Byte,
    val currentLap: Byte,
    val timeInMillis: Int,
    val timeInfo: TimeInfo,
    val timeMarker: TimeMarker
) : Message
