package javaparser.utils;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.resolution.SymbolResolver;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import javaparser.visitors.MethodCallVisitor;

import java.io.File;

public class MethodNameAndParameters {
    public static String PATH = "D:\\code\\javaAnalyzer\\src\\main\\java\\pmd\\deadcodetest\\utils\\KMP.java";

    public static void main(String[] args) throws Exception {

        //config
        CombinedTypeSolver typeSolver = new CombinedTypeSolver();
        JavaSymbolSolver symbolSolver = new JavaSymbolSolver(typeSolver);
        SymbolResolver symbolResolver = new JavaSymbolSolver(typeSolver);
        ParserConfiguration parserConfiguration = new ParserConfiguration();
        parserConfiguration.setSymbolResolver(symbolResolver);
        JavaParser javaParser = new JavaParser(parserConfiguration);

        ParseResult<CompilationUnit> result = javaParser.parse(new File(PATH));
        MethodCallVisitor visitor = new MethodCallVisitor();

        if(result.getResult().isPresent()){
            result.getResult().get().accept(        new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(MethodDeclaration md, Object arg) {
                    // Print the method name and parameter types
//                System.out.print("Method Name: " + md.getName());
//                System.out.print("(");
//                md.getParameters().forEach(p -> System.out.print(p.getType()));
//                System.out.print(")");
                    StringBuffer sb = new StringBuffer();
                    md.getParameters().forEach(parameter -> sb.append(parameter.getType()));

                    System.out.println(sb);
                }
            }, null);
//            result.getResult().get().accept(new MethodCallExprVisitor(myclass), null);
        }


//        // Parse the Java file
//        CompilationUnit cu = StaticJavaParser.parse(new File(PATH));

    }
}

