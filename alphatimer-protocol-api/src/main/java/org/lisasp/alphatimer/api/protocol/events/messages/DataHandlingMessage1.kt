package org.lisasp.alphatimer.api.protocol.events.messages

import org.lisasp.alphatimer.api.protocol.events.messages.enums.KindOfTime
import org.lisasp.alphatimer.api.protocol.events.messages.enums.MessageType
import org.lisasp.alphatimer.api.protocol.events.messages.enums.RankInfo
import org.lisasp.alphatimer.api.protocol.events.messages.enums.TimeType
import org.lisasp.alphatimer.api.protocol.events.messages.values.UsedLanes
import java.time.LocalDateTime

data class DataHandlingMessage1(
    val original: String,
    val messageType: MessageType,
    val kindOfTime: KindOfTime,
    val timeType: TimeType,
    val usedLanes: UsedLanes,
    val lapCount: Byte,
    val event: Short, val heat: Byte, val rank: Byte, val rankInfo: RankInfo
) : Message
