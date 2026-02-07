package com.example.demo.stego;

import java.util.ArrayList;
import java.util.List;

public class BitStreamUtil {

    public static List<String> to2BitSymbols(byte[] cipherText) {

        List<String> symbols = new ArrayList<>();

        for (byte b : cipherText) {
            for (int i = 6; i >= 0; i -= 2) {
                int twoBits = (b >> i) & 0b11;
                symbols.add(String.format("%2s",
                        Integer.toBinaryString(twoBits))
                        .replace(' ', '0'));
            }
        }

        System.out.println("2-bit Symbols Count: " + symbols.size());
        return symbols;
    }
}
