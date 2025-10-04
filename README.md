# C

**Java practice projects & exercises — consolidated workspace**

> **Short description:** This repository contains a collection of Java projects, exercises and supporting files (including ANTLR grammars) used during learning and practice. The code is organized with a typical VS Code Java workspace layout (`src`, `bin`, `.vscode`) and focuses on core Java skills: object-oriented design, algorithms, data structures, parsing, and small utilities.

---

## What this repository is for

This repo is a personal portfolio of Java work — a place to collect small projects, experiments and learning exercises that show practical competence with Java and language tooling. It is useful for:

* Demonstrating to employers that you have hands-on experience writing, structuring and organizing Java code.
* Sharing runnable exercises and examples that illustrate your coding style, problem solving and familiarity with common Java tools (build/run, packaging).
* Keeping track of experiments (for example parsers implemented with ANTLR) and small utilities.

---

## Repository structure

```
/ (.vscode)
/bin/        # compiled outputs (committed for convenience in some setups)
/src/        # Java sources and ANTLR grammar files
README.md    # this file
```

> The repository follows the default Visual Studio Code Java layout. If you customise folder settings, check `.vscode/settings.json`.

---

## Languages & technologies

* **Language:** Java (primary).
* **Other:** ANTLR (grammar files present in the repo).
* **Editor:** Visual Studio Code (workspace files included).

---

## How to build and run (suggested)

These instructions assume a standard Java SDK is installed (Java 11+ recommended) and you use VS Code or command line.

### Using VS Code

1. Open the repository folder in VS Code.
2. The `JAVA PROJECTS` view helps manage dependencies and run main classes.
3. Build and run directly from the editor.

### Using command line

1. Compile sources (from repository root):

```bash
javac -d bin $(find src -name "*.java")
```

2. Run a main class (replace `com.example.Main` with the real package + class):

```bash
java -cp bin com.example.Main
```

> **Note:** Add concrete `javac`/`java` commands here after confirming the actual package names and entry points in `src`.

---

## What to add to make this README stronger (recommended edits)

To make this README most useful for employers, add the following for each notable subproject or file:

* **Project name & one-line purpose** (e.g. `Mini parser — reads custom expressions and outputs AST`).
* **Key files/classes** and a short description of each (entry points, important modules).
* **How to run** exact example command(s), with expected input and sample output/screenshots.
* **Dependencies** (Maven/Gradle/third-party libs) and how to install them.
* **Tests** — how to run unit tests, or a note if none exist.
* **Status & TODOs** — what’s finished vs work-in-progress.

---

## Example section you can copy per subproject

### `Project name` — Short description

**Purpose:** one-sentence description.

**Files:**

* `src/.../Main.java` — entry point (how to run).
* `src/.../Parser.g4` — ANTLR grammar (if present).

**How to run:**

```bash
javac -d bin src/your/package/*.java
java -cp bin your.package.Main example-input.txt
```

**Notes / learning outcomes:** e.g. practiced lexer/parser design, recursion, OOP principles, file I/O.
