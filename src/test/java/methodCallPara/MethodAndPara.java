package methodCallPara;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.resolution.SymbolResolver;
import com.github.javaparser.resolution.UnsolvedSymbolException;
import com.github.javaparser.resolution.declarations.ResolvedValueDeclaration;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import javaparser.visitors.MethodCallVisitor;

import java.io.File;
import java.io.FileInputStream;

public class MethodAndPara {

    public static String PATH = "D:\\code\\javaAnalyzer\\src\\main\\java\\pmd\\deadcodetest\\utils\\KMP.java";

    public static void main(String[] args) throws Exception {
// 创建一个新的ParserConfiguration对象
        ParserConfiguration parserConfiguration = new ParserConfiguration();

// 设置一个自定义的SymbolResolver
        parserConfiguration.setSymbolResolver(new JavaSymbolSolver(new CombinedTypeSolver()));

// 使用带有配置的ParserConfiguration的JavaParser实例
        JavaParser javaParser = new JavaParser(parserConfiguration);
        ParseResult<CompilationUnit> result = javaParser.parse(new File(PATH));




    }
}
