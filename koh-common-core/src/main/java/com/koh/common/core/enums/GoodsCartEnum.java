package com.koh.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @author kohlarnhin
 * @create 2022/10/12 15:48
 */
@AllArgsConstructor
@NoArgsConstructor
public enum GoodsCartEnum {

    /**
     * 订单状态
     */
    NORMAL(1, "正常"),
    UN_STOCK(2, "库存不足"),
    INVALID(3, "失效"),
    LOCKING(4, "锁定中");

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
