package com.fizzy.redis.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

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
             ValueOperations<Object, Object> ops = redisTemplate.opsForValue();
             ops.set(key, value);
             return true;
         } catch (Exception e) {
             e.printStackTrace();
             return false;
         }
     }

    /**
     * 普通缓存放入，设置过期时间
     * @param key 键
     * @param value 值
     * @return true 成功 false 失败
     */
    public boolean setExpire(String key, Object value,long time ,TimeUnit timeUnit) {
        try {
            ValueOperations<Object, Object> ops = redisTemplate.opsForValue();
            ops.set(key, value, time, timeUnit);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 普通缓存取出
     * @param key 键
     * @return true 成功 false 失败
     */
    public String get(String key) {
        try {
            ValueOperations<Object, Object> ops = redisTemplate.opsForValue();
            return (String) ops.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            return "失败";
        }
    }

    public boolean delete(String key) {
        try {
            return Boolean.TRUE.equals(redisTemplate.delete(key));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean setZSet(String key, Object value, double heat) {
        try {
            ZSetOperations<Object, Object> ops = redisTemplate.opsForZSet();
            ops.add(key, value, heat);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeZSet(String key, Object value) {
        try {
            ZSetOperations<Object, Object> ops = redisTemplate.opsForZSet();
            ops.remove(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 分页取出 hash中zset值
     * @param key key
     * @param pageNum 页码
     * @param pageSize 页大小
     * @return 结果
     */
    public Set<Object> getZSet(String key, int pageNum, int pageSize){
        Set<Object> result = null;
        ZSetOperations<Object, Object> ops = redisTemplate.opsForZSet();
        try {
            result = ops.reverseRangeByScore(key, 0, 100000, (long) (pageNum - 1) *pageSize, pageSize);
            //1 100000代表score的排序氛围值，即从1-100000的范围
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean setSet(String key, Object value) {
        try {
            SetOperations<Object, Object> ops = redisTemplate.opsForSet();
            ops.add(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Set<Object> getSet(String key) {
        try {
            SetOperations<Object, Object> ops = redisTemplate.opsForSet();
            return ops.members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean removeSet(String key, Object value) {
        try {
            SetOperations<Object, Object> ops = redisTemplate.opsForSet();
            ops.remove(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public Long setSize(String key) {
        try {
            SetOperations<Object, Object> ops = redisTemplate.opsForSet();
            return  ops.size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    public boolean isSetMember(String key, Object value) {
        try {
            SetOperations<Object, Object> ops = redisTemplate.opsForSet();
            return Boolean.TRUE.equals(ops.isMember(key, value));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
