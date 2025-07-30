/*
 * Created by IntelliJ IDEA.
 * Language: Java
 * Property of Umesh Gunasekara
 * @Author: SLMORA
 * @DateTime: 7/30/2025 9:55 PM
 */
package com.slmora.common.hex;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * The {@code MoraHexUtilities} Class created for Utility class providing various methods to convert strings and byte arrays to and from hexadecimal format.
 *  * Supports Apache Commons, Spring Security Crypto, manual bitwise, and Java built-in approaches.
 * Codes<br>
 * 1 - {@link }<br>
 * Methods<br>
 * <ul>
 *     <li>{@link }</li>
 * </ul>
 * <p>
 * Notes
 * <ul>
 *     <li>....</li>
 * </ul>
 *
 * @author: SLMORA
 * @since 1.0
 *
 * <blockquote><pre>
 * <br>Version      Date            Editor              Note
 * <br>-------------------------------------------------------
 * <br>1.0          7/30/2025      SLMORA                Initial Code
 * </pre></blockquote>
 */
public class MoraHexUtilities
{
    final static Logger LOGGER = LogManager.getLogger(MoraHexUtilities.class);

    private static final char[] HEX_UPPER = "0123456789ABCDEF".toCharArray();
    private static final char[] HEX_LOWER = "0123456789abcdef".toCharArray();

    /**
     * Converts a UTF-8 string to its hexadecimal representation using Apache Commons Codec.
     * @param inputString the input string
     * @return hex-encoded string
     */
    public String convertStringToHexWithApacheCommons(String inputString) {
        char[] chars = org.apache.commons.codec.binary.Hex.encodeHex(inputString.getBytes(StandardCharsets.UTF_8));
        return String.valueOf(chars);
    }

    /**
     * Converts a UTF-8 string to its hexadecimal representation using Apache Commons Codec.
     * @param inputString the input string
     * @param isLowercase the input boolean for set lowercase output
     * @return hex-encoded string
     */
    public String convertStringToHexWithApacheCommons(String inputString, Boolean isLowercase) {
        return org.apache.commons.codec.binary.Hex.encodeHexString(inputString.getBytes(StandardCharsets.UTF_8), isLowercase);
    }

    /**
     * Converts a byte array to a hexadecimal string using Apache Commons Codec.
     * @param bytes byte array
     * @return hex-encoded string
     */
    public String convertByteArrayToHexWithApacheCommons(byte[] bytes) {
        return org.apache.commons.codec.binary.Hex.encodeHexString(bytes);
    }

