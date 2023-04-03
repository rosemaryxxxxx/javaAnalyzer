package javaparser;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import javaparser.visitors.MethodCallVisitor;

import java.io.FileInputStream;

public class Main {
    public static String PATH = "D:\\code\\javaAnalyzer\\src\\main\\java\\pmd\\deadcodetest\\utils\\KMP.java";
    public static void main(String[] args) throws Exception {

        FileInputStream in = new FileInputStream(PATH);
        ParseResult<CompilationUnit> result = new JavaParser().parse(in);

        MethodCallVisitor mv = new MethodCallVisitor();
        //获得cu
//        CompilationUnit cu = result.getResult().get();
        if(result.getResult().isPresent()){
            result.getResult().get().accept(mv, null);
//            result.getResult().get().accept(new MethodCallExprVisitor(myclass), null);
        }
//        System.out.println(mv.packageName+mv.methodName);

    }

}
