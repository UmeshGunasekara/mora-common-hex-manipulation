/*
 * Created by IntelliJ IDEA.
 * Language: Java
 * Property of Umesh Gunasekara
 * @Author: SLMORA
 * @DateTime: 7/31/2025 12:32 AM
 */
package com.slmora.common.hex;

import com.slmora.common.hex.MoraHexUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The {@code MoraHexUtilitiesTest} Class created for
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
 * <br>1.0          7/31/2025      SLMORA                Initial Code
 * </pre></blockquote>
 */
public class MoraHexUtilitiesTest
{
    private MoraHexUtilities util;
    private final String sampleText = "Hello";
    private final byte[] sampleBytes = sampleText.getBytes(StandardCharsets.UTF_8);
    private final String sampleHex = "48656c6c6f";

    @BeforeEach
    void setUp() {
        util = new MoraHexUtilities();
    }

    @Test
    void testConvertStringToHexWithApacheCommons() {
        assertEquals(sampleHex, util.convertStringToHexWithApacheCommons(sampleText));
    }

    @Test
    void testConvertByteArrayToHexWithApacheCommons() {
        assertEquals(sampleHex, util.convertByteArrayToHexWithApacheCommons(sampleBytes));
    }

    @Test
    void testConvertStringToUnHexWithApacheCommons() {
        assertEquals(sampleText, util.convertStringToUnHexWithApacheCommons(sampleHex));
    }

    @Test
    void testConvertStringToUnHexByteArrayWithApacheCommons() {
        assertArrayEquals(sampleBytes, util.convertStringToUnHexByteArrayWithApacheCommons(sampleHex));
    }

    @Test
    void testConvertStringToHexWithIntegerWrapper() {
        assertEquals(sampleHex, util.convertStringToHexWithIntegerWrapper(sampleText));
    }

    @Test
    void testConvertStringToUnHexWithIntegerWrapper() {
        assertEquals(sampleText, util.convertStringToUnHexWithIntegerWrapper(sampleHex));
    }

    @Test
    void testConvertByteArrayToHexWithIntegerWrapper() {
        assertEquals(sampleHex, util.convertByteArrayToHexWithIntegerWrapper(sampleBytes));
    }

    @Test
    void testConvertStringToHexWithBitwiseSift() {
        assertEquals(sampleHex, util.convertStringToHexWithBitwiseSift(sampleText, true));
    }

    @Test
    void testConvertByteArrayToHexWithBitwiseSift() {
        assertEquals(sampleHex, util.convertByteArrayToHexWithBitwiseSift(sampleBytes, true));
    }

    @Test
    void testConvertStringToUnHexByteArrayWithBitwiseSift() {
        assertArrayEquals(sampleBytes, util.convertStringToUnHexByteArrayWithBitwiseSift(sampleHex, true));
    }

    @Test
    void testConvertByteArrayToHexWithStringFormat() {
        assertEquals(sampleHex, util.convertByteArrayToHexWithStringFormat(sampleBytes));
    }

    @Test
    void testConvertStringToHexWithSpringCrypto() {
        assertEquals(sampleHex, util.convertStringToHexWithSpringCrypto(sampleText));
    }

    @Test
    void testConvertByteArrayToHexWithSpringCrypto() {
        assertEquals(sampleHex, util.convertByteArrayToHexWithSpringCrypto(sampleBytes));
    }

    @Test
    void testConvertStringToUnHexWithSpringCrypto() {
        assertEquals(sampleText, util.convertStringToUnHexWithSpringCrypto(sampleHex));
    }

    @Test
    void testConvertStringToUnHexByteArrayWithSpringCrypto() {
        assertArrayEquals(sampleBytes, util.convertStringToUnHexByteArrayWithSpringCrypto(sampleHex));
    }

//    @Test
//    void testConvertToHex() throws Exception {
//        File tempFile = File.createTempFile("testfile", ".txt");
//        tempFile.deleteOnExit();
//        try (PrintStream ps = new PrintStream(tempFile)) {
//            ps.print(sampleText);
//        }
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        util.convertToHex(new PrintStream(outputStream), tempFile);
//        assertTrue(outputStream.toString().contains("48 65 6C 6C 6F"));
//    }

}