    /**
     * Decodes a hexadecimal string into a UTF-8 string using Apache Commons Codec.
     * @param hexString hex-encoded string
     * @return decoded string
     */
    public String convertStringToUnHexWithApacheCommons(String hexString) {
        try {
            byte[] bytes = org.apache.commons.codec.binary.Hex.decodeHex(hexString);
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (DecoderException e) {
            LOGGER.error(ExceptionUtils.getStackTrace(e));
            return "";
        }
    }

    /**
     * Decodes a hexadecimal string into a byte array using Apache Commons Codec.
     * @param hexString hex-encoded string
     * @return decoded byte array
     */
    public byte[] convertStringToUnHexByteArrayWithApacheCommons(String hexString) {
        try {
            return org.apache.commons.codec.binary.Hex.decodeHex(hexString);
        } catch (DecoderException e) {
            LOGGER.error(ExceptionUtils.getStackTrace(e));
            return new byte[0];
        }
    }

    /**
     * Converts a string to hexadecimal using Integer wrapper (char to decimal to hex).
     * @param inputString the input string
     * @return hex-encoded string
     */
    public String convertStringToHexWithIntegerWrapper(String inputString) {
        StringBuilder hex = new StringBuilder();
        for (char c : inputString.toCharArray()) {
            // convert int to hex, for decimal 97 hex 61
            hex.append(Integer.toHexString(c));
        }
        return hex.toString();
    }

    /**
     * Converts a hex string to a regular string using Integer wrapper (hex to decimal to char).
     * @param hexString the hex-encoded string
     * @return decoded string
     */
    public String convertStringToUnHexWithIntegerWrapper(String hexString) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < hexString.length() - 1; i += 2) {
            String tempInHex = hexString.substring(i, (i + 2));
            //convert hexString to decimal
            int decimal = Integer.parseInt(tempInHex, 16);
            // convert the decimal to char
            result.append((char) decimal);
        }
        return result.toString();
    }

    /**
     * Converts byte array to hex string using Integer wrapper (byte to int to hex).
     * @param bytes input byte array
     * @return hex string
     */
    public String convertByteArrayToHexWithIntegerWrapper(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte aByte : bytes) {
            int decimal = (int) aByte & 0xff;               // bytes widen to int, need mask, prevent sign extension
            // get last 8 bits
            String hex = Integer.toHexString(decimal);
            if (hex.length() % 2 == 1) {                    // if half hex, pad with zero, e.g \t
                hex = "0" + hex;
            }
            result.append(hex);
        }
        return result.toString();
    }

    /**
     * Converts string to hex using bitwise shifts, optionally in lower/uppercase.
     * @param inputString input string
     * @param lowercase true for lowercase, false for uppercase
     * @return hex string
     */
    public String convertStringToHexWithBitwiseSift(String inputString, boolean lowercase) {
        char[] hexArray = lowercase ? HEX_LOWER : HEX_UPPER;
        byte[] bytes = inputString.getBytes(StandardCharsets.UTF_8);
        // two chars form the hex value.
        char[] hex = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            // 1 byte = 8 bits,
            // upper 4 bits is the first half of hex
            // lower 4 bits is the second half of hex
            // combine both and we will get the hex value, 0A, 0B, 0C
            int v = bytes[j] & 0xFF;               // byte widened to int, need mask 0xff
            // prevent sign extension for negative number
            hex[j * 2] = hexArray[v >>> 4];       // get upper 4 bits
            hex[j * 2 + 1] = hexArray[v & 0x0F];  // get lower 4 bits
        }
        return new String(hex);
    }


    /**
     * Converts byte array to hex using bitwise masking.
     * @param bytes input byte array
     * @param lowercase true for lowercase hex
     * @return hex string
     */
    public String convertByteArrayToHexWithBitwiseSift(byte[] bytes, boolean lowercase) {
        char[] hexArray = lowercase ? HEX_LOWER : HEX_UPPER;
        char[] result = new char[bytes.length * 2];        //  1 hex contains two chars
        //  hex = [0-f][0-f], e.g 0f or ff
        int j = 0;
        for (byte aByte : bytes) {                    // loop byte by byte
            // 0xF0 = FFFF 0000
            result[j++] = hexArray[(0xF0 & aByte) >>> 4];    // get the top 4 bits, first half hex char
            // 0x0F = 0000 FFFF
            result[j++] = hexArray[(0x0F & aByte)];          // get the bottom 4 bits, second half hex char
            // combine first and second half, we get a complete hex
        }
        return String.valueOf(result);

    }

    /**
     * Converts a hex string to byte array using bitwise operations.
     * @param hexString the hex input
     * @param lowercase flag for expected case, affects error handling only
     * @return byte array
     */
    public byte[] convertStringToUnHexByteArrayWithBitwiseSift(String hexString, boolean lowercase) {

        if (hexString.length() % 2 != 0) {
            throw new IllegalArgumentException("Hex string must have even number of characters");
        }
        int nChars = hexString.length();
        byte[] result = new byte[nChars / 2];                                  // 1 hex = 2 char
        for (int i = 0; i < nChars; i += 2) {                                  // step 2, 1 hex = 2 char
            int msb = Character.digit(hexString.charAt(i), 16);                         // char -> hex, base16
            int lsb = Character.digit(hexString.charAt(i + 1), 16);
            if (msb < 0 || lsb < 0) {
                throw new IllegalArgumentException(
                        "Detected a Non-hex character at " + (i + 1) + " or " + (i + 2) + " position");
            }
            result[i / 2] = (byte) ((msb << 4) | lsb);
        }
        return result;

    }

    /**
     * Converts byte array to hex using Java String.format.
     * @param bytes input byte array
     * @return hex string
     */
    public String convertByteArrayToHexWithStringFormat(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte aByte : bytes) {
            result.append(String.format("%02x", aByte));
            // upper case
            // result.append(String.format("%02X", aByte));
        }
        return result.toString();
    }

    /**
     * Encodes a string to hex using Spring Security Crypto Hex class.
     * @param inputString input string
     * @return hex string
     */
    public String convertStringToHexWithSpringCrypto(String inputString) {
        // display in lowercase, default
        char[] chars = org.springframework.security.crypto.codec.Hex.encode(inputString.getBytes(StandardCharsets.UTF_8));
        return String.valueOf(chars);
    }

    /**
     * Converts byte array to hex using Spring Security Crypto.
     * @param bytes input byte array
     * @return hex string
     */
    public String convertByteArrayToHexWithSpringCrypto(byte[] bytes) {
        char[] result = org.springframework.security.crypto.codec.Hex.encode(bytes);
        return new String(result);
    }

    /**
     * Decodes hex string to plain string using Spring Security Crypto.
     * @param hexString hex input
     * @return decoded string
     */
    public String convertStringToUnHexWithSpringCrypto(String hexString) {
        byte[] bytes = org.springframework.security.crypto.codec.Hex.decode(hexString);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * Decodes hex string to byte array using Spring Security Crypto.
     * @param hexString hex input
     * @return decoded byte array
     */
    public byte[] convertStringToUnHexByteArrayWithSpringCrypto(String hexString) {
        byte[] bytes = org.springframework.security.crypto.codec.Hex.decode(hexString);
        return bytes;
    }

    /**
     * Reads file content and prints formatted hex + ASCII view (like hex editor).
     * @param out output stream
     * @param file file to read
     * @throws IOException if file access fails
     */
    public void convertToHex(PrintStream out, File file) throws IOException
    {
        try (InputStream is = new FileInputStream(file)) {

            int bytesCounter = 0;
            int value = 0;
            StringBuilder sbHex = new StringBuilder();
            StringBuilder sbText = new StringBuilder();
            StringBuilder sbResult = new StringBuilder();

            while ((value = is.read()) != -1) {
                //convert to hex value with "X" formatter
                sbHex.append(String.format("%02X ", value));

                //If the chracater is not convertable, just print a dot symbol "."
                sbText.append(Character.isISOControl(value) ? "." : (char) value);

                //if 16 bytes are read, reset the counter,
                //clear the StringBuilder for formatting purpose only.
                if (bytesCounter == 15) {
                    sbResult.append(sbHex).append("      ").append(sbText).append("\n");
                    sbHex.setLength(0);
                    sbText.setLength(0);
                    bytesCounter = 0;
                } else {
                    bytesCounter++;
                }
            }

            //if still got content
            if (bytesCounter != 0) {
                //add spaces more formatting purpose only
                for (; bytesCounter < 16; bytesCounter++) {
                    //1 character 3 spaces
                    sbHex.append("   ");
                }
                sbResult.append(sbHex).append("      ").append(sbText).append("\n");
            }

            out.print(sbResult);
        }
    }
}
