package javaparser.utils;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

import static shixian.utils.Utils.getMethodName;
import static shixian.utils.Utils.replaceSlashWithPoint;

public class MethodCallExtractor1 {
//    public static String PATH = "D:\\code\\javaAnalyzer\\src\\main\\java\\pmd\\deadcodetest\\utils\\callsss.java";
    private static String PATH;
    public static String pakageName;
    public static List<String> imports;
    public static List<String> fullMethods;
    public static Map<String, Set<String>> methodCallsWithCallee;
    public static Stack<String> mainAndCalleeOfMain;
    private static  String beforeZipName;

    public static String getBeforeZipName() {
        return beforeZipName;
    }

    public static void setBeforeZipName(String beforeZipName) {
        MethodCallExtractor1.beforeZipName = beforeZipName;
    }

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
     * 获取方法集合，main栈，map.为了生成完整的方法路径所以传入参数beforeZipName
     * @param beforeZipName
     * @throws FileNotFoundException
     */
    public void preStartParse(String beforeZipName) throws FileNotFoundException {
        FileInputStream in = new FileInputStream(PATH);

        String packageName1 = replaceSlashWithPoint(PATH.substring(index0fLastSlash(beforeZipName)+1));
        setPakageName(packageName1);
        //当解析出现错误时候，比如.java文件中存在语法错误，需要继续解析下一个.java文件，捕获异常，继续执行
        try{
            CompilationUnit cu = StaticJavaParser.parse(in);
            MethodCallVisitor visitor = new MethodCallVisitor();
            visitor.visit(cu, null);

            //输出packageName
//            String packageName = visitor.getPackageName();
//            setPakageName(packageName);


//        System.out.println("package:"+packageName);

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
        }catch (Exception e){
//            e.printStackTrace();
        }
    }

    public void startParse(String beforeZipName) throws FileNotFoundException {

        String packageName1 = replaceSlashWithPoint(PATH.substring(index0fLastSlash(beforeZipName)+1));
        setPakageName(packageName1);
        FileInputStream in = new FileInputStream(PATH);
        try {
            CompilationUnit cu = StaticJavaParser.parse(in);

            MethodCallVisitor visitor = new MethodCallVisitor();
            visitor.visit(cu, null);

            List<String> imports = visitor.getImports();
            imports.add(packageName1);
            setImports(imports);


            Stack<String> mainAndCalleeOfMain = new Stack<>();
            Map<String, Set<String>> methodCalls = visitor.getMethodCalls();
            for (Map.Entry<String, Set<String>> entry : methodCalls.entrySet()) {
                if (getMethodName(entry.getKey()).equals("main")) {
                    mainAndCalleeOfMain.push(entry.getKey());
                }
            }
            setMainAndCalleeOfMain(mainAndCalleeOfMain);
        }catch (Exception e){
            //            e.printStackTrace();
        }
    }




    private static class MethodCallVisitor extends VoidVisitorAdapter<Void> {

        private Map<String, Set<String>> methodCalls = new HashMap<>();
        private StringBuffer currentMethod;
        private String packageName2;
        private List<String> imports = new ArrayList<>();

        @Override
        public void visit(MethodDeclaration n, Void arg) {
//            currentMethod = n.getNameAsString();
            setPackageName2(pakageName);
            //获取完整路径
            currentMethod = new StringBuffer();
            currentMethod.append(packageName2+"."+n.getName()+"(");
            n.getParameters().forEach(parameter -> currentMethod.append(parameter.getType()));
            currentMethod.append(")");
            methodCalls.put(currentMethod.toString(), new HashSet<>());
            super.visit(n, arg);
        }

        @Override
        public void visit(ConstructorDeclaration n , Void arg){
            setPackageName2(pakageName);
            //获取完整路径
            currentMethod = new StringBuffer();
            currentMethod.append(packageName2+"."+n.getName()+"(");
            n.getParameters().forEach(parameter -> currentMethod.append(parameter.getType()));
            currentMethod.append(")");
            methodCalls.put(currentMethod.toString(), new HashSet<>());
            super.visit(n, arg);
        }

        @Override
        public void visit(MethodCallExpr n, Void arg) {
            String methodName = n.getNameAsString();
            //把被调用的方法名加入到对应set中
            if(currentMethod!=null){
                methodCalls.get(currentMethod.toString()).add(methodName);
            }
            super.visit(n, arg);
        }
        @Override
        public void visit(ObjectCreationExpr n, Void arg) {
            String constructor = n.getTypeAsString();
            if(currentMethod!=null){
                methodCalls.get(currentMethod.toString()).add(constructor);
            }
            super.visit(n, arg);
        }

//        @Override
//        public void visit(PackageDeclaration n, Void arg) {
////            System.out.println("package:"+n.getName());
//            packageName = n.getNameAsString();
//            //好像：当一个java文件中没有pakage字段的时候，pakage不是null，而是根本不存在
////            if(packageName == null){
////                imports.add("null."+getClassName(PATH));
////            }
//            //把pakage也装入imports中
//            imports.add(packageName+"."+getClassName(PATH));
//            super.visit(n, arg);
//        }

        @Override
        public void visit(ImportDeclaration n, Void arg) {
            imports.add(n.getNameAsString());
            super.visit(n, arg);
        }

        public List<String> getImports() {
            return imports;
        }

        public String getPackageName2() {
            return packageName2;
        }

        public void setPackageName2(String packageName2) {
            this.packageName2 = packageName2;
        }

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
    public static int index0fLastSlash(String beforeZipName){
        int size = beforeZipName.length();
        int j;
        for(j = size-1;j>0;j--){
            if(beforeZipName.charAt(j) == '\\'){
                break;
            }
        }
        return j;
    }

}

