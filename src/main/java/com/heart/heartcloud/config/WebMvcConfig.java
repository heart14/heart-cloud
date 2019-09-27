package com.heart.heartcloud.config;

import com.heart.heartcloud.common.CloudConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName: WebMvcConfig
 * @Description:
 * @Author: Heart
 * @Date: 2019/7/23 17:17
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 本地图片映射虚拟路径
     * 使得可以用http://localhost:8080/image/1.jpg的方式访问本地E://HEARTCLOUD/1.jpg的图片
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/local/**").addResourceLocations("file:"+CloudConstants.ROOT_DIR);
    }

}
