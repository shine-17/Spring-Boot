package com.rtnet.fap.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.rtnet.fap.FapTest1Application;

@RestController
public class ResponseController {
	private static final Logger logger = LoggerFactory.getLogger(FapTest1Application.class);
	
	@Autowired
    private ResourceLoader resourceLoader;
	
	@RequestMapping(value = "/testForForm", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> testForForm(@RequestParam Map<String, String> reqParam) {
		logger.info("testForForm/POST");
		
		for(Entry<String, String> entry : reqParam.entrySet()) {
			entry.setValue(entry.getValue() + "_testForForm");
		}
		
		return reqParam;
	}
	
	@RequestMapping(value="/testForJson", method = RequestMethod.POST /*, produces = {MediaType.APPLICATION_JSON_VALUE} */)
	public ResponseEntity<Resource> testForJson(MultipartFile file){
		try {
			logger.info("testForJson/POST");

			String line;
			BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
			while ((line = br.readLine()) != null) {
				//System.out.println(line);
			}
			System.out.println("file : " + file.getName());
			
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }

}
