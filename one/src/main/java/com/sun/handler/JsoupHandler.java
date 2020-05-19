package com.sun.handler;

import com.google.gson.Gson;
import com.sun.bean.DataBean;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author SunG
 * @date 2020/5/12 20:47
 */
public class JsoupHandler {

    public static String urlStr = "https://ncov.dxy.cn/ncovh5/view/pneumonia?scene=2&clicktime=1579579384&enterid=1579579384&from=singlemessage&isappinstalled=0";

    public static ArrayList<DataBean> getData() {

        //用于存储查找到的DataBean对象
        ArrayList<DataBean> result = new ArrayList<>();
        try {
            Document document = Jsoup.connect(urlStr).get();

            Elements scripts = document.select("script");
            //找到指定的标签数据
            Element oneScript = document.getElementById("getAreaStat");
            String data = oneScript.data();
            //字符串截取出json数据
            String subData = data.substring(data.indexOf("["),data.lastIndexOf("]")+1);

            //将得到的subData转化为集合
            Gson gson = new Gson();

            List list = gson.fromJson(subData, ArrayList.class);
            //找出想要的数据
            for (int i = 0;i<list.size();i++){
                Map item = (Map) list.get(i);
                String provinceName = (String) item.get("provinceName");
                double nowConfirm = (Double) item.get("currentConfirmedCount");
                double confirm = (Double) item.get("confirmedCount");
                double heal = (Double) item.get("curedCount");
                double dead = (Double) item.get("deadCount");

                //将所得数据存储在对象中
                DataBean dataBean = new DataBean(provinceName,(int)nowConfirm,(int)confirm,(int)heal,(int)dead);
                //将对象存储在列表中
                result.add(dataBean);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    return result;
    }
}
