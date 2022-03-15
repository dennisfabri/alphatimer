package org.lisasp.alphatimer.api.serial.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class SerialConfiguration {

    public static final int BAUD_9600 = 9600;
    public static final int BAUD_14400 = 14400;
    public static final int BAUD_19200 = 19200;
    public static final int BAUD_28800 = 28800;
    public static final int BAUD_38400 = 38400;
    public static final int BAUD_57600 = 57600;
    public static final int BAUD_115200 = 115200;
    public static final int BAUD_256000 = 256000;
    public static final int BAUD_512000 = 512000;

    public static final SerialConfiguration ARES21 = new SerialConfiguration(BAUD_9600,
                                                                             DataBits.Seven,
                                                                             StopBits.One,
                                                                             Parity.Even);
    public static final SerialConfiguration Quantum = new SerialConfiguration(BAUD_9600,
                                                                              DataBits.Eight,
                                                                              StopBits.One,
                                                                              Parity.None);
    public static final SerialConfiguration TEST = new SerialConfiguration(BAUD_512000,
                                                                           DataBits.Seven,
                                                                           StopBits.One,
                                                                           Parity.Even);

    private final int baud;
    private final DataBits databits;
    private final StopBits stopbits;
    private final Parity parity;

}
