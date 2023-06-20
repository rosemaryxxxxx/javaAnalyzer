package methodCallPara;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.type.Type;

import java.io.File;
import java.io.FileNotFoundException;

public class VariableTypeExtractor {
    public static void main(String[] args) throws FileNotFoundException {
        // Parse the Java file
        CompilationUnit cu = StaticJavaParser.parse(new File("D:\\code\\javaAnalyzer\\src\\main\\java\\pmd\\deadcodetest\\utils\\KMP.java"));

        // Find all variable declarations in the file
        for (FieldDeclaration field : cu.findAll(FieldDeclaration.class)) {
            // Get the type of the variable
            Type type = field.getElementType();

            // Print the name and type of the variable
            System.out.println("Variable Name: " + field.getVariables().get(0).getName());
            System.out.println("Variable Type: " + type.toString());
        }
    }
}
