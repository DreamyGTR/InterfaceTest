package com.example.interfacetest;

import com.alibaba.fastjson.JSONObject;
import com.example.entity.InterfaceAndTestCaseBean;
import com.example.mapper.InterFaceMapper;
import com.example.mapper.InterfaceAndTestCaseBeanMapper;
import com.example.mapper.TestCaseBeanMapper;
import com.example.util.HttpUtil;
import com.example.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.bson.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Slf4j
public class Test1 extends BaseTest {
    @Autowired
    private InterFaceMapper interFaceMapper;

    @Autowired
    private TestCaseBeanMapper testCaseBeanMapper;

    @Autowired
    private InterfaceAndTestCaseBeanMapper interfaceAndTestCaseBeanMapper;
    private CloseableHttpClient client;
    private CloseableHttpResponse response;

    @DataProvider(name = "testData")
    public Iterator<Object[]> testData() {
        List<InterfaceAndTestCaseBean> dataList = interfaceAndTestCaseBeanMapper.getInterfaceAndTestCaseBeanByInterfaceId(1);
        System.out.println(dataList.size());
        List<Object[]> oList = new ArrayList<>();
        for (InterfaceAndTestCaseBean i : dataList) {
            oList.add(new Object[]{i});
        }
        return oList.iterator();
    }

    @Test(testName = "基本信息接口", dataProvider = "testData")
    public void testJson(InterfaceAndTestCaseBean interfaceAndTestCaseBean) throws IOException {
        System.out.println("接口对象的参数为:" + interfaceAndTestCaseBean.toString());
        CloseableHttpResponse response = HttpUtil.getResponse(interfaceAndTestCaseBean);
        Map<String, String> expectResultMap = JsonUtil.getJsonMap(interfaceAndTestCaseBean.getExpectResult());
        SoftAssert sa = new SoftAssert();
        sa.assertEquals(response.getStatusLine().getStatusCode(), Integer.parseInt(expectResultMap.get("status")));
        JSONObject rpEntity = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
        sa.assertEquals(rpEntity.get("success"), expectResultMap.get("success"));
        sa.assertAll();
    }

    @DataProvider(name = "testData1")
    public Iterator<Object[]> testData1() {
        List<InterfaceAndTestCaseBean> dataList = interfaceAndTestCaseBeanMapper.getInterfaceAndTestCaseBeanByInterfaceId(2);
        System.out.println(dataList.size());
        List<Object[]> oList = new ArrayList<>();
        for (InterfaceAndTestCaseBean i : dataList) {
            oList.add(new Object[]{i});
        }
        return oList.iterator();
    }

    @Test(testName = "Json测试接口", dataProvider = "testData1")
    public void testJson1(InterfaceAndTestCaseBean interfaceAndTestCaseBean) throws IOException {
        Reporter.log("Json测试接口");
        CloseableHttpResponse response = HttpUtil.getResponse(interfaceAndTestCaseBean);
        Map<String, String> expectResultMap = JsonUtil.getJsonMap(interfaceAndTestCaseBean.getExpectResult());
        SoftAssert sa = new SoftAssert();
        sa.assertEquals(500, Integer.parseInt(expectResultMap.get("status")));
        System.out.println(EntityUtils.toString(response.getEntity()));
        sa.assertAll();

    }

    @DataProvider(name = "testFile")
    public Iterator<Object[]> testFile() {
        List<InterfaceAndTestCaseBean> dataList = interfaceAndTestCaseBeanMapper.getInterfaceAndTestCaseBeanByInterfaceId(1);
        System.out.println(dataList.size());
        List<Object[]> oList = new ArrayList<>();
        for (InterfaceAndTestCaseBean i : dataList) {
            oList.add(new Object[]{i});
        }
        return oList.iterator();
    }

    @DataProvider(name = "dataKF")
    public Iterator<Object[]> dataKF() {
        List<InterfaceAndTestCaseBean> dataList = interfaceAndTestCaseBeanMapper.getInterfaceAndTestCaseBeanByInterfaceId(3);
        List<Object[]> oList = new ArrayList<>();
        for (InterfaceAndTestCaseBean i : dataList) {
            oList.add(new Object[]{i});
        }
        return oList.iterator();
    }

    @Test(testName = "快反接口", dataProvider = "dataKF")
    public void testKuaiFan(InterfaceAndTestCaseBean interfaceAndTestCaseBean) throws IOException {
        System.out.println("--------------" + interfaceAndTestCaseBean.toString());
        CloseableHttpResponse response = HttpUtil.getResponse(interfaceAndTestCaseBean);
        Map<String, String> expectResultMap = JsonUtil.getJsonMap(interfaceAndTestCaseBean.getExpectResult());
        JSONObject dataJSON = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
        log.info(dataJSON.toJSONString());
        SoftAssert sa = new SoftAssert();
        sa.assertEquals(dataJSON.get("message"), expectResultMap.get("message"));
        sa.assertEquals(dataJSON.get("success").toString(), expectResultMap.get("success"));
        sa.assertAll();
    }

    @DataProvider(name = "dataKK")
    public Iterator<Object[]> dataKK() {
        List<InterfaceAndTestCaseBean> dataList = interfaceAndTestCaseBeanMapper.getInterfaceAndTestCaseBeanByInterfaceId(4);
        List<Object[]> oList = new ArrayList<>();
        for (InterfaceAndTestCaseBean i : dataList) {
            oList.add(new Object[]{i});
        }
        return oList.iterator();
    }

    @Test(testName = "快反接口2", dataProvider = "dataKK")
    public void testKuaiFan2(InterfaceAndTestCaseBean interfaceAndTestCaseBean) throws IOException {
        System.out.println("--------------" + interfaceAndTestCaseBean.toString());
        CloseableHttpResponse response = HttpUtil.getResponse(interfaceAndTestCaseBean);
        Map<String, String> expectResultMap = JsonUtil.getJsonMap(interfaceAndTestCaseBean.getExpectResult());
        JSONObject dataJSON = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
        SoftAssert sa = new SoftAssert();
        sa.assertEquals(dataJSON.get("message"), expectResultMap.get("message"));
        sa.assertEquals(dataJSON.get("success").toString(), expectResultMap.get("success"));
        sa.assertAll();
    }
}