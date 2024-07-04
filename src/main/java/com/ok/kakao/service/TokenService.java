package com.ok.kakao.service;

import com.ok.kakao.entity.Token;

public interface TokenService {
    void save(String accessToken, String refreshToken);
    String getAccessToken();
    String getRefreshToken();
}
