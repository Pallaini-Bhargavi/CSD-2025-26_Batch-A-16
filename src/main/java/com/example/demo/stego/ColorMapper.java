package com.example.demo.stego;

import java.awt.Color;
import java.util.Map;

public class ColorMapper {

    private static final Map<String, Color> COLOR_MAP = Map.of(
            "00", new Color(255, 0, 0),     // RED
            "01", new Color(0, 90, 255),    // BLUE
            "10", new Color(0, 220, 80),    // GREEN
            "11", new Color(255, 220, 0)    // YELLOW
    );

    public static Color map(String bits) {
        return COLOR_MAP.getOrDefault(bits, Color.BLACK);
    }
}
