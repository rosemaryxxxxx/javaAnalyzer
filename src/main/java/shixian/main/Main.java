package shixian.main;

import javaparser.utils.MethodCallExtractor1;
import shixian.utils.Statistics;
import utils.KMP;
import javaparser.visitors.VariableTypeVisitor;

import java.io.FileNotFoundException;
import java.util.*;

import static javaparser.visitors.VariableTypeVisitor.extarctType;
import static shixian.utils.Utils.*;
import static shixian.utils.ZipFiles.extractFileStructureOfZip;

public class Main {
    //装载所有的import
//    public static List<String> imports = new ArrayList<>();
    //装载所有的方法的完整路径
    public static List<String> fullMethods = new ArrayList<>();
    //装载所有的方法的完整路径以及其调用方法的方法名（非完整路径）
    public static Map<String, Set<String>> methodCallsWithCallee = new HashMap<>();
//    //装载所有main方法和main方法调用的方法，以及后续调用的方法
    public static List<String> deadMethods = new ArrayList<>();
    public static Map<String,List<String>> methodAndItsImports1 = new HashMap<>();
    public static Map<String,List<Integer>> methodAndItsPostion1 = new HashMap<>();
    public static Map<String,String> methodAndItsType1 = new HashMap<>();
//    public static Stack<String> mainAndCalleeOfMain = new Stack<>();

    /**
     * 解析.java文件，获得其import集合、方法完整路径集合、完整路径以及其调用方法的方法名（非完整路径）集合
     * @param path
     */
    public static void preParseJava(String path ,String beforeZipName) throws FileNotFoundException {
        //装载所有的import,每一个.java文件对应的imports集合不一样
//        List<String> imports = new ArrayList<>();

        MethodCallExtractor1 methodCallExtractor1 = new MethodCallExtractor1();
//        methodCallExtractor1.setPATH("D:\\code\\javaAnalyzer\\src\\main\\java\\pmd\\deadcodetest\\utils\\KMP.java");
        methodCallExtractor1.setPATH(path);
//        methodCallExtractor1.setBeforeZipName(beforeZipName);
        methodCallExtractor1.preStartParse(beforeZipName);

        fullMethods.addAll(methodCallExtractor1.getFullMethods());

        deadMethods.addAll(methodCallExtractor1.getFullMethods());

        Map<String, Set<String>> temp = new HashMap<>();
        temp.putAll(methodCallsWithCallee);
        temp.putAll(methodCallExtractor1.getMethodCallsWithCallee());
        methodCallsWithCallee = temp;

        Map<String,List<String>> temp1 = new HashMap<>();
        temp1.putAll(methodAndItsImports1);
        temp1.putAll(methodCallExtractor1.getMethodAndItsImports());
        methodAndItsImports1 = temp1;

        Map<String,List<Integer>> temp2 = new HashMap<>();
        temp2.putAll(methodAndItsPostion1);
        temp2.putAll(methodCallExtractor1.getMethodAndItsPosition());
        methodAndItsPostion1 = temp2;

        Map<String,String> temp3 = new HashMap<>();
        temp3.putAll(methodAndItsType1);
        temp3.putAll(methodCallExtractor1.getMethodAndItsType());
        methodAndItsType1 = temp3;

    }

    /**
     * 根据方法调用
     *
     * @param path
     * @param beforeZipName
     * @throws FileNotFoundException
     */
    public static void parseJava(String path, String beforeZipName) throws FileNotFoundException {
        MethodCallExtractor1 methodCallExtractor1 = new MethodCallExtractor1();
        methodCallExtractor1.setPATH(path);

        methodCallExtractor1.startParse(beforeZipName);

        List<String> imports = new ArrayList<>();
//        imports = methodCallExtractor1.getImports();

//        System.out.println("imports:"+imports);

        Stack<String> mainAndCalleeOfMain = new Stack<>();
        mainAndCalleeOfMain.addAll(methodCallExtractor1.getMainAndCalleeOfMain());
        System.err.println(mainAndCalleeOfMain);

        //主要算法逻辑
        while (!mainAndCalleeOfMain.isEmpty()){
            String mainOrCalleeOfMain = mainAndCalleeOfMain.pop();
            //程序入口应该是活方法
            deadMethods.remove(mainOrCalleeOfMain);
            imports = methodAndItsImports1.get(mainOrCalleeOfMain);
            Set<String> setofmethod = methodCallsWithCallee.get(mainOrCalleeOfMain);
            for (String name:setofmethod){
                List<String> sameNames = new ArrayList<>();
                for (String stringBuffer : fullMethods){
                    String methodNameString = getMethodName(stringBuffer);
                    //和方法集合匹配
                    if(Objects.equals(getMethodNameInSet(name), methodNameString)){
                        sameNames.add(stringBuffer);
                    }
                }

                if(sameNames.size() == 1){
                    //防止循环调用
                    if(getMethodName(sameNames.get(0)).equals(getMethodName(mainOrCalleeOfMain))){
                        break;
                    }
                    //活方法入栈
                    mainAndCalleeOfMain.push(sameNames.get(0));
//                    fullMethods.remove(sameNames.get(0));
                    deadMethods.remove(sameNames.get(0));
                }
                if(sameNames.size() > 1){
                    int flag = 0;
                    for(String samename : sameNames){
                        String sameNameString = samename;

                        for(String importEle : imports){
                            //匹配import,注意以*结尾的import
//                            System.out.println("方法名："+sameNameString+",import："+importEle);
                            if(KMP.kmp(sameNameString,removeStarIfExist(importEle))){
//                                fullMethods.remove(samename);
                                //防止循环调用
                                if(getMethodName(samename).equals(getMethodName(mainOrCalleeOfMain))){
                                    flag=1;
                                    break;
                                }
                                //活方法入栈
                                mainAndCalleeOfMain.push(samename);
                                deadMethods.remove(samename);
                                flag=1;
                                break;
                            }
                        }
//                        if(flag==1){
//                            break;
//                        }
                    }

                }
            }
        }


    }


