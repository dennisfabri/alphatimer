package de.dennisfabri.alphatimer.collector.utils;

import de.dennisfabri.alphatimer.collector.exceptions.InvalidDataException;
import lombok.AllArgsConstructor;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@AllArgsConstructor
public class ByteArrayUtils {

    private String toString(byte... data) {
        return new String(data, StandardCharsets.UTF_8);
    }

    public char toCharacter(byte data) {
        return toString(data).charAt(0);
    }

    private static final byte Space = 0x20;
    private static final byte Dot = 0x2E;
    private static final byte Colon = 0x3A;
    private static final byte Plus = 0x2B;
    private static final byte Minus = 0x2D;

    private static final byte[] digits = new byte[]{0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0x39, Space};
    private static final byte[] digitsAndDots = new byte[]{0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0x39, Space, Dot, Colon, Plus, Minus};

    private static final boolean[] allowedDigitsBitmap;
    private static final boolean[] allowedDigitsAndDotsBitmap;

    static {
        allowedDigitsBitmap = new boolean[256];
        for (byte d : digits) {
            allowedDigitsBitmap[d] = true;
        }

        allowedDigitsAndDotsBitmap = new boolean[256];
        for (byte d : digitsAndDots) {
            allowedDigitsAndDotsBitmap[d] = true;
        }
    }


    private boolean hasOnlyDigits(byte... data) {
        for (byte candidate : data) {
            if (!allowedDigitsBitmap[candidate]) {
                return false;
            }
        }
        return true;
    }

    private boolean hasOnlyDigitsAndDots(byte... data) {
        for (byte candidate : data) {
            if (!allowedDigitsAndDotsBitmap[candidate]) {
                return false;
            }
        }
        return true;
    }

    public byte toByteValue(byte digit) throws InvalidDataException {
        try {
            if (digit == 0x20) {
                // Field is unused and interpreted as zero
                return 0;
            }
            if (!hasOnlyDigits(digit)) {
                throw new NumberFormatException("Character not allowed");
            }
            return Byte.parseByte(toString(digit).trim());
        } catch (NumberFormatException nfe) {
            throw new InvalidDataException(
                    String.format("%d must be the ascii-representation of a digit.", digit),
                    nfe);
        }
    }

    public byte toByteValue(byte digit1, byte digit2) throws InvalidDataException {
        try {
            if (digit1 == 0x20 && digit2 == 0x20) {
                // Field is unused and interpreted as zero
                return 0;
            }
            if (!hasOnlyDigits(digit1, digit2)) {
                throw new NumberFormatException("Character not allowed");
            }
            return Byte.parseByte(toString(digit1, digit2).trim());
        } catch (
                NumberFormatException nfe) {
            throw new InvalidDataException(
                    String.format(
                            "%d, %d must be the ascii-representation of a one or two digits number.",
                            digit1,
                            digit2),
                    nfe);
        }
    }

    public short toShortValue(byte digit1, byte digit2, byte digit3) throws InvalidDataException {
        try {
            if (digit1 == 0x20 && digit2 == 0x20 && digit3 == 0x20) {
                // Field is unused and interpreted as zero
                return 0;
            }
            if (!hasOnlyDigits(digit1, digit2, digit3)) {
                throw new NumberFormatException("Character not allowed");
            }
            return Short.parseShort(toString(digit1, digit2, digit3).trim());
        } catch (
                NumberFormatException nfe) {
            throw new InvalidDataException(
                    String.format(
                            "%d, %d, %d must be the ascii-representation of a one to three digits number.",
                            digit1,
                            digit2,
                            digit3),
                    nfe);
        }
    }

    private byte[] extract(byte[] data, int offset, int length) {
        return Arrays.copyOfRange(data, offset, offset + length);
    }

    public String toString(byte[] data, int offset, int length) {
        return toString(extract(data, offset, length));
    }

    public int extractTimeInMillis(byte[] data, int offset, int length) {
        String text = toString(data, offset, length).trim();

        try {
            if (text.equals("")) {
                return 0;
            }
            if (text.equals("-.--")) {
                return 0;
            }
            if (text.equals("+ -.--")) {
                return 0;
            }
            if (text.equals("DNS")) {
                return 0;
            }
            if (text.equals("DNF")) {
                return 0;
            }
            if (text.startsWith("+") || text.startsWith("-")) {
                text = text.substring(1);
            }
            if (text.indexOf('.') < text.indexOf(":")) {
                text = text.replaceFirst("\\.", ":");
            }
            if (!hasOnlyDigitsAndDots(extract(data, offset, length))) {
                throw new NumberFormatException("Unallowed characters");
            }

            int hours = 0;
            int minutes = 0;

            String[] parts = text.split(":");
            if (parts.length > 2) {
                hours = toInt(parts[parts.length - 3]);
            }
            if (parts.length > 1) {
                minutes = toInt(parts[parts.length - 2]);
            }

            return hours * 60 * 60 * 1000 + minutes * 60 * 1000 + toMillis(parts[parts.length - 1]);
        } catch (NumberFormatException nfe) {
            throw new NumberFormatException(String.format("'%s' is not a correct time representation", text));
        }
    }

    private int toMillis(String millis) {
        return (int) Math.round(toDouble(millis) * 1000);
    }

    private int toInt(String integerText) {
        return Integer.parseInt(integerText.trim());
    }

    private double toDouble(String doubleText) {
        return Double.parseDouble(doubleText.trim());
    }
}
