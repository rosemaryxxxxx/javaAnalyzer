package javaparser.utils;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.File;

public class MethodNameAndParameters {
    public static String PATH = "D:\\code\\javaAnalyzer\\src\\main\\java\\pmd\\deadcodetest\\utils\\KMP.java";

    public static void main(String[] args) throws Exception {

        // Parse the Java file
        CompilationUnit cu = StaticJavaParser.parse(new File(PATH));

        // Visit all the method declarations using a visitor
        new VoidVisitorAdapter<Object>() {
            @Override
            public void visit(MethodDeclaration md, Object arg) {
                // Print the method name and parameter types
//                System.out.print("Method Name: " + md.getName());
//                System.out.print("(");
//                md.getParameters().forEach(p -> System.out.print(p.getType()));
//                System.out.print(")");
                StringBuffer sb = new StringBuffer();
                sb.append("pakageName"+"className");
                md.getParameters().forEach(parameter -> sb.append(parameter.getType()));
                System.out.println(sb);
            }
        }.visit(cu, null);
    }
}

