package javaparser.visitors;

import com.github.javaparser.JavaParser;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class CodeDeleter extends VoidVisitorAdapter<Void> {
    private int startLine;
    private int endLine;

    public CodeDeleter(int startLine, int endLine) {
        this.startLine = startLine;
        this.endLine = endLine;
    }

    @Override
    public void visit(BlockStmt n, Void arg) {
        super.visit(n, arg);
        int beginLine = n.getBegin().get().line;
        int endLine = n.getEnd().get().line;
        if (beginLine >= startLine && endLine <= endLine) {
            n.remove();
        }
    }

    public static void main(String[] args) throws Exception {
        // 读取 Java 文件
        FileInputStream in = new FileInputStream("D:\\code\\javazip\\t0504\\pmd\\deadcodetest\\utils\\KMP.java");

        // 解析 Java 文件
        CompilationUnit cu = StaticJavaParser.parse(in);

        // 删除指定范围内的代码
        CodeDeleter deleter = new CodeDeleter(23, 27);
        deleter.visit(cu, null);

        // 将修改后的 AST 输出到文件
        FileOutputStream out = new FileOutputStream("D:\\code\\javazip\\t0504\\pmd\\deadcodetest\\utils\\KMP.java");
        out.write(cu.toString().getBytes());
        out.close();

        // 关闭输入流
        in.close();
    }
}
