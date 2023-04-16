package javaparser.utils;

import com.github.javaparser.JavaParser;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashSet;
import java.util.Set;

public class MethodCallFinder {
    public static String PATH = "D:\\code\\javaAnalyzer\\src\\main\\java\\pmd\\deadcodetest\\utils\\utils.KMP.java";

    public static void main(String[] args) throws Exception {
        // 读取Java源文件
        FileInputStream in = new FileInputStream(PATH);

        // 解析Java源文件
        CompilationUnit cu = StaticJavaParser.parse(in);

        // 创建MethodCallVisitor对象
        MethodCallVisitor visitor = new MethodCallVisitor();

        // 遍历所有的方法
        cu.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(MethodDeclaration md, Void arg) {
                super.visit(md, arg);
                md.accept(visitor, null);
            }
        }, null);

        // 输出所有的方法调用
        Set<String> methodCalls = visitor.getMethodCalls();
        for (String methodCall : methodCalls) {
            System.out.println(methodCall);
        }
    }

    private static class MethodCallVisitor extends VoidVisitorAdapter<Void> {
        private Set<String> methodCalls = new HashSet<>();

        public Set<String> getMethodCalls() {
            return methodCalls;
        }

        @Override
        public void visit(MethodCallExpr n, Void arg) {
            super.visit(n, arg);
            methodCalls.add(n.getNameAsString());
        }
    }
}
