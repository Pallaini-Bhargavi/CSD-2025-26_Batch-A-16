package com.example.demo.stego;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;

import static com.example.demo.stego.HeatmapConstants.GRID_WIDTH;
import static com.example.demo.stego.HeatmapConstants.SYMBOL_COLORS;
import static com.example.demo.stego.HeatmapConstants.TILE_GAP;
import static com.example.demo.stego.HeatmapConstants.TILE_SIZE;

public class HeatmapDecoder {
    public static List<String> extractSymbols(
            MultipartFile image,
            int symbolCount) throws Exception {

        BufferedImage img =
                ImageIO.read(image.getInputStream());

        List<String> symbols = new ArrayList<>(symbolCount);

        int extracted = 0;

        // Total tiles per heatmap = GRID_WIDTH * GRID_HEIGHT
        int tilesPerRow = GRID_WIDTH;

        for (int tileIndex = 0;
             tileIndex < symbolCount;
             tileIndex++) {

            int row = tileIndex / tilesPerRow;
            int col = tileIndex % tilesPerRow;

            int x =
                col * (TILE_SIZE + TILE_GAP)
                + TILE_GAP + TILE_SIZE / 2;

            int y =
                row * (TILE_SIZE + TILE_GAP)
                + TILE_GAP + TILE_SIZE / 2;

            // Safety bounds
            if (x >= img.getWidth() || y >= img.getHeight()) {
                break;
            }

            Color pixel =
                new Color(img.getRGB(x, y));

            symbols.add(colorToSymbol(pixel));
            extracted++;

            if (extracted == symbolCount) {
                break;
            }
        }

        return symbols;
    }

    /**
     * Maps tile color → nearest 2-bit symbol
     */
    private static String colorToSymbol(Color c) {

        int minDistance = Integer.MAX_VALUE;
        int bestIndex = 0;

        for (int i = 0; i < SYMBOL_COLORS.length; i++) {
            Color ref = SYMBOL_COLORS[i];

            int dist =
                Math.abs(c.getRed()   - ref.getRed()) +
                Math.abs(c.getGreen() - ref.getGreen()) +
                Math.abs(c.getBlue()  - ref.getBlue());

            if (dist < minDistance) {
                minDistance = dist;
                bestIndex = i;
            }
        }

        return switch (bestIndex) {
            case 0 -> "00";
            case 1 -> "01";
            case 2 -> "10";
            default -> "11";
        };
    }
}
