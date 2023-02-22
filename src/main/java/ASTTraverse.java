import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.printer.YamlPrinter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;

public class ASTTraverse {
    public static String PATH = "D:\\code\\javazip\\t\\t1\\t219.java";

    public static void main(String[] args) throws IOException {
        ASTTraverse astTraverse = new ASTTraverse();
        astTraverse.astTraverse(PATH);
    }

    /**
     * 遍历AST，从AST中获取想要的元素
     * @param path
     * @throws FileNotFoundException
     */
    public void astTraverse(String path) throws FileNotFoundException {
        FileInputStream in = new FileInputStream(path);
        ParseResult<CompilationUnit> result = new JavaParser().parse(in);
        if(result.getResult().isPresent()){
            result.getResult().get().accept(new MethodVisitor(), null);
//            result.getResult().ifPresent(YamlPrinter::print);
        }
    }

    static class MethodVisitor extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(MethodDeclaration n, Void arg) {
            /* here you can access the attributes of the method.
             this method will be called for all methods in this
             CompilationUnit, including inner class methods */
            System.out.println("method name:"+n.getName());
            super.visit(n, arg);
        }

        @Override
        public void visit(BlockStmt n, Void arg){
            System.out.println("method body:"+n.toString());
            super.visit(n, arg);
        }

        @Override
        /**
         * 截取javadoc第一句
         */
        public void visit(JavadocComment n, Void arg){
//            System.out.println("package:"+n.toString());
            String des = n.toString();
            des = des.substring(8);
            String description = des.substring(0,des.indexOf('\n'));
            System.out.println("description:"+description);
            super.visit(n, arg);
        }

//        @Override
//        public void visit(ClassOrInterfaceDeclaration n, Void arg) {
//            System.out.println("class:"+n.getName());
//            System.out.println("extends:"+n.getExtendedTypes());
//            System.out.println("implements:"+n.getImplementedTypes());
//
//            super.visit(n, arg);
//        }


//        @Override
//        public void visit(PackageDeclaration n, Void arg) {
//            System.out.println("package:"+n.getName());
//            super.visit(n, arg);
//        }
    }

}
