

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.printer.DotPrinter;
import com.github.javaparser.printer.XmlPrinter;
import com.github.javaparser.printer.YamlPrinter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class javaParserTest {
    public static void main(String[] args) throws IOException {
        String path="D:\\codebaseOfCodeMatcher\\2\\Conversions\\OctalToBinary.java";
        File base = new File(path);
        ParseResult<CompilationUnit> result = new JavaParser().parse(base);
        //yaml格式输出AST
        result.getResult().ifPresent(YamlPrinter::print);
        //xml格式输出AST
        //result.getResult().ifPresent(XmlPrinter::print);
        //dot格式输出AST，可以用Graphiz实现AST的可视化
//        result.getResult().ifPresent(e -> System.out.println(new DotPrinter(true).output(e)));
    }

}
