package org.lisasp.alphatimer.test.serial;

class TestUtil {
    static boolean isTestWithSerialHardwareEnabled() {
        return System.getProperty("alphatimer.runTestsWithSerialHardware", "false").equalsIgnoreCase("true");
    }
}
