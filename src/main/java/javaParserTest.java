

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.printer.YamlPrinter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class javaParserTest {
    public static void main(String[] args) throws IOException {
        String path="D:\\code\\javazip\\t\\t1\\99.java";
        File base = new File(path);
        ParseResult<CompilationUnit> result = new JavaParser().parse(Paths.get(path));
        result.getResult().ifPresent(YamlPrinter::print);
    }

}
