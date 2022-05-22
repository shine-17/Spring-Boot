package com.rtnet.fap.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rtnet.fap.FapTest1Application;
import com.rtnet.fap.common.FileService;

@RestController
public class ResponseController {
	private static final Logger logger = LoggerFactory.getLogger(FapTest1Application.class);
	
	@Autowired
    private FileService fileService;
	
	@RequestMapping(value = "/testForForm", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> testForForm(@RequestParam Map<String, String> reqParam) {
		logger.info("testForForm/POST");
		
		for(Entry<String, String> entry : reqParam.entrySet()) {
			entry.setValue(entry.getValue() + "_testForForm");
		}
		
		return reqParam;
	}
	
	@RequestMapping(value="/testForJson", method = RequestMethod.POST /*, produces = {MediaType.APPLICATION_JSON_VALUE} */)
	public ResponseEntity<LinkedMultiValueMap<String, Object>> testForJson(MultipartFile file){
		try {
			logger.info("testForJson/POST");
			
			if(file.isEmpty())
				System.out.println(String.format("File[%s] is Empty. Return", file.getOriginalFilename()));

			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			String line;
			BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
			while ((line = br.readLine()) != null) {
				//System.out.println(line);
			}
			System.out.println("file : " + file.getOriginalFilename());
		 	
			String copyfileName = fileService.saveFile(file);
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
	                .path("/fap_1/")
	                .path(fileName)
	                .toUriString();

			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }

}
