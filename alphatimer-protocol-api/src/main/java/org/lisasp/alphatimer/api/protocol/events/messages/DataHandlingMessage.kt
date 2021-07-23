package org.lisasp.alphatimer.api.protocol.events.messages

import org.lisasp.alphatimer.api.protocol.events.messages.enums.*
import org.lisasp.alphatimer.api.protocol.events.messages.values.UsedLanes
import java.time.LocalDateTime

data class DataHandlingMessage(
    val originalText1: String,
    val originalText2: String,
    val timestamp: LocalDateTime,
    val competition: String,
    val messageType: MessageType,
    val kindOfTime: KindOfTime,
    val timeType: TimeType,
    val usedLanes: UsedLanes,
    val lapCount: Byte,
    val event: Short,
    val heat: Byte,
    val rank: Byte,
    val rankInfo: RankInfo,
    val lane: Byte,
    val currentLap: Byte,
    val timeInMillis: Int, val timeInfo: TimeInfo, val timeMarker: TimeMarker
) : Message
