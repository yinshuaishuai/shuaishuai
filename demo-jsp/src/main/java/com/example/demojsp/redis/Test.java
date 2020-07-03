package com.example.demojsp.redis;

import redis.clients.jedis.Jedis;

/**
 * @Author YS
 * @Date 2020/6/28 11:23
 **/
public class Test {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1",6379);
//        jedis.auth("111234qwerasdf0");
        byte[] bytes = "test*".getBytes();
        jedis.psubscribe(new RedisAdapter(),bytes);
    }

}
