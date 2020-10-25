package com.kingshuk.webservices.rest.controllers;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.kingshuk.webservices.rest.models.MyHelloWorldBean;

@RestController
public class RestFulController {

	@Autowired
	private MessageSource messageSource;

	@GetMapping(path = "/hello-world")
	public String helloWorld() {
		return "Hello Kingshuk Mukherjee";
	}

	@GetMapping(path = "/hello-world-bean")
	public MyHelloWorldBean helLoWorldBean() {
		return new MyHelloWorldBean("Kingshuk", "Mukherjee");
	}

	@GetMapping(path = "/hello-world-locale")
	public String helLoWorldLocale(@RequestHeader(name = HttpHeaders.ACCEPT_LANGUAGE,
									required = false) Locale locale) {
		return messageSource.getMessage("hello.world.message", null, locale).concat("Kingshuk Mukherjee");
	}

}