    public static void main(String[] srgs) throws FileNotFoundException {
//        List<String> paths = new ArrayList<>();
//        paths.add("D:\\code\\javaAnalyzer\\src\\main\\java\\pmd\\deadcodetest\\utils\\KMPWithMain.java");
//        paths.add("D:\\code\\javaAnalyzer\\src\\main\\java\\pmd\\deadcodetest\\utils\\callsss.java");

//        String zipPath = "D:\\code\\javazip\\t0510\\javaAnalyzer.zip";
        String zipPath = "D:\\code\\javazip\\t0504\\pmd.zip";
//        String zipPath = "D:\\codebaseOfCodeMatcher\\test\\2.zip";
//        String zipPath = "D:\\code\\javazip\\t0510\\t1.zip";
//        String zipPath = "D:\\code\\javazip\\t0515\\aws-cognito-java-desktop-app.zip";
//        String zipPath = "D:\\code\\javazip\\t0515\\latexdraw.zip";
        //没有test文件
//        String zipPath = "D:\\code\\javazip\\t0530\\latexdraw.zip";
//        String zipPath = "D:\\code\\javazip\\t0531\\t2.zip";
//        String zipPath = "D:\\code\\javazip\\t0601\\t2.zip";
//        String zipPath = "D:\\code\\javazip\\t0607\\constructor.zip";

        List<String> paths = new ArrayList<>();
        extractFileStructureOfZip(zipPath,paths);

        for(String path : paths){
            extarctType(path,getBeforeZipName(zipPath));
            preParseJava(path,getBeforeZipName(zipPath));

        }
         System.out.println("原项目中的method个数："+fullMethods.size());
//        System.out.println("原项目中的methods："+fullMethods);
//        System.out.println("method和对应的imports："+methodAndItsImports1);

//        Set<String> set1 = methodCallsWithCallee.get("latexdraw.src.main.java.net.sf.latexdraw.view.svg.SVGArrow.toSVG(SVGDocumentboolean)");
//        Set<String> set2 = methodCallsWithCallee.get("latexdraw.src.main.java.net.sf.latexdraw.view.svg.SVGShapesFactory.fillShapeCreationList()");
//        boolean i = fullMethods.contains("latexdraw.src.main.java.net.sf.latexdraw.view.svg.SVGShapesFactory.fillShapeCreationList()");

        for(String path : paths){
            parseJava(path,getBeforeZipName(zipPath));
        }


//        parseJava("D:\\code\\javazip\\t1\\00.java");
        System.out.println("死方法的个数："+deadMethods.size());

        System.out.println("dead methods are followed:");
        System.out.println(deadMethods);
//        System.out.println(methodCallsWithCallee);

        //数据分析
        List<Integer> res = Statistics.deadMethodStatistics(methodAndItsPostion1,deadMethods);
        float f = ((float) res.get(0)/((float) res.get(0)+(float) res.get(1)))*100.0f;
        float f1 = ((float) res.get(2)/((float) res.get(2)+(float) res.get(3)))*100.0f;
        System.out.println("["+zipPath+"]"+"中"+"\n"
                +"死方法的个数："+res.get(0)+"\n"
                +"活方法的的个数：" + res.get(1)+"\n"
                +"死方法的代码行："+res.get(2)+"\n"
                +"活方法的代码行："+res.get(3)+"\n"
                +"死方法的个数占全部方法个数比例："+f+"%"+"\n"
                +"死方法代码行占全部方法代码行的比例："+f1+"%"+"\n"
        );
    }
}
