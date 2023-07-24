package com.koh.common.core.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author kohlarnhin
 * @create 2022/8/23 14:50
 */
@Data
public class HeaderManager implements Serializable {

    private Long managerId;

    private String managerName;

    private String managerPhone;

    private String picture;

    private String managerAddress;

    private String managerContact;
}
