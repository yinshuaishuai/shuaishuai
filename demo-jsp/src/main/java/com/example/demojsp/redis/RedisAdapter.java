package com.example.demojsp.redis;

import redis.clients.jedis.BinaryJedisPubSub;

import java.nio.charset.StandardCharsets;

/**
 * @Author YS
 * @Date 2020/6/28 11:24
 **/
public class RedisAdapter extends BinaryJedisPubSub {

    public RedisAdapter() {

    }

    public void onPMessage(byte[] pattern, byte[] channel, byte[] message) {
        System.out.println(new String(message, StandardCharsets.UTF_8));
        System.out.println(new String(channel, StandardCharsets.UTF_8));
        System.out.println(new String(pattern, StandardCharsets.UTF_8));

    }

}
