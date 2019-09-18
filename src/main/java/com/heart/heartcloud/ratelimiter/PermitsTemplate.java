package com.heart.heartcloud.ratelimiter;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * @ClassName: PermitsTemplate
 * @Description: TODO
 * @Author: jayhe
 * @Date: 2019/9/17 14:12
 * @Version: v1.0
 */
public class PermitsTemplate extends RedisTemplate<String,RedisPermits> {
}
