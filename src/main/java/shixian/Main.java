package shixian;

import javaparser.utils.MethodCallExtractor1;

import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    //装载所有的import
//    public static List<String> imports = new ArrayList<>();
    //装载所有的方法的完整路径
    public static List<StringBuffer> fullMethods = new ArrayList<>();
    //装载所有的方法的完整路径以及其调用方法的方法名（非完整路径）
    public static Map<StringBuffer, Set<String>> methodCallsWithCallee = new HashMap<>();

    /**
     * 解析.java文件，获得其import集合、方法完整路径集合、完整路径以及其调用方法的方法名（非完整路径）集合
     * @param path
     */
    public static void parseJava(String path) throws FileNotFoundException {
        //装载所有的import,每一个.java文件对应的imports集合不一样
        List<String> imports = new ArrayList<>();

        MethodCallExtractor1 methodCallExtractor1 = new MethodCallExtractor1();
//        methodCallExtractor1.setPATH("D:\\code\\javaAnalyzer\\src\\main\\java\\pmd\\deadcodetest\\utils\\KMP.java");
        methodCallExtractor1.setPATH(path);
        methodCallExtractor1.startParse();
//        System.out.println("packageName:"+methodCallExtractor1.getPakageName());
//        System.out.println(methodCallExtractor1.getImports());
        imports.addAll(methodCallExtractor1.getImports());

        fullMethods.addAll(methodCallExtractor1.getFullMethods());

        Map<StringBuffer, Set<String>> temp = new HashMap<>();
        temp.putAll(methodCallsWithCallee);
        temp.putAll(methodCallExtractor1.getMethodCallsWithCallee());
        methodCallsWithCallee = temp;
    }


    public static void main(String[] srgs) throws FileNotFoundException {
        List<String> paths = new ArrayList<>();
        paths.add("D:\\code\\javaAnalyzer\\src\\main\\java\\pmd\\deadcodetest\\utils\\KMP.java");
        paths.add("D:\\code\\javaAnalyzer\\src\\main\\java\\pmd\\deadcodetest\\utils\\callsss.java");
        for(String path : paths){
            parseJava(path);
        }
        System.out.println(fullMethods);
        System.out.println(methodCallsWithCallee);

    }
}
