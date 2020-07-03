package com.example.demojsp.gitlab;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.misc.Extension;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import java.util.Arrays;
import java.util.Base64;

/**
 * @Author YS
 * @Date 2020/6/29 14:32
 **/
public class Test {

    public static void main(String[] args) throws IOException {
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
        String s1 = new String(decode, StandardCharsets.UTF_8);


        MutableDataSet options = new MutableDataSet();
        options.setFrom(ParserEmulationProfile.MARKDOWN);
        options.set(Parser.EXTENSIONS, Arrays.asList(new Extension[] { TablesExtension.create()}));
        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        Node document = parser.parse(s1);
        String html = renderer.render(document);
        System.out.println(html);
    }

    public static void main1(String[] args) throws IOException {//projects/:id/repository/archive
        HttpGet httpGet = new HttpGet("http://gitlab.com/api/v4/projects/19647349/repository/archive.zip?private_token=VEvRfWA9ixkWEfjN61r_&ref=master");
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse execute = client.execute(httpGet);
        HttpEntity entity = execute.getEntity();
        InputStream is = entity.getContent();
        File file = new File("D:\\a.zip");
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
