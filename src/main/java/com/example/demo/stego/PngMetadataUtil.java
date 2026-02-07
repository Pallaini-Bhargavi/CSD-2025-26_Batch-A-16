package com.example.demo.stego;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;

import org.w3c.dom.NodeList;

public class PngMetadataUtil {

    private static final String FORMAT = "javax_imageio_png_1.0";
    private static final String TEXT_ENTRY = "tEXtEntry";

    public static byte[] writeWithMetadata(
            BufferedImage image,
            String senderPublicKeyBase64,
            int symbolCount) throws Exception {

        ImageWriter writer =
                ImageIO.getImageWritersByFormatName("png").next();

        ImageWriteParam param = writer.getDefaultWriteParam();
        ImageTypeSpecifier type =
                ImageTypeSpecifier.createFromBufferedImageType(
                        BufferedImage.TYPE_INT_RGB);

        IIOMetadata metadata =
                writer.getDefaultImageMetadata(type, param);

        IIOMetadataNode root =
                new IIOMetadataNode(FORMAT);

        IIOMetadataNode text =
                new IIOMetadataNode("tEXt");

        text.appendChild(textNode("SENDER_PUB",
                senderPublicKeyBase64));

        text.appendChild(textNode("SYMBOL_COUNT",
                String.valueOf(symbolCount)));

        root.appendChild(text);
        metadata.mergeTree(FORMAT, root);

        ByteArrayOutputStream out =
                new ByteArrayOutputStream();

        writer.setOutput(ImageIO.createImageOutputStream(out));
        writer.write(metadata,
                new javax.imageio.IIOImage(image, null, metadata),
                param);

        writer.dispose();
        return out.toByteArray();
    }

    private static IIOMetadataNode textNode(
            String key, String value) {

        IIOMetadataNode node =
                new IIOMetadataNode(TEXT_ENTRY);
        node.setAttribute("keyword", key);
        node.setAttribute("value", value);
        return node;
    }

    public static String extractSenderPublicKey(
            IIOMetadata metadata) {

        return readValue(metadata, "SENDER_PUB");
    }

    public static int extractSymbolCount(
            IIOMetadata metadata) {

        String v = readValue(metadata, "SYMBOL_COUNT");
        return v == null ? 0 : Integer.parseInt(v);
    }

    private static String readValue(
            IIOMetadata metadata, String key) {

        try {
            IIOMetadataNode root =
                    (IIOMetadataNode)
                            metadata.getAsTree(FORMAT);

            NodeList nodes =
                    root.getElementsByTagName(TEXT_ENTRY);

            for (int i = 0; i < nodes.getLength(); i++) {
                IIOMetadataNode n =
                        (IIOMetadataNode) nodes.item(i);

                if (key.equals(n.getAttribute("keyword"))) {
                    return n.getAttribute("value");
                }
            }
        } catch (Exception e) {
            System.out.println("Unable to fetch data.");
        }
        return null;
    }
public static String getSenderPublicKey(IIOMetadata metadata) {
    return extractSenderPublicKey(metadata);
}

public static int getSymbolCount(IIOMetadata metadata) {
    return extractSymbolCount(metadata);
}

}
