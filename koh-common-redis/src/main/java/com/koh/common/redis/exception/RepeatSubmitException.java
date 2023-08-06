/**
 * 重复提交异常类
 * 
 * 作者：kohlarnhin
 * 时间：2023年08月06日
 */

package com.koh.common.redis.exception;

import lombok.Getter;

/**
 * 当检测到重复提交时抛出的异常类。
 * <p>
 * 作者：kohlarnhin
 * 时间：2023年08月06日
 */
@Getter
public class RepeatSubmitException extends RuntimeException {

    /**
     * 错误消息。
     */
    private final String message;

    /**
     * 导致异常的请求的ID。
     */
    private final String requestId;

    /**
     * 导致异常的请求的URL。
     */
    private final String requestUrl;

    /**
     * 请求被发送到的服务。
     */
    private final String requestService;

    /**
     * 请求内容
     */
    private final String requestBody;

    /**
     * 构造一个新的RepeatSubmitException，其中包含指定的详细消息、请求ID、请求URL和请求服务。
     *
     * @param message 错误消息。
     * @param requestId 导致异常的请求的ID。
     * @param requestUrl 导致异常的请求的URL。
     * @param requestService 请求被发送到的服务。
     */
    public RepeatSubmitException(String message, String requestId, String requestUrl, String requestService, String requestBody) {
        super(message);
        this.message = message;
        this.requestId = requestId;
        this.requestUrl = requestUrl;
        this.requestService = requestService;
        this.requestBody = requestBody;
    }

}
