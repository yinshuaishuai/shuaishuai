package com.example.demojsp;

import com.example.demojsp.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @Author YS
 * @Date 2020/6/24 17:29
 **/
//@Controller
    @RestController
public class TestController {


        @Autowired
        private TestService testService;

        @GetMapping("/index")
        public String index(){
            testService.getTable();
            return "完成";
        }

        @GetMapping("/del")
        public String del(){
            testService.delTable();
            return "完成";
        }


        @GetMapping("/test")
        public void test(HttpServletResponse response) throws IOException {
            BufferedImage read = ImageIO.read(new File("D:\\demo\\record\\images\\hystrix-thread-pool-queue.png"));
            ImageIO.write(read,"png",response.getOutputStream());
        }


//
//    @GetMapping("index")
//    public String index(){
//        return "index";
//    }
//
//
//    private static TestService testService;
//
//
//    public TestService getTestService() {
//        return testService;
//    }
//
//    @Autowired
//    public void setTestService(TestService testService) {
//        TestController.testService = testService;
//    }
//
//    @RequestMapping("test")
//    public void test(){
//        testService.getS();
//        System.out.println(1111);
//    }

}
