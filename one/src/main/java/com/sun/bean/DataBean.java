package com.sun.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.lang.annotation.Target;

/**
 * @author SunG
 * @date 2020/5/8 16:50
 */

//当使用mybatis plus，需要实现对象序列化
@Data @AllArgsConstructor
@NoArgsConstructor
@TableName("illness")
public class DataBean implements Serializable {

    private String area;
    private int nowConfirm;
    private int confirm;
    private int heal;
    private int dead;

}
