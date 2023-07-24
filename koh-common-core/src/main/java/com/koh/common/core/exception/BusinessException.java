package com.koh.common.core.exception;

import lombok.Data;

/**
 * @author kohlarnhin
 * @create 2022/7/15 14:09
 */
@Data
public class BusinessException extends RuntimeException {

    private String msg;

    private String innerMsg;

    public BusinessException(String msg) {
        this.msg = msg;
    }

    public BusinessException(String msg, String innerMsg) {
        this.msg = msg;
        this.innerMsg = innerMsg;
    }
}
