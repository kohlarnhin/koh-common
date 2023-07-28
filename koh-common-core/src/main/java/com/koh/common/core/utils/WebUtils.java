package com.koh.common.core.utils;


import com.alibaba.fastjson.JSON;
import com.koh.common.core.constant.BizCodeEnum;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class WebUtils
{
    /**
     * 将字符串渲染到客户端
     * 
     * @param response 渲染对象
     * @param string 待渲染的字符串
     * @return null
     */
    public static String renderString(HttpServletResponse response, String string) {
        try
        {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 未登录
     *
     * @param response 渲染对象
     * @return null
     */
    public static String NoLogin(HttpServletResponse response) {
        try
        {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            R result = R.fail(BizCodeEnum.UNAUTHORIZED.getCode(), BizCodeEnum.UNAUTHORIZED.getMsg());
            String json = JSON.toJSONString(result);
            response.getWriter().print(json);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}