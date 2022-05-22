package com.rtnet.fap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.rtnet.fap.common.FileUploadProperties;

@EnableConfigurationProperties({
    FileUploadProperties.class
})
@SpringBootApplication
public class FapTest1Application {

	public static void main(String[] args) {
		SpringApplication.run(FapTest1Application.class, args);
	}

}
