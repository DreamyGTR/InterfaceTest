package com.example.mapper;

import com.example.entity.InterfaceAndTestCaseBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InterfaceAndTestCaseBeanMapper {
    public List<InterfaceAndTestCaseBean> getInterfaceAndTestCaseBeanByInterfaceId(@Param("id") Integer id);
}
