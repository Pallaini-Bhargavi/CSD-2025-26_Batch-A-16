package com.example.demo.stego;

public class HeatmapLayout {

    private final int heatmapIndex; 
    private final int tileIndex;    
    private final String symbol;

    public HeatmapLayout(
            int heatmapIndex,
            int tileIndex,
            String symbol) {

        this.heatmapIndex = heatmapIndex;
        this.tileIndex = tileIndex;
        this.symbol = symbol;
    }

    public int getHeatmapIndex() {
        return heatmapIndex;
    }

    public int getTileIndex() {
        return tileIndex;
    }

    public String getSymbol() {
        return symbol;
    }
}
