package com.koh.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

/**
 * 商品属性的种类
 *
 * @author 一杯清水
 */
@AllArgsConstructor
@NoArgsConstructor
public enum GoodsPropertyCategory {

    /**
     * 商品属性值
     */
    ENUM_jia_liao(1, "加料"),
    ENUM_temperature(2, "温度"),
    ENUM_tian_du(3, "甜度"),
    ENUM_kou_wei(4, "口味"),
    ENUM_size(5, "规格");

    private Integer id;

    public String value;

    public Integer getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public static String getValueById(Integer id) {
        return Stream.of(GoodsPropertyCategory.values()).filter(em -> em.getId().equals(id)).findFirst().get().getValue();
    }

    public static List<PropertyCategory> getListMap() {
        List<PropertyCategory> list = new LinkedList<>();
        for (GoodsPropertyCategory value : values()) {
            PropertyCategory propertyCategory = new PropertyCategory();
            propertyCategory.setId(value.getId());
            propertyCategory.setName(value.getValue());
            list.add(propertyCategory);
        }
        return list;
    }

    @Override
    public String toString() {
        return value;
    }

    @Data
    public static class PropertyCategory {
        public Integer id;

        public String name;
    }

}
