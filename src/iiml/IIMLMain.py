import sys
import os
import numpy as np
import cv2
from antlr4 import *
from IIMLLexer import IIMLLexer
from IIMLParser import IIMLParser
from Interpreter import Interpreter

def main(file=None):
    if file is None:
        print("Usage: IIMLMain.py <input_file>")
        return None

    visitor = Interpreter()

    # Handle file path - make it relative to current directory if needed
    if not os.path.isabs(file):
        file = os.path.join(os.getcwd(), file)

    try:
        with open(file, 'r') as f:
            input_stream = InputStream(f.read())
    except FileNotFoundError:
        print(f"Error: File {file} not found")
        return None

    lexer = IIMLLexer(input_stream)
    stream = CommonTokenStream(lexer)
    parser = IIMLParser(stream)
    tree = parser.program()

    if parser.getNumberOfSyntaxErrors() == 0:
        result = visitor.visit(tree)
        # Ensure result is uint8 for OpenCV morphological operations
        if isinstance(result, np.ndarray):
            if result.dtype != np.uint8:
                result = (result * 255).astype(np.uint8)
        else:
            print("No image generated")
        return result
    else:
        print(f"Syntax errors found in {file}")
        return None

if __name__ == '__main__':
    if len(sys.argv) > 1:
        main(sys.argv[1])
    else:
        print("Usage: python IIMLMain.py <input_file>")
