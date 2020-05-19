package com.sun.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author SunG
 * @date 2020/5/18 20:45
 */
@Data
@AllArgsConstructor
public class GraphAddBean {

    private String date;
    private int addConfirm;
    private int addSuspect;
}
