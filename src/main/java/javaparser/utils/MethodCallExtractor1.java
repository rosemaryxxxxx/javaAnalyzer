package javaparser.utils;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.h2.util.IntArray;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.PseudoColumnUsage;
import java.util.*;

import static shixian.utils.Utils.*;

public class MethodCallExtractor1 {
//    public static String PATH = "D:\\code\\javaAnalyzer\\src\\main\\java\\pmd\\deadcodetest\\utils\\callsss.java";
    private static String PATH;
    public static String pakageName;
    public static List<String> imports = new ArrayList<>();
    public static List<String> fullMethods = new ArrayList<>();
    public static Map<String, Set<String>> methodCallsWithCallee = new HashMap<>();
    public static Stack<String> mainAndCalleeOfMain = new Stack<>();
    private static String beforeZipName;
    public static Map<String , List<String>> methodAndItsImports = new HashMap<>();
    public static Map<String,List<Integer>> methodAndItsPosition = new HashMap<>();
    private static Map<String,String> methodAndItsType = new HashMap<>();
    private static Map<String,Map<String,String>> classAndAugmentType = new HashMap<>();


    public static Map<String, List<Integer>> getMethodAndItsPosition() {
        return methodAndItsPosition;
    }

    public static void setMethodAndItsPosition(Map<String, List<Integer>> methodAndItsPosition) {
        MethodCallExtractor1.methodAndItsPosition = methodAndItsPosition;
    }

    public static Map<String, List<String>> getMethodAndItsImports() {
        return methodAndItsImports;
    }

    public static void setMethodAndItsImports(Map<String, List<String>> methodAndItsImports) {
        MethodCallExtractor1.methodAndItsImports = methodAndItsImports;
    }

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

    public Map<String, String> getMethodAndItsType() {
        return methodAndItsType;
    }

    public void setMethodAndItsType(Map<String, String> methodAndItsType) {
        this.methodAndItsType = methodAndItsType;
    }

    public static Map<String, Map<String, String>> getClassAndAugmentType() {
        return classAndAugmentType;
    }

    public static void setClassAndAugmentType(Map<String, Map<String, String>> classAndAugmentType) {
        MethodCallExtractor1.classAndAugmentType = classAndAugmentType;
    }

    /**
     * 获取方法集合(除去main函数)，map.为了生成完整的方法路径所以传入参数beforeZipName
     * @param beforeZipName
     * @throws FileNotFoundException
     */
    public void preStartParse(String beforeZipName) throws FileNotFoundException {
        FileInputStream in = new FileInputStream(PATH);
        new File("ds/ds-sd");

        //example: packageName1 = pmd.deadcodetest.utils.KMP
        String packageName1 = replaceSlashWithPoint(PATH.substring(index0fLastSlash(beforeZipName)+1));
        setPakageName(packageName1);

        //当解析出现错误时候，比如.java文件中存在语法错误，需要继续解析下一个.java文件，捕获异常，继续执行
        try{
            CompilationUnit cu = StaticJavaParser.parse(in);
            MethodCallVisitor visitor = new MethodCallVisitor();
            visitor.visit(cu, null);

            //获取位置信息
            Map<String,List<Integer>> methodAndItsPosition1 = visitor.getMethodAndItsPosition2();
            setMethodAndItsPosition(methodAndItsPosition1);

            //输出方法的完整路径以及其调用的方法
            Map<String, Set<String>> methodCalls = visitor.getMethodCalls();
            setMethodCallsWithCallee(methodCalls);
            List<String> fullMethods = new ArrayList<>();

            //methodAndItsImports
            Map<String,List<String>> methodAndItsImports = visitor.getMethodAndItsImports2();
            //给methodAndItsImports中的imports都加上本java文件的路径
//            for(Map.Entry<String, List<String>> entry1 : methodAndItsImports.entrySet() ){
//                entry1.getValue().add(packageName1);
//            }
            //检查方法是否属于某一个包的时候使用包名的路径而不是类的路径
            String pakageNameWithoutClassName = packageName1.substring(0,index0fLastPoint(packageName1));
            for(Map.Entry<String, List<String>> entry1 : methodAndItsImports.entrySet() ){
                entry1.getValue().add(pakageNameWithoutClassName);
                //没懂为啥要break，通过debug得出规律
                break;
            }
            setMethodAndItsImports(methodAndItsImports);

            //fullMethods
            for (Map.Entry<String, Set<String>> entry : methodCalls.entrySet()) {
                //所有方法的集合不包含main方法
//                if(!getMethodName(entry.getKey()).equals("main")){
//                    fullMethods.add(entry.getKey());
//                }
                fullMethods.add(entry.getKey());
//            System.out.println(entry.getKey() + ": " + entry.getValue());
            }
            setFullMethods(fullMethods);

            //methodAndItsType
            Map<String, String> methodAndItsType2 = visitor.getMethodAndItsType2();
            setMethodAndItsType(methodAndItsType2);


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

//            List<String> imports = visitor.getImports();
//            imports.add(packageName1);
//            setImports(imports);

            Stack<String> mainAndCalleeOfMain = new Stack<>();
            Map<String, Set<String>> methodCalls = visitor.getMethodCalls();
            for (Map.Entry<String, Set<String>> entry : methodCalls.entrySet()) {
//                String liveMehod = "fillShapeCreationList";
                String liveMehod = "main";
                String entrance = "toSVG";
                if (getMethodName(entry.getKey()).equals(liveMehod) || getMethodName(entry.getKey()).equals(entrance) ) {
                    mainAndCalleeOfMain.push(entry.getKey());
                }
            }
            setMainAndCalleeOfMain(mainAndCalleeOfMain);
        }catch (Exception e){
//            e.printStackTrace();
        }
    }




