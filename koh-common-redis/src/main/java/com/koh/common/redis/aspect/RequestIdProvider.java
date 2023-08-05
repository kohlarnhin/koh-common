package com.koh.common.redis.aspect;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 唯一请求标识
 * <p>
 * 作者：kohlarnhin
 * 时间：2023年08月06日
 */
public interface RequestIdProvider {

    /**
     * 获取给定 HttpServletRequest 的唯一请求 ID。
     *
     * @param request HttpServletRequest 对象
     * @return 唯一请求 ID，类型为 String
     */
    String getRequestId(HttpServletRequest request);
}
