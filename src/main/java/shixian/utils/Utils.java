package shixian.utils;

import java.util.HashMap;

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
     * 将斜杠代替为点，并且删除路径最后的“.java”
     *
     * @param s
     * @return
     */
    public static String replaceSlashWithPoint(String s){
        if(s == null) return null;
        int len = s.length();
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

    public static String analyseType(String argument){
        if(argument.startsWith("\"") && argument.endsWith("\"")){
            return "String";
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
        if(argument.endsWith("f") || argument.endsWith("F")) return "float";
        if(argument.endsWith("d") || argument.endsWith("D")) return "double";
        if(argument.endsWith("l") || argument.endsWith("L")) return "long";
        char[] chars = argument.toCharArray();
        for (char c : chars){
            if(c<'0'||c>'9'){
                break;
            }
        }
        return "others";
     }
}
