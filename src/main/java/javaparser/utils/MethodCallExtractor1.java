package javaparser.utils;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class MethodCallExtractor1 {
    public static String PATH = "D:\\code\\javaAnalyzer\\src\\main\\java\\pmd\\deadcodetest\\utils\\KMP.java";

    public static void main(String[] args) throws Exception {
        FileInputStream in = new FileInputStream(new File(PATH));
        CompilationUnit cu = StaticJavaParser.parse(in);

        MethodCallVisitor visitor = new MethodCallVisitor();
        visitor.visit(cu, null);

        Map<StringBuffer, Set<String>> methodCalls = visitor.getMethodCalls();
        for (Map.Entry<StringBuffer, Set<String>> entry : methodCalls.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        List<String> imports = visitor.getImports();
        for (String i : imports) {
            System.out.println("import:"+i);
        }
    }

    private static class MethodCallVisitor extends VoidVisitorAdapter<Void> {

        private Map<StringBuffer, Set<String>> methodCalls = new HashMap<>();
        private StringBuffer currentMethod;
        private String packageName;
        private List<String> imports = new ArrayList<>();

        @Override
        public void visit(MethodDeclaration n, Void arg) {
//            currentMethod = n.getNameAsString();
            //获取完整路径
            currentMethod = new StringBuffer();
            currentMethod.append(packageName+"."+getClassName(PATH)+"."+n.getName()+"(");
            n.getParameters().forEach(parameter -> currentMethod.append(parameter.getType()));
            currentMethod.append(")");
            methodCalls.put(currentMethod, new HashSet<>());
            super.visit(n, arg);
        }

        @Override
        public void visit(MethodCallExpr n, Void arg) {
            String methodName = n.getNameAsString();
            methodCalls.get(currentMethod).add(methodName);
            super.visit(n, arg);
        }

        @Override
        public void visit(PackageDeclaration n, Void arg) {
            System.out.println("package:"+n.getName());
            packageName = n.getNameAsString();
            super.visit(n, arg);
        }

        @Override
        public void visit(ImportDeclaration n, Void arg) {
            imports.add(n.getNameAsString());
            super.visit(n, arg);
        }

        public List<String> getImports() {
            return imports;
        }

        public Map<StringBuffer, Set<String>> getMethodCalls() {
            return methodCalls;
        }
    }

    public static String getClassName(String path){
        int size = path.length();
        int left = 0,right = 0;
        for(int i=size-1;i>0;i--){
            if(path.charAt(i) == '.'){
                right = i;
            }
            if(path.charAt(i) == '\\'){
                left = i;
                break;
            }
        }
        return path.substring(left+1,right);
    }

}

