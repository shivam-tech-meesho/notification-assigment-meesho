package com.meesho.notification_system.services;

import com.meesho.notification_system.constants.Redis;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class BlackListServiceImp implements BlackListService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    public void addToBlockList(List<String> numbers) {

        stringRedisTemplate.executePipelined(
                new RedisCallback<Object>() {
                    public Object doInRedis(@NonNull RedisConnection connection) throws DataAccessException {
                        StringRedisConnection stringRedisConn = (StringRedisConnection) connection;
                        for (String number : numbers) {
                            stringRedisConn.sAdd(Redis.BLOCKLIST_KEY, number);
                        }
                        return null;
                    }
                });

    }

    public void removeFromBlockList(List<String> numbers) {

        stringRedisTemplate.executePipelined(
                new RedisCallback<Object>() {
                    public Object doInRedis(@NonNull RedisConnection connection) throws DataAccessException {
                        StringRedisConnection stringRedisConn = (StringRedisConnection) connection;
                        for (String number : numbers) {
                            stringRedisConn.sRem(Redis.BLOCKLIST_KEY, number);
                        }
                        return null;
                    }
                });

    }

    public boolean isBlocked(String number) {
        return Boolean.TRUE.equals(stringRedisTemplate.opsForSet().isMember(Redis.BLOCKLIST_KEY, number));
    }

}
