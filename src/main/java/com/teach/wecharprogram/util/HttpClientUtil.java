package com.teach.wecharprogram.util;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.util.*;


@Slf4j
public class HttpClientUtil {

    public static String sendPost(String url, Map params, Map headers) throws IOException {

        PostMethod postMethod = new PostMethod(url);
        postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        Set<String> set = headers.keySet();
        for (String k : set) {
            postMethod.setRequestHeader(k, headers.get(k).toString());
        }
        List<NameValuePair> data = new ArrayList<NameValuePair>();

        if (Objects.nonNull(params)) {
            for (Iterator iter = params.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
                String value = String.valueOf(params.get(name));
                data.add(new NameValuePair(name, value));
            }
        }


        NameValuePair[] nvps = new NameValuePair[data.size()];
        for (int i = 0; i < data.size(); i++) {
            nvps[i] = data.get(i);
        }
        // 将表单的值放入postMethod中
        postMethod.setRequestBody(nvps);

        HttpClient httpClient = new HttpClient();
        httpClient.executeMethod(postMethod);
        // 链接超时
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(120000);
        // 读取超时
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(120000);
        InputStream soapResponseData = postMethod.getResponseBodyAsStream();
        return new String(getString(soapResponseData).getBytes("UTF-8"),"UTF-8");
    }

