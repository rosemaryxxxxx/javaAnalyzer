package javaparser.visitors;

import com.github.javaparser.JavaParser;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.FileInputStream;
import java.util.*;

public class MethodCalleeAndParameterVisitor extends VoidVisitorAdapter<Void> {

    private String currentMethod;

    @Override
    public void visit(MethodDeclaration n, Void arg) {
        currentMethod = n.getNameAsString();
        System.out.println("方法名："+currentMethod);
        super.visit(n, arg);
    }

    @Override
    public void visit(MethodCallExpr n, Void arg) {
//        n.getArguments().forEach(parameter -> System.out.println(parameter.calculateResolvedType().describe()));
        if(currentMethod!=null){
            System.out.println(currentMethod+"的调用函数："+n.getNameAsString());
            n.getArguments().forEach(p -> System.out.println(p.toString()));
//            n.getArguments().forEach(p->p.calculateResolvedType().describe());
        }

        super.visit(n, arg);
    }


    public static void main(String[] args) throws Exception {
        // 读取 Java 文件
        FileInputStream in = new FileInputStream("D:\\code\\javazip\\t419\\pmd\\deadcodetest\\utils\\callsss.java");

        // 解析 Java 文件
        CompilationUnit cu = StaticJavaParser.parse(in);

        // 查找方法调用的方法名和参数类型
        MethodCalleeAndParameterVisitor visitor = new MethodCalleeAndParameterVisitor();
        visitor.visit(cu, null);

        // 关闭输入流
        in.close();
    }
}
