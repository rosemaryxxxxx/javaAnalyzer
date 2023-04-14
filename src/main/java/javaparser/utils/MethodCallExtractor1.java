package javaparser.utils;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class MethodCallExtractor1 {
//    public static String PATH = "D:\\code\\javaAnalyzer\\src\\main\\java\\pmd\\deadcodetest\\utils\\callsss.java";
    private static String PATH;
    public static String pakageName;
    public static List<String> imports;
    public static List<StringBuffer> fullMethods;
    public static Map<StringBuffer, Set<String>> methodCallsWithCallee;

    public static Map<StringBuffer, Set<String>> getMethodCallsWithCallee() {
        return methodCallsWithCallee;
    }

    public static void setMethodCallsWithCallee(Map<StringBuffer, Set<String>> methodCallsWithCallee) {
        MethodCallExtractor1.methodCallsWithCallee = methodCallsWithCallee;
    }

    public static List<StringBuffer> getFullMethods() {
        return fullMethods;
    }

    public static void setFullMethods(List<StringBuffer> fullMethods) {
        MethodCallExtractor1.fullMethods = fullMethods;
    }

    public static String getPATH() {
        return PATH;
    }
    public static void setPATH(String PATH) {
        MethodCallExtractor1.PATH = PATH;
    }


    public static String getPakageName() {
        return pakageName;
    }

    public static void setPakageName(String pakageName) {
        MethodCallExtractor1.pakageName = pakageName;
    }

    public static List<String> getImports() {
        return imports;
    }

    public static void setImports(List<String> imports) {
        MethodCallExtractor1.imports = imports;
    }

    public void startParse() throws FileNotFoundException {
        FileInputStream in = new FileInputStream(PATH);
        CompilationUnit cu = StaticJavaParser.parse(in);

        MethodCallVisitor visitor = new MethodCallVisitor();
        visitor.visit(cu, null);

        //输出packageName
        String packageName = visitor.getPackageName();
        setPakageName(packageName);
//        System.out.println("package:"+packageName);

        //输出imports
        List<String> imports = visitor.getImports();
        setImports(imports);
//        for (String i : imports) {
//            System.out.println("import:"+i);
//        }

        //输出方法的完整路径以及其调用的方法
        Map<StringBuffer, Set<String>> methodCalls = visitor.getMethodCalls();
        setMethodCallsWithCallee(methodCalls);
        List<StringBuffer> fullMethods = new ArrayList<>();
        for (Map.Entry<StringBuffer, Set<String>> entry : methodCalls.entrySet()) {
            fullMethods.add(entry.getKey());
//            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        setFullMethods(fullMethods);

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
//            System.out.println("package:"+n.getName());
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
        public String getPackageName() { return packageName;}

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

