package com.example.util;

import com.example.entity.InterfaceAndTestCaseBean;
import com.example.entity.InterfaceBean;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.util.ObjectUtils;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class HttpUtil {
    private static HttpPost post;
    private static HttpGet get;
    private static HttpClient client=new DefaultHttpClient();

    public static CloseableHttpResponse getResponse(InterfaceAndTestCaseBean data) throws UnsupportedEncodingException {
        if (!ObjectUtils.isEmpty(data)){
            if (data.getInterfaceMethod().equalsIgnoreCase("POST")){
                post=new HttpPost(data.getInterfaceUrl());
                Map<String, String> jsonMap = JsonUtil.getJsonMap(data.getCaseHeader());
                jsonMap.forEach((headerKey, headerValue) -> post.setHeader(headerKey, headerValue));
                post.setEntity(new StringEntity(data.getCaseBody()));
            }else if (data.getInterfaceMethod().equalsIgnoreCase("GET")){
                get=new HttpGet(data.getInterfaceUrl());
            }
        }
        return null;
    }
}