    static class MethodCallVisitor extends VoidVisitorAdapter<Void> {

        private Map<String, Set<String>> methodCalls = new HashMap<>();
        private StringBuffer currentMethod;
        private String packageName2;
        private List<String> imports = new ArrayList<>();
        private Map<String,List<String>> methodAndItsImports2 = new HashMap<>();
        private Map<String,List<Integer>> methodAndItsPosition2 = new HashMap<>();
        private Map<String,String> methodAndItsType2 = new HashMap<>();
        private Map<String,Map<String,String>> classAndAugmentType2 = new HashMap<>();

        @Override
        public void visit(MethodDeclaration n, Void arg) {

            // Get the variable declarations for this method
//            n.getBody().ifPresent(body -> body.findAll(VariableDeclarationExpr.class).forEach(vde -> {
//
//                // Iterate over each variable in the declaration and print its type
//                vde.getVariables().forEach(variable -> System.out.println(variable.getName()+": "+variable.getTypeAsString()));
//            }));

//            currentMethod = n.getNameAsString();

            setPackageName2(pakageName);
            //获取完整路径
            currentMethod = new StringBuffer();
            currentMethod.append(packageName2+"."+n.getName()+"(");
            //使用“？”而不使用“,”来隔离参数，是为了防止后续在分离参数的时候出错
            n.getParameters().forEach(parameter -> currentMethod.append(parameter.getType()).append("?"));
            //考虑到currentMethod没有参数的情况
            if(currentMethod.charAt(currentMethod.length()-1) == '?'){
                currentMethod.deleteCharAt(currentMethod.length()-1);
            }
            currentMethod.append(")");

            methodCalls.put(currentMethod.toString(), new HashSet<>());

            methodAndItsImports2.put(currentMethod.toString(),imports);

            methodAndItsType2.put(currentMethod.toString(),n.getTypeAsString());

            //获取方法的位置信息
            int startLine = n.getBegin().get().line;
            int startCol = n.getBegin().get().column;
            int endLine = n.getEnd().get().line;
            int endCol = n.getEnd().get().column;
            List<Integer> positionList = new ArrayList<>();
            positionList.add(startLine);
            positionList.add(startCol);
            positionList.add(endLine);
            positionList.add(endCol);
            methodAndItsPosition2.put(currentMethod.toString(),positionList);

            super.visit(n, arg);
        }

