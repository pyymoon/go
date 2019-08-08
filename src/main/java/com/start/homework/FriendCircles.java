package com.start.homework;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class FriendCircles {
    private List<String> array = new ArrayList<>();
    private Map<String, Integer> map = new HashMap<>();

    public Integer count() {
        int count = 0;
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            java.util.Map.Entry entry = (Map.Entry) it.next();
            if (entry.getValue().equals(-1)) {
                count++;
            }
        }
        return count;
    }

    //找到当前节点的根节点的上一跳节点
    public String find(String key) {
        while (map.get(key) != -1 && map.get(array.get(map.get(key))) != -1) {
            key = array.get(map.get(array.get(map.get(key))));
        }
        return key;
    }

    //把两颗树的根连起来
    public void union(String a, String b) {
        String lastRootA = find(a);
        String lastRootB = find(b);
        if (array.get(map.get(lastRootB)) != array.get(map.get(lastRootA))) {
            map.put(array.get(map.get(lastRootB)), map.get(lastRootA));
        }
    }

    //插入数组过程
    public void insert(String a, String b) {
        if (a.equals(b)) {
            return;
        }
        array.add(a);
        array.add(b);
        if (!map.containsKey(a)) {
            map.put(a, -1);
        }
        if (map.get(a) == -1) {
            if (map.containsKey(b)) {
                if (map.get(b) != -1) {
                    String lastRoot = find(b);
                    map.put(array.get(map.get(lastRoot)), array.size() - 2);
                } else {
                    map.put(b, array.size() - 2);
                }
            } else {
                map.put(b, array.size() - 2);
            }
        } else {
            if (map.containsKey(b)) {
                union(a, b);
            } else {
                map.put(b, array.size() - 2);
            }
        }
    }

    /**
     * 读取csv文件，同时进行并查集的初始化
     *
     * @param filepath
     * @return
     */
    public void readCsv(String filepath) {
        File file = new File(filepath);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        List<Set> result = new ArrayList<>();
        String line = "";
        String[] lines = {};
        try {
            line = br.readLine();
            while ((line = br.readLine()) != null) {
                lines = line.split("\t");
                insert(lines[0], lines[1]);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        String filepath = "/Users/ouyangle/Desktop/zhenghao08_sns_activity_2019-08-05-11-58-51_sql1.csv";
        FriendCircles fc = new FriendCircles();
        Long time1 = System.currentTimeMillis();
        fc.readCsv(filepath);
        Integer result = fc.count();
        Long time2 = System.currentTimeMillis();
        System.out.println("result:" + result);
        System.out.println("花费时间" + (time2 - time1));
    }
}
