package shixian.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    /**
     * 获取不带参数的方法名
     * @param s
     * @return
     */
    public static String getMethodName(String s){
        int len = s.length();
        int start = 0, end = len;
        for(int i = len-1;i>0;i--){
            if(s.charAt(i) == '('){
                end = i;
            }
            if(s.charAt(i) == '.'){
                start = i;
                break;
            }
        }
        return s.substring(start+1,end);
    }

    /**
     * 获取不带“<”的方法名，主要针对set中的构造方法
     * @param s
     * @return
     */
    public static String getMethodNameInSet(String s){
        int len = s.length();
        for(int j=len-1;j>0;j--){
            if(s.charAt(j) == '<'){
                return s.substring(0,j);
            }
        }
        return s;
    }

    /**
     * 移除import中结尾的*。以便于匹配
     * @param s
     * @return
     */
    public static String removeStarIfExist(String s){
        int len = s.length();
        if(s.charAt(len-1) == '*'){
            return s.substring(0,len-1);
        }
        return s;
    }

    /**
     * 将斜杠代替为点，并且删除路径最后的“\xx.java”
     *
     * @param s
     * @return
     */
    public static String replaceSlashWithPoint(String s){
        if(s == null) return null;
        int len = s.length();
//        int j ;
//        for(j =len-1;j>=0;j--){
//            if(s.charAt(j)=='\\') break;
//        }
        s=s.substring(0,len-5);
        String str = s.replaceAll("\\\\",".");
        return str;
    }

    /**
     * 获取zip路径的前面部分
     * @param zipPath
     * @return
     */
    public static String getBeforeZipName(String zipPath){
        if (zipPath == null) return null;

        int len = zipPath.length();
        return zipPath.substring(0,len-4);
    }

    /**
     * 根据参数确定参数类型，包括以方法为参数的情况
     * @param argument
     * @param typeMap
     * @return
     */
    public static String analyseType(String argument, Map<String,String> typeMap){
        byte b = 1;
        short s = 1;

        test(1,1,1, b, s, 1);
        test1(1.0, 2F,3, b, s, 6666L);

        //首先从typeMap找
        String mapVal = typeMap.get(argument);
        if(mapVal != null){
            return mapVal;
        }else {
            //在map中找不到的情况





            if(argument.startsWith("\"") && argument.endsWith("\"")){
                return "String";
            }
            if(argument.startsWith("\'") && argument.endsWith("\'")){
                return "char";
            }
            if(argument.startsWith("new")){
                int j = 0;
                int start = 0 , end = 0;
                for(;j<argument.length();j++){
                    if(argument.charAt(j) == ' '){
                        start = j;
                    }
                    if(argument.charAt(j) == '('){
                        end = j;
                        break;
                    }
                }
                return argument.substring(start+1,end);
            }
            if(Character.isDigit(argument.charAt(0)) && argument.endsWith("f") || argument.endsWith("F")) return "float";
            if(Character.isDigit(argument.charAt(0)) && argument.endsWith("d") || argument.endsWith("D")) return "double";
            if(Character.isDigit(argument.charAt(0)) && argument.endsWith("l") || argument.endsWith("L")) return "long";
//            char[] chars = argument.toCharArray();
//            for (char c : chars){
//            if(c<'0'||c>'9'){
//                break;
//            }
//        }
        }

     //在我们覆盖范围以外的地方返回一个特定的值
        return "0";
     }


     public static void test(double d, float f, int i, byte b, short s, long l){
         System.out.println(d+f+i);
     }
    public static void test1(Double d, Float f, Integer i, Byte b, Short s, Long l){
        System.out.println(d+f+i);
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

    public static int index0fLastPoint(String beforeZipName){
        int size = beforeZipName.length();
        int j;
        for(j = size-1;j>0;j--){
            if(beforeZipName.charAt(j) == '.'){
                break;
            }
        }
        return j;
    }


}
