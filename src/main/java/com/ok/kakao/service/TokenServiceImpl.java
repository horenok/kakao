package com.ok.kakao.service;

import com.ok.kakao.entity.Token;
import com.ok.kakao.repository.TokenRepository;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService{
    private final TokenRepository tokenRepository;

    public TokenServiceImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void save(String accessToken, String refreshToken) {
        Token token = new Token();
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);

        tokenRepository.save(token);
    }

    @Override
    public String getAccessToken() {

        Token token = tokenRepository.getToken();

        return token.getAccessToken();
    }

    @Override
    public String getRefreshToken() {

        Token token = tokenRepository.getToken();

        return token.getRefreshToken();
    }
}
