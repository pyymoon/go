package com.start.homework;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.omg.CORBA.INTERNAL;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

/**
 * @author ouyangle
 */
public class FriendsCircle {
    class Node {
        public String id;
        public Integer index;

        public Node(String id, Integer index) {
            this.id = id;
            this.index = index;
        }
    }

    private List<Node> queryList = new ArrayList<>();
    private List<Set> friendsList;

    /**
     * 查找并查集是否有相等的id，相等返回数组下标，不存在返回-1
     *
     * @param id
     * @return
     */
    public Integer find(String id) {
        for (int i = 0; i < queryList.size(); i++) {
            if (queryList.get(i).id.equals(id)) {
                return i;
            }
        }
        return -1;
    }

    public Integer findRoot(String id) {
        Integer index = find(id);
        if (index == -1) {
            return index;
        }
        while (queryList.get(index).index != -1) {
            index = queryList.get(index).index;
        }
        return index;
    }

    /**
     * 将set中的id插入并查集，有相同的id存在，就把另一个id对应的index置为相同id对应的index
     *
     * @param ids
     */
    public void insert(String[] ids) {
        if (ids[0].equals(ids[1])) {
            return;
        }
        Integer index1 = findRoot(ids[0]);
        Integer index2 = findRoot(ids[1]);
        if (index1 != -1) {
            if (index2 != -1) {
                queryList.get(index1).index = index2;
                System.out.println(index2);
                return;
            } else {
                queryList.add(new Node(ids[1], index1));
                System.out.println(index1);
                return;
            }
        } else {
            if (index2 != -1) {
                queryList.add(new Node(ids[0], index2));
                System.out.println(index2);
                return;
            } else {
                queryList.add(new Node(ids[0], -1));
                queryList.add(new Node(ids[1], queryList.size() - 1));
                System.out.print(queryList.size() - 1);
                return;
            }
        }
    }

    public Integer cout() {
        int cout = 0;
        for (int i = 0; i < queryList.size(); i++) {
            if (queryList.get(i).index == -1) {
                cout++;
            }
        }
        return cout;
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
                insert(lines);

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        String filepath = "/Users/ouyangle/Desktop/zhenghao08_sns_activity_2019-08-05-11-58-51_sql1.csv";
        FriendsCircle fc = new FriendsCircle();
        fc.readCsv(filepath);
        Integer result = fc.cout();
        System.out.println("result:" + result);
    }
}
