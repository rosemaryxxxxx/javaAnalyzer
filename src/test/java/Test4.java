import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.printer.YamlPrinter;
import javaparser.utils.MethodCallExtractor1;
import javaparser.visitors.MethodCallVisitor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Test4 {
    public static void main(String[] args) throws IOException {
        String path="D:\\codebaseOfCodeMatcher\\2\\Conversions\\HexToOct.java";
        FileInputStream in = new FileInputStream(path);
        try{
            CompilationUnit cu = StaticJavaParser.parse(in);
            MethodCallVisitor visitor = new MethodCallVisitor();
            visitor.visit(cu, null);
        }catch (Exception e){
            e.printStackTrace();
        }

//        CompilationUnit cu = StaticJavaParser.parse(in);
//        MethodCallVisitor visitor = new MethodCallVisitor();
//        visitor.visit(cu, null);

        System.out.println("******");

    }
}
