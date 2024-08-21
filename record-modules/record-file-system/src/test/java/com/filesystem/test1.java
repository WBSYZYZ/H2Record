package com.filesystem;

import org.apache.commons.collections4.ListUtils;

import java.util.ArrayList;
import java.util.List;

public class test1 {
    public static void main(String[] args) {
        List<String> aa=new ArrayList<>();
        aa.add("1");
        aa.add("2");
        aa.add("3");
        aa.add("4");
        aa.add("5");
        aa.add("6");
        aa.add("7");
        aa.add("8");
        aa.add("9");
        aa.add("10");
        aa.add("12");
        aa.add("13");
        aa.add("14");
        aa.add("15");
        aa.add("16");
        aa.add("17");
        aa.add("18");
        aa.add("19");
        aa.add("20");
        aa.add("21");
        aa.add("22");
        aa.add("23");
        aa.add("24");

        int size=(int) Math.ceil((double) 3 / 4);
        List<List<String>> partitionDealList= ListUtils.partition(aa,(aa.size()+1)/4);
        System.out.println(partitionDealList.size());
        partitionDealList.stream().forEach(list->{
            System.out.println(Thread.currentThread().getName()+list);
        });
    }
}
