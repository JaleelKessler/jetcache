package com.alicp.jetcache.autoconfigure;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import com.alicp.jetcache.support.FastjsonKeyConvertor;
import com.alicp.jetcache.support.JavaValueDecoder;
import com.alicp.jetcache.support.JavaValueEncoder;
import com.alicp.jetcache.test.beans.MyFactoryBean;
import com.alicp.jetcache.test.spring.SpringTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.function.Function;

/**
 * Created on 2019/6/21.
 *
 * @author huangli
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.alicp.jetcache.test.beans", "com.alicp.jetcache.anno.inittestbeans"})
@EnableMethodCache(basePackages = {"com.alicp.jetcache.test.beans", "com.alicp.jetcache.anno.inittestbeans"})
@EnableCreateCacheAnnotation
public class MockStarterTest extends SpringTest {

    @Test
    public void tests() throws Exception {
        System.setProperty("spring.profiles.active", "mock");
        context = SpringApplication.run(MockStarterTest.class);
        doTest();
    }

    @Component
    public static class A {
        @CreateCache
        private Cache cache;

        @PostConstruct
        public void test() {
            Assert.assertTrue(cache.PUT("K", "V").isSuccess());
        }
    }

    @Bean(name = "factoryBeanTarget")
    public MyFactoryBean factoryBean() {
        return new MyFactoryBean();
    }

    @Bean
    public Function<Object, byte[]> myEncoder() {
        return new JavaValueEncoder(true);
    }

    @Bean
    public Function<byte[], Object> myDecoder() {
        return new JavaValueDecoder(true);
    }

    @Bean
    public Function<Object, Object> myConvertor() {
        return FastjsonKeyConvertor.INSTANCE;
    }

}
