package com.heart.heartcloud.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @ClassName:ShiroConfig
 * @Description:
 * @Author: Heart
 * @Date: 2019/7/12 10:05
 */
@Configuration
public class ShiroConfig {

    private static final Logger logger = LoggerFactory.getLogger(ShiroConfig.class);

    /**
     * 注入shiro过滤器
     *
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //过滤链定义
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        /*
            一种RBAC思路：
            在这里把不需要登录的uri配置好，然后加上/**配置所有uri都需要登录
            这样整个项目的身份认证就可以了
            然后开启shiro注解模式
            在接口上通过注解来设定访问uri所需要的角色、权限
         */

        //访问静态资源不需要身份验证
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/fonts/**", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        //用户登录的请求接口不需要身份验证
        filterChainDefinitionMap.put("/login", "anon");
        //用户注册的请求接口不需要身份验证
        filterChainDefinitionMap.put("/reg", "anon");
        //用户登出的请求接口不需要身份验证
        filterChainDefinitionMap.put("/logout", "anon");
        //其它所有请求都需要身份验证
        filterChainDefinitionMap.put("/**", "authc");
        //指定登录页面，不设置则默认寻找login.jsp
        shiroFilterFactoryBean.setLoginUrl("/loginpage");
        //指定未授权页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        /*
            但现在出现的问题是，尽管配置了上面的403页面
            当没有响应权限的用户访问uri时，会直接抛异常，页面返回500，而不是跳转到403页面
            虽然可以通过spring或者手动创建一个类继承什么什么来捕获异常设置页面跳转
            但想知道的是shiro竟然没有完善这一点吗？
         */

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 注入shiro securityManager
     *
     * @return
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm());
        return securityManager;
    }

    /**
     * 注入shiro 自定义Realm
     *
     * @return
     */
    @Bean
    public ShiroRealm shiroRealm() {
        ShiroRealm shiroRealm = new ShiroRealm();
        shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        shiroRealm.setAuthenticationCachingEnabled(true);
        shiroRealm.setAuthorizationCachingEnabled(true);
        return shiroRealm;
    }

    /**
     * 注入shiro 凭证匹配器
     * 用于加密
     *
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        hashedCredentialsMatcher.setHashIterations(2);
        return hashedCredentialsMatcher;
    }

    /**
     * 开启shiro aop注解配置
     * 可在接口上通过注解设置所需角色、权限
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * spring boot中要使用shiro权限注解，除了上面的advisor，还需要下面这个配置
     *
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    /**
     * spring提供的异常处理方法
     *
     * @return
     */
    @Bean(name = "simpleMappingExceptionResolver")
    public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
        SimpleMappingExceptionResolver simpleMappingExceptionResolver = new SimpleMappingExceptionResolver();
        Properties properties = new Properties();
        properties.setProperty("UnauthorizedException", "/403");
        simpleMappingExceptionResolver.setExceptionMappings(properties);
        simpleMappingExceptionResolver.setDefaultErrorView("/error");
        return simpleMappingExceptionResolver;
    }
}
