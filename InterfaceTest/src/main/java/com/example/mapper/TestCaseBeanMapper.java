package com.example.mapper;

import com.example.entity.TestCaseBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TestCaseBeanMapper {
    public List<TestCaseBean> getTestCaseBeanList();
}
