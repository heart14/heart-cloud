package com.heart.heartcloud;

import com.heart.heartcloud.domain.CloudUser;
import com.heart.heartcloud.service.CloudUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HeartCloudApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private CloudUserService cloudUserService;

    @Test
    public void redisTest() {
        // redis存储数据
        String key1 = "heart";
        redisTemplate.opsForValue().set(key1, "redis test...");
        // 获取数据
        String value = (String) redisTemplate.opsForValue().get(key1);

        System.out.println("Redis :Key = " + key1 + ", Value = " + value);

        List<CloudUser> allUser = cloudUserService.findAllUser();

        String key2 = "cloud";
        redisTemplate.opsForValue().set(key2, allUser);
        List<CloudUser> userList = (List<CloudUser>) redisTemplate.opsForValue().get(key2);
        System.out.println("Redis :Key = " + key2 + ", Value = " + userList);
    }
}
