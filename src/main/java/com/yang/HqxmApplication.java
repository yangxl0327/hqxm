package com.yang;

import org.apache.catalina.Context;
import org.apache.tomcat.util.scan.StandardJarScanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author 龙小洋
 */
@SpringBootApplication

@MapperScan("com.yang.dao")
public class HqxmApplication {

    public static void main(String[] args) {
        SpringApplication.run(HqxmApplication.class, args);
    }
    /**
     * 不扫描Manifest文件
     * @return
     * 在启动类中配置不扫描manifest文件
     * springboot启动报jar包扫描错误，但不影响项目正常使用：
     */
    @Bean
    public TomcatServletWebServerFactory tomcatFactory() {
        return new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                ((StandardJarScanner) context.getJarScanner()).setScanManifest(false);
            }
        };
    }


}
