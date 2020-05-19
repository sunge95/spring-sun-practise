package com.sun.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GraphColumnarBean implements Comparable<GraphColumnarBean> {

    private String area;
    private int fromAbroad;

    @Override
    public int compareTo(GraphColumnarBean o) {
//        无序
//        return this.getFromAbroad() - this.getFromAbroad();
//        从大到小
        return o.getFromAbroad() - this.getFromAbroad();
//        无序
//        return o.getFromAbroad() - o.getFromAbroad();
//        从小到大
//        return this.getFromAbroad() - o.getFromAbroad();
    }
}
