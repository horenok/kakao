package com.ok.kakao.service;

import com.ok.kakao.dto.DefaultMessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomMessageService {

    @Autowired
    MessageService messageService;

    public boolean sendMyMessage() {
        DefaultMessageDto myMsg = new DefaultMessageDto();
        myMsg.setBtnTitle("자세히보기");
        myMsg.setMobileUrl("");
        myMsg.setObjType("text");
        myMsg.setWebUrl("");
        myMsg.setText("카카오메세지 테스트 - ok");

        String accessToken = AuthService.authToken;

        return messageService.sendMessage(accessToken, myMsg);
    }
}