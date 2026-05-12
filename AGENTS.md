# AGENTS.md

This file provides guidance to AI coding assistants when working with code in this repository.

## Build Commands

```bash
mvn compile          # compile sources
mvn test             # run all tests
mvn test -Dtest=X    # run a single test class (e.g., -Dtest=AesUtilsTest)
mvn package          # package JAR
mvn clean            # clean build output
```

## Architecture

Single-module Maven project (groupId `cn.lance`, artifactId `commons`, JDK 25). A personal utility library of reusable Java components under the package `cn.lance.commons.util`, organized into sub-packages by concern:

- **`crypto/`** — AES (ECB/CBC), RSA, EdDSA (Ed25519), HMAC, TOTP, and PGP (via PGPainless). All keys/output are hex, Base64, or ASCII-armored strings.
- **`json/`** — Jackson 3.x wrapper (`tools.jackson.core`, not `com.fasterxml.jackson.core`) with a shared `ObjectMapper` singleton configured for JSR-310 dates (`yyyy-MM-dd HH:mm:ss`), no timestamps, ignoring unknown properties.
- **`jwt/`** — JWT creation and verification via jjwt, supporting HS256 and RS256.
- **`qr/`** — QR code generation/decoding via ZXing (PNG images as Base64).
- **`uuid/`** — UUID generation, Snowflake IDs (timestamp-offset-based, thread-safe).
- **`tlv/`** — TLV string parser producing a tree of `TlvNode` objects.
- **`conf/`** — `.properties` and `.yaml` file loading from the classpath.
- **`str/`** — Extends Apache Commons `StringUtils` with SLF4J-style `{}` template formatting.

## Key Patterns

- All utility classes have private constructors (static method containers).
- `Objects.requireNonNull` used as precondition guards on public method parameters.
- Checked exceptions are wrapped in `RuntimeException` rather than declared in `throws` clauses.
- Configuration values (key sizes, timeouts, algorithms) are `private static final` constants at the top of the class.
- Tests are integration-style: create input, perform operation, then round-trip verify (e.g., encrypt → decrypt, assert original equals result). Tests use JUnit Jupiter 6 + Mockito 5.
- Jackson is the Jackson 3.x namespace: import `tools.jackson.core.*` and `tools.jackson.databind.*`, not the `com.fasterxml.jackson` 2.x namespace. The JSR-310 module (`jackson-datatype-jsr310`) is still under the `com.fasterxml.jackson` namespace.

## Git Conventions

Commits follow [Conventional Commits](https://www.conventionalcommits.org/) with scoped prefixes: `feat(util):`, `chore(dependency):`, `style(util):`, `refactor:`, etc.
