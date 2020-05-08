package com.sun.handler;

import com.google.gson.Gson;
import com.sun.bean.DataBean;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author SunG
 * @date 2020/5/8 17:13
 */
public class DataHandler {

    public static void main(String[] args) throws Exception {
        getData();
    }

    public static List<DataBean> getData() throws Exception{

        FileReader fr = new FileReader("tmp1.txt");
        char[] cBuf = new char[1024];
        int cRead = 0;
        StringBuilder sb = new StringBuilder();
        while ((cRead = fr.read(cBuf))>0) {
            sb.append(new String(cBuf, 0, cRead));
        }
        fr.close();

        List<DataBean> result = new ArrayList<>();
        Gson gson = new Gson();
        Map map = gson.fromJson(sb.toString(), Map.class);
//        System.out.println(map);
        ArrayList areaList = (ArrayList) map.get("areaTree");
        Map areaMap = (Map) areaList.get(0);
        ArrayList childList = (ArrayList) areaMap.get("children");
        for (int i = 0; i<childList.size(); i++){
            Map childMap = (Map) childList.get(i);
            String name = (String) childMap.get("name");
            Map totalMap = (Map) childMap.get("total");
            double nowConfirm = (Double) totalMap.get("nowConfirm");
            double confirm = (Double) totalMap.get("confirm");
            double heal = (double) totalMap.get("heal");
            double dead = (double) totalMap.get("dead");
            DataBean dataBean = new DataBean(name,(int)nowConfirm,(int)confirm,(int)heal,(int)dead);
            result.add(dataBean);
        }
        System.out.println(result);
        return result;
    }
}
