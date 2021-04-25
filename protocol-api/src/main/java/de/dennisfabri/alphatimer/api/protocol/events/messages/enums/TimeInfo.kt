package de.dennisfabri.alphatimer.api.protocol.events.messages.enums

enum class TimeInfo(val value: Char) {
    Normal(' '),
    Edited('E'),
    Backup('B'),
    Manual('M'),
    UnknownAsterisk('*');
}
