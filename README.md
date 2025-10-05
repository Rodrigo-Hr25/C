# IML / IIML Compiler & Interpreter  
A domain-specific language (DSL) for grayscale image manipulation and morphological operations  

**Category:** Compiler 

---

## 📌 Overview  
This project implements a **compiler and interpreter for IML (Image Manipulation Language)**, a domain-specific language designed for grayscale image processing and morphological operations.  

The system is composed of two tightly connected languages:  

- **IML (compiled)** → Translates into Python code that leverages **OpenCV** and **NumPy** for efficient image manipulation.  
- **IIML (interpreted)** → A secondary language used to define geometric patterns (e.g., circles, rectangles, crosses) that can be combined with images during execution.  

This project demonstrates strong skills in compiler design, code generation, semantic analysis, and integration between Java and Python runtimes.

---

## 🧩 Architecture  

### Compilation Pipeline  
1. **Lexical & Syntactic Analysis**  
   - Grammar rules defined in `IML.g4` and `IIML.g4`  
   - ANTLR4 generates parsers  

2. **Semantic Analysis**  
   - Type checking, scope validation, semantic correctness  
   - Ensures correct usage of image and numeric operations  

3. **Code Generation**  
   - Java visitor-based AST traversal  
   - Uses StringTemplate (`Template.stg`) for generating Python code  

4. **Runtime Execution**  
   - Python code imports helper libraries (`OpenCV`, `NumPy`)  
   - The IIML interpreter is invoked for geometric shape definitions  

---

## 📂 Repository Structure  

```
/
├── src/                  # Compiler and grammar sources
│   ├── IML.g4            # Main language grammar
│   ├── IIML.g4           # Secondary language grammar
│   ├── compiler/         # Java compiler (semantic analysis, code gen, errors)
│   └── iiml/             # Python-based IIML interpreter
├── examples/             # Example programs (.iml and .iiml)
├── bin/                  # Compilation scripts
├── final/                # Generated Python code and runtime
├── requirements.txt      # Python dependencies
└── README.md
```

---

## ⚙️ How to Use  

### 1. Requirements  
- **Java** (for the compiler)  
- **ANTLR4** (parser generator)  
- **Python 3.x** with:  
  - `opencv-python`  
  - `numpy`  

Install Python dependencies:  
```bash
pip install -r requirements.txt
```

### 2. Compile an IML Program  
```bash
./compile -f program.iml -o program.py
```

### 3. Run the Generated Python Code  
```bash
cd final/
python program.py
```

### 4. Test with Examples  
Sample programs are available under the `examples/` directory.  

---

## ✨ Features  

### Language Types  
- `image` → Grayscale image with saturating arithmetic  
- `number` → Floating-point numeric values  
- `percentage` → Percentage values (`70%`)  
- `string` → Text strings  
- `list of <type>` → Dynamic lists supporting nesting  

### Image Operations  
- **Pixel-wise arithmetic:** `.+`, `.-`, `.*`  
- **Morphological transformations:** `dilate`, `erode`, `open`, `close`  
- **Scaling:** `-*`, `|*`, `+*`  
- **Flipping / inversion:** `-`, `|`, `+`, `.-`  
- **Pixel analysis:** `any pixel`, `all pixel`, `count pixel`  

### IIML Shapes  
- `circle`, `rect`, `cross`, `plus` → Basic geometric patterns  

### Additional Features  
- Type checking and semantic validation  
- Error reporting system with contextualized messages  
- Integration of image arithmetic with Python runtime  
- Automatic normalization of images into `[0,1]` float range  

---

## ✅ Key Highlights  

- Full **compiler pipeline**: lexical, syntax, semantic analysis, and code generation  
- **Cross-language integration**: Java compiler + Python runtime  
- **Advanced error handling** with meaningful messages  
- **Efficient image processing** via OpenCV and NumPy  
- Example-driven validation with minimal and desirable test cases  
- Modular, reusable, and extensible codebase  

---

## 💼 Professional Context  

This project was developed as part of my **compiler engineering and domain-specific languages portfolio**.  
It demonstrates advanced skills in **language design, compiler construction, semantic analysis, and applied computer vision**.  

---
