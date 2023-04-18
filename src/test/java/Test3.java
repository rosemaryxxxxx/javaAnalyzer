import utils.KMP;

import java.io.FileNotFoundException;
import java.util.*;

//获取callee的完整路径
public class Test3 {
    public static void main(String[] srgs) throws FileNotFoundException {

        List<String> fullMethods = new ArrayList<>();
//        fullMethods.add(new StringBuffer("pmd.deadcodetest.utils.callsss.main(String[])"));
//        fullMethods.add(new StringBuffer("pmd.deadcodetest.utils.KMPWithMain.main(String[])"));
        fullMethods.add("pmd.deadcodetest.utils.KMPWithMain.testprivate()");
        fullMethods.add("pmd.deadcodetest.utils.KMPWithMain.test(int)");
        fullMethods.add("pmd.deadcodetest.utils.KMPWithMain.kmpwithmain(StringString)");
        fullMethods.add("pmd.deadcodetest.utils.KMPWithMain.buildNext(String)");


        Map<String, Set<String>> methodCallsWithCallee = new HashMap<>();
        Set<String> set = new HashSet<>();
        set.add("println");
        set.add("kmpwithmain");
        String sb = "pmd.deadcodetest.utils.callsss.main(String[])";
        methodCallsWithCallee.put(sb,set);
        Set<String> set1 = new HashSet<>();
        set1.add("kmpwithmain");
        String sb1 = "pmd.deadcodetest.utils.KMPWithMain.main(String[])";
        methodCallsWithCallee.put(sb1,set1);
        Set<String> set2 = new HashSet<>();
        set2.add("buildNext");
        set2.add("length");
        set2.add("println");
        String sb2 = "pmd.deadcodetest.utils.KMPWithMain.kmpwithmain(StringString)";
        methodCallsWithCallee.put(sb2,set2);
        Set<String> set3 = new HashSet<>();
        set3.add("length");
        set3.add("charAt");
        String sb3 = "pmd.deadcodetest.utils.KMPWithMain.buildNext(String)";
        methodCallsWithCallee.put(sb3,set3);

        Stack<String> mainAndCalleeOfMain = new Stack<>();
        mainAndCalleeOfMain.push("pmd.deadcodetest.utils.callsss.main(String[])");
        mainAndCalleeOfMain.push("pmd.deadcodetest.utils.KMPWithMain.main(String[])");


        while (!mainAndCalleeOfMain.isEmpty()){
            List<String> imports = new ArrayList<>();
            String mainOrCalleeOfMain = mainAndCalleeOfMain.pop();
            if(mainOrCalleeOfMain.equals("pmd.deadcodetest.utils.callsss.main(String[])")){
                imports.add("java.util.List");
                imports.add("pmd.deadcodetest.utils.KMPWithMain.*");
                imports.add("pmd.deadcodetest.utils");
            }
            if(mainOrCalleeOfMain.equals("pmd.deadcodetest.utils.KMPWithMain.main(String[])")){
                imports.add("pmd.deadcodetest.utils");
            }
            if(mainOrCalleeOfMain.equals("pmd.deadcodetest.utils.KMPWithMain.kmpwithmain(StringString)")){
                imports.add("pmd.deadcodetest.utils");
            }
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

        //set中的名字走完了，停止探索

        List<String> res = fullMethods;


    }

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
