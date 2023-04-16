package javaparser;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import javaparser.visitors.MethodCallVisitor;

import java.io.FileInputStream;

public class Main1 {
    public static String PATH = "D:\\code\\javaAnalyzer\\src\\main\\java\\pmd\\deadcodetest\\utils\\utils.KMP.java";
    public static void main(String[] args) throws Exception {

        FileInputStream in = new FileInputStream(PATH);
        ParseResult<CompilationUnit> result = new JavaParser().parse(in);

        MethodCallVisitor mv = new MethodCallVisitor();
        //获得cu
        CompilationUnit cu = result.getResult().get();

        cu.accept(mv,null);

    }

}

