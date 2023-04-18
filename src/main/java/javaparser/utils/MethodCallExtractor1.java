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
import shixian.utils.Utils.*;

import static shixian.utils.Utils.getMethodName;

public class MethodCallExtractor1 {
//    public static String PATH = "D:\\code\\javaAnalyzer\\src\\main\\java\\pmd\\deadcodetest\\utils\\callsss.java";
    private static String PATH;
    public static String pakageName;
    public static List<String> imports;
    public static List<String> fullMethods;
    public static Map<String, Set<String>> methodCallsWithCallee;
    public static Stack<String> mainAndCalleeOfMain;

    public static Stack<String> getMainAndCalleeOfMain() {
        return mainAndCalleeOfMain;
    }

    public static void setMainAndCalleeOfMain(Stack<String> mainAndCalleeOfMain) {
        MethodCallExtractor1.mainAndCalleeOfMain = mainAndCalleeOfMain;
    }

    public static Map<String, Set<String>> getMethodCallsWithCallee() {
        return methodCallsWithCallee;
    }

    public static void setMethodCallsWithCallee(Map<String, Set<String>> methodCallsWithCallee) {
        MethodCallExtractor1.methodCallsWithCallee = methodCallsWithCallee;
    }

    public static List<String> getFullMethods() {
        return fullMethods;
    }

    public static void setFullMethods(List<String> fullMethods) {
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

    /**
     * 获取方法集合，main栈，map
     * @throws FileNotFoundException
     */
    public void preStartParse() throws FileNotFoundException {
        FileInputStream in = new FileInputStream(PATH);
        CompilationUnit cu = StaticJavaParser.parse(in);

        MethodCallVisitor visitor = new MethodCallVisitor();
        visitor.visit(cu, null);

        //输出packageName
        String packageName = visitor.getPackageName();
        setPakageName(packageName);
//        System.out.println("package:"+packageName);

        //输出imports
//        List<String> imports = visitor.getImports();
//        setImports(imports);
//        for (String i : imports) {
//            System.out.println("import:"+i);
//        }

        //输出方法的完整路径以及其调用的方法
        Map<String, Set<String>> methodCalls = visitor.getMethodCalls();
        setMethodCallsWithCallee(methodCalls);
        List<String> fullMethods = new ArrayList<>();

        for (Map.Entry<String, Set<String>> entry : methodCalls.entrySet()) {
            if(!getMethodName(entry.getKey()).equals("main")){
                fullMethods.add(entry.getKey());
            }
//            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        setFullMethods(fullMethods);


    }

    public void startParse() throws FileNotFoundException {
        FileInputStream in = new FileInputStream(PATH);
        CompilationUnit cu = StaticJavaParser.parse(in);

        MethodCallVisitor visitor = new MethodCallVisitor();
        visitor.visit(cu, null);

        List<String> imports = visitor.getImports();
        setImports(imports);

        Stack<String> mainAndCalleeOfMain = new Stack<>();
        Map<String, Set<String>> methodCalls = visitor.getMethodCalls();
        for (Map.Entry<String, Set<String>> entry : methodCalls.entrySet()) {
            if(getMethodName(entry.getKey()).equals("main")){
                mainAndCalleeOfMain.push(entry.getKey());
            }
        }
        setMainAndCalleeOfMain(mainAndCalleeOfMain);
    }




    private static class MethodCallVisitor extends VoidVisitorAdapter<Void> {

        private Map<String, Set<String>> methodCalls = new HashMap<>();
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
            methodCalls.put(currentMethod.toString(), new HashSet<>());
            super.visit(n, arg);
        }

        @Override
        public void visit(MethodCallExpr n, Void arg) {
            String methodName = n.getNameAsString();
            //把被调用的方法名加入到对应set中
            methodCalls.get(currentMethod.toString()).add(methodName);
            super.visit(n, arg);
        }

        @Override
        public void visit(PackageDeclaration n, Void arg) {
//            System.out.println("package:"+n.getName());
            packageName = n.getNameAsString();
            //把pakage也装入imports中
            imports.add(packageName+"."+getClassName(PATH));
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

        public Map<String, Set<String>> getMethodCalls() {
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

