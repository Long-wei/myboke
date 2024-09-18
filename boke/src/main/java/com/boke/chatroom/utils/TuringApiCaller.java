package com.boke.chatroom.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

//人机
@Component
public class TuringApiCaller implements CommandLineRunner {

    private  RestTemplate restTemplate = new RestTemplate();
    private String text;
    private String message;

    @Override
    public void run(String... args) throws Exception {
        if(text == null){
            return;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        // 构建请求体
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("reqType", 0);
        Map<String, Object> perception = new HashMap<>();
        perception.put("inputText", new HashMap<String, String>() {{
            put("text", text);
        }});
        requestBody.put("perception", perception);
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("apiKey", "4d54f32acc4545d39dcd65c9d6277ada");
        userInfo.put("userId", "111");
        requestBody.put("userInfo", userInfo);

        String jsonRequest = objectMapper.writeValueAsString(requestBody);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(jsonRequest, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://openapi.turingapi.com/openapi/api/v2",
                entity,
                String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            message = response.getBody();
            System.out.println(response.getBody());
        } else {
            System.out.println("Error: " + response.getStatusCode());
        }
    }

    public String start(String text) throws Exception {
        this.text = text;
        run();
        return message;
    }
}
