package javaparser;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import javaparser.visitors.MethodCallVisitor;

import java.io.FileInputStream;

public class Main1 {
    public static String PATH = "D:\\code\\javazip\\t419\\src\\compressclient.java";
    public static void main(String[] args) throws Exception {

        FileInputStream in = new FileInputStream(PATH);
        CompilationUnit cu = StaticJavaParser.parse(in);
        MethodCallVisitor visitor = new MethodCallVisitor();
        visitor.visit(cu, null);
    }

}

