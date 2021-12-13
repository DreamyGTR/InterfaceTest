package java.com.example.interfacetest;

import com.alibaba.fastjson.JSONObject;
import com.example.mapper.InterFaceMapper;
import com.example.mapper.TestCaseBeanMapper;
import com.example.util.JsonUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class Test1 extends BaseTest{
    @Autowired
    private InterFaceMapper interFaceMapper;

    @Autowired
    private TestCaseBeanMapper testCaseBeanMapper;
    private CloseableHttpClient client;
    private CloseableHttpResponse response;


    @Test
    public void test() throws IOException {
//     client=  HttpClientBuilder.create().build();
//     HttpGet get=new HttpGet(interFaceMapper.getInterfaceList().get(0).getUrl());
//     response=client.execute(get);
//     System.out.println(EntityUtils.toString(response.getEntity()));


//        List<InterfaceBean> interfaceBeans = interFaceMapper.getInterfaceList();
//        List<TestCaseBean> cascList= testCaseBeanMapper.getTestCaseBeanList();
//        cascList.forEach(caseInfo -> {
//            HttpPost testPost = new HttpPost();
//            String headerJson = caseInfo.getHeader();
//            Map<String, String> headerMap = JSONObject.parseObject(headerJson, Map.class);
//            headerMap.forEach((headerKey, headerValue) -> testPost.setHeader(headerKey, headerValue));
//        });


        // 查询中间表
        // 查询接口表Map
        // Case放入Map
        // 根据接口表List获取Case表信息
        // 遍历中间表, 将用例信息放入单个请求中,发送请求
    }

    @Test
    public void testJson(){
        String expectResult="{\"status\":\"200\",\"success\":\"true\"}";
        JSONObject jsonObject1 = JsonUtil.getJsonObject(expectResult);
        Assert.assertEquals("200",jsonObject1.get("status"));
        Assert.assertEquals("true",jsonObject1.get("success"));
    }
}
