package com.example.demo.stego;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;


public class HeatmapImageRenderer {

    private static final int TILE_SIZE = 22;   
    private static final int TILE_GAP  = 2;    
    private static final int GRID_WIDTH = 10;  

    public static BufferedImage render(List<HeatmapLayout> layouts) {

        int tileCount = layouts.size();
        int rows = (int) Math.ceil(tileCount / (double) GRID_WIDTH);

        int width =
                GRID_WIDTH * (TILE_SIZE + TILE_GAP) + TILE_GAP;
        int height =
                rows * (TILE_SIZE + TILE_GAP) + TILE_GAP;

        BufferedImage image =
                new BufferedImage(width, height,
                        BufferedImage.TYPE_INT_RGB);

        Graphics2D g = image.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);

        for (int i = 0; i < tileCount; i++) {
            HeatmapLayout layout = layouts.get(i);

            int row = i / GRID_WIDTH;
            int col = i % GRID_WIDTH;

            int x = col * (TILE_SIZE + TILE_GAP) + TILE_GAP;
            int y = row * (TILE_SIZE + TILE_GAP) + TILE_GAP;

            g.setColor(colorFor(layout.getSymbol()));
            g.fillRect(x, y, TILE_SIZE, TILE_SIZE);
        }

        g.dispose();
        return image;
    }

    private static Color colorFor(String s) {
    return switch (s) {
        case "00" -> new Color(255, 0, 0);   // RED
        case "01" -> new Color(0, 90, 255); // BLUE
        case "10" -> new Color(0, 220, 80); // GREEN
        case "11" -> new Color(255, 220, 0);// YELLOW
        default -> Color.BLACK;
    };
    }
}
