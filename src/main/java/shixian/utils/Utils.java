package shixian.utils;

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
}
