package com.mkrana.recipe.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NumberFormatException.class)
	public ModelAndView handleNumberFormatException(Exception exception) {
		log.error("handleNumberFormatException", exception);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("400badrequest");
		mav.addObject("exception", exception);
		return mav;
	}

}
