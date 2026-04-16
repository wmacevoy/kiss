# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

KISS (Keep It Simple, Stupid) is a Java library for teaching software development concepts to beginners. It provides a simplified API so students don't need to understand packages, static methods, access modifiers, or arrays to write and test programs. Single import: `import static kiss.API.*;`

**Version:** 1.2 | **Java:** 1.8 (source/target in pom.xml), 1.7 (Makefile javac) | **License:** Apache 2.0

## Build Commands

Uses **Make** (primary build) and **Maven** (deployment/docs):

- `make lib` — compile and create `kiss.jar` + `kiss-with-tests.jar` with SHA256 checksums
- `make test` — run all tests (self-tests + example tests)
- `make self-test` — run core library tests: `java -cp kiss-with-tests.jar kiss.API --app kiss.util.Test`
- `make example-tests` — run tests in each `examples/*/` subdirectory
- `make examples` — compile all examples
- `make clean` — remove all build artifacts
- `make doc` — generate Javadoc via Maven
- `make deploy` — full deploy cycle: clean, build, commit JARs, push, Maven Central publish

## Architecture

### Two-Layer Design

**Public facade** — `kiss/API.java`: Static methods re-exporting functionality from `kiss.util.*`. Also imports all `java.lang.Math` methods (except `random()`). Defines inner interfaces `Close`, `Listener<Event>`, and class `Generator<Event>`.

**Implementation** — `kiss/util/`:
- `Run.java` — Entry point (`main`). Discovers and runs `testXXX()` methods in declaration order, then `run()`, then `close()`. Seeds RNG with `seed(1)` before each test (deterministic), `seed()` before `run()` (cryptographic).
- `IO.java` — Thread-local stacked I/O streams. Supports `outExpect`/`inProvide` for test verification, `outOpen`/`inOpen` for file redirection.
- `RNG.java` / `AESPRNG.java` — AES-based PRNG (fast + cryptographically strong).
- `As.java` — Type conversion utilities.
- `Cipher.java` — AES-GCM encryption, SHA-256 hashing, hex encode/decode.
- `Reflect.java` — Reflection utilities for method discovery.

### Entry Point Flow

```
kiss.util.Run.main() →
  1. Parse args (--app ClassName, --notest, --norun, --)
  2. Load app class (default: App in default package, or JAVA_APP env var)
  3. Construct instance
  4. Run all testXXX() methods (seed(1) before each)
  5. Run run() method if present (seed() before)
  6. Run close() method if present (always, even on error)
```

### Testing Model

Tests are `testXXX()` methods in the same class as `run()` — no separate test framework. The built-in test runner discovers them via reflection. Key patterns:

```java
// Output verification with try-with-resources
void testRun() {
    try (Close out = outExpect("Hello, World!")) {
        run();
    }
}

// Input injection
void testHi() {
    try (Close in = inProvide("Alice")) {
        try (Close out = outExpect("Hi, Alice!")) {
            hi();
        }
    }
}
```

Self-tests live in `kiss/util/Test*.java` classes. Example tests live in `examples/*/src/main/java/App.java`.

### Examples and Katas

Each `examples/` and `katas/` subdirectory is a standalone Maven project with its own `pom.xml` and `Makefile`. The Makefile provides `this` (compile), `test`, and `run` targets. App class is always `App.java` in the default package.
