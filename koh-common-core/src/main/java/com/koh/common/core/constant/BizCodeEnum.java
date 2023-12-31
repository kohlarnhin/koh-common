package com.koh.common.core.constant;


/**
 * @author kohlarnhin
 */
public enum BizCodeEnum {

    /**
     * 用户未登录
     */
    UNAUTHORIZED(401, "用户未登录"),
    /**
     * 参数格式校验失败
     */
    VALID_EXCEPTION(10001, "参数格式校验失败");

    private int code;
    private String msg;

    BizCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
