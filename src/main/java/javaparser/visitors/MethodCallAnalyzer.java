package javaparser.visitors;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class MethodCallAnalyzer {
    public static String PATH = "D:\\code\\javaAnalyzer\\src\\main\\java\\pmd\\deadcodetest\\calls\\calls.java";

    public static void main(String[] args) throws Exception {

        // Parse the Java code and create an Abstract Syntax Tree (AST)
        FileInputStream in = new FileInputStream(PATH);
        ParseResult<CompilationUnit> result = new JavaParser().parse(in);
        CompilationUnit cu = result.getResult().get();
        in.close();

        // Find all the method declarations in the AST
        List<MethodDeclaration> methods = cu.findAll(MethodDeclaration.class);

        // Initialize a list to store the method call relationships
        List<String[]> methodCalls = new ArrayList<>();

        // Iterate through each method declaration in the AST
//        for(MethodDeclaration method : methods) {
//            // Retrieve the statements within the current method
//            List<ExpressionStmt> stmts = method.getBody().get().getStatements();
//
//            // Iterate through each statement in the current method
//            for(ExpressionStmt stmt : stmts) {
//                // Check if the current statement is a method call expression
//                if(stmt.getExpression() instanceof MethodCallExpr) {
//                    // Retrieve the method call expression
//                    MethodCallExpr mce = (MethodCallExpr)stmt.getExpression();
//                    // Retrieve the name of the method being called
//                    String methodName = mce.getNameAsString();
//                    // Retrieve the name of the class containing the method being called
//                    String className = mce.resolve().declaringType().getNameAsString();
//                    // Add the method call relationship to the list
//                    methodCalls.add(new String[] {method.getNameAsString(), methodName, className});
//                }
//            }
//        }

        // Print out the method call relationships
        for (String[] methodCall : methodCalls) {
            System.out.println(methodCall[0] + " calls " + methodCall[1] + " in " + methodCall[2]);
        }
    }
}
