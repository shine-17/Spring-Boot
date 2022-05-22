package com.rtnet.fap.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {
	private Path fileLocation;
	
	public FileService() {
		this.fileLocation = null;
	}
	
    public FileService(FileUploadProperties fileUploadProperties) {
		this.fileLocation = Paths.get(fileUploadProperties.getLocation())
                .toAbsolutePath().normalize();
        
        try {
        	Files.createDirectories(this.fileLocation);
        } catch (Exception e) {
        	e.printStackTrace();
    	}
    }
    
    public String saveFile(MultipartFile file) {
    	String fileName = StringUtils.cleanPath(file.getOriginalFilename());
	  
		try {
			/* 실제 파일이 upload 되는 부분 */
			fileLocation = Paths.get("/fap/fap_1");
			Path targetLocation = this.fileLocation.resolve(fileName);
			
			String.format("File[%s]'s Location : %s", fileName, fileLocation.getRoot());
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		return fileName;
	}
    
    public Resource loadFile(String fileName) throws FileNotFoundException {
        try {
            Path file = this.fileLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(file.toUri());

            if(resource.exists() || resource.isReadable()) {
                return resource;
            }else {
                throw new FileNotFoundException("Could not find file");
            }
        } catch (MalformedURLException e) {
            throw new FileNotFoundException("Could not download file");
        }
    }
}
