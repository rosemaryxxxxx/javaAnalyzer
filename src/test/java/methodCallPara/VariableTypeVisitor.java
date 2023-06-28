package methodCallPara;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

//获取java文件中函数中定义的每一个变量的类型
public class VariableTypeVisitor extends VoidVisitorAdapter<Void> {



    @Override
    public void visit(MethodDeclaration n, Void arg) {
        super.visit(n, arg);

        // Get the variable declarations for this method
        n.getBody().ifPresent(body -> body.findAll(VariableDeclarationExpr.class).forEach(vde -> {

            // Iterate over each variable in the declaration and print its type
            vde.getVariables().forEach(variable -> System.out.println(variable.getName()+": "+variable.getTypeAsString()));
        }));
    }


    public static void main(String[] args) throws FileNotFoundException {
        FileInputStream in = new FileInputStream("D:\\code\\javazip\\t0625\\KMP.java");
        CompilationUnit cu = StaticJavaParser.parse(in);

        VariableTypeVisitor visitor = new VariableTypeVisitor();
        visitor.visit(cu, null);

    }
}

