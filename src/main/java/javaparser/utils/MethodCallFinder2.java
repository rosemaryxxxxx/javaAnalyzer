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

//获取java文件内所有的函数调用，未去重
public class MethodCallFinder2 {
    public static String PATH = "D:\\code\\javaAnalyzer\\src\\main\\java\\pmd\\deadcodetest\\utils\\utils.KMP.java";

    public static void main(String[] args) throws Exception {
        // 读取Java源文件
        FileInputStream in = new FileInputStream(PATH);

        // 解析Java源文件
        CompilationUnit cu = StaticJavaParser.parse(in);

        // Visit all methods in the AST
        cu.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(MethodDeclaration md, Void arg) {
                // Visit all nodes in the method body
                md.getBody().ifPresent(body -> {
                    body.accept(new VoidVisitorAdapter<Void>() {
                        @Override
                        public void visit(MethodCallExpr call, Void arg) {
                            // Get the name of the called method
                            String methodName = call.getNameAsString();

                            // Do something with the method name
                            System.out.println("Method call: " + methodName);
                        }
                    }, null);
                });
            }
        }, null);

    }
}

