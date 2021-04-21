package de.dennisfabri.alphatimer.collector;

public class Characters {

    public static final byte SOH_StartOfHeader = 0x01;
    public static final byte STX_StartOfText = 0x02;
    public static final byte EOT_EndOfText = 0x04;
    public static final byte BS_CursorHome = 0x08;
    public static final byte LF_LineFeed = 0x0A;
    public static final byte ERL_EraseLine = 0x0B;
    public static final byte ERP_ErasePage = 0x0C;
    public static final byte DLE_Position = 0x10;
    public static final byte DC1_ = 0x11;
    public static final byte DC2_Periphery = 0x12;
    public static final byte DC3_Identification = 0x13;
    public static final byte DC4_Command = 0x14;
    public static final byte SPACE = 0x20;
    public static final byte FieldSeparator = 0x7C;
}
