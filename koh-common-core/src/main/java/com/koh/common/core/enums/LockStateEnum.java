package com.koh.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @author kohlarnhin
 * @create 2022/9/6 14:04
 */
@AllArgsConstructor
@NoArgsConstructor
public enum LockStateEnum {

    /**
     * 库存锁定状态
     */
    ALL(0, "查询所有"),
    LOCKED(1, "已锁定"),
    UNLOCK(2, "已解锁"),
    SUB(3, "扣减");

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
