package com.imooc.miaosha.redis;

import com.alibaba.fastjson.JSON;
import com.imooc.miaosha.controller.GoodsController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisService {

    private JedisPool jedisPool;

    private static Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    public RedisService(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }


    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz) {
        /* *
         * 获取单个对象
         * @param [prefix, key, clazz]
         * @return T
         */
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            String str = jedis.get(realKey);
            return stringToBean(str, clazz);
        } finally {
            //释放jedis连接
            returnToPool(jedis);
        }
    }

    public <T> boolean set(KeyPrefix prefix, String key, T value) {
        /* *
         * 设置单个对象 将T类型的 value 序列化,并拼接出正确的key,将(key,value)存入redis
         * @param [prefix, key, value]
         * @return boolean
         */
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            String str = beanToString(value);
            if (str == null || str.length() <= 0) {
                return false;
            }
            int seconds = prefix.expireSeconds();
            if (seconds <= 0) {
                jedis.set(realKey, str);
            } else {
                //给value 加上一个有效期
                String code = jedis.setex(realKey, seconds, str);
                logger.info("setex to redis => " + code);
            }
            return true;
        } finally {
            returnToPool(jedis);
        }
    }

    public boolean exist(KeyPrefix prefix, String key) {
        /* *
         * 判断是否存在
         * @param [prefix, key]
         * @return boolean
         */
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            return jedis.exists(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    public Long incr(KeyPrefix prefix, String key) {
        /* *
         * 增加值
         * @param [prefix, key]
         * @return java.lang.Long
         */
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            return jedis.incr(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    public Long decr(KeyPrefix prefix, String key) {
        /* *
         * 减少值 如果key不存在 或者值的类型不匹配, 返回-1,(原子操作
         * @param [prefix, key]
         * @return java.lang.Long
         */
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            return jedis.decr(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    private <T> String beanToString(T value) {
        if (value == null) {
            return null;
        }
        //fixme 为什么这个地方可以用 泛型.getClass() 得到泛型类型，而 get()方法却要用Class<T> clazz
        Class<?> clazz = value.getClass();
        if (clazz == int.class || clazz == Integer.class) {
            return "" + value;
        } else if (clazz == String.class) {
            //fixme 为什么把String强制转换成String
            return (String) value;
        } else if (clazz == long.class || clazz == Long.class) {
            return "" + value;
        } else {
            //对于其他类型 当作bean处理
            /* *
             * JavaBean是特殊的Java类
             * 1.提供一个默认的无参构造函数。
             * 2.需要被序列化并且实现了Serializable接口。
             * 3.可能有一系列可读写属性。
             * 4.可能有一系列的"getter"或"setter"方法。
             */
            return JSON.toJSONString(value);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T stringToBean(String str, Class<T> clazz) {
        if (str == null || str.length() <= 0 || clazz == null) {
            return null;
        }
        if (clazz == int.class || clazz == Integer.class) {
            return (T) Integer.valueOf(str);
        } else if (clazz == String.class) {
            return (T) str;
        } else if (clazz == long.class || clazz == Long.class) {
            return (T) Long.valueOf(str);
        } else {
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }

    private void returnToPool(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }


}