        @Override
        public void visit(ConstructorDeclaration n , Void arg){
            setPackageName2(pakageName);
            //获取完整路径
            currentMethod = new StringBuffer();
            currentMethod.append(packageName2+"."+n.getName()+"(");
            n.getParameters().forEach(parameter -> currentMethod.append(parameter.getType()).append("?"));
            if(currentMethod.charAt(currentMethod.length()-1) == '?'){
                currentMethod.deleteCharAt(currentMethod.length()-1);
            }
            currentMethod.append(")");
            methodCalls.put(currentMethod.toString(), new HashSet<>());

            methodAndItsImports2.put(currentMethod.toString(),imports);


            //获取构造方法的位置信息
            int startLine = n.getBegin().get().line;
            int startCol = n.getBegin().get().column;
            int endLine = n.getEnd().get().line;
            int endCol = n.getEnd().get().column;
            List<Integer> positionList = new ArrayList<>();
            positionList.add(startLine);
            positionList.add(startCol);
            positionList.add(endLine);
            positionList.add(endCol);
            methodAndItsPosition2.put(currentMethod.toString(),positionList);

            super.visit(n, arg);
        }

        @Override
        public void visit(MethodCallExpr n, Void arg) {
//            String className = getClassName(pakageName);
            setClassAndAugmentType2(classAndAugmentType);
            String methodName = n.getNameAsString();
//            methodCalls.get(currentMethod.toString()).add(methodName+"()");
            StringBuilder  methodNameWithArgument = new StringBuilder(methodName);
            methodNameWithArgument.append('(');
            //参数获取
            NodeList<Expression> nodeList = n.getArguments();
            if(currentMethod != null && nodeList != null){
                if(nodeList.size() == 0){
                    methodCalls.get(currentMethod.toString()).add(methodName+"()");
                }else {
                    for (Expression expression : nodeList) {
                        //对参数的具体操作
                        //解析参数对应的类型
                        methodNameWithArgument.append(analyseType(expression.toString(),pakageName, classAndAugmentType));
                        methodNameWithArgument.append('?');
                    }
                    methodNameWithArgument.deleteCharAt(methodNameWithArgument.length()-1);
                    methodNameWithArgument.append(')');
                    methodCalls.get(currentMethod.toString()).add(methodNameWithArgument.toString());
                }
            }
            super.visit(n, arg);
        }
        //构造函数
        @Override
        public void visit(ObjectCreationExpr n, Void arg) {
            setClassAndAugmentType2(classAndAugmentType);
            String constructor = n.getTypeAsString();
            StringBuilder constructorNameWithArgument = new StringBuilder(constructor);
            constructorNameWithArgument.append('(');
            //参数获取
            NodeList<Expression> nodeList = n.getArguments();
            if(currentMethod != null && nodeList != null){
                if(nodeList.size() == 0){
                    methodCalls.get(currentMethod.toString()).add(constructor+"()");
                }else {
                    for (Expression expression : nodeList) {
                        constructorNameWithArgument.append(analyseType(expression.toString(),pakageName,classAndAugmentType));
                        constructorNameWithArgument.append('?');
                    }
                    constructorNameWithArgument.deleteCharAt(constructorNameWithArgument.length()-1);
                    constructorNameWithArgument.append(')');
                    methodCalls.get(currentMethod.toString()).add(constructorNameWithArgument.toString());
                }
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

        public Map<String, List<String>> getMethodAndItsImports2() {
            return methodAndItsImports2;
        }

        public void setMethodAndItsImports2(Map<String, List<String>> methodAndItsImports2) {
            this.methodAndItsImports2 = methodAndItsImports2;
        }

        public Map<String, List<Integer>> getMethodAndItsPosition2() {
            return methodAndItsPosition2;
        }

        public void setMethodAndItsPosition2(Map<String, List<Integer>> methodAndItsPosition2) {
            this.methodAndItsPosition2 = methodAndItsPosition2;
        }

        public Map<String, String> getMethodAndItsType2() {
            return methodAndItsType2;
        }

        public void setMethodAndItsType2(Map<String, String> methodAndItsType2) {
            this.methodAndItsType2 = methodAndItsType2;
        }

        public Map<String, Map<String, String>> getClassAndAugmentType2() {
            return classAndAugmentType2;
        }

        public void setClassAndAugmentType2(Map<String, Map<String, String>> classAndAugmentType2) {
            this.classAndAugmentType2 = classAndAugmentType2;
        }
    }



}

