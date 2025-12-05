📌 Secure Data Hiding Using Calendar Heatmap Steganography

A lightweight and secure steganography system that hides secret text messages inside calendar heatmap tiles using key-based randomization.

📝 Overview

This project introduces a novel steganography approach that hides secret information inside a calendar-style heatmap, instead of commonly used media like images or QR codes.
The heatmap visually resembles a normal activity chart, making the hidden data difficult to detect.
A key-based random tile sequence ensures that the message can be decoded only by someone who has the valid key.

🔐 Features

Hide text messages inside colored heatmap tiles

Key-based random tile mapping for strong security

Scalable tile grid for increased data capacity

Lightweight Java implementation

Simple encoding and decoding interfaces

Output generated as a heatmap image (PNG)

🧠 How It Works
Encoding (Hiding Message)

User enters a text message.

Message is converted to binary.

Heatmap grid is generated (e.g., 60 × 60 tiles).

Secret key generates a random tile order.

Bits are embedded into tiles as specific colors.

Final encoded heatmap image is saved.

Decoding (Extracting Message)

Encoded heatmap image is loaded.

User enters the same secret key.

Tile order is recreated.

Colors → bits → original message is reconstructed.

🚀 Technologies Used

Java (Core logic for encoding & decoding)

Image Processing (BufferedImage)

Custom Color Encoding

Pseudo-Random Key Scheduling

🎯 Goal

To build a secure, flexible, and less detectable data-hiding system using heatmap visualization instead of traditional image or QR-based steganography.

🌱 Future Enhancements

Multi-bit tile encoding for higher data capacity

Integration with secure messaging apps

Mobile and web-based user interface

Encryption + steganography hybrid layer

📦 How to Run
Encoding
java Encoder "YOUR MESSAGE HERE"

Decoding
java Decoder encoded_heatmap.png KEY

🧪 Example

Input Message:

MEET AT 5 PM

Output:

A heatmap image (encoded_heatmap.png)

Key (e.g., 17549)

Decoding:
Enter image + key → original message returned.
