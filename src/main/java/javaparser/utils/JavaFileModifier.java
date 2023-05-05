package javaparser.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;

public class JavaFileModifier {
    public static void removeMethod(String filePath, String methodName) throws IOException {
        // 从文件中读取Java代码
        FileInputStream in = new FileInputStream(filePath);
        CompilationUnit cu = StaticJavaParser.parse(in);
        in.close();

        // 查找要删除的方法
        List<TypeDeclaration<?>> types = cu.getTypes();
        for(TypeDeclaration<?> type : types) {
            List<MethodDeclaration> methods = type.getMethods();
            for(MethodDeclaration method : methods) {
                if(method.getNameAsString().equals(methodName)) {
                    // 删除方法体语句
//                    method.getBody().ifPresent(body -> body.getStatements().clear());
                      method.remove();
                }
            }
        }

        // 将修改后的代码写回文件
        FileOutputStream out = new FileOutputStream(filePath);
        out.write(cu.toString().getBytes());
        out.close();
    }

    public static void main(String[] args) throws IOException {
        removeMethod("D:\\code\\javazip\\t0504\\pmd\\deadcodetest\\utils\\KMP.java", "kmp");
    }
}
