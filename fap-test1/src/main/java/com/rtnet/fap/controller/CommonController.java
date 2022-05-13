package com.rtnet.fap.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rtnet.fap.FapTest1Application;

@RestController
public class CommonController {
	private static final Logger logger = LoggerFactory.getLogger(FapTest1Application.class);
	
	@RequestMapping("/")
	public String root_test() {
		return "Hello Spring (/)";
	}
	
	@RequestMapping("/demo")
	public String root_demo() {
		return "Hello Spring (demo)";
	}

}
