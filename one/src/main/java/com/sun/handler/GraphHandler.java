package com.sun.handler;

import com.google.gson.Gson;
import com.sun.bean.GraphAddBean;
import com.sun.bean.GraphBean;
import com.sun.bean.GraphColumnarBean;
import com.sun.bean.GraphPieBean;
import com.sun.util.HttpClientUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author SunG
 * @date 2020/5/18 18:11
 */
public class GraphHandler {

    public static String urlStr = "https://view.inews.qq.com/g2/getOnsInfo?name=disease_other";

    /**
     * 获取日期和当前确证数据
     * @return
     */
    public static List<GraphBean> getGraphBean(){
        List<GraphBean> graphBeans = new ArrayList<>();

        String str = HttpClientUtil.doGet(urlStr);
        //将得到的json转化为可以列表
        Gson gson = new Gson();
        Map map = gson.fromJson(str, Map.class);
        String subStr = (String) map.get("data");

        //将subStr也转化成json
        Map subMap = gson.fromJson(subStr,Map.class);
        ArrayList subList = (ArrayList) subMap.get("chinaDayList");

        //然后遍历subList
        for (int i = 0;i<subList.size();i++){
            Map item = (Map) subList.get(i);
            String date = (String) item.get("date");
            double nowConfirm = (double) item.get("nowConfirm");
            //将date和nowConfirm封装到对象中
            GraphBean graphBean = new GraphBean(date,(int)nowConfirm);
            //将对象装入集合
            graphBeans.add(graphBean);
        }
        return graphBeans;
    }

    public static List<GraphAddBean> getGraphAddBean(){
        //存储最后的返回结果
        List<GraphAddBean> result = new ArrayList<>();
        //获取根据请求查询到的数据
        String str =  HttpClientUtil.doGet(urlStr);
        //str转为json格式
        Gson gson = new Gson();
        Map map = gson.fromJson(str,Map.class);
        //获取String类型的data
        String subStr = (String) map.get("data");
        //将subStr转化位JSON
        Map subMap = gson.fromJson(subStr,Map.class);
        ArrayList list = (ArrayList) subMap.get("chinaDayAddList");
        //遍历list
        for (int i = 0;i<list.size(); i++){
            Map item = (Map) list.get(i);
            String date = (String) item.get("date");
            double confirm = (double) item.get("confirm");
            double suspect = (double) item.get("suspect");

            //将数据封装到对象中
            GraphAddBean graphAddBean = new GraphAddBean(date,(int)confirm,(int)suspect);
            //将对象装入集合
            result.add(graphAddBean);
        }
        return result;
    }

    private static String url2 = "https://view.inews.qq.com/g2/getOnsInfo?name=disease_h5";

    public static List<GraphColumnarBean> getGraphColumnarData(){
        List<GraphColumnarBean> result = new ArrayList<>();
        String str = HttpClientUtil.doGet(url2);
        //转为json
        Gson gson = new Gson();
        Map map = gson.fromJson(str, Map.class);
        String subStr = (String) map.get("data");
        Map subMap = gson.fromJson(subStr,Map.class);
        ArrayList subList = (ArrayList) subMap.get("areaTree");
        Map areaMap= (Map) subList.get(0);
        ArrayList childList = (ArrayList) areaMap.get("children");
        for (int i=0;i<childList.size();i++){
            Map childMap = (Map) childList.get(i);
            String name = (String) childMap.get("name");
            ArrayList childList2 = (ArrayList) childMap.get("children");
            for (int j=0;j<childList2.size();j++){
                Map chMap = (Map) childList2.get(j);
                if ("境外输入".equals(chMap.get("name"))){
                    Map toMap = (Map) chMap.get("total");
                    double confirm = (double) toMap.get("confirm");
                    GraphColumnarBean bean = new GraphColumnarBean(name,(int)confirm);
                    result.add(bean);
                }
            }
        }
        return result;
    }

    public static List<GraphPieBean> getGraphPieBean(){
        List<GraphPieBean> result = new ArrayList<>();

        String str = HttpClientUtil.doGet(urlStr);
        //将得到的json转化为可以列表
        Gson gson = new Gson();
        Map map = gson.fromJson(str, Map.class);
        String subStr = (String) map.get("data");

        //将subStr也转化成json
        Map subMap = gson.fromJson(subStr,Map.class);
        Map statisMap = (Map) subMap.get("nowConfirmStatis");

        for (Object o:statisMap.keySet()){
            String name = (String) o;
            switch (name){
                case "gat":
                    name ="港澳台确诊数";
                    break;
                case "import":
                    name = "境外输入";
                    break;
                case "province":
                    name = "本土病例";
                    break;
            }

            double value = (Double) statisMap.get(o);
            name += ":" + (int) value + "例";

            GraphPieBean bean = new GraphPieBean(name, (int) value);
            result.add(bean);
        }

        return result;
    }

//    public static void main(String[] args) {
//        getGraphAddBean();
//    }
}
