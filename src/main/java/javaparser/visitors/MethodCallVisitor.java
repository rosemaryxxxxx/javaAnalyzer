package javaparser.visitors;

import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class MethodCallVisitor extends VoidVisitorAdapter<Void> {

    public String packageName;
    public String methodName;

//    @Override
//    public void visit(MethodDeclaration n, Void arg) {
////        currentClass = n.resolve().getQualifiedName();
//        super.visit(n, arg);
//    }

//    @Override
//    public void visit(MethodCallExpr n, Void arg) {
//        String methodName = n.getNameAsString();
//        String methodPath = currentClass + "." + methodName;
//        System.out.println("Method Name: " + methodName);
//        System.out.println("Method Path: " + methodPath);
//        super.visit(n, arg);
//    }


    @Override
    public void visit(MethodDeclaration n, Void arg) {
            /* here you can access the attributes of the method.
             this method will be called for all methods in this
             CompilationUnit, including inner class methods */
        System.out.println("method name:"+n.getName());
        methodName = n.getNameAsString();
        super.visit(n, arg);
    }
//
//    @Override
//    public void visit(BlockStmt n, Void arg){
//        System.out.println("method body:"+n.toString());
//        super.visit(n, arg);
//    }
//
//    @Override
//    /**
//     * 截取javadoc第一句
//     */
//    public void visit(JavadocComment n, Void arg){
////            System.out.println("package:"+n.toString());
//        String des = n.toString();
//        des = des.substring(8);
//        String description = des.substring(0,des.indexOf('\n'));
//        System.out.println("description:"+description);
//        super.visit(n, arg);
//    }

//        @Override
//        public void visit(ClassOrInterfaceDeclaration n, Void arg) {
//            System.out.println("class:"+n.getName());
//            System.out.println("extends:"+n.getExtendedTypes());
//            System.out.println("implements:"+n.getImplementedTypes());
//
//            super.visit(n, arg);
//        }


        @Override
        public void visit(PackageDeclaration n, Void arg) {
            System.out.println("package:"+n.getName());
            packageName = n.getNameAsString();
            super.visit(n, arg);
        }
}
