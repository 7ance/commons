# Commons

可复用的 Java 工具组件集，覆盖日常开发中的常见需求。

## 环境要求

- **JDK** 25+
- **Maven** 3.x

## 安装

```xml
<dependency>
    <groupId>cn.lance</groupId>
    <artifactId>commons</artifactId>
    <version>1.0.0</version>
</dependency>
```

## 模块

### 加密 (`cn.lance.commons.util.crypto`)

| 工具 | 说明 |
|---|---|
| `AesUtils` | AES 加解密，支持 ECB 和 CBC 模式。256 位密钥，支持随机 IV 生成。密钥与密文均以十六进制编码。 |
| `RsaUtils` | RSA 2048 位密钥对生成、加解密及 SHA256withRSA 签名/验签。密钥支持 Base64 或 PEM 编码。 |
| `EdDsaUtils` | Ed25519 (EdDSA) 密钥对生成、签名与验签。密钥支持 Base64 或 PEM 编码。 |
| `HmacUtils` | HMAC 密钥生成与签名，支持可配置的算法与密钥长度（如 HmacSHA256、HmacSHA512）。输出为十六进制。 |
| `TotpUtils` | TOTP 一次性密码 URI 生成与验证码校验，兼容 Google Authenticator。默认 6 位数字，30 秒周期（HmacSHA1）。 |
| `PgpUtils` | PGP 密钥对生成、加密、解密、签名与验签（基于 PGPainless）。支持 ASCII-armored 格式及口令保护的私钥。 |

### JSON (`cn.lance.commons.util.json`)

| 工具 | 说明 |
|---|---|
| `JsonUtils` | Jackson 3.x (`tools.jackson.core`) 封装，提供预配置的共享 `ObjectMapper`，支持 JSR-310 日期时间（`yyyy-MM-dd HH:mm:ss`）、禁用时间戳、忽略未知属性。支持序列化、反序列化、格式化输出、JSON 校验及 JSON 路径遍历。 |

### JWT (`cn.lance.commons.util.jwt`)

| 工具 | 说明 |
|---|---|
| `JwtUtils` | JWT 生成与校验（基于 jjwt），支持 HS256（HMAC-SHA256）和 RS256（RSA）算法。 |

### 二维码 (`cn.lance.commons.util.qr`)

| 工具 | 说明 |
|---|---|
| `QrCodeUtils` | 二维码生成与解码（基于 ZXing）。PNG 输出以 Base64 编码。支持可配置的纠错等级、边距和字符编码。 |

### UUID (`cn.lance.commons.util.uuid`)

| 工具 | 说明 |
|---|---|
| `UuidUtils` | UUID 生成（标准格式、无连字符、自定义长度）。自定义长度使用 `SecureRandom`。 |
| `SnowflakeIdUtils` | 线程安全的 Snowflake 风格 64 位分布式 ID 生成（1 位符号 + 41 位时间戳偏移 + 5 位数据中心 + 5 位工作节点 + 12 位序列）。起始时间戳为 2025-01-01。 |

### 配置 (`cn.lance.commons.util.conf`)

| 工具 | 说明 |
|---|---|
| `ConfigUtils` | 基于 classpath 的 `.properties` 和 `.yaml` 文件加载。分别返回标准 `Properties` 和 `Map<String, Object>`。 |

### 字符串 (`cn.lance.commons.util.str`)

| 工具 | 说明 |
|---|---|
| `ExtendedStringUtils` | SLF4J 风格的 `{}` 模板格式化。简单、零依赖的字符串插值。 |

### TLV (`cn.lance.commons.util.tlv`)

| 工具 | 说明 |
|---|---|
| `TlvUtils` | Tag-Length-Value 字符串解析器，生成 `TlvNode` 树结构，支持可配置的解析深度。 |
| `TlvNode` | 数据结构，表示解析后的 TLV 节点：标签、长度、值和嵌套子标签。 |

## 构建

```bash
mvn compile          # 编译源码
mvn test             # 运行所有测试
mvn test -Dtest=X    # 运行单个测试类
mvn package          # 打包 JAR
mvn clean            # 清理构建产物
```

## License

MIT
