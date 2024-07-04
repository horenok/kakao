package com.ok.kakao.service;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


@Service
public class AuthService extends HttpCallService{
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String AUTH_URL = "https://kauth.kakao.com/oauth/token";

    @Autowired
    private final TokenService tokenService;

    public AuthService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public boolean getKakaoAuthToken(String code)  {
        HttpHeaders header = new HttpHeaders();
        String accessToken = "";
        String refrashToken = "";
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

        header.set("Content-Type", APP_TYPE_URL_ENCODED);

        parameters.add("code", code);
        parameters.add("grant_type", "authorization_code");
        parameters.add("client_id", "b9b7dfdc11fe3b0532649d4e9d23dcf4");
        parameters.add("redirect_url", "http://localhost:8080");
//        parameters.add("client_secret", "client secret"); 보안 사용경우에만

        HttpEntity<?> requestEntity = httpClientEntity(header, parameters);

        ResponseEntity<String> response = httpRequest(AUTH_URL, HttpMethod.POST, requestEntity);
        JSONObject jsonData = new JSONObject(response.getBody());
        accessToken = jsonData.get("access_token").toString();
        refrashToken = jsonData.get("refresh_token").toString();

        if(accessToken.isEmpty() || refrashToken.isEmpty()) {
            logger.debug("토큰발급에 실패했습니다.");
            return false;
        }else {
            tokenService.save(accessToken, refrashToken);
            return true;
        }
    }

    public boolean refreshToken(String code)  {
        HttpHeaders header = new HttpHeaders();
        String refrashToken = tokenService.getRefreshToken();
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

        header.set("Content-Type", APP_TYPE_URL_ENCODED);

        parameters.add("grant_type", "refresh_token");
        parameters.add("client_id", "b9b7dfdc11fe3b0532649d4e9d23dcf4");
        parameters.add("refresh_token", refrashToken);
//        parameters.add("client_secret", "client secret"); 보안 사용경우에만

        HttpEntity<?> requestEntity = httpClientEntity(header, parameters);

        ResponseEntity<String> response = httpRequest(AUTH_URL, HttpMethod.POST, requestEntity);
        JSONObject jsonData = new JSONObject(response.getBody());
        String accessToken = jsonData.get("access_token").toString();

        if(accessToken.isEmpty() || refrashToken.isEmpty()) {
            logger.debug("토큰발급에 실패했습니다.");
            return false;
        }else {
            tokenService.save(accessToken, refrashToken);
            return true;
        }
    }

}