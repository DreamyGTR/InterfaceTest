package com.example.interfacetest;

import com.alibaba.fastjson.JSONObject;
import com.example.entity.InterfaceAndTestCaseBean;
import com.example.mapper.InterFaceMapper;
import com.example.mapper.InterfaceAndTestCaseBeanMapper;
import com.example.mapper.TestCaseBeanMapper;
import com.example.util.HttpUtil;
import com.example.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.testng.Reporter;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 接口自动化用例编写层
 * 编辑人:zhaotianyu
 */

@Slf4j
public class Test1 extends BaseTest {
    @Autowired
    private InterfaceAndTestCaseBeanMapper interfaceAndTestCaseBeanMapper;

    @BeforeSuite
    public void beforeSuite() {
        log.info("------BeforeSuite------");
    }

    @BeforeClass
    public void beforeTest() {
        log.info("------BeforeTest------");
    }

    @DataProvider(name = "kuaiFanData")
    public Iterator<Object[]> kuaiFanData() {
        List<String> paramList = new ArrayList<>();
        paramList.add("快反");
        List<InterfaceAndTestCaseBean> dataList = interfaceAndTestCaseBeanMapper.getInterfaceAndTestCaseBeanByInterfaceId(paramList);
        List<Object[]> oList = new ArrayList<>();
        for (InterfaceAndTestCaseBean i : dataList) {
            oList.add(new Object[]{i});
        }
        return oList.iterator();
    }

    //基本信息接口测试
    @Test(groups = "kuaiFan", testName = "快反接口", dataProvider = "kuaiFanData")
    public void kuaiFan(InterfaceAndTestCaseBean interfaceAndTestCaseBean) throws IOException {
        CloseableHttpResponse response = HttpUtil.getResponse(interfaceAndTestCaseBean);
        SoftAssert sa = new SoftAssert();
        checkResponse(interfaceAndTestCaseBean, response, sa);
        sa.assertAll();
    }

    @DataProvider(name = "productData")
    public Iterator<Object[]> productData() {
        List<String> paramList = new ArrayList<>();
        paramList.add("产品判定");
        List<InterfaceAndTestCaseBean> dataList = interfaceAndTestCaseBeanMapper.getInterfaceAndTestCaseBeanByInterfaceId(paramList);
        System.out.println(dataList.size());
        List<Object[]> oList = new ArrayList<>();
        for (InterfaceAndTestCaseBean i : dataList) {
            oList.add(new Object[]{i});
        }
        return oList.iterator();
    }
    //产品判定接口测试
    @Test(groups = "product", testName = "产品判定接口", dataProvider = "productData")
    public void productTest(InterfaceAndTestCaseBean interfaceAndTestCaseBean) throws IOException {
        log.info("接口名称:"+interfaceAndTestCaseBean.getInterfaceName());
        CloseableHttpResponse response = HttpUtil.getResponse(interfaceAndTestCaseBean);
        SoftAssert sa = new SoftAssert();
        checkResponse(interfaceAndTestCaseBean, response, sa);
        sa.assertAll();
    }

    @DataProvider(name = "projectData")
    public Iterator<Object[]> projectData() {
        List<String> paramList = new ArrayList<>();
        paramList.add("立项审批");
        List<InterfaceAndTestCaseBean> dataList = interfaceAndTestCaseBeanMapper.getInterfaceAndTestCaseBeanByInterfaceId(paramList);
        System.out.println(dataList.size());
        List<Object[]> oList = new ArrayList<>();
        for (InterfaceAndTestCaseBean i : dataList) {
            oList.add(new Object[]{i});
        }
        return oList.iterator();
    }
    @Test(groups = "project", testName = "立项审批接口", dataProvider = "projectData")
    public void testProject(InterfaceAndTestCaseBean interfaceAndTestCaseBean) throws IOException {
        CloseableHttpResponse response = HttpUtil.getResponse(interfaceAndTestCaseBean);
        SoftAssert sa = new SoftAssert();
        checkResponse(interfaceAndTestCaseBean, response, sa);
        sa.assertAll();
    }


    @DataProvider(name = "productFileData")
    public Iterator<Object[]> productFileData() {
        List<String> paramList = new ArrayList<>();
        paramList.add("产品判定文件");
        List<InterfaceAndTestCaseBean> dataList = interfaceAndTestCaseBeanMapper.getInterfaceAndTestCaseBeanByInterfaceId(paramList);
        System.out.println(dataList.size());
        List<Object[]> oList = new ArrayList<>();
        for (InterfaceAndTestCaseBean i : dataList) {
            oList.add(new Object[]{i});
        }
        return oList.iterator();
    }
    @Test(groups = "productFile", testName = "产品判定文件", dataProvider = "productFileData")
    public void productFile(InterfaceAndTestCaseBean interfaceAndTestCaseBean) throws IOException {
        CloseableHttpResponse response = HttpUtil.getResponse(interfaceAndTestCaseBean);
        SoftAssert sa = new SoftAssert();
        checkResponse(interfaceAndTestCaseBean, response, sa);
        sa.assertAll();
    }
    //立项策划会
    @DataProvider(name = "createProjectData")
    public Iterator<Object[]> createProjectData() {
        List<String> paramList = new ArrayList<>();
        paramList.add("立项策划会");
        List<InterfaceAndTestCaseBean> dataList = interfaceAndTestCaseBeanMapper.getInterfaceAndTestCaseBeanByInterfaceId(paramList);
        System.out.println(dataList.size());
        List<Object[]> oList = new ArrayList<>();
        for (InterfaceAndTestCaseBean i : dataList) {
            oList.add(new Object[]{i});
        }
        return oList.iterator();
    }
    @Test(groups = "createProject", testName = "立项策划会", dataProvider = "createProjectData")
    public void createProject(InterfaceAndTestCaseBean interfaceAndTestCaseBean) throws IOException {
        CloseableHttpResponse response = HttpUtil.getResponse(interfaceAndTestCaseBean);
        SoftAssert sa = new SoftAssert();
        checkResponse(interfaceAndTestCaseBean, response, sa);
        sa.assertAll();
    }

    /**
     * 封装断言方法,用于接口冒烟测试正向流程
     */
    private String checkResponse(InterfaceAndTestCaseBean data, CloseableHttpResponse response, SoftAssert softAssert) throws IOException {
        Reporter.log("接口名称:" + data.getInterfaceName());
        Reporter.log("接口地址:" + data.getInterfaceUrl());
        Reporter.log("请求实体:" + data.getCaseBody());
        Map<String, String> expectResultMap = JsonUtil.getJsonMap(data.getExpectResult());
        if (!ObjectUtils.isEmpty(expectResultMap)) {
            softAssert.assertEquals(response.getStatusLine().getStatusCode(), 200);
            String entity = EntityUtils.toString(response.getEntity());
            if (response.getEntity() != null) {
                if (JsonUtil.isJSON(entity)) {
                    Map<String, String> responseEntityMap = JsonUtil.getJsonMap(entity);
                    softAssert.assertEquals(responseEntityMap.get("success"),true);
                    Reporter.log("响应实体:" + entity);
                    return entity;
                }
            }
            return entity;
        }
        return null;
    }

    @AfterClass
    public void afterClass() {
        log.info("------AfterClass------");
    }

    @AfterSuite
    public void afterSuite() {
        log.info("------AfterSuite------");
    }
}