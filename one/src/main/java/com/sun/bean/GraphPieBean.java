package com.sun.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author SunG
 * @date 2020/5/19 10:39
 */
@Data
@AllArgsConstructor
public class GraphPieBean implements Comparable<GraphPieBean>{

    private String name;
    private int value;

    @Override
    public int compareTo(GraphPieBean o) {
        return this.getValue()-o.getValue();
    }
}
