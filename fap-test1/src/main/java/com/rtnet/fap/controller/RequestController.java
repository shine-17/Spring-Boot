package com.rtnet.fap.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.rtnet.fap.FapTest1Application;
import com.rtnet.fap.service.RequestService;

@RestController
public class RequestController {
	private static final Logger logger = LoggerFactory.getLogger(FapTest1Application.class);
	
	@Autowired
	RequestService requestService;
	
//	@RequestMapping(value = "/req/form")
//	public @ResponseBody String reqForm() {
//		logger.info("reqForm");
//		JsonNode node = requestService.requestHttpForm();
//		return node.toPrettyString();
//	}
	
	@RequestMapping(value="/req/test")
    public @ResponseBody String reqJson(){
		logger.info("reqTest");
        JsonNode node = requestService.requestHttpJson();
        
        if(node != null)
        	return node.toPrettyString();
        else
        	return null;
    }
}
