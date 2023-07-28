package com.koh.common.core.utils;

import com.cfc.common.core.entity.HeaderManager;
import com.cfc.common.core.entity.HeaderShop;
import com.cfc.common.core.entity.HeaderUser;
import com.cfc.common.core.filter.UserInfoFilter;
import org.springframework.stereotype.Component;

/**
 * 获取用户信息
 * @author kohlarnhin
 * @create 2022/7/13 16:01
 */
@Component
public class UserInfoUtil {

    /**
     * 获取门店信息
     * @return
     */
    public HeaderShop getShop() {
        HeaderShop headerShop = UserInfoFilter.headerShopThreadLocal.get();
        UserInfoFilter.headerShopThreadLocal.remove();
        return headerShop;
    }

    /**
     * 获取用户端用户信息
     * @return
     */
    public HeaderUser getUser() {
        HeaderUser headerUser = UserInfoFilter.headerUserThreadLocal.get();
        UserInfoFilter.headerUserThreadLocal.remove();
        return headerUser;
    }

    /**
     * 获取管理员信息
     * @return
     */
    public HeaderManager getManager() {
        HeaderManager headerManager = UserInfoFilter.headerManagerThreadLocal.get();
        UserInfoFilter.headerManagerThreadLocal.remove();
        return headerManager;
    }
}
