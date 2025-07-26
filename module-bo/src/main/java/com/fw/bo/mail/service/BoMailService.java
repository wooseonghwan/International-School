package com.fw.bo.mail.service;

import com.fw.core.dto.bo.BoMailDTO;
import com.fw.core.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoMailService {

    private final JavaMailSender javaMailSender;

    public String sendSimpleMessage(BoMailDTO boMailDTO) {

        File file = null;
        String authCode =  RandomUtil.createRandomText(6);
        try {
            MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true, "UTF-8");

            //필수정보 설정
            mimeMessageHelper.setSubject("인증번호 입니다"); //메일제목
            mimeMessageHelper.setText("인증번호 : " + authCode); //메일내용
            mimeMessageHelper.setFrom("softpuzzle0410@gmail.com"); //발신자
            mimeMessageHelper.setTo(boMailDTO.getEmail()); //수신자
            //mimeMessageHelper.setCc(); //참조자

            //메일 발송
            javaMailSender.send(mimeMailMessage);
        }
        catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return authCode;
    }
}
