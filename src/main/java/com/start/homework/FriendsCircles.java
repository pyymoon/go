package com.start.homework;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FriendsCircles {

    private Map<String, Integer> map = new HashMap<>();
    private Integer[] array;

    public void initMap(String filepath) {
        BufferedReader br = readCsv(filepath);
        String line = "";
        String[] lines = {};
        int count = 0;
        try {
            line = br.readLine();
            while ((line = br.readLine()) != null) {
                lines = line.split("\t");
                if (!map.containsKey(lines[0])) {
                    map.put(lines[0], count);
                    count++;
                }
                if (!map.containsKey(lines[1])) {
                    map.put(lines[1], count);
                    count++;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public BufferedReader readCsv(String filepath) {
        File file = new File(filepath);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return br;
    }

    public void initArray(int n) {
        array = new Integer[n];
        for (int i = 0; i < n; i++) {
            array[i] = -1;
        }
    }

    public Integer find(Integer x) {
        while (array[x] != -1) {
            x = array[x];
        }
        return x;
    }

    public void union(Integer x, Integer y) {
        int rootx = find(x);
        int rooty = find(y);
        if (rootx != rooty) {
            array[rootx] = rooty;
        }
    }

    public void insert(String filepath) {
        BufferedReader br = readCsv(filepath);
        String line = "";
        String[] lines = {};
        try {
            line = br.readLine();
            while ((line = br.readLine()) != null) {
                lines = line.split("\t");
                union(map.get(lines[0]), map.get(lines[1]));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Integer count() {
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == -1) {
                count++;
            }
        }
        return count;
    }


    public static void main(String[] args) {
        String filepath = "/Users/ouyangle/Desktop/zhenghao08_sns_activity_2019-08-05-11-58-51_sql1.csv";
        FriendsCircles fc = new FriendsCircles();
        fc.initMap(filepath);
        fc.initArray(fc.map.size());
        Long time1 = System.currentTimeMillis();
        fc.insert(filepath);
        Integer count = fc.count();
        Long time2 = System.currentTimeMillis();
        System.out.println("result:" + count);
        System.out.println("花费时间" + (time2 - time1));
    }
}
