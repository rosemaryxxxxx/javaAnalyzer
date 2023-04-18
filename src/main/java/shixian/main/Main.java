package shixian.main;

import javaparser.utils.MethodCallExtractor1;
import utils.KMP;

import java.io.FileNotFoundException;
import java.util.*;

import static shixian.utils.Utils.getMethodName;
import static shixian.utils.Utils.removeStarIfExist;

public class Main {
    //装载所有的import
//    public static List<String> imports = new ArrayList<>();
    //装载所有的方法的完整路径
    public static List<String> fullMethods = new ArrayList<>();
    //装载所有的方法的完整路径以及其调用方法的方法名（非完整路径）
    public static Map<String, Set<String>> methodCallsWithCallee = new HashMap<>();
//    //装载所有main方法和main方法调用的方法，以及后续调用的方法
//    public static Stack<String> mainAndCalleeOfMain = new Stack<>();

    /**
     * 解析.java文件，获得其import集合、方法完整路径集合、完整路径以及其调用方法的方法名（非完整路径）集合
     * @param path
     */
    public static void preParseJava(String path) throws FileNotFoundException {
        //装载所有的import,每一个.java文件对应的imports集合不一样
//        List<String> imports = new ArrayList<>();

        MethodCallExtractor1 methodCallExtractor1 = new MethodCallExtractor1();
//        methodCallExtractor1.setPATH("D:\\code\\javaAnalyzer\\src\\main\\java\\pmd\\deadcodetest\\utils\\utils.KMP.java");
        methodCallExtractor1.setPATH(path);
        methodCallExtractor1.preStartParse();

        fullMethods.addAll(methodCallExtractor1.getFullMethods());


        Map<String, Set<String>> temp = new HashMap<>();
        temp.putAll(methodCallsWithCallee);
        temp.putAll(methodCallExtractor1.getMethodCallsWithCallee());
        methodCallsWithCallee = temp;
    }

    /**
     * 根据方法调用
     * @param path
     * @throws FileNotFoundException
     */
    public static void parseJava(String path) throws FileNotFoundException {
        MethodCallExtractor1 methodCallExtractor1 = new MethodCallExtractor1();
        methodCallExtractor1.setPATH(path);
        methodCallExtractor1.startParse();

        List<String> imports = new ArrayList<>();
        imports = methodCallExtractor1.getImports();

        Stack<String> mainAndCalleeOfMain = new Stack<>();
        mainAndCalleeOfMain.addAll(methodCallExtractor1.getMainAndCalleeOfMain());


        while (!mainAndCalleeOfMain.isEmpty()){
            String mainOrCalleeOfMain = mainAndCalleeOfMain.pop();
            Set<String> setofmethod = methodCallsWithCallee.get(mainOrCalleeOfMain);
            for (String name:setofmethod){
                List<String> sameNames = new ArrayList<>();
                for (String stringBuffer : fullMethods){
                    String methodNameString = getMethodName(stringBuffer.toString());
                    //和方法集合匹配
                    if(Objects.equals(name, methodNameString)){
                        sameNames.add(stringBuffer);
//                    fullMethods.remove(stringBuffer);
                    }
                }

                if(sameNames.size() == 1){
                    mainAndCalleeOfMain.push(sameNames.get(0));
                    fullMethods.remove(sameNames.get(0));
                }
                if(sameNames.size() > 1){
                    for(String samename : sameNames){
                        String sameNameString = samename.toString();
                        for(String importEle : imports){
                            //匹配import,注意以*结尾的import
                            if(KMP.kmp(sameNameString,removeStarIfExist(importEle))){
                                fullMethods.remove(samename);
                                break;
                            }
                        }
                    }

                }
            }
        }


    }


    public static void main(String[] srgs) throws FileNotFoundException {
        List<String> paths = new ArrayList<>();
        paths.add("D:\\code\\javaAnalyzer\\src\\main\\java\\pmd\\deadcodetest\\utils\\KMPWithMain.java");
        paths.add("D:\\code\\javaAnalyzer\\src\\main\\java\\pmd\\deadcodetest\\utils\\callsss.java");
        for(String path : paths){
            preParseJava(path);
        }

        for(String path : paths){
            parseJava(path);
        }

        System.out.println("dead methods are followed:");
        System.out.println(fullMethods);
        System.out.println(methodCallsWithCallee);


    }
}
