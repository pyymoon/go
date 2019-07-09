package com.start.mybatis;

public interface MbService {
    Boolean insert(String name,String content);

    MbEntity select(Integer id);

    Boolean delete(String name);

    Boolean update(String name,String content);

}
