package com.example.util;

import com.alibaba.fastjson.JSONObject;
import com.example.entity.InterfaceAndTestCaseBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.bson.json.JsonObject;
import org.springframework.util.ObjectUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
public class HttpUtil {
    private static HttpPost post;
    private static HttpGet get;
    private static CloseableHttpResponse response;
    private static CloseableHttpClient httpClient = getHttpClient(false);

    public static CloseableHttpResponse getResponse(InterfaceAndTestCaseBean data) throws IOException {
        if (httpClient != null) {
            httpClient=getHttpClient(false);
            if (!ObjectUtils.isEmpty(data)) {
                if (data.getInterfaceMethod().equalsIgnoreCase("POST")) {
                    log.info("---------这是POST请求---------");
                    try {
                        post = new HttpPost(data.getInterfaceUrl());
                        Map<String, String> jsonMap = JsonUtil.getJsonMap(data.getCaseHeader());
                        jsonMap.forEach((headerKey, headerValue) -> post.setHeader(headerKey, headerValue));
                        if (!ObjectUtils.isEmpty(data.getCaseBody())) {
                            JSONObject json = JSONObject.parseObject(data.getCaseBody());
                            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                            for (String key : json.keySet()) {
                                parameters.add(new BasicNameValuePair(key, json.getString(key)));
                            }
                            // 将Content-Type设置为application/x-www-form-urlencoded类型
                            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters, "UTF-8");
                            post.setEntity(formEntity);
                        }
                        StringEntity entity = new StringEntity(data.getCaseBody(), "UTF-8");
                        response = httpClient.execute(post);
                        return response;
                    } catch (ClientProtocolException e) {
                        e.printStackTrace();
                    }
                } else if (data.getInterfaceMethod().equalsIgnoreCase("GET")) {
                    CloseableHttpClient httpClient = getHttpClient(false);
                    log.info("---------这是GET请求---------");
                    if (!ObjectUtils.isEmpty(data.getCaseBody())) {
                        StringBuffer params = new StringBuffer();
                        try {
                            Map<String, String> dataMap = JsonUtil.getJsonMap(data.getCaseBody());
                            if (!ObjectUtils.isEmpty(dataMap)) {
                                params.append(data.getInterfaceUrl()).append("?");
                                for (String key : dataMap.keySet()) {
                                    if (!dataMap.get(key).equalsIgnoreCase("")) {
                                        params.append(key + "=" + URLEncoder.encode(dataMap.get(key), "utf-8"));
                                        params.append("&");
                                    } else {
                                        params.deleteCharAt(params.length() - 1);
                                        params.append(key);
                                        break;
                                    }
                                }
                                if (params.indexOf("&") > 0) {
                                    params.deleteCharAt(params.length() - 1);
                                }
                                get = new HttpGet(String.valueOf(params));
                            }
                            get = new HttpGet(String.valueOf(params));
                            log.info(String.valueOf(params));
                            try {
                                // 配置信息
                                RequestConfig requestConfig = RequestConfig.custom()
                                        // 设置连接超时时间(单位毫秒)
                                        .setConnectTimeout(5000)
                                        // 设置请求超时时间(单位毫秒)
                                        .setConnectionRequestTimeout(5000)
                                        // socket读写超时时间(单位毫秒)
                                        .setSocketTimeout(5000)
                                        // 设置是否允许重定向(默认为true)
                                        .setRedirectsEnabled(true).build();

                                // 将上面的配置信息 运用到这个Get请求里
                                get.setConfig(requestConfig);
                                // 由客户端执行(发送)Get请求
                                response = httpClient.execute(get);
                                return response;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } finally {

                        }
                    } else {
                        get = new HttpGet(data.getInterfaceUrl());
                        response=httpClient.execute(get);
                        return response;
                    }
                } else if (data.getInterfaceMethod().equalsIgnoreCase("multipart/form-data")) {
                    System.out.println("这是multipart/form-data方法!");
                    post = new HttpPost(data.getInterfaceUrl());
                    CloseableHttpClient httpClient = getHttpClient(false);
                    try {
                        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
                        // 第一个文件
                        String filesKey = "files";
                        File file1 = new File("C:\\Users\\JustryDeng\\Desktop\\back.jpg");
                        multipartEntityBuilder.addBinaryBody(filesKey, file1);
                        // 第二个文件(多个文件的话，使用同一个key就行，后端用数组或集合进行接收即可)
                        File file2 = new File("C:\\Users\\JustryDeng\\Desktop\\头像.jpg");
                        // 防止服务端收到的文件名乱码。 我们这里可以先将文件名URLEncode，然后服务端拿到文件名时在URLDecode。就能避免乱码问题。
                        // 文件名其实是放在请求头的Content-Disposition里面进行传输的，如其值为form-data; name="files"; filename="头像.jpg"
                        multipartEntityBuilder.addBinaryBody(filesKey, file2, ContentType.DEFAULT_BINARY, URLEncoder.encode(file2.getName(), "utf-8"));
                        // 其它参数(注:自定义contentType，设置UTF-8是为了防止服务端拿到的参数出现乱码)
                        ContentType contentType = ContentType.create("text/plain", Charset.forName("UTF-8"));
                        multipartEntityBuilder.addTextBody("name", "邓沙利文", contentType);
                        multipartEntityBuilder.addTextBody("age", "25", contentType);
                        HttpEntity httpEntity = multipartEntityBuilder.build();
                        post.setEntity(httpEntity);

                        response = httpClient.execute(post);
                        HttpEntity responseEntity = response.getEntity();
                        System.out.println("HTTPS响应状态为:" + response.getStatusLine());
                        if (responseEntity != null) {
                            System.out.println("HTTPS响应内容长度为:" + responseEntity.getContentLength());
                            // 主动设置编码，来防止响应乱码
                            String responseStr = EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);
                            System.out.println("HTTPS响应内容为:" + responseStr);
                        }
                    } catch (ParseException | IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            // 释放资源
                            if (httpClient != null) {
                                httpClient.close();
                            }
                            if (response != null) {
                                response.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }

    private static CloseableHttpClient getHttpClient(boolean isHttps) {
        CloseableHttpClient httpClient;
        if (isHttps) {
            SSLConnectionSocketFactory sslSocketFactory;
            try {
                /// 如果不作证书校验的话
                sslSocketFactory = getSocketFactory(false, null, null);

                /// 如果需要证书检验的话
                // 证书
                //InputStream ca = this.getClass().getClassLoader().getResourceAsStream("client/ds.crt");
                // 证书的别名，即:key。 注:cAalias只需要保证唯一即可，不过推荐使用生成keystore时使用的别名。
                // String cAalias = System.currentTimeMillis() + "" + new SecureRandom().nextInt(1000);
                //sslSocketFactory = getSocketFactory(true, ca, cAalias);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            httpClient = HttpClientBuilder.create().setSSLSocketFactory(sslSocketFactory).build();
            return httpClient;
        }
        httpClient = HttpClientBuilder.create().build();
        return httpClient;
    }

    /**
     * HTTPS辅助方法, 为HTTPS请求 创建SSLSocketFactory实例、TrustManager实例
     *
     * @param needVerifyCa  是否需要检验CA证书(即:是否需要检验服务器的身份)
     * @param caInputStream CA证书。(若不需要检验证书，那么此处传null即可)
     * @param cAalias       别名。(若不需要检验证书，那么此处传null即可)
     *                      注意:别名应该是唯一的， 别名不要和其他的别名一样，否者会覆盖之前的相同别名的证书信息。别名即key-value中的key。
     * @return SSLConnectionSocketFactory实例
     * @throws NoSuchAlgorithmException 异常信息
     * @throws CertificateException     异常信息
     * @throws KeyStoreException        异常信息
     * @throws IOException              异常信息
     * @throws KeyManagementException   异常信息
     * @date 2019/6/11 19:52
     */
    private static SSLConnectionSocketFactory getSocketFactory(boolean needVerifyCa, InputStream caInputStream, String cAalias)
            throws CertificateException, NoSuchAlgorithmException, KeyStoreException,
            IOException, KeyManagementException {
        X509TrustManager x509TrustManager;
        // https请求，需要校验证书
        if (needVerifyCa) {
            KeyStore keyStore = getKeyStore(caInputStream, cAalias);
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
            }
            x509TrustManager = (X509TrustManager) trustManagers[0];
            // 这里传TLS或SSL其实都可以的
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{x509TrustManager}, new SecureRandom());
            return new SSLConnectionSocketFactory(sslContext);
        }
        // https请求，不作证书校验
        x509TrustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] arg0, String arg1) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] arg0, String arg1) {
                // 不验证
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, new TrustManager[]{x509TrustManager}, new SecureRandom());
        return new SSLConnectionSocketFactory(sslContext);
    }

    /**
     * 获取(密钥及证书)仓库
     * 注:该仓库用于存放 密钥以及证书
     *
     * @param caInputStream CA证书(此证书应由要访问的服务端提供)
     * @param cAalias       别名
     *                      注意:别名应该是唯一的， 别名不要和其他的别名一样，否者会覆盖之前的相同别名的证书信息。别名即key-value中的key。
     * @return 密钥、证书 仓库
     * @throws KeyStoreException        异常信息
     * @throws CertificateException     异常信息
     * @throws IOException              异常信息
     * @throws NoSuchAlgorithmException 异常信息
     * @date 2019/6/11 18:48
     */
    private static KeyStore getKeyStore(InputStream caInputStream, String cAalias)
            throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException {
        // 证书工厂
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        // 秘钥仓库
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null);
        keyStore.setCertificateEntry(cAalias, certificateFactory.generateCertificate(caInputStream));
        return keyStore;
    }
}