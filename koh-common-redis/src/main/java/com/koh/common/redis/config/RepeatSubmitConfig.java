package com.koh.common.redis.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类，用于防止重复提交。
 * <p>
 * 作者：kohlarnhin
 * 时间：2023年08月06日
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "repeat.submit")
public class RepeatSubmitConfig {
    /**
     * 启用/禁用重复提交预防的标志。
     */
    private boolean enable;

    /**
     * 用于重复提交预防的服务名称。
     */
    private String serviceName;
}
