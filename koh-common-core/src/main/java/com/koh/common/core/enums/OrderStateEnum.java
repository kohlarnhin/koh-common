package com.koh.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @author kohlarnhin
 * @create 2022/9/6 14:04
 */
@AllArgsConstructor
@NoArgsConstructor
public enum OrderStateEnum {

    /**
     * 订单状态
     */
    CREATING(-2, "创建中"),
    CANCEL(-1, "已取消"),
    ALL(0, "查询所有"),
    NO_PAY(1, "待支付"),
    NO_DELIVERY(2, "待配送"),
    DELIVERYING(3, "配送中"),
    NO_PICKED(4, "待取货"),
    PICKED(5, "已取货");

    private Integer id;

    public String value;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "OrderStateEnum{" +
                "id=" + id +
                ", value='" + value + '\'' +
                '}';
    }
}
