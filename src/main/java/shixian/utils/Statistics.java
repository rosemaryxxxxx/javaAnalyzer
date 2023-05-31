package shixian.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Statistics {
    /**
     * 分析死方法的数值信息
     * @param methodAndItsPostion
     * @param deadMethodList
     * @return
     */
    public static List<Integer> deadMethodStatistics(Map<String, List<Integer>> methodAndItsPostion, List<String> deadMethodList) {
        int deadNum = 0, liveNum = 0, deadLOC = 0, liveLOC = 0;
        //res中分别是死方法的个数，活方法的个数，死方法的代码行数，活方法的代码行数
        List<Integer> res = new ArrayList<>();
        for (String dm : deadMethodList) {
            List<Integer> pos = methodAndItsPostion.get(dm);
            if(pos != null){
                deadLOC += pos.get(2) - pos.get(0);
            }
            deadNum++;
            methodAndItsPostion.remove(dm);
        }
        for(Map.Entry<String, List<Integer>> entry : methodAndItsPostion.entrySet()){
            List<Integer> pos = entry.getValue();
            if(pos != null){
                liveLOC += pos.get(2) - pos.get(0);
            }
            liveNum++;
        }
        res.add(deadNum);
        res.add(liveNum);
        res.add(deadLOC);
        res.add(liveLOC);
        return res;
    }
}
