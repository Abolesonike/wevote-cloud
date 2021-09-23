package com.fizzy.redis.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

     /**
     * 普通缓存放入
     * @param key 键
     * @param value 值
      * @return true 成功 false 失败
      */
     public boolean set(String key, Object value) {
         try {
             System.out.println(key);
             System.out.println(value);
             ValueOperations<Object, Object> ops = redisTemplate.opsForValue();
             ops.set(key, value);
             System.out.println((String) ops.get(key));
             return true;
         } catch (Exception e) {
             e.printStackTrace();
             return false;
         }
     }
}
