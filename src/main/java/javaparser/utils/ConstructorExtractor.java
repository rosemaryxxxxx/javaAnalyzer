package javaparser.utils;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ConstructorDeclaration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class ConstructorExtractor {
    public static void main(String[] args) throws FileNotFoundException {
        // 文件路径
        String filename = "D:\\code\\javazip\\t419\\src\\HEncoder.java";
        File file = new File(filename);

        // 使用JavaParser解析Java文件
        CompilationUnit cu = StaticJavaParser.parse(file);

        // 获取所有构造函数
        List<ConstructorDeclaration> constructors = cu.findAll(ConstructorDeclaration.class);
        for (ConstructorDeclaration constructor : constructors) {
            System.out.println(constructor.getDeclarationAsString());
        }
    }
}
