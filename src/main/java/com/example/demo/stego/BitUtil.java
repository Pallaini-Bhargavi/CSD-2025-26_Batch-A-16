package com.example.demo.stego;

import java.util.List;

public class BitUtil {
    public static List<String> to2BitSymbols(byte[] cipherText) {
        return BitStreamUtil.to2BitSymbols(cipherText);
    }
}
