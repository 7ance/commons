# Commons

A collection of reusable Java utility components for common programming tasks.

## Requirements

- **JDK** 25+
- **Maven** 3.x

## Installation

```xml
<dependency>
    <groupId>cn.lance</groupId>
    <artifactId>commons</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Modules

### Cryptography (`cn.lance.commons.util.crypto`)

| Utility | Description |
|---|---|
| `AesUtils` | AES encryption/decryption with ECB and CBC modes. Supports 256-bit keys, random IV generation. All keys and ciphertext are hex-encoded. |
| `RsaUtils` | RSA 2048-bit key pair generation, encryption/decryption, and SHA256withRSA signing/verification. Keys use Base64 or PEM encoding. |
| `EdDsaUtils` | Ed25519 (EdDSA) key pair generation, signing, and verification. Keys use Base64 or PEM encoding. |
| `HmacUtils` | HMAC key generation and signing with configurable algorithm and key size (e.g., HmacSHA256, HmacSHA512). Outputs in hex. |
| `TotpUtils` | TOTP (Time-Based One-Time Password) URI generation and passcode verification, compatible with Google Authenticator. Defaults to 6-digit codes with a 30-second period (HmacSHA1). |
| `PgpUtils` | PGP key pair generation, encryption, decryption, signing, and verification via PGPainless. Supports ASCII-armored keys and passphrase-protected secret keys. |

### JSON (`cn.lance.commons.util.json`)

| Utility | Description |
|---|---|
| `JsonUtils` | Jackson 3.x (`tools.jackson.core`) wrapper providing a pre-configured shared `ObjectMapper` with JSR-310 date/time support (`yyyy-MM-dd HH:mm:ss`), disabled timestamps, and unknown-property tolerance. Supports serialization, deserialization, pretty-printing, JSON validation, and JSON path traversal. |

### JWT (`cn.lance.commons.util.jwt`)

| Utility | Description |
|---|---|
| `JwtUtils` | JWT creation and verification via jjwt, supporting HS256 (HMAC-SHA256) and RS256 (RSA) algorithms. |

### QR Code (`cn.lance.commons.util.qr`)

| Utility | Description |
|---|---|
| `QrCodeUtils` | QR code generation and decoding via ZXing. PNG output encoded as Base64. Supports configurable error correction, margins, and character encoding. |

### UUID (`cn.lance.commons.util.uuid`)

| Utility | Description |
|---|---|
| `UuidUtils` | UUID generation (standard, custom-length without hyphens). Uses `SecureRandom` for custom-length strings. |
| `SnowflakeIdUtils` | Thread-safe Snowflake-style 64-bit distributed ID generation (1-bit sign + 41-bit timestamp offset + 5-bit datacenter + 5-bit worker + 12-bit sequence). Epoch starts at 2025-01-01. |

### Configuration (`cn.lance.commons.util.conf`)

| Utility | Description |
|---|---|
| `ConfigUtils` | Classpath-based `.properties` and `.yaml` file loading. Returns standard `Properties` and `Map<String, Object>` respectively. |

### String (`cn.lance.commons.util.str`)

| Utility | Description |
|---|---|
| `ExtendedStringUtils` | SLF4J-style `{}` template formatting. Simple, zero-dependency string interpolation. |

### TLV (`cn.lance.commons.util.tlv`)

| Utility | Description |
|---|---|
| `TlvUtils` | Tag-Length-Value string parser producing a tree of `TlvNode` objects with configurable parsing depth. |
| `TlvNode` | Data class representing a parsed TLV node: tag, length, value, and nested sub-tags. |

## Build

```bash
mvn compile          # Compile sources
mvn test             # Run all tests
mvn test -Dtest=X    # Run a single test class
mvn package          # Package JAR
mvn clean            # Clean build output
```

## License

MIT
