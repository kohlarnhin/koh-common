package com.koh.common.core.entity;

import lombok.Data;

/**
 * @author kohlarnhin
 * @create 2022/7/11 14:01
 */
@Data
public class HeaderUser {

    private Long userId;

    private String userName;

    private String picture;

    private String phone;
}
