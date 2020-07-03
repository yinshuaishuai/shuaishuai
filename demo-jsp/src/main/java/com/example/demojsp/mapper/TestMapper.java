package com.example.demojsp.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @Author YS
 * @Date 2020/7/1 10:00
 **/
public interface TestMapper {
    void getTable(@Param("tableName") String tableName);

    void delTable(@Param("tableName") String tableName);
}
