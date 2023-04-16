package javaparser.utils;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class MethodCallExtractor {
    public static String PATH = "D:\\code\\javaAnalyzer\\src\\main\\java\\pmd\\deadcodetest\\utils\\utils.KMP.java";

    public static void main(String[] args) throws Exception {
        FileInputStream in = new FileInputStream(new File(PATH));
        CompilationUnit cu = StaticJavaParser.parse(in);

        MethodCallVisitor visitor = new MethodCallVisitor();
        visitor.visit(cu, null);

        Map<String, Set<String>> methodCalls = visitor.getMethodCalls();
        for (Map.Entry<String, Set<String>> entry : methodCalls.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    private static class MethodCallVisitor extends VoidVisitorAdapter<Void> {

        private Map<String, Set<String>> methodCalls = new HashMap<>();
        private String currentMethod;

        @Override
        public void visit(MethodDeclaration n, Void arg) {
            currentMethod = n.getNameAsString();
            methodCalls.put(currentMethod, new HashSet<>());
            super.visit(n, arg);
        }

        @Override
        public void visit(MethodCallExpr n, Void arg) {
            String methodName = n.getNameAsString();
            methodCalls.get(currentMethod).add(methodName);
            super.visit(n, arg);
        }

        public Map<String, Set<String>> getMethodCalls() {
            return methodCalls;
        }
    }

}



