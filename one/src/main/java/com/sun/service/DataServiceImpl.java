package com.sun.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.bean.DataBean;
import com.sun.handler.DataHandler;
import com.sun.handler.JsoupHandler;
import com.sun.mapper.DataMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author SunG
 * @date 2020/5/11 20:59
 */
@Service
public class DataServiceImpl extends ServiceImpl<DataMapper,DataBean> implements DataService {

//    @Override
//    public List<DataBean> list() {
//        List<DataBean> result = null;
//        try {
//            result = DataHandler.getData();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//    @Override
//    public List<DataBean> listById(int id) {
//        if (id==2){
//            return JsoupHandler.getData();
//        }
//        return list();
//    }

}
