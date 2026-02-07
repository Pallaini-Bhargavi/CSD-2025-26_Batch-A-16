# Secure Data Hiding Using Calendar Heatmap Steganography

A secure and efficient steganography system that hides confidential text messages inside calendar heatmap tiles using cryptographic key–controlled randomization.

---

## 📝 Overview

This project presents a novel steganography technique that hides secret information inside a **calendar-style heatmap**, rather than using conventional carriers such as images or QR codes. The generated heatmap visually resembles normal activity data, making the presence of hidden information difficult to identify. Strong cryptographic key control ensures that only authorized users can successfully decode the hidden message.

---

## 🔐 Key Features

1. Secure hiding of text messages within calendar heatmap tiles
2. Elliptic Curve Cryptography–based key control combined with AES encryption
3. Random tile-based data embedding for improved security
4. Flexible heatmap grid supporting varying data sizes
5. Session-based private key handling and secure public key storage
6. Heatmap output generated as an image without visible distortion

---

## 🧠 System Working

### Encoding Process (Message Hiding)

1. The sender enters the secret plain text message.
2. Elliptic Curve Cryptography is used to generate a shared secret using the sender’s private key (session-based) and receiver’s public key (stored in database).
3. An AES encryption key is derived from the ECC shared secret using a cryptographic hash function.
4. The plain text message is encrypted using AES to produce cipher text.
5. The encrypted data is converted into binary form.
6. A calendar heatmap grid is generated and tiles are arranged using a key-controlled random order.
7. Binary data is embedded into the heatmap tiles using predefined color mappings.
8. The final output is an encrypted calendar heatmap image, which can be safely shared.

### Decoding Process (Message Extraction)

1. The receiver uploads the encrypted calendar heatmap image.
2. The sender’s public key is fetched from the database using embedded metadata.
3. The receiver’s private key is regenerated securely within the session.
4. The ECC shared secret is recomputed and the AES key is derived.
5. Hidden binary data is extracted from the heatmap tiles using the same key-controlled order.
6. AES decryption is applied to recover the original plain text message.

---

## 🚀 Technologies Used

1. Java (core implementation of encoding and decoding logic)
2. Elliptic Curve Cryptography (secure key generation and exchange)
3. Advanced Encryption Standard (AES) for message encryption and decryption
4. Image processing techniques for heatmap generation
5. Key-controlled random tile mapping strategy

---

## 🎯 Project Goal

To design a **secure, scalable, and low-detectability data hiding system** that conceals confidential messages inside calendar heatmaps using strong cryptographic key control instead of traditional media-based steganography techniques.

---

## 🌱 Future Enhancements

* Integration with secure web or messaging applications
* Support for multi-user collaborative heatmaps
* Cloud-based deployment with enhanced access control
* Blockchain-based public key verification

---

## 📦 Execution Overview

### Encoding

* User inputs a secret message.
* The system generates an encrypted calendar heatmap image as output.

### Decoding

* Authorized user uploads the heatmap image.
* The original message is successfully recovered using valid cryptographic keys.

---

## 🧪 Example

### ENCODING:
**Input:**

```
MEET AT 5 PM
```

**Output:**

```
Encrypted Calendar Heatmap Image
```
### DECODING:
**Input:**

```
Encrypted Calendar Heatmap Image
```

**Output:**

```
MEET AT 5 PM
```

---

## 📚 Reference Papers

1. [https://ieeexplore.ieee.org/document/10083471](https://ieeexplore.ieee.org/document/10083471)
2. [https://ieeexplore.ieee.org/document/9335027](https://ieeexplore.ieee.org/document/9335027)
3. [https://ieeexplore.ieee.org/document/8985346](https://ieeexplore.ieee.org/document/8985346)
4. [https://www.researchgate.net/publication/301756752_QR-code_Image_Steganography](https://www.researchgate.net/publication/301756752_QR-code_Image_Steganography)
5. [https://www.researchgate.net/publication/351066080_Steganography_of_Messages_Encrypted_With_QR_Code](https://www.researchgate.net/publication/351066080_Steganography_of_Messages_Encrypted_With_QR_Code)
