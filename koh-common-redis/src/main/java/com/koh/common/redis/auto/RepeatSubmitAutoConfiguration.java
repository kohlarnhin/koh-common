package com.koh.common.redis.auto;

import com.koh.common.redis.aspect.RepeatSubmitAspect;
import com.koh.common.redis.aspect.RequestIdProvider;
import com.koh.common.redis.config.RepeatSubmitConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 重复提交自动配置类
 * <p>
 * 作者：kohlarnhin
 * 日期：2021年10月19日
 */
@Configuration
@ConditionalOnClass(RepeatSubmitAspect.class)
@EnableConfigurationProperties(RepeatSubmitConfig.class)
public class RepeatSubmitAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RequestIdProvider requestIdProvider() {
        return request -> null;
    }
    
    /**
     * 配置重复提交切面
     * @param requestIdProvider 请求ID提供者
     * @param repeatSubmitConfig 重复提交配置
     * @return RepeatSubmitAspect 重复提交切面
     */
    @Bean
    @ConditionalOnMissingBean
    public RepeatSubmitAspect repeatSubmitAspect(RequestIdProvider requestIdProvider, RepeatSubmitConfig repeatSubmitConfig) {
        return new RepeatSubmitAspect(requestIdProvider, repeatSubmitConfig);
    }
}
