package com.rtnet.fap.service;

import java.util.ArrayList;
import java.util.List;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rtnet.fap.common.FileCRC32Check;

@Component
public class RequestService {
	
//	public JsonNode requestHttpForm(){
//        HttpClient client = HttpClientBuilder.create().build();
//        HttpPost httpPost = new HttpPost("http://localhost:9001/testForForm");
//
//        try {
//
//            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
//            
//            nameValuePairs.add(new BasicNameValuePair("Email", "youremail"));
//            nameValuePairs.add(new BasicNameValuePair("Passwd", "yourpassword"));
//            nameValuePairs.add(new BasicNameValuePair("accountType", "GOOGLE"));
//            nameValuePairs.add(new BasicNameValuePair("source", "Google-cURL-Example"));
//            nameValuePairs.add(new BasicNameValuePair("service", "ac2dm"));
//
//            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//
//            HttpResponse response = client.execute(httpPost);
//
//            if (response.getStatusLine().getStatusCode() == 200) {
//                ResponseHandler<String> handler = new BasicResponseHandler();
//                String body = handler.handleResponse(response);
//                System.out.println("[RESPONSE] requestHttpForm() " + body);
//
//                ObjectMapper objectMapper = new ObjectMapper();
//                JsonNode node = objectMapper.readTree(body);
//
//                return node;
//
//            } else {
//                System.out.println("response is error : " + response.getStatusLine().getStatusCode());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
	
	public JsonNode requestHttpJson(){
		//DefaultHttpClient??? deprecated ?????? ????????? ????????? ???????????? client ??????
        HttpClient client = HttpClientBuilder.create().build(); // HttpClient ??????
        HttpPost httpPost = new HttpPost("http://localhost:9001/testForJson"); //POST ????????? URL ??????
        
        try {
            //httpPost.setHeader("Accept", "application/json"); // Accept : ?????????????????? ???????????? ?????? ????????? ?????? (application/json ?????? json ????????? ?????? ??? ????????? json ???????????? ?????????) 
            //httpPost.setHeader("Content-Type", "application/json; charset=UTF-8"); // Content-Type : ???????????? ????????? ??????
            //httpPost.setHeader("Content-Type" ,"multipart/form-data; boundary=" + boundary);
			
            // Test entity
//			String jsonString = "{\"Email\":\"dh.kim@rtnet.co.kr\",\"Passwd\":\"dhkim\",\"accountType\":\"GOOGLE\",\"source\":\"Google-cURL-Example\",\"service\":\"ac2dm\"}";
//			StringEntity entity = new StringEntity(jsonString);
//            entity.setContentType("application/json; charset=UTF-8");
            
            // File entity
            StringBuffer fileData = new StringBuffer();
            String rootPath = "C:\\fap";
            String fileName = "task1.txt";
            
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(rootPath + "\\" + fileName), "UTF-8"));
            System.out.println("File : " + rootPath + "\\" + fileName);
            
            char[] buf = new char[1024];
            int numRead = 0;
            
            while((numRead = br.read(buf)) != -1) {
            	fileData.append(buf, 0, numRead);
            }
            
            if(br != null)
            	br.close();
            
            StringEntity fileEntity = new StringEntity(fileData.toString());
            fileEntity.setContentType("multipart/form-data"); // MediaType.MULTIPART_FORM_DATA
            
            HttpEntity httpEntity = MultipartEntityBuilder.create()
                    .addTextBody("name", "test")
                    .addBinaryBody("file",
                    new File(rootPath + "\\" + fileName),
                    ContentType.MULTIPART_FORM_DATA,
                    fileName)
                    .build();
            
            
            // File CRC Check
//            FileCRC32Check fileCRC32Check = new FileCRC32Check();
//            HashMap<File, Long> map = fileCRC32Check.getResult("C:\\fap");
//            
//            Iterator<File> iter = map.keySet().iterator();
//            while(iter.hasNext()) {
//            	File item = iter.next();
//            	System.out.println(item + " : " + map.get(item));
//            }
            
            httpPost.setEntity(httpEntity); //json ????????? ??????

            HttpResponse response = client.execute(httpPost);

            //Response ??????
            if (response.getStatusLine().getStatusCode() == 200) {
                ResponseHandler<String> handler = new BasicResponseHandler();
                String body = handler.handleResponse(response);
                System.out.println("[RESPONSE] requestHttpJson() " + body);
                
                // File Upload ?????? -> CRC32 ?????? ??? ?????? ?????????(??????,??????,??????) ??????
                

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode node = objectMapper.readTree(body);

                return node;
            } else {
                System.out.println("response is error : " + response.getStatusLine().getStatusCode());
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
