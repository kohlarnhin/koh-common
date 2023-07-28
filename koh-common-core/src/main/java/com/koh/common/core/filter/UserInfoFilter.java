package com.koh.common.core.filter;

import com.alibaba.fastjson.JSONObject;
import com.koh.common.core.constant.Constants;
import com.koh.common.core.constant.DesConstant;
import com.koh.common.core.entity.HeaderManager;
import com.koh.common.core.entity.HeaderShop;
import com.koh.common.core.entity.HeaderUser;
import com.koh.common.core.utils.DesUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author kohlarnhin
 * @create 2022/7/13 15:59
 */
@Component
public class UserInfoFilter extends OncePerRequestFilter {
    public static ThreadLocal<HeaderShop> headerShopThreadLocal = new ThreadLocal<>();
    public static ThreadLocal<HeaderUser> headerUserThreadLocal = new ThreadLocal<>();
    public static ThreadLocal<HeaderManager> headerManagerThreadLocal = new ThreadLocal<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String headerShop = request.getHeader(Constants.HEADER_SHOP);
        String headerUser = request.getHeader(Constants.HEADER_USER);
        String headerManager = request.getHeader(Constants.HEADER_MANAGER);
        if (headerShop != null) {
            //解密
            String decrypt = DesUtil.decrypt(headerShop, DesConstant.DES_KEY);
            HeaderShop headerShopEntity = JSONObject.parseObject(decrypt, HeaderShop.class);
            headerShopThreadLocal.set(headerShopEntity);
        }
        if (headerUser != null) {
            //解密
            String decrypt = DesUtil.decrypt(headerUser, DesConstant.DES_KEY);
            HeaderUser headerUserEntity = JSONObject.parseObject(decrypt, HeaderUser.class);
            headerUserThreadLocal.set(headerUserEntity);
        }
        if (headerManager != null) {
            //解密
            String decrypt = DesUtil.decrypt(headerManager, DesConstant.DES_KEY);
            HeaderManager headerUserEntity = JSONObject.parseObject(decrypt, HeaderManager.class);
            headerManagerThreadLocal.set(headerUserEntity);
        }
        filterChain.doFilter(request, response);
    }
}
