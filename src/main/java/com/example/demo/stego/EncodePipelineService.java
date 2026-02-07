package com.example.demo.stego;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class EncodePipelineService {
    public List<HeatmapLayout> encode(
            byte[] plaintextBytes,
            byte[] aesKey) throws Exception {

        // 1️ Plaintext bytes → 2-bit symbols
        List<String> symbols =
                BitEncodingUtil.bytesTo2BitSymbols(plaintextBytes);

        // 2️ AES-key–driven permutation (security layer)
        List<Integer> permutation =
                TilePermutationUtil.permutePositions(
                        symbols.size(), aesKey);

        // 3️ Generate heatmap layout
        return HeatmapGenerator.generateHeatmaps(
                symbols, permutation);
    }
}
