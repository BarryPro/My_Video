package com.belong.util;

import com.belong.config.ConstantConfig;
import redis.clients.jedis.Jedis;

/**
 * @author barry
 * @since 2018/4/22 17:37
 */
public class RedisUtil {
    private static Jedis jedis;//非切片额客户端连接
    static
    {
        initJedis();
    }

    /**
     * 初始化非切片池
     */
    private static void initJedis()
    {
        // 池基本配置
        jedis = new Jedis(ConstantConfig.HOST,ConstantConfig.RESIS_PORT,1000);
    }

    /**
     * 初始化切片池
     */

    public static void listWrite(String key,String value){
        jedis.lpush(key,value);
    }

    public static String listRead(String key) {
        return jedis.lpop(key);
    }

    public static void startRedis(){
        jedis.connect();
    }

    public static void stopRedis(){
        jedis.quit();
    }

    public static void main(String[] args) {

    }
}
