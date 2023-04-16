import utils.KMP;

import java.io.FileNotFoundException;
import java.util.*;

//获取callee的完整路径
public class Test3 {
    public static void main(String[] srgs) throws FileNotFoundException {
        List<String> imports = new ArrayList<>();
        imports.add("java.util.List");
        imports.add("pmd.deadcodetest.utils.KMPWithMain.kmpwithmain");

        List<StringBuffer> fullMethods = new ArrayList<>();
        fullMethods.add(new StringBuffer("pmd.deadcodetest.utils.callsss.main(String[])"));
        fullMethods.add(new StringBuffer("pmd.deadcodetest.utils.KMPWithMain.kmpwithmain(StringString)"));
        fullMethods.add(new StringBuffer("pmd.deadcodetest.utils.KMPWithMain.buildNext(String)"));



        Map<StringBuffer, Set<String>> methodCallsWithCallee = new HashMap<>();
        Set<String> set = new HashSet<>();
        set.add("println");
        set.add("kmpwithmain");
        StringBuffer sb = new StringBuffer("pmd.deadcodetest.utils.callsss.main(String[])");
        methodCallsWithCallee.put(sb,set);

        //还要加一层循环
        for (String name:set){
            List<StringBuffer> sameNames = new ArrayList<>();
            for (StringBuffer stringBuffer : fullMethods){
                String methodNameString = getMethodName(stringBuffer.toString());
                //和方法集合匹配
                if(Objects.equals(name, methodNameString)){
                    sameNames.add(stringBuffer);
//                    fullMethods.remove(stringBuffer);
                }
            }

            if(sameNames.size() == 1) fullMethods.remove(sameNames.get(0));
            if(sameNames.size() > 1){
                for(StringBuffer samename : sameNames){
                    String sameNameString = samename.toString();
                    for(String importEle : imports){
                        if(KMP.kmp(sameNameString,importEle)){
                            fullMethods.remove(samename);
                            break;
                        }
                    }
                }

            }
        }
        //set中的名字走完了，停止探索

        List<StringBuffer> res = fullMethods;


    }

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
}
