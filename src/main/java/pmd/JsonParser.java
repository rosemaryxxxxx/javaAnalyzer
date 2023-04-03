package pmd;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class JsonParser {

    public static void main(String arg[]){
        String filePath = "D:\\code\\pmdrules\\report\\t1.json";
        // 读取json文件
        JSONObject jsonObejct = readJsonFile(filePath);

        JSONArray jsonArray = JSONArray.parseArray(jsonObejct.getString("files"));
        for (int j = 0;j<jsonArray.size();j++){
            JSONObject fileObject = JSONObject.parseObject(jsonArray.get(j).toString());
            String filepath = fileObject.getString("filename");
            JSONArray violationsArray = JSONArray.parseArray(fileObject.getString("violations"));
            int sizeOfViolationsArray = violationsArray.size();
            List<int[]> positions = new ArrayList<>();
            for(int i = 0;i<sizeOfViolationsArray;i++) {
                JSONObject violationsObject = JSONObject.parseObject(violationsArray.get(i).toString());
                int beginline = violationsObject.getIntValue("beginline");
                int begincolumn = violationsObject.getIntValue("begincolumn");
                int endline = violationsObject.getIntValue("endline");
                int endcolumn = violationsObject.getIntValue("endcolumn");
                int[] position = new int[]{beginline, begincolumn, endline, endcolumn};
                positions.add(position);
        }

        }

    }



    /**
     * 读取json文件信息
     * @param filename
     * @return
     */
    public static JSONObject readJsonFile(String filename){
        String jsonString = "";
        File jsonFile = new File(filename);
        try {
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
            int ch = 0;
            StringBuffer stringBuffer = new StringBuffer();
            while ((ch = reader.read()) != -1){
                stringBuffer.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonString = stringBuffer.toString();
        } catch (FileNotFoundException e){
            JSONObject notFoundJson = new JSONObject();
            notFoundJson.put("code","ERR");
            notFoundJson.put("msg","该地区GeoJson文件不存在！");
            return notFoundJson;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JSONObject.parseObject(jsonString);
    }


}
