package com.koh.common.core.valid;

import java.lang.annotation.*;

/**
 * @author kohlarnhin
 * @create 2022/3/17 17:26
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface RequireValid {

}
