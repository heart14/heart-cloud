package com.heart.heartcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName: ThreadPoolConfig
 * @Description: TODO
 * @Author: jayhe
 * @Date: 2019/9/27 10:33
 * @Version: v1.0
 */
@Configuration
public class ThreadPoolConfig {

    /**
     * 核心线程池大小
     */
    private int corePoolSize = 50;

    /**
     * 最大可创建的线程数
     */
    private int maxPoolSize = 200;

    /**
     * 线程池维护线程所允许的空闲时间
     */
    private long keepAliveSeconds = 0L;

    @Bean(name = "threadPoolExecutor")
    public ThreadPoolExecutor threadPoolExecutor() {
        return new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveSeconds, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), new ThreadFactory() {

            private final AtomicInteger tCount = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "Heart cloud #" + tCount.getAndIncrement());
            }
        });
    }
}
