package com.example.interfacetest;

import com.example.entity.InterfaceAndTestCaseBean;
import com.example.mapper.InterfaceAndTestCaseBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 获取数据list并转换成object类型数组
 */
public class TestDataProviderData {
    @Autowired
    private static InterfaceAndTestCaseBeanMapper data;

    @DataProvider(name = "testData",parallel = true)
    public static Iterator<Object[]> test(){
        List<InterfaceAndTestCaseBean> dataList = data.getInterfaceAndTestCaseBeanByInterfaceId(1);
        System.out.println(dataList.size());
        List<Object[]> oList=new ArrayList<>();
        for (InterfaceAndTestCaseBean i:dataList) {
            oList.add(new Object[]{i});
        }
        return oList.iterator();
    }
    @Test
    public void test1() {
        List<InterfaceAndTestCaseBean> dataList = data.getInterfaceAndTestCaseBeanByInterfaceId(1);
        System.out.println(dataList);
    }
}
