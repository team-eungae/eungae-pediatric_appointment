package com.playdata.eungae.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {

	@ExceptionHandler(RuntimeException.class)
	public String RuntimeException(RuntimeException e,  Model model) {
		model.addAttribute("errorMessage", e.getMessage());
		return "error/500";
	}
}
