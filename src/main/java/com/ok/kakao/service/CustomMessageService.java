package com.ok.kakao.service;

import com.ok.kakao.dto.DefaultMessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomMessageService {

    @Autowired
    MessageService messageService;

    @Autowired
    TokenService tokenService;

    public boolean sendMyMessage() {
        DefaultMessageDto myMsg = new DefaultMessageDto();
        myMsg.setBtnTitle("자세히보기");
        myMsg.setMobileUrl("");
        myMsg.setObjType("text");
        myMsg.setWebUrl("");
        myMsg.setText("카톡 나에게 보내기 테스트");

//        String accessToken = "9gNnsqon-4xkJ-C5mKbOJFgeVSX6DtAZAAAAAQo9dVoAAAGPnuumy9EMsmlHt4Ko"; // 테스트용 액세스토큰 하드코딩
        String accessToken = tokenService.getAccessToken();

        return messageService.sendMessage(accessToken, myMsg);
    }
}