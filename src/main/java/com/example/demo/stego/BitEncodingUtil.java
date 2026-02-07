package com.example.demo.stego;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class BitEncodingUtil {
    public static List<String> bytesTo2BitSymbols(byte[] data) {

        List<String> symbols = new ArrayList<>();

        for (byte b : data) {
            int unsigned = b & 0xFF; 
            for (int i = 6; i >= 0; i -= 2) {
                int v = (unsigned >> i) & 0b11;
                symbols.add(
                        String.format("%2s",
                                Integer.toBinaryString(v))
                                .replace(' ', '0'));
            }
        }
        return symbols;
    }

    // ================= DECODE =================
    public static byte[] symbolsToBytes(List<String> symbols) {

        if (symbols.size() % 4 != 0) {
            throw new IllegalArgumentException(
                    "Symbol count must be multiple of 4");
        }

        int byteCount = symbols.size() / 4;
        byte[] result = new byte[byteCount];

        for (int i = 0; i < byteCount; i++) {
            int value = 0;
            for (int j = 0; j < 4; j++) {
                value = (value << 2)
                        | Integer.parseInt(
                                symbols.get(i * 4 + j), 2);
            }
            result[i] = (byte) value;
        }
        return result;
    }

    // ================= TEXT =================
    public static String symbolsToText(List<String> symbols) {
        byte[] bytes = symbolsToBytes(symbols);
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
