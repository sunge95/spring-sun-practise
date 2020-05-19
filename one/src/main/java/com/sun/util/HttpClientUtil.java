package com.sun.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;


/**
 * @author SunG
 * @date 2020/5/18 17:29
 */

//建立连接，挖掘数据，返回String类型
public class HttpClientUtil {

//    public static String url = "https://view.inews.qq.com/g2/getOnsInfo?name=disease_other";
//
//    public static void main(String[] args) {
//        doGet(url);
//    }

    public static String doGet(String urlStr){

        //提供闭合的HttpClient对象
        CloseableHttpClient httpClient = null;
        //提供闭合的响应对象
        CloseableHttpResponse httpResponse = null;

        String result = null;
        try {
        //使用默认的创建方式
        httpClient = HttpClients.createDefault();
        //创建一个请求 传入一个url
        HttpGet httpGet = new HttpGet(urlStr);
        //设置请求头的方式
        httpGet.addHeader("Accept","application/json");

        // 设置请求参数  连接时间、数据读取时间(socketTimeOut)等  单位是ms
        //   ConnectionRequestTimeout  指从共享连接池中取出连接的超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(35000)
                .setConnectionRequestTimeout(35000)
                .setSocketTimeout(60000)
                .build();
        // 设置配置参数
        httpGet.setConfig(requestConfig);
        //执行请求
        httpResponse =  httpClient.execute(httpGet);
        //从返回的对象中获取响应的数据
        HttpEntity entity = httpResponse.getEntity();

        result = EntityUtils.toString(entity);
//        System.out.println(result);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
