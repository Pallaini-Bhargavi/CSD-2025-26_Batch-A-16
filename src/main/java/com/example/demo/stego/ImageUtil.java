package com.example.demo.stego;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ImageUtil {

    public static File saveHeatmap(BufferedImage image, String fileName)
            throws Exception {

        File output = new File("generated/" + fileName);
        output.getParentFile().mkdirs();

        ImageIO.write(image, "png", output);
        return output;
    }
}
