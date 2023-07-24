package com.koh.common.core.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 门店表
 * </p>
 *
 * @author kohlarnhin
 * @since 2022-07-13
 */
@Data
public class HeaderShop implements Serializable {

    private Long shopId;

    private String siteId;

    private String shopName;

    private String shopPhone;

    private String picture;

    private String shopAddress;

    private String shopContact;
}