    /**
     * @param url
     * @param params
     * @param headers
     * @return 发送json请求
     */
    public static String sendPostJson(String url, JSONObject params, Map headers) {
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        if (Objects.nonNull(headers)) {
            Set<String> set = headers.keySet();
            for (String k : set) {
                post.addHeader(k, headers.get(k).toString());
            }
        }
        try {
            StringEntity s = new StringEntity(params.toString());
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");//发送json数据需要设置contentType
            post.setEntity(s);
            HttpResponse res = httpclient.execute(post);
            String result = EntityUtils.toString(res.getEntity());// 返回json格式：
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param url
     * @return 发送json请求
     */
    public static String sendPostXml(String url, String xmlString) {
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        try {
            StringEntity s = new StringEntity(xmlString);
            s.setContentEncoding("UTF-8");
            s.setContentType("application/xml");//发送json数据需要设置contentType
            post.setEntity(s);
            HttpResponse res = httpclient.execute(post);
            String result = EntityUtils.toString(res.getEntity());// 返回json格式：
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param url
     * @param jsonString JSONObject.toJOSNString(obj)
     * @param charset
     * @return
     */
    public static String sendPostJson(String url, String jsonString, String charset) {
        try {
            PostMethod postMethod = new PostMethod(url);
            postMethod.setRequestHeader("Content-Type", "application/json; charset=" + charset);
            if (jsonString != null && !jsonString.trim().equals("")) {
                RequestEntity requestEntity = new StringRequestEntity(jsonString, "application/json", charset);
                postMethod.setRequestEntity(requestEntity);
            }
            HttpClient httpClient = new HttpClient();
            httpClient.executeMethod(postMethod);
            // 链接超时
            httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(120000);
            // 读取超时
            httpClient.getHttpConnectionManager().getParams().setSoTimeout(120000);
            String soapResponseData = postMethod.getResponseBodyAsString();

            return soapResponseData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param url
     * @param jsonString JSONObject.toJOSNString(obj)
     * @param charset
     * @return
     */
    public static byte[] sendPostJsonByte(String url, String jsonString, String charset) {
        try {
            PostMethod postMethod = new PostMethod(url);
            postMethod.setRequestHeader("Content-Type", "application/json; charset=" + charset);
            if (jsonString != null && !jsonString.trim().equals("")) {
                RequestEntity requestEntity = new StringRequestEntity(jsonString, "application/json", charset);
                postMethod.setRequestEntity(requestEntity);
            }
            HttpClient httpClient = new HttpClient();
            httpClient.executeMethod(postMethod);
            // 链接超时
            httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(120000);
            // 读取超时
            httpClient.getHttpConnectionManager().getParams().setSoTimeout(120000);
            byte[] responseBody = postMethod.getResponseBody();
            return responseBody;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String sendPost(String url, Map params) throws IOException {

        PostMethod postMethod = new PostMethod(url);
        postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        List<NameValuePair> data = new ArrayList<NameValuePair>();


        for (Iterator iter = params.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String value = String.valueOf(params.get(name));
            data.add(new NameValuePair(name, value));
        }

        NameValuePair[] nvps = new NameValuePair[data.size()];
        for (int i = 0; i < data.size(); i++) {
            nvps[i] = data.get(i);
        }
        // 将表单的值放入postMethod中
        postMethod.setRequestBody(nvps);

        HttpClient httpClient = new HttpClient();
        httpClient.executeMethod(postMethod);
        // 链接超时
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(120000);
        // 读取超时
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(120000);
        InputStream soapResponseData = postMethod.getResponseBodyAsStream();
        return getString(soapResponseData);
    }

    public static String sendGet(String url) {
        try {
            GetMethod getMethod = new GetMethod(url);
            getMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");

            HttpClient httpClient = new HttpClient();
            httpClient.getParams().setParameter(HttpMethodParams.USER_AGENT, "Apache-HttpClient/4.1.1 (java 1.5)");
            httpClient.executeMethod(getMethod);
            // 链接超时
            httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(120000);
            // 读取超时
            httpClient.getHttpConnectionManager().getParams().setSoTimeout(120000);
            String soapResponseData = getMethod.getResponseBodyAsString();
            soapResponseData = new String(soapResponseData.trim().getBytes("utf-8"), "utf-8");
            return soapResponseData;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public static String sendGet(String url,Map<String,Object> headers) throws IOException {

        GetMethod getMethod = new GetMethod(url);
        if(headers!=null){
            Set<Map.Entry<String, Object>> entrySet = headers.entrySet();
            entrySet.stream().forEach(o -> getMethod.setRequestHeader(new Header(o.getKey(),o.getValue().toString())));
        }else {
            getMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        }

        HttpClient httpClient = new HttpClient();
        httpClient.getParams().setParameter(HttpMethodParams.USER_AGENT, "Apache-HttpClient/4.1.1 (java 1.5)");
        httpClient.executeMethod(getMethod);
        // 链接超时
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(120000);
        // 读取超时
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(120000);
        String soapResponseData = getMethod.getResponseBodyAsString();
        soapResponseData = new String(soapResponseData.trim().getBytes("utf-8"), "utf-8");
        return soapResponseData;
    }

    public static InputStream sendGetFile(String url) {
        CloseableHttpClient client = HttpClients.createDefault();
        RequestConfig config = null;
        //使用代理
        config = RequestConfig.custom().build();
        //目标文件url
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(config);
        try {
            HttpResponse respone = client.execute(httpGet);
            if (respone.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                return null;
            }
            HttpEntity entity = respone.getEntity();
            if (entity != null) {
                InputStream is = entity.getContent();
                File file = new File("/zhuoan/hotel.csv");
                if (!new File("/zhuoan").exists()) {
                    new File("/zhuoan").mkdir();
                }
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buffer = new byte[4096];
                int len = -1;
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                return is;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String sendGetJson(String url) throws IOException {

        GetMethod getMethod = new GetMethod(url);
        getMethod.setRequestHeader("Content-Type", "application/json; charset=utf-8");

        HttpClient httpClient = new HttpClient();
        httpClient.getParams().setParameter(HttpMethodParams.USER_AGENT, "Apache-HttpClient/4.1.1 (java 1.5)");
        httpClient.executeMethod(getMethod);
        // 链接超时
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(120000);
        // 读取超时
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(120000);
        String soapResponseData = getMethod.getResponseBodyAsString();
        soapResponseData = new String(soapResponseData.trim().getBytes("ISO-8859-1"), "utf-8");
        return soapResponseData;
    }

    public static String getString(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer stringBuffer = new StringBuffer();
        String str = "";
        while ((str = reader.readLine()) != null) {
            stringBuffer.append(str);
        }
        return stringBuffer.toString();
    }
}