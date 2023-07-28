package com.koh.common.core.entity;

import jakarta.validation.constraints.Min;
import lombok.Data;



/**
 * @author kohlarnhin
 * @create 2022/7/14 10:31
 */
@Data
public class CommonParamPage {

    @Min(value = 1,message = "当前页最小值是1")
    private int pageNo = 1;

    @Min(value = 1,message = "每页个数最小值是1")
    private int pageSize = 10;
}
