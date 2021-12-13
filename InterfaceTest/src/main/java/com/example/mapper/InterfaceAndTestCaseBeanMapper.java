package com.example.mapper;

import com.example.entity.InterfaceAndTestCaseBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InterfaceAndTestCaseBeanMapper {
    public List<InterfaceAndTestCaseBean> getInterfaceAndTestCaseBeanByInterfaceId(Integer id);
}
