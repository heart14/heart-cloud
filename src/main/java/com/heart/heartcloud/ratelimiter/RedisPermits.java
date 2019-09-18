package com.heart.heartcloud.ratelimiter;

/**
 * @ClassName: RedisPermits
 * @Description: TODO
 * @Author: jayhe
 * @Date: 2019/9/17 14:14
 * @Version: v1.0
 */
public class RedisPermits {

    private Long maxPermits;

    private Long storedPermits;

    private Long intervalMillis;

    private Long nextFreeTicketMillis = System.currentTimeMillis();

    public RedisPermits() {

    }


}
