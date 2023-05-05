package javaparser.visitors;

import com.github.javaparser.JavaParser;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.io.FileInputStream;

public class MethodLocationVisitor extends VoidVisitorAdapter<Void> {

    @Override
    public void visit(MethodDeclaration n, Void arg) {
        super.visit(n, arg);

        // 获取方法名、起始位置和结束位置
        String methodName = n.getNameAsString();
        int startLine = n.getBegin().get().line;
        int startCol = n.getBegin().get().column;
        int endLine = n.getEnd().get().line;
        int endCol = n.getEnd().get().column;

        // 输出结果
        System.out.println("Method \"" + methodName + "\" starts at line " + startLine +
                ", column " + startCol + " and ends at line " + endLine + ", column " + endCol);
    }

    public static void main(String[] args) throws Exception {
        // 读取 Java 文件
        FileInputStream in = new FileInputStream("D:\\code\\javazip\\t0504\\pmd\\deadcodetest\\utils\\KMP.java");

        // 解析 Java 文件
        CompilationUnit cu = StaticJavaParser.parse(in);

        // 遍历 AST 并使用我们的 visitor
        MethodLocationVisitor visitor = new MethodLocationVisitor();
        visitor.visit(cu, null);

        // 关闭输入流
        in.close();
    }
}

