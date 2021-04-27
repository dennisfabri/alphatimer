package org.lisasp.alphatimer.serial;

public class TestUtil {
    static boolean isTestWithSerialHardwareEnabled() {
        return System.getProperty("alphatimer.runTestsWithSerialHardware", "false").equalsIgnoreCase("true");
    }
}
