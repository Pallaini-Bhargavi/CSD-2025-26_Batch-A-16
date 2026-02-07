package com.example.demo.stego;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service  
public class DecodePipelineService {

    public String decode(
            MultipartFile image,
            byte[] aesKey,
            int symbolCount) throws Exception {

        // 1️ Extract symbols from heatmap image
        List<String> extractedSymbols =
                HeatmapDecoder.extractSymbols(
                        image, symbolCount);

        // 2️ Reverse permutation (AES-key driven)
        List<String> originalOrder =
                TilePermutationUtil.inversePermute(
                        extractedSymbols, aesKey);

        // 3️ Symbols → raw bytes
        byte[] recoveredBytes =
                BitEncodingUtil.symbolsToBytes(
                        originalOrder);

        // 4 Convert bytes → UTF-8 text
        return new String(
                recoveredBytes,
                java.nio.charset.StandardCharsets.UTF_8);
    }
}
