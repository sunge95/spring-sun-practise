package com.sun.controller;

import com.google.gson.Gson;
import com.sun.bean.*;
import com.sun.handler.GraphHandler;
import com.sun.org.apache.xpath.internal.operations.Mod;
import com.sun.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author SunG
 * @date 2020/5/11 21:02
 */
@Controller
public class DataController {

    @Autowired
    private DataService dataService;

//    Model是什么意思
    @GetMapping("/")
    public String list(Model model){
        List<DataBean> list = dataService.list();
        model.addAttribute("dataList",list);
        return "list";
    }

//    @GetMapping("/list/{id}")
//    public String listById(Model model, @PathVariable String id){
//        List<DataBean> list = dataService.listById(Integer.parseInt(id));
//        model.addAttribute("dataList",list);
//        return "list";
//    }

    @GetMapping("/graph")
    public String graph(Model model){
        List<GraphBean> list = GraphHandler.getGraphBean();
        //将list中的数据拆成两个列表返回前端
        //因为前端需要的数据是  x轴所有数据的数组和y轴所有数据的数组
        List<String> dateList = new ArrayList<>();
        List<Integer> nowConfirmList = new ArrayList<>();
        for (int i=0;i<list.size();i++){
            GraphBean item = list.get(i);
            dateList.add(item.getDate());
            nowConfirmList.add(item.getNowConfirm());
        }
        //将两个列表转为JSON格式，装入会话中，便于前端页面查找
        model.addAttribute("dateList",new Gson().toJson(dateList));
        model.addAttribute("nowConfirmList",new Gson().toJson(nowConfirmList));
        return "graph";
    }

    @GetMapping("/graphAdd")
    public String graphAdd(Model model){
        List<GraphAddBean> list = GraphHandler.getGraphAddBean();
        //将list中的数据拆成两个列表返回前端
        //因为前端需要的数据是  x轴所有数据的数组和y轴所有数据的数组
        List<String> dateList = new ArrayList<>();
        List<Integer> confirmList = new ArrayList<>();
        List<Integer> suspectList = new ArrayList<>();
        for (int i=0;i<list.size();i++){
            GraphAddBean item = list.get(i);
            dateList.add(item.getDate());
            confirmList.add(item.getAddConfirm());
            suspectList.add(item.getAddSuspect());
        }
        //将两个列表转为JSON格式，装入会话中，便于前端页面查找
        model.addAttribute("dateList",new Gson().toJson(dateList));
        model.addAttribute("confirmList",new Gson().toJson(confirmList));
        model.addAttribute("suspectList",new Gson().toJson(suspectList));
        return "graphAdd";
    }

    @GetMapping("/graphColumnar")
    public String graphColumnar(Model model){
        List<GraphColumnarBean> list = GraphHandler.getGraphColumnarData();
        Collections.sort(list);
        //将list中的数据拆成两个列表返回前端
        //因为前端需要的数据是  x轴所有数据的数组和y轴所有数据的数组
        List<String> areaList = new ArrayList<>();
        List<Integer> fromBroadList = new ArrayList<>();
        for (int i=0;i<10;i++){
            GraphColumnarBean item = list.get(i);
            areaList.add(item.getArea());
            fromBroadList.add(item.getFromAbroad());
        }
        //将两个列表转为JSON格式，装入会话中，便于前端页面查找
        model.addAttribute("areaList",new Gson().toJson(areaList));
        model.addAttribute("fromBroadList",new Gson().toJson(fromBroadList));
        return "graphColumnar";
    }

    @GetMapping("/graphPie")
    public String graphPie(Model model){
        List<GraphPieBean> list = GraphHandler.getGraphPieBean();
        Collections.sort(list);
        model.addAttribute("list",new Gson().toJson(list));
        return "graphPie";
    }

    @GetMapping("/map")
    public String getMap(Model model){
        List<MapBean> result = new ArrayList<>();
        List<DataBean> list = dataService.list();
        for (int i=0;i<list.size();i++){
            DataBean bean = list.get(i);
            MapBean mapBean = new MapBean(bean.getArea(),bean.getNowConfirm());
            result.add(mapBean);
        }
        model.addAttribute("mapData",new Gson().toJson(result));
        return "map";
    }
}
