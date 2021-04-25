package de.dennisfabri.alphatimer.api.protocol.events.messages

import de.dennisfabri.alphatimer.api.protocol.events.messages.enums.KindOfTime
import de.dennisfabri.alphatimer.api.protocol.events.messages.enums.MessageType
import de.dennisfabri.alphatimer.api.protocol.events.messages.enums.RankInfo
import de.dennisfabri.alphatimer.api.protocol.events.messages.enums.TimeType
import de.dennisfabri.alphatimer.api.protocol.events.messages.values.UsedLanes

data class DataHandlingMessage1(
    val original: String,
    val messageType: MessageType,
    val kindOfTime: KindOfTime,
    val timeType: TimeType,
    val usedLanes: UsedLanes,
    val lapCount: Byte,
    val event: Short, val heat: Byte, val rank: Byte, val rankInfo: RankInfo
) : Message
