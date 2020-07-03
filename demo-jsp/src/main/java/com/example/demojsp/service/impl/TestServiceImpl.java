package com.example.demojsp.service.impl;

import com.example.demojsp.mapper.TestMapper;
import com.example.demojsp.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author YS
 * @Date 2020/6/28 16:50
 **/
@Service
public class TestServiceImpl implements TestService {

    public void getS(){
        System.out.println("123456");
    }

    @Autowired
    private TestMapper testMapper;

    @Override
    public void getTable() {
        String tableName = "t_user";
        testMapper.getTable(tableName);
    }

    @Override
    public void delTable() {
        String tableName = "t_user";
        testMapper.delTable(tableName);
    }

}
