package methodCallPara;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.io.FileInputStream;

public class VariableVisitor extends VoidVisitorAdapter<Void> {
    @Override
    public void visit(FieldDeclaration n, Void arg) {
        super.visit(n, arg);
        for(VariableDeclarator v : n.getVariables()) {
            System.out.println("Variable Name: " + v.getNameAsString() + ", Type: " + v.getType());
        }
    }

    public static void main(String[] args) throws Exception {
        FileInputStream in = new FileInputStream("D:\\code\\javaAnalyzer\\src\\main\\java\\pmd\\deadcodetest\\utils\\KMP.java");
        CompilationUnit cu = StaticJavaParser.parse(in);
        VariableVisitor vv = new VariableVisitor();
        vv.visit(cu, null);
    }
}

