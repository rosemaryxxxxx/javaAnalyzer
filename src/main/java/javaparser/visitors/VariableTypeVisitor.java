package javaparser.visitors;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

//获取java文件中函数中定义的每一个变量的类型
public class VariableTypeVisitor extends VoidVisitorAdapter<Void> {
    static Map<String, String> augementAndType = new HashMap<>();

    @Override
    public void visit(MethodDeclaration md, Void arg) {
        super.visit(md, arg);

        // Get the variable declarations for this method
        md.getBody().ifPresent(body -> body.findAll(VariableDeclarationExpr.class).forEach(vde -> {

            // Iterate over each variable in the declaration and print its type
            vde.getVariables().forEach(variable ->
                    augementAndType.put(variable.getNameAsString(),variable.getTypeAsString())
//                    System.out.println(variable.getName()+": "+variable.getTypeAsString())
                    );
        }));
    }

    public static void main(String[] args) throws FileNotFoundException {
        FileInputStream in = new FileInputStream("D:\\code\\javaAnalyzer\\src\\main\\java\\pmd\\deadcodetest\\utils\\KMP.java");
        CompilationUnit cu = StaticJavaParser.parse(in);

        VariableTypeVisitor visitor = new VariableTypeVisitor();
        visitor.visit(cu, null);

        Map<String, String> augementAndType1 = augementAndType;
    }

    /**
     * 获取java文件中所有的参数和对应的类型
     * @param javaPath
     * @throws FileNotFoundException
     */
    public static Map<String, String> extarctType(String javaPath) throws FileNotFoundException {
        FileInputStream in = new FileInputStream(javaPath);
        CompilationUnit cu = StaticJavaParser.parse(in);
        VariableTypeVisitor visitor = new VariableTypeVisitor();
        visitor.visit(cu,null);
        return augementAndType;
    }

}

