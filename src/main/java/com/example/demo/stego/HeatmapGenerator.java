package com.example.demo.stego;

import java.util.ArrayList;
import java.util.List;

public class HeatmapGenerator {

    // Calendar heatmap capacity
    public static final int TILES_PER_HEATMAP = 365;

    /**
     * Generates one or more heatmaps.
     * - Each heatmap has exactly 365 tiles
     * - Each symbol occupies exactly 1 tile
     * - If symbols exceed 365, new heatmap is created
     */
    public static List<HeatmapLayout> generateHeatmaps(
            List<String> symbols,
            List<Integer> permutation) {

        List<HeatmapLayout> layouts = new ArrayList<>();

        int heatmapIndex = 0;
        int tileIndex = 0;

        for (int i = 0; i < permutation.size(); i++) {

            int originalIndex = permutation.get(i);

            // Safety check
            if (originalIndex >= symbols.size()) {
                continue;
            }

            String symbol = symbols.get(originalIndex);

            layouts.add(
                new HeatmapLayout(
                    heatmapIndex,  
                    tileIndex,      
                    symbol
                )
            );

            tileIndex++;
            if (tileIndex == TILES_PER_HEATMAP) {
                heatmapIndex++;
                tileIndex = 0;
            }
        }

        return layouts;
    }
}
