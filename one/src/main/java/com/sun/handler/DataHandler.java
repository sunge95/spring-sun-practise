package com.sun.handler;

import com.google.gson.Gson;
import com.sun.bean.DataBean;
import com.sun.service.DataService;
import com.sun.util.HttpURLConnectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author SunG
 * @date 2020/5/12 0:13
 */
//把普通pojo实例化到spring容器中，相当于配置文件中的<bean id="" class=""/>
//    泛指组件，当组件不好归类的时候，我们可以使用这个注解进行标注
//    使用@PostConstruct，首先将DataHandler申明成一个组件
@Component
public class DataHandler {

    @Autowired
    public DataService dataService;

    public static String strUrl = "https://view.inews.qq.com/g2/getOnsInfo?name=disease_h5";

//    @PostConstruct 和 @Scheduled 会冲突 一般不会放在一起使用
//    在服务器加载Servlet时运行，而且只执行一次
//    @PostConstruct
    public void saveData(){
        List<DataBean> dataBeans = getData();
        //先将数据清空，然后存储数据
        dataService.remove(null);
        dataService.saveBatch(dataBeans);
    }

    //配置定时执行的注解 支持cron表达式
    @Scheduled(cron = "0 0/1 * * * ? ")
    public void updateData(){
        saveData();
    }

    public static List<DataBean> getData(){
        //获取实时的字符串数据
        String respJson = HttpURLConnectionUtil.doGet(strUrl);
        //将数据转化为json格式
        Gson gson = new Gson();
        Map map = gson.fromJson(respJson, Map.class);
        //提取map中的data,此时的data是String
        String subStr = (String) map.get("data");
        //再次将String转为json
        Map subMap = gson.fromJson(subStr, Map.class);

        //从subMap中查找想要的数据
        ArrayList arrayList = (ArrayList) subMap.get("areaTree");

        Map dataMap = (Map) arrayList.get(0);
        ArrayList childList = (ArrayList) dataMap.get("children");

        //创建一个存储dataBean的容器
        List<DataBean> result = new ArrayList<>();

        //将得到的各个城市List转化为bean
        for (int i = 0; i<childList.size(); i++){
            Map tmp = (Map) childList.get(i);
            String name = (String) tmp.get("name");
            Map totalMap = (Map) tmp.get("total");
            //获取每个城市的疫情状态
            double nowConfirm = (double) totalMap.get("nowConfirm");
            double confirm = (double) totalMap.get("confirm");
            double heal = (double) totalMap.get("heal");
            double dead = (double) totalMap.get("dead");
            //将获取的数据封装在 bean中，方便传输
            DataBean dataBean = new DataBean(name,(int)nowConfirm,(int)confirm,(int)heal,(int)dead);
            //将每个城市对象封装到result中，方便传输
            result.add(dataBean);
        }

        return result;
    }

    public static void main(String[] args) {
        getData();
    }
}
