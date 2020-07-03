package com.example.demojsp.github;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BufferedHeader;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author YS
 * @Date 2020/6/30 16:57
 **/
public class Test {


    public static void main(String[] args) throws IOException {//https://www.jianshu.com/p/a0c7d0482415
//            /
//            JavaGuide
        HttpGet httpPost = new HttpGet("https://api.github.com/repos/Snailclimb/JavaGuide/commits?page=2&per_page=40&ref=master");
//        HttpGet httpPost = new HttpGet("https://api.github.com/repos/Snailclimb/JavaGuide/contents?ref=master");
//        httpPost.setHeader("token","8b55d362a20c96be96b0982664420399d98d7602");
//        httpPost.setHeader("Accept", "application/json");
//        Header ht = new BufferedHeader();
//        httpPost.setHeader(ht);
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse execute = client.execute(httpPost);
        HttpEntity entity = execute.getEntity();
        System.out.println(EntityUtils.toString(entity, "UTF-8"));
    }
public static void main1(String[] args) throws IOException {//https://www.jianshu.com/p/a0c7d0482415
//            /
//            JavaGuide
//        HttpGet httpPost = new HttpGet("https://api.github.com/repos/Snailclimb/JavaGuide/commits?page=1&per_page=1&ref=master");
        HttpGet httpPost = new HttpGet("https://api.github.com/repos/Snailclimb/JavaGuide/zipball/master");
//        httpPost.setHeader("token","8b55d362a20c96be96b0982664420399d98d7602");
//        httpPost.setHeader("Accept", "application/json");
//        Header ht = new BufferedHeader();
//        httpPost.setHeader(ht);
    long offset = 1000*1024;
    httpPost.addHeader("Range", "bytes=" + 0 + "-"
            + (0 + offset - 1));
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse execute = client.execute(httpPost);
    HttpEntity entity = execute.getEntity();
//    System.out.println(EntityUtils.toString(entity,"UTF-8"));
    InputStream is = entity.getContent();
    File file = new File("D:\\abc.zip");
    FileOutputStream fileout = new FileOutputStream(file);
    byte[] buffer=new byte[1024];
    int ch = 0;
    while ((ch = is.read(buffer)) != -1) {
        fileout.write(buffer,0,ch);
    }
    is.close();
    fileout.flush();
    fileout.close();
    }

}
