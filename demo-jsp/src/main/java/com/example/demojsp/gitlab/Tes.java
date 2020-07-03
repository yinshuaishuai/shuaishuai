package com.example.demojsp.gitlab;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @Author YS
 * @Date 2020/6/29 11:35
 **/
public class Tes {

    public static void main1(String[] args) throws IOException {//https://gitlab.com/yinshuaishuai/record
        HttpGet httpGet = new HttpGet("http://gitlab.com/api/v4/projects/19647349/repository/tree?private_token=VEvRfWA9ixkWEfjN61r_&ref=master");
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse execute = client.execute(httpGet);
        HttpEntity entity = execute.getEntity();
        System.out.println(EntityUtils.toString(entity, "UTF-8"));
    }

    public static void main(String[] args) throws IOException {//https://gitlab.com/yinshuaishuai/record
        HttpGet httpGet = new HttpGet("http://gitlab.com/api/v4/projects/19647349/repository/commits?private_token=VEvRfWA9ixkWEfjN61r_&ref=master&page=1&per_page=1");
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse execute = client.execute(httpGet);
        HttpEntity entity = execute.getEntity();
        System.out.println(EntityUtils.toString(entity, "UTF-8"));
    }


 public static void main2(String[] args) throws IOException {//https://gitlab.com/yinshuaishuai/record
        HttpGet httpGet = new HttpGet("http://gitlab.com/api/v4/projects/19647349/repository/files/20200629.md?private_token=VEvRfWA9ixkWEfjN61r_&ref=master");
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse execute = client.execute(httpGet);
        HttpEntity entity = execute.getEntity();
     String s = EntityUtils.toString(entity, "UTF-8");
//     System.out.println(EntityUtils.toString(entity, "UTF-8"));
     JsonParser jsonParser = new JsonParser();
     JsonObject asJsonObject = jsonParser.parse(s).getAsJsonObject();
     Base64.Decoder decoder = Base64.getDecoder();
     String content = asJsonObject.get("content").getAsString();
     byte[] decode = decoder.decode(content);
     System.out.println(new String(decode, StandardCharsets.UTF_8));
//     Base64.Decoder decoder = Base64.getDecoder();
//     byte[] decode = decoder.decode("W3siaWQiOiJlNjlkZTI5YmIyZDFkNjQzNGI4YjI5YWU3NzVhZDhjMmU0OGM1MzkxIiwibmFtZSI6IuaWsOW7uuS9jeWbvuWbvuWDjy5ibXAiLCJ0eXBlIjoiYmxvYiIsInBhdGgiOiJkb2Mv5paw5bu65L2N5Zu+5Zu+5YOPLmJtcCIsIm1vZGUiOiIxMDA2NDQifV0KW3siaWQiOiI4NjY1MDc4ZGMzNDQ5MmZjYzJhNTBmODRlNmYzMDYwMmU3N2UyMTdhIiwibmFtZSI6ImRvYyIsInR5cGUiOiJ0cmVlIiwicGF0aCI6ImRvYyIsIm1vZGUiOiIwNDAwMDAifSx7ImlkIjoiZDcwM2YwOWRhZWViNWE1YTJkZWUzYjMwMDM2YTVjNmY2OTIzMDlhMCIsIm5hbWUiOiJpbWFnZXMiLCJ0eXBlIjoidHJlZSIsInBhdGgiOiJpbWFnZXMiLCJtb2RlIjoiMDQwMDAwIn1d");
//     System.out.println(new String(decode, StandardCharsets.UTF_8));

 }

}
