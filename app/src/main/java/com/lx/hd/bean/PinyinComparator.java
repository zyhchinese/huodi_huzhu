package com.lx.hd.bean;

import java.util.Comparator;

/**
 * @author TB
 */
public class PinyinComparator implements Comparator<CarPP> {

    public int compare(CarPP o1, CarPP o2) {
        if (o1.getIndex().equals("@") || o2.getIndex().equals("#")) {
            return -1;
        } else if (o1.getIndex().equals("#") || o2.getIndex().equals("@")) {
            return 1;
        } else {
            return o1.getIndex().compareTo(o2.getIndex());
        }
    }

}
