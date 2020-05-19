package com.sun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sun.bean.DataBean;

/**
 * @author SunG
 * @date 2020/5/18 11:38
 */

//使用这个mapper，去链接数据库的相应的表
//使用这个接口，可以不用写相应的sql
//如果希望程序可以处理这个mapper，需要在入口类添加一个扫描
//业务层调用这个mapper
public interface DataMapper extends BaseMapper<DataBean> {

}
