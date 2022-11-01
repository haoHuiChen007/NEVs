package com.example.nevs.exception;

import com.example.nevs.common.E;
import com.example.nevs.common.R;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 全局异常处理类
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public R ExceptionHandler(Exception e) {
		if (e instanceof GlobalException) {
			GlobalException ex = (GlobalException) e;
			return R.error(ex.getRespBeanEnum());
		} else if (e instanceof BindException) {
			BindException ex = (BindException) e;
			R responseBean = R.error(E.BIND_ERROR);
			responseBean.setMessage("参数校验异常：" + ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
			return responseBean;
		}
		return R.error(E.ERROR);
	}

}