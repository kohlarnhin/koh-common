package com.koh.common.core.exception;


import com.koh.common.core.constant.BizCodeEnum;
import com.koh.common.core.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kohlarnhin
 * @create 2022/3/18 15:54
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleValidException(MethodArgumentNotValidException e) {
        log.error("数据校验出现问题:{},异常类型:{}", e.getMessage(), e.getClass());
        BindingResult result = e.getBindingResult();
        Map<String, String> map = new HashMap<>();
        result.getFieldErrors().forEach(info -> {
            String message = info.getDefaultMessage();
            String field = info.getField();
            map.put(field, message);
        });
        return R.fail(BizCodeEnum.VALID_EXCEPTION.getCode(), BizCodeEnum.VALID_EXCEPTION.getMsg(),map);
    }

    @ExceptionHandler(value = BusinessException.class)
    public R handleBusinessException(BusinessException e) {
        return R.fail(201, e.getMsg());
    }
}
