package javaparser.utils;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MethodCallGraph {
    public static String PATH = "D:\\code\\javaAnalyzer\\src\\main\\java\\pmd\\deadcodetest\\utils\\utils.KMP.java";

    public static void main(String[] args) throws IOException {
        // Parse the Java file into an AST
        CompilationUnit cu = StaticJavaParser.parse(new File(PATH));

        // Create a Map to store the call graph
        Map<String, Set<String>> callGraph = new HashMap<>();

        // Use a visitor to traverse the AST and collect the method calls
        cu.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(MethodCallExpr n, Void arg) {
                super.visit(n, arg);

                // Get the name of the caller and callee
                String callerName = n.getScope().toString();
                String calleeName = n.getName().getIdentifier();

                // Add the call to the call graph
                if (!callGraph.containsKey(callerName)) {
                    callGraph.put(callerName, new HashSet<>());
                }
                callGraph.get(callerName).add(calleeName);
            }

            @Override
            public void visit(MethodDeclaration n, Void arg) {
                super.visit(n, arg);

                // Get the name of the method
                String methodName = n.getName().getIdentifier();

                // If the method is not in the call graph yet, add it with an empty set of callees
                if (!callGraph.containsKey(methodName)) {
                    callGraph.put(methodName, new HashSet<>());
                }
            }
        }, null);

        // Print the call graph
        for (Map.Entry<String, Set<String>> entry : callGraph.entrySet()) {
            String caller = entry.getKey();
            Set<String> callees = entry.getValue();
            System.out.println(caller + " calls: " + callees);
        }
    }
}

