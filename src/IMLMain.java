import java.io.FileWriter;
import java.io.IOException;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.stringtemplate.v4.*;
import error.ErrorHandling;
import error.ErrorHandlingListener;

import java.util.HashMap;

public class IMLMain {
    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println("Usage: java IMLMain <input_file> <output_file>");
            System.exit(1);
        }
        String fileName = args[0];
        String outputFileName = args[1];

        try {
            // create a CharStream that reads from the input file:
            CharStream input = CharStreams.fromFileName(fileName);
            // create a lexer that feeds off of input CharStream:
            IMLLexer lexer = new IMLLexer(input);
            // create a buffer of tokens pulled from the lexer:
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            // create a parser that feeds off the tokens buffer:
            IMLParser parser = new IMLParser(tokens);
            // replace error listener:
            parser.removeErrorListeners(); // remove ConsoleErrorListener
            parser.addErrorListener(new ErrorHandlingListener());
            // begin parsing at program rule:
            ParseTree tree = parser.program();
            
            if (parser.getNumberOfSyntaxErrors() == 0) {
                System.out.println("No syntax errors found. Starting semantic analysis...");
                
                // Reset error handling for semantic analysis
                ErrorHandling.reset();
                
                // Perform semantic analysis
                SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer();
                semanticAnalyzer.visit(tree);

                if (ErrorHandling.hasErrors()) {
                    System.out.println(String.format("%d semantic error(s) found. Exiting...", ErrorHandling.errorCount()));
                    if (ErrorHandling.warningCount() > 0) {
                        System.out.println(String.format("%d warning(s) found.", ErrorHandling.warningCount()));
                    }
                    return;
                }

                System.out.println("No semantic errors found. Generating Python code...");
                if (ErrorHandling.warningCount() > 0) {
                    System.out.println(String.format("%d warning(s) found.", ErrorHandling.warningCount()));
                }

                // Get variable types from semantic analyzer by accessing the variablesMap field
                HashMap<String, VariableTypes> variablesType = semanticAnalyzer.getVariablesMap();
                
                // Generate code
                CodeGenerator codeGenerator = new CodeGenerator(variablesType);
                StringTemplateWithType result = codeGenerator.visit(tree);
                
                if (result != null && result.getTemplate() != null) {
                    FileWriter output = new FileWriter(outputFileName);
                    output.write(result.getTemplate().render());
                    output.close();
                    System.out.println(String.format("Python code generated successfully to %s", outputFileName));
                } else {
                    System.err.println("Error: Code generation failed - no output generated");
                    System.exit(1);
                }
            } else {
                System.out.println(String.format("%d syntax error(s) found. Exiting...", parser.getNumberOfSyntaxErrors()));
            }
        }
        catch(IOException e) {
            System.err.println("Error reading input file: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        catch(RecognitionException e) {
            System.err.println("Error parsing input: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        catch(Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
