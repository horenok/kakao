package com.ok.kakao.scheduler;

import com.ok.kakao.dto.DefaultMessageDto;
import com.ok.kakao.service.AuthService;
import com.ok.kakao.service.CustomMessageService;
import com.ok.kakao.service.HttpCallService;
import com.ok.kakao.service.MessageService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.Locale;

@Component
public class MessageScheduler extends HttpCallService {

    @Autowired
    AuthService authService;

    @Autowired
    MessageService messageService;

    @Autowired
    CustomMessageService customMessageService;

    @Scheduled(cron="0 43 16 * * ?")
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
    }

    @Scheduled(cron="0 44 16 * * ?")
    public void kakaoTestSendMessage() {
        String authToken = authService.authToken;
        if(!StringUtils.isEmpty(authToken)) {
            DefaultMessageDto msgDto = new DefaultMessageDto();

            msgDto.setBtnTitle("자세히보기");
            msgDto.setMobileUrl("");
            msgDto.setObjType("text");
            msgDto.setWebUrl("");
            msgDto.setText("스케줄러 테스트");

            messageService.sendMessage(authToken, msgDto);
        }
    }
}
