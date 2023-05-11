package com.yang.config;

import com.yang.realm.MyRealm;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

@Configuration //1.声明一个配置类@
public class ShiroFilter {
    //2.声明一个@Bean对象交给spring工厂管理 需要的Bean对象为过滤器

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(){
        //可以通过ShiroFilterFactoryBean 配置整个shiro过滤器
        //3.创建一个shiroFilterFactoryBean 对象
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //4.配置过滤器链（1.一定使用LinkedHashMap  2.要讲anon过滤器声明写在前面）
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        //(如果出现静态资源被拦截的情况会出现302错误！) anon:不拦截
        linkedHashMap.put("/img/**","anon");
        linkedHashMap.put("/boot/**","anon");
        linkedHashMap.put("/echarts/**","anon");
        linkedHashMap.put("/jqgrid/**","anon");
        linkedHashMap.put("/kindeditor/**","anon");
        linkedHashMap.put("/upload/**","anon");
        //将登陆方法方行
        linkedHashMap.put("/admin/login","anon");
        linkedHashMap.put("/admin/yzm","anon");
        //authc 通过验证方可方行
        linkedHashMap.put("/**","authc");
        //6.将过滤器链交给shiroFilterFactoryBean 管理
        shiroFilterFactoryBean.setFilterChainDefinitionMap(linkedHashMap);
        //7.设置登陆的URL
        shiroFilterFactoryBean.setLoginUrl("/jsp/login.jsp");
        //8.将DefaultWebSecurityManager交给shiroFilterFactoryBean
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        return  shiroFilterFactoryBean;

    }
    //创建SecurityManager对象交给spring管理

    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myRealm());
        EhCacheManager ehCacheManager = new EhCacheManager();
        securityManager.setCacheManager(ehCacheManager);
        return securityManager;
    }
    //创建myRealm对象 交给spring工厂管理
    @Bean
    public MyRealm myRealm(){
        MyRealm myRealm = new MyRealm();
        return myRealm;
    }


}

