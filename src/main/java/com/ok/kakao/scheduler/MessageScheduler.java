package com.ok.kakao.scheduler;

import com.ok.kakao.service.HttpCallService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class MessageScheduler extends HttpCallService {

    @Scheduled(cron="0 44 18 * * ?")
    public void kakaoMessageScheduler() {

        String url = "https://kauth.kakao.com/oauth/authorize";

        HttpHeaders header = new HttpHeaders();
        header.set("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("client_id", "b9b7dfdc11fe3b0532649d4e9d23dcf4");
        parameters.add("response_type", "code");
        parameters.add("redirect_uri", "http://localhost:8080");
        parameters.add("scope", "profile_nickname,profile_image,friends,talk_message");
//        parameters.add("client_secret", "client secret"); 보안 사용경우에만

        HttpEntity<?> requestEntity = httpClientEntity(header, parameters);

        ResponseEntity<String> response = httpRequest(url, HttpMethod.GET, requestEntity);
        JSONObject jsonData = new JSONObject(response.getBody());
        System.out.println(jsonData);

    }
}
