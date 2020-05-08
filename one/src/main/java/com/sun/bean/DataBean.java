package com.sun.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author SunG
 * @date 2020/5/8 16:50
 */
@Data @AllArgsConstructor
public class DataBean {

    private String name;
    private int newConfirm;
    private int confirm;
    private int heal;
    private int dead;

}
