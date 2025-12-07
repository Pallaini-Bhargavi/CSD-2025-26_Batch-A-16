# Secure Data Hiding Using Calendar Heatmap Steganography

A lightweight and secure steganography system that hides secret text messages inside calendar heatmap tiles using key-based randomization.

## 📝 Overview

This project introduces a novel steganography approach that hides secret information inside a calendar-style heatmap, instead of commonly used media like images or QR codes.
The heatmap visually resembles a normal activity chart, making the hidden data difficult to detect.
A key-based random tile sequence ensures that the message can be decoded only by someone who has the valid key.

## 🔐 Features

1. Hide text messages inside colored heatmap tiles

2. Key-based random tile mapping for strong security

3. Scalable tile grid for increased data capacity

4. Lightweight Java implementation

5. Simple encoding and decoding interfaces

6. Output generated as a heatmap image (PNG)

## 🧠 How It Works

### Encoding (Hiding Message)

1. User enters a text message.

2. Message is converted to cipher text.
 
3. Ciphertext is again converted to binary.

4. Heatmap grid is generated .

5. Secret key generates in a random tile order.

6. Bits are embedded into tiles as specific colors.

7. Final encoded heatmap image is saved.

### Decoding (Extracting Message)

1. Encoded heatmap image is loaded.

2. User enters the same secret key.

3. Tile order is recreated.

4. Colors → bits → original message is reconstructed.

# 🚀 Technologies Used

1. Java (Core logic for encoding & decoding)

2. Image Processing (BufferedImage)

3. AES algorithm (Cipher text)

4. Custom Color Encoding

5. Pseudo-Random Key Scheduling

### 🎯 Goal

To build a secure, flexible, and less detectable data-hiding system using heatmap visualization instead of traditional image or QR-based steganography.

### 🌱 Future Enhancements

Integration with secure messaging apps

### 📦 How to Run

Encoding
java Encoder "YOUR MESSAGE HERE"

Decoding
java Decoder encoded_heatmap.png KEY

### 🧪 Example

### Encoding
### Input:
``
MEET AT 5 PM
``

### Output:
```
A heatmap image (encoded_heatmap.png)
```
```
Key (e.g., ad3%@s!d07c)
```

### Decoding:
### Input:
Enter image + key → original message returned.
```
encoded_heatmap.png
```
```
ad3%@s!d07c
```
### Output:
```
MEET AT 5 PM
```